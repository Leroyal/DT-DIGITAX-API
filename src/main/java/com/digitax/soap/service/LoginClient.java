package com.digitax.soap.service;

import gov.irs.a2a.mef.mefheader.MeFHeaderType;

public class LoginClient extends ServiceClient<LoginResult, LoginRequest>{
	private Login_Service myService;
	private Login myPort;
	
	/**
	 * Default constructor to create a new ETINRetrievalClient
	 * object.
	 */	
	public LoginClient() {
		this.myService = new Login_Service();
	}
	
	@Override
	protected LoginResponseHandler handleRequest(ServiceContext context, LoginRequest ctx)
			throws ServiceException {

		gov.irs.a2a.mef.mefmsiservices.ObjectFactory bodyObjects =
			new gov.irs.a2a.mef.mefmsiservices.ObjectFactory();

		MeFHeaderType header = getRequestHeader(getBindingProvider());
		Holder<MeFHeaderType> meFHeader = new Holder<MeFHeaderType>(header);
		LoginRequestType login = bodyObjects.createLoginRequestType();

		LoginResponseHandler responseHandler = null;
		try {
			responseHandler = doRequest(meFHeader, login);
		} catch (ErrorExceptionDetail e) {
			throw new ServiceException(e.getMessage(), e, Level.SEVERE);
		}
		return responseHandler;
	}
	
	protected LoginResponseHandler doRequest(Holder<MeFHeaderType> header,
										  LoginRequestType loginRequest)
													throws ErrorExceptionDetail {
		
		LoginResponseType response = getLogin().login(header, loginRequest);
		return new LoginResponseHandler(header.value, response);
	}

	@Override
	protected LoginResult invoke(ServiceContext context, LoginRequest requestArg)
	throws ToolkitException, ServiceException {
		return super.invoke(context, requestArg);
	}

	/**
	 * Login using a signing key stored in a File based KeyStore such as a JKS or PKCS12 KeyStore file.
	 * Suitable for use under both MS Windows and non-Windows based operating environments.
	 * @param context The service context.
	 * @param keyStoreFile The keystore containing your client certificate.
	 * @param keyStorePassword The keystore file password
	 * @param keyAlias The alias or "Friendly name" to the private key in the KeyStore that should be used for authentication.
	 * You may use also use
	 * {@link gov.irs.mef.services.util.KeyStoreUtil#listPrivateKeyAliases() KeyStoreUtil.listPrivateKeyAliases()}
	 * to obtain a list of key aliases contained within the KeyStore that may be suitable for use as the {@code alias}
	 * parameter to this method.
	 * @param keyPassword The private key password if different from the keyStorePassword.
	 * 					  If <tt>null</tt> is passed for this value it will be set to the  same value as keyStorePassword.
	 * @param keyStoreType The type of private key store file such as JKS or PKCS12.  If <tt>null</tt>
	 * 					   is passed for this value it will be set to java.security.KeyStore.getDefautlType()
	 *					   (normally resolves to "JKS").
	 * @return LoginResult an object encapsulating the results of the service invocation.
	 * @throws ToolkitException
	 * @throws ServiceException
	 */
	public LoginResult invoke(ServiceContext context,
								File keyStoreFile,
								String keyStorePassword,
								String keyAlias,
								String keyPassword,
								String keyStoreType)
		throws ToolkitException, ServiceException {
		
		LoginRequest req = new LoginRequest(keyStoreFile,
											keyStorePassword,
											keyAlias,
											keyPassword,
											keyStoreType);
		return invoke(context, req);
	}
	
	/**
	 * This method is intended for use by users of the MS Windows operating system.
	 * Login using a private signing key found in the Windows personal KeyStore repository.
	 * You may use also use
	 * {@link gov.irs.mef.services.util.KeyStoreUtil#listPrivateKeyAliases() KeyStoreUtil.listPrivateKeyAliases()}
	 * to obtain a list of key aliases contained within the KeyStore that may be suitable for use as the {@code alias}
	 * parameter to this method.
	 * @param context The service context.
	 * @param alias The alias or "Friendly name" to the private key in the KeyStore that should be used for authentication.
	 * @return LoginResult object encapsulating the results of the service invocation.
	 * @throws ToolkitException
	 * @throws ServiceException
	 * @since 2012v3.0
	 */
	public LoginResult invoke(ServiceContext context, String alias)
		throws ToolkitException, ServiceException {

		LoginRequest req = new LoginRequest(alias);
		return invoke(context, req);
	}
	
	/**
	 * Login using a signing key stored in a File based KeyStore such as a JKS or PKCS12 KeyStore file.
	 * Suitable for use under both MS Windows and non-Windows based operating environments.
	 * @param context The service context.
	 * @param keyStoreFile The keystore containing your client certificate.
	 * @param password The keystore file password
	 * @param alias The alias or "Friendly name" to the private key in the KeyStore that should be used for authentication.
	 * You may use also use
	 * {@link gov.irs.mef.services.util.KeyStoreUtil#listPrivateKeyAliases() KeyStoreUtil.listPrivateKeyAliases()}
	 * to obtain a list of key aliases contained within the KeyStore that may be suitable for use as the {@code alias}
	 * parameter to this method.
	 * @return LoginResult object encapsulating the results of the service invocation.
	 * @throws ToolkitException
	 * @throws ServiceException
	 */
	public LoginResult invoke(ServiceContext context,
			File keyStoreFile,
			String password,
			String alias)
		throws ToolkitException, ServiceException {

		LoginRequest req = new LoginRequest(keyStoreFile,
											password,
											alias);
		return invoke(context, req);
	}

	@Override
	protected final Service getService() {
		return this.myService;
	}

	@Override
	/*
	 * Note, once getLogin() is called, it is too late to set the handler resolver.
	 * The handler resolver must be set prior to creating the Login object or it will
	 * not take effect.
	 */
	protected final BindingProvider getBindingProvider() {
		return (BindingProvider) getLogin();
	}
	
	private synchronized Login getLogin() {
		if (this.myPort == null) {
			this.myPort = this.myService.getLogin();
		}
		return this.myPort;
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	protected final HandlerResolver setupHandlerChain(List<Handler> aHandlerChain,
													  ServiceContext svcContext,
													  LoginRequest request) {
		//If the port is alreayd established, setting the handler chaing has no effect.
		if (this.myPort != null) {
			return null;
		}
		return new LoginHandlerResolver(svcContext, request, aHandlerChain);
	}
	
	private static class LoginHandlerResolver extends BaseHandlerResolver {

		@SuppressWarnings("unchecked")
		LoginHandlerResolver (ServiceContext context, LoginRequest request, List<Handler> chain) {
			super(chain);
			this.handlerChain.add(new LoginSecurityHeaderHandler(context, request));
		}
	}

	@Override
	protected String getEndpointURL(ServiceContext context) {
		return ServiceEndpoints.LOGIN.getEndpoint(context.getTestCdType());
	}
	
}
