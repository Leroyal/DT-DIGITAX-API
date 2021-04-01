package com.digitax.soap.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.digitax.exception.ErrorMessages;
import com.digitax.exception.ToolkitRuntimeException;
import com.digitax.mef.ApplicationContext;
import com.digitax.service.data.ETIN;
import com.digitax.util.LogUtil;

import gov.irs.a2a.mef.mefheader.TestCdType;

public class ServiceContext {
	private static Logger logger = LogUtil.getLogger(ServiceContext.class.getName());
	private SessionInfo sessionInfo;

	//Fields that can go in MeFHeader.
	private ETIN etin;
	private String appSysID;
	private TestCdType testCd;
	private String clientSoftwareTxt = "MeFA2AJavaToolkit" + ApplicationContext.TOOLKIT_VERSION ;
	
	private static final String className = ServiceContext.class.getName();

	/**
	 * Constructs a ServiceRequestContext with default values for some MeF Header
	 * fields. 
	 *
	 * Default header values are as follows:
	 *  Session indicator - Yes
	 *  TestIndicator - Test
	 *  
	 * @param etin Implicitly required by all MeF requests because it is used
	 * 				 as part of Message ID generation.
	 * @param appSysID Application System ID of user as assigned during the MeF automated
	 * 					 enrollment process.  Required by all MeF Requests
	 * @param testOrProd TestCdType.T if this is to be a test message or
	 * 					 TestCdType.P if this is to be a production message.
	 * 					 If null, will default to TestIndicatorType.T.
	 * @throws ToolkitRuntimeException if appSysID value is empty or null.
	 */
	public ServiceContext(ETIN etin, String appSysID, TestCdType testOrProd) {
		this.etin = etin;
		setAppSysID(appSysID);
		this.testCd = testOrProd;
		if (this.testCd == null) {
			this.testCd = TestCdType.T;
		}
		this.sessionInfo = new SessionInfo();
	}

	/**
	 * @return appSysID
	 */
	public String getAppSysID() {
		return this.appSysID;
	}

	/**
	 * @param appSysID
	 * @throws ToolkitRuntimeException if appSysID value is empty or null.
	 */
	public void setAppSysID(String appSysID) {
		String methodName = "setAppSysID()";
		if (appSysID == null || "".equals(appSysID.trim())) {
			String msg = ErrorMessages.MeFClientSDK000032.getErrorMessage() +
				"; " + className + "; " + methodName;
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
			logger.log(Level.SEVERE, msg, tkre);
			throw tkre;
		}
		this.appSysID = appSysID;
	}

	/**
	 * @return ETIN
	 */
	public ETIN getEtin() {
		return this.etin;
	}

	/**
	 * @param etin
	 */
	public void setEtin(ETIN etin) {
		this.etin = etin;
	}

	/**
	 * @return SessionInfo
	 */
	public SessionInfo getSessionInfo() {
		return this.sessionInfo;
	}

	/**
	 * @param sessionInfo
	 */
	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	/**
	 * @return TestIndicatorType
	 */
	public TestCdType getTestCdType() {
		return this.testCd;
	}

	/**
	 * @param testCd
	 */
	public void setTestCdType(TestCdType testCd) {
		this.testCd = testCd;
	}
	
	/**
	 * @return clientSoftwareTxt
	 */
	public String getClientSoftwareTxt() {
		return this.clientSoftwareTxt;
	}

	/**
	 * @param aClientSoftwareTxt
	 */
	public void setClientSoftwareTxt(String aClientSoftwareTxt) {
		this.clientSoftwareTxt = aClientSoftwareTxt;
	}

	
}
