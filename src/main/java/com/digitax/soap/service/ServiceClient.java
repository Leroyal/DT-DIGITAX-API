package com.digitax.soap.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;

import com.digitax.exception.ErrorMessages;
import com.digitax.exception.ToolkitException;
import com.digitax.mef.ApplicationContext;
import com.digitax.mef.validation.ValidationErrors;
import com.digitax.util.LogUtil;

public class ServiceClient <R extends Result, T extends Request>{
	private Time readTimeout = Time.READ;
	private Timer connectTimeout = Time.CONNECT;
	private BindingProvider bp;
	protected Logger logger;
	private final String className = getClass().getName();
	private static boolean CHUNKING_ENABLED = true;
	private static final int DEFAULT_CHUNK_SIZE = 8192;
	private static int CONFIG_CHUNK_SIZE = DEFAULT_CHUNK_SIZE;
	private static final String baseServiceClassName = ServiceClient.class.getName();
	private static Logger baseLogger = LogUtil.getLogger(baseServiceClassName);
	//The key under which the MeFHeader will be inserted into the Message Context.
	private static final String MEF_HEADER_KEY = "MeF.header";
	
	static {
		File transportConfig = new File(ApplicationContext.getConfigDir(), "transport.properties");
		FileInputStream configIn = null;
		try {
			configIn = new FileInputStream(transportConfig);
			Properties p = new Properties();
			p.load(configIn);
			String chunkingOn = p.getProperty("http.chunking.enabled");
			CHUNKING_ENABLED = Boolean.parseBoolean(chunkingOn);
			if (CHUNKING_ENABLED) {
				String chunkingSize = p.getProperty("http.chunk.size");
				try {
					int cs = Integer.parseInt(chunkingSize);
					if (cs > 0) {
						CONFIG_CHUNK_SIZE = cs;
					}
				} catch (NumberFormatException nfe) {
					String msg = ErrorMessages.MeFClientSDK000029.getErrorMessage() +
					" NumberFormatException trying to get HTTP chunk size from transport.properties. " +
					"Using default values instead. Exception message follows; " +
					ServiceClient.class.getName() + "; " + nfe.getMessage();
					baseLogger.warning(msg);
				}
			}
		} catch (IOException ioe) {
			String msg = ErrorMessages.MeFClientSDK000029.getErrorMessage() +
			" Could not load transport config. Using defaults."  +
			"Using default values instead. Exception message follows; " +
			ServiceClient.class.getName() + "; " + ioe.getMessage();
			baseLogger.warning(msg);
		} finally {
			if (configIn != null) {
				try {
					configIn.close();
				} catch (Exception ignore) {/*done all we can.*/}
			}
		}
	}

	protected ServiceClient() {
		this.logger = LogUtil.getLogger(this.className);
	}

