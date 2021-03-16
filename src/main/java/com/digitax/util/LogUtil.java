package com.digitax.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.xml.soap.Detail;

import javax.xml.soap.SOAPFault;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

//import org.springframework.context.ApplicationContext;

import com.digitax.exception.ErrorMessages;
import com.digitax.exception.ToolkitRuntimeException;
import com.digitax.mef.ApplicationContext;

public class LogUtil {
	private static final String _className = LogUtil.class.getName();

	//Prevent instantiation.
	private LogUtil() {}
	
	private static boolean initSucceeded = false;
	
	static {
		FileInputStream propsIn = null;
		try {
			validateLoggingConfig();
			propsIn = new FileInputStream(ApplicationContext.getLoggingConfigFile());
			LogManager.getLogManager().readConfiguration(propsIn);
			initSucceeded = true;
		} catch (SecurityException e) {
			String errMsg = ErrorMessages.MeFClientSDK000024.getErrorMessage() +
				"; " + _className + "; " + e.getMessage();
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(errMsg, e);
			tkre.setLogged(false);
			throw tkre;
		} catch (FileNotFoundException e) {
			String errMsg = ErrorMessages.MeFClientSDK000014.getErrorMessage() +
				"; " + _className + "; " + e.getMessage();
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(errMsg, e);
			tkre.setLogged(false);
			throw tkre;
		} catch (IOException e) {
			String errMsg = ErrorMessages.MeFClientSDK000029.getErrorMessage() +
				"; " + _className + "; " + e.getMessage();
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(errMsg, e);
			tkre.setLogged(false);
			throw tkre;
		} finally {
			if (propsIn != null) {
				try {
					propsIn.close();
				} catch (IOException ignore) {
					//This is not fatal or very consequential, continue.
				}
			}
		}
	}

	/**
	 * Get a Logger object for the given className.
	 * @param className The name of the Class for which the Logger object is being requested.
	 * @return Logger
	 */
	public static Logger getLogger(String className) {
		String methodName = "getLogger()";
		
		if (initSucceeded) {
			Logger returnVal = Logger.getLogger(className);
			if (returnVal.getLevel() == Level.OFF) {
				String msg = ErrorMessages.MeFClientSDK000022.getErrorMessage() +
							"; " + className + "; " + methodName;
				ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
				tkre.setLogged(false);
				throw tkre;
			}
			return returnVal;
		}
		String msg = ErrorMessages.MeFClientSDK000024.getErrorMessage() +
					 "; " + className + "; " + methodName;
		ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
		tkre.setLogged(false);
		throw tkre;
	}
	
	/**
	 * Formats a string containing the details of a SOAP fault.
	 * @param sf
	 * @return String - the SOAP fault details
	 */
	public static String getSOAPFaultDetails(SOAPFault sf) {
		if (sf != null) {
			StringBuilder logMsg = new StringBuilder();
			if (sf.getFaultString() != null) {
				logMsg.append("Fault String: ").append(sf.getFaultString()).append(" - ");
			}
			if (sf.getFaultCode() != null) {
				logMsg.append("Fault Code: ").append(sf.getFaultCode()).append(" - ");
			}
			Detail detail = sf.getDetail();
			if (detail != null) {
				Node faultDataNode = detail.getElementsByTagName("faultData").item(0);
				if (faultDataNode != null) {
					Source src = new DOMSource(faultDataNode);
					StringWriter sw = new StringWriter();
					javax.xml.transform.Result r = new StreamResult(sw);
					TransformerFactory tf = TransformerFactory.newInstance();
					try {
						Transformer t = tf.newTransformer();
						t.transform(src, r);
						logMsg.append("Detail: ").append(sw.getBuffer().toString());
					} catch (TransformerConfigurationException e) {
						logMsg.append("Fault detail not available.");
					} catch (TransformerException e) {
						logMsg.append("Fault detail not available.");
					}
				} else {
					// not all exceptions have faultData node -- for auditing, also
					// need to also get MeFException or A2AException nodes
					Node exceptionNode = detail.getFirstChild();
					if (exceptionNode != null) {
						Source src = new DOMSource(exceptionNode);
						StringWriter sw = new StringWriter();
						javax.xml.transform.Result r = new StreamResult(sw);
						TransformerFactory tf = TransformerFactory.newInstance();
						try {
							Transformer t = tf.newTransformer();
							t.transform(src, r);
							logMsg.append("Detail: ").append(sw.getBuffer().toString());
						} catch (TransformerConfigurationException e) {
							logMsg.append("Fault detail not available.");
						} catch (TransformerException e) {
							logMsg.append("Fault detail not available.");
						}						
					}
				}
			}
			return logMsg.toString();
		}
		return null;
	}
	
	/*
	 * By IRS requirement users of toolkit are not supposed to be allowed to run
	 * with toolkit logging completely disabled.
	 */
	private static void validateLoggingConfig() throws FileNotFoundException, IOException {
		Properties p = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(ApplicationContext.getLoggingConfigFile());
			p.load(fis);
			Set propKeys = p.keySet();
			Iterator it = propKeys.iterator();
			while (it.hasNext()) {
				String currentKey = (String) it.next();
				if (currentKey.toLowerCase().trim().endsWith(".level")) {
					if ("off".equalsIgnoreCase(p.getProperty(currentKey))) {
						String errMsg = ErrorMessages.MeFClientSDK000022.getErrorMessage() +
						"; " + _className;
						ToolkitRuntimeException tkre = new ToolkitRuntimeException(errMsg);
						tkre.setLogged(false);
						throw tkre;
					}
				}
			}
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					//Nothing else we can do, move on.
				}
			}
		}
	}

}