	@SuppressWarnings("unchecked")
	protected synchronized R invoke(ServiceContext context, T request) throws ToolkitException, ServiceException {
		final String methodName = "invoke()";
		baseLogger.entering(baseServiceClassName, methodName);
		R responseContainer = null;
		try {
			baseLogger.info("Beginning request validation phase.");
			ValidationErrors validationErrors = request.validate();
			if (validationErrors != null && !validationErrors.isEmpty()) {
				handleRequestValidationErrors(validationErrors);
				String validationFailMsg = ErrorMessages.MeFClientSDK000039.getErrorMessage() +
					"; " + this.className + "; " + methodName;
				ToolkitException tke = new ToolkitException(validationFailMsg);
				this.logger.log(Level.WARNING, validationFailMsg, tke);
				throw tke;
			}

			/*Only want to set Handler Resolver on the inital creation of this object.
			 *Further subsequent invocations will have no effect.
			 */
			if (this.bp == null) {
				baseLogger.info("Setting up service client JAX-WS handler chain.");
				List<Handler> handlerChain = new ArrayList<Handler>();
				Service aService = getService();
				HandlerResolver resolver = setupHandlerChain(handlerChain, context, request);
				if (resolver != null) {
					if (resolver instanceof BaseHandlerResolver) {
						BaseHandlerResolver bhr = (BaseHandlerResolver) resolver;
						/*The handler resolver returned from setupHandlerChain might
						 *not contain the same List instance we passed in to the
						 *setupHandlerChain method so get the list from the resolver
						 *rather than adding these handlers directly to 'handlerChain'. 
						 */
						List<Handler> bhrList = bhr.getChain();
						if(bhrList != null) {
							bhrList.add(new SessionCookieHandler(context, request));
							bhrList.add(new XOPAttachmentHandler());
							bhrList.add(new MessageLogHandler(request));
						}
					}
					aService.setHandlerResolver(resolver);
				}
			}

			this.bp = getBindingProvider();
			setupTransportProperties();
			baseLogger.info("Constructing MeF request message header.");
			buildMeFHeader(context, request);
			setInvocationEndpoint(context);
			AuditImpl.getInstance().logRequest(request, context);
			baseLogger.info("Beginning service invocation.");
			ResponseHandler<R> responseHandler = handleRequest(context, request);
			if (responseHandler instanceof AttachmentResponseHandler) {
				baseLogger.info("Service invocation completed. " +
								"Now attempting to save response attachment to file system.");
				((AttachmentResponseHandler<R>) responseHandler).saveAttachment();
			}
			baseLogger.info("Begin response handling phase.");
			Builder<R> respContainerBuilder = responseHandler.handleResponse(context);
			baseLogger.info("Constructing response object.");
			responseContainer = respContainerBuilder.build();
			AuditImpl.getInstance().logResponse(request, responseContainer, context);
		} catch (ToolkitException tke) {
			//Assumedly already logged at point of instantiation before being thrown which should
			//be the place the error actually occured.  Don't want it to be caught by
			//Throwable catch block at the end or it will be logged again so just rethrow here.
			throw tke;
		} catch (ServiceException se) {
			StringBuilder logMsg = getLogMsg(request).append(getServiceExceptionInfo(se));
			this.logger.log(se.getSeverity(), logMsg.toString(), se);			
			throw se;
		} catch (SOAPFaultException sfe) {
			SOAPFault sf = sfe.getFault();
			logSOAPFault(sf, request);
			throw new ToolkitRuntimeException("SOAPFaultException", sfe);
		} catch (WebServiceException wse) {
			String msg = ErrorMessages.MeFClientSDK000029.getErrorMessage() +
			"; " + this.className + "; " + methodName + "; " + wse.getMessage() + getMessage(wse);
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg, wse);
			this.logger.log(Level.SEVERE, msg, tkre);
			//capture HTTP errors (i.e. 401) etc... in audit log
			AuditImpl.getInstance().logError(request, wse.getMessage());
			throw tkre;
		} catch (ToolkitRuntimeException tkre) {
			//If the exception could not be logged when it was created for whatever reason, log it here.
			if (!tkre.isLogged()) {
				String msg = ErrorMessages.MeFClientSDK000029.getErrorMessage() +
				"; " + this.className + "; " + methodName + "; " + tkre.getMessage() + getMessage(tkre);
				this.logger.log(Level.SEVERE, msg, tkre);
			}
			throw tkre;
		} catch (RuntimeException rte){
			String msg = ErrorMessages.MeFClientSDK000029.getErrorMessage() +
			"; " + this.className + "; " + methodName + "; " + rte.getMessage() + getMessage(rte);
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg, rte);
			this.logger.log(Level.SEVERE, msg, tkre);
			throw tkre;
		} catch (Throwable t) {
			String msg = ErrorMessages.MeFClientSDK000029.getErrorMessage() +
				"; " + this.className + "; " + methodName + "; " + t.getMessage();
			ToolkitException tke = new ToolkitException(msg, t);
			this.logger.log(Level.SEVERE, tke.getMessage() + getMessage(t), tke);
			throw tke;
		} finally {
			baseLogger.exiting(baseServiceClassName, methodName);
		}
		
		return responseContainer;
	}
	
	private void setupTransportProperties() {
		if (this.bp != null) {
			final int milliSeconds = 1000;
			Map<String,Object> jaxwsRequestContext = this.bp.getRequestContext();
			if (ServiceClient.CHUNKING_ENABLED) {
				jaxwsRequestContext.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE,
										ServiceClient.CONFIG_CHUNK_SIZE);
			}
			jaxwsRequestContext.put(JAXWSProperties.CONNECT_TIMEOUT,
									(this.connectTimeout.getSeconds() * milliSeconds));
			jaxwsRequestContext.put(JAXWSProperties.REQUEST_TIMEOUT,
									(this.readTimeout.getSeconds() * milliSeconds));
		}
	}

	protected abstract ResponseHandler<R> handleRequest(ServiceContext context, T request)
											 throws ToolkitException, ServiceException;

	protected abstract Service getService();

	protected abstract BindingProvider getBindingProvider();
	
	protected abstract String getEndpointURL(ServiceContext context);
	
	/*
	 * This is protected instead of private intentionally.  The default implementation
	 * will handle setting the JSESSIONID HTTP cookie header only
	 * @param aService
	 * @param aContext
	 */
	
	@SuppressWarnings("unchecked")
	protected HandlerResolver setupHandlerChain(List<Handler> aHandlerChain, ServiceContext aContext, T request) {
		/* Once the BindingProvider has been created that means the port on the
		 * underlying JAX-WS client proxy instance has already been created.  At
		 * that point it is too late in the lifecycle to set the handler chain.
		 */
		if (this.bp != null) {
			return null;
		}
		aHandlerChain.add(new BasicA2ASecurityHeaderHandler(aContext));
		return new BaseHandlerResolver(aHandlerChain);
	}

	protected MeFHeaderType getRequestHeader(BindingProvider binding) {
		if (binding != null) {
			Map<String,Object> jaxwsRequestContext = binding.getRequestContext();
			return (MeFHeaderType) jaxwsRequestContext.get(ServiceClient.MEF_HEADER_KEY);
		}
		return null;
	}

	private void buildMeFHeader(ServiceContext context, Request req)
		throws ToolkitException {
		
		Map<String,Object> jaxwsRequestContext = this.bp.getRequestContext();
		String action = (String) jaxwsRequestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		req.setAction(action);
		MeFHeaderType requestHeader = req.buildMeFRequestHeader(context);
		baseLogger.info("The request Message ID was generated: " + requestHeader.getMessageID() + " at " +
						requestHeader.getMessageTs() + " for service endpoint invocation " + action);
		jaxwsRequestContext.put(ServiceClient.MEF_HEADER_KEY, requestHeader);
	}
	
	/*
	 * Try to get a configured endpoint URL String from the subclass.
	 * If it isn't specified just use the one from the WSDL.
	 */
	private void setInvocationEndpoint(ServiceContext context) throws ToolkitException {
		String methodName = "setInvocationEndpoint()";
		Map<String,Object> jaxwsRequestContext = this.bp.getRequestContext();
		String newEndpointValue = getEndpointURL(context);
		if (newEndpointValue != null && !"".equals(newEndpointValue.trim())) {
			if (newEndpointValue.startsWith("http://")) {
				this.logger.warning("Non secure http protocol was specified. Forcing to https://");
				StringBuilder tmp = new StringBuilder(newEndpointValue);
				tmp.insert(4, 's');
				newEndpointValue = tmp.toString();
			}
			if (!newEndpointValue.startsWith("https://")) {
				newEndpointValue = "https://" + newEndpointValue;
			}
			try {
				new URL(newEndpointValue);
			} catch (MalformedURLException e) {
				String msg = ErrorMessages.MeFClientSDK000019.getErrorMessage() +
					"; " + this.className + "; " + methodName + "; " + e.getMessage();
				ToolkitException tke = new ToolkitException(msg, e);
				this.logger.log(Level.SEVERE, msg, tke);
				throw tke;
			}
			baseLogger.info("Invocation endpoint was set to: " + newEndpointValue);
			jaxwsRequestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newEndpointValue);
		}
	}

	private void handleRequestValidationErrors(ValidationErrors errorList) {
		baseLogger.warning("The request message failed validation with the following results:");
		for (ValidationError error : errorList) {
			baseLogger.warning(error.getMessage());
		}
	}
	
	private StringBuilder getMessage(Throwable t) {
		StringBuilder msg = new StringBuilder(" Message: ").append(t.getMessage());
		if (t.getCause() != null) {
			msg.append(t.getCause().getMessage());
		}
		return msg;
	}
	
	private void logSOAPFault(SOAPFault sf, Request req) {
		if (sf != null) {
			StringBuilder logMsg = new StringBuilder(getLogMsg(req));
			String logDetails = LogUtil.getSOAPFaultDetails(sf);
			if (logDetails != null) {
				logMsg.append(logDetails);
			}
			baseLogger.log(Level.SEVERE, logMsg.toString(), sf);			
		}
	}

	private String getServiceExceptionInfo(ServiceException se) {
		StringBuilder outStr = new StringBuilder();
		if (se.getMessage() != null) {
			outStr.append("ServiceException message - ").append(se.getMessage()).append(ApplicationContext.NEWLINE);
		}
		if (se.getType() != null) {
			outStr.append("Wrapped exception type - ").append(se.getType()).append(ApplicationContext.NEWLINE);
		}
		if (se.getErrorClassification() != null) {
			outStr.append("Error Classification - ").append(se.getErrorClassification()).append(ApplicationContext.NEWLINE);
		}
		if (se.getErrorCode() != null) {
			outStr.append("MeF Error Code - ").append(se.getErrorCode()).append(ApplicationContext.NEWLINE);
		}
		if (se.getErrorMessage() != null) {
			outStr.append("MeF Error Message - ").append(se.getErrorMessage()).append(ApplicationContext.NEWLINE);
		}
		return outStr.toString();
	}

	private StringBuilder getLogMsg(Request request) {
		StringBuilder logMsg = new StringBuilder("Error during service invocation");
		MeFHeaderType header = request.getMeFRequestHeader();
		if (header != null) {
			String msgId = header.getMessageID();
			if (msgId != null) {
				logMsg.append(" for Message ID ").append(msgId);
			}
		}
		logMsg.append(": ");
		return logMsg;
	}

	/**
	 * @return The message ID associated with the most recently invoked service
	 * 		   client proxy instance.  Since Message ID generation does not take place until
	 * 		   such time as invoke() is called on a given service client, this method
	 * 		   will return <tt>null</tt> if invoked before to invoke() has returned.
	 */
	public String getMessageID() {
		MeFHeaderType header = getRequestHeader(this.bp);
		if (header != null) {
			return header.getMessageID();
		}
		return null;
	}

	/**
	 * Specify the request read timeout in seconds.
	 * @param readTimeout The request read timeout specified in seconds.
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout.setSeconds(readTimeout);
	}

	/**
	 * Specify the request connect timeout in seconds.
	 * @param connectTimeout The request connect timeout specified in seconds.
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout.setSeconds(connectTimeout);
	}


}
