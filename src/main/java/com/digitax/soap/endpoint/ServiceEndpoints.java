package com.digitax.soap.endpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.digitax.exception.ErrorMessages;
import com.digitax.exception.ToolkitRuntimeException;
import com.digitax.mef.ApplicationContext;
import com.digitax.util.LogUtil;

public enum ServiceEndpoints {
	GET_2290_SCHEDULE_1S_BY_MSG_ID,
	GET_2290_SCHEDULE_1S_BY_MSG_ID_MTOM,
	
	GET_2290_SCHEDULE_1S,
	GET_2290_SCHEDULE_1S_MTOM,
	
	GET_2290_SCHEDULE_1,
	GET_2290_SCHEDULE_1_MTOM,
	
	GET_ACK,
	GET_ACK_MTOM,
	
	GET_ACK_NOTIFICATION,
	GET_ACK_NOTIFICATION_MTOM,
	
	GET_ACK_NOTIFICATIONS,
	GET_ACK_NOTIFICATIONS_MTOM,
	
	GET_ACKS_BY_MSG_ID,
	GET_ACKS_BY_MSG_ID_MTOM,
	
	GET_ACKS,
	GET_ACKS_MTOM,
	
	GET_NEW_2290_SCHEDULE_1S,
	GET_NEW_2290_SCHEDULE_1S_MTOM,
	
	GET_NEW_ACK_NOTIFICATIONS,
	GET_NEW_ACK_NOTIFICATIONS_MTOM,
	
	GET_NEW_ACKS,
	GET_NEW_ACKS_MTOM,
	
	GET_NEW_SUBMISSIONS,
	GET_NEW_SUBMISSIONS_MTOM,
	
	GET_NEW_SUBMISSIONS_STATUS,
	GET_NEW_SUBMISSIONS_STATUS_MTOM,
	
	GET_SUBMISSION,
	GET_SUBMISSION_MTOM,
	
	GET_SUBMISSIONS_BY_MSG_ID,
	GET_SUBMISSIONS_BY_MSG_ID_MTOM,
	
	GET_SUBMISSIONS,
	GET_SUBMISSIONS_MTOM,
	
	GET_SUBMISSIONS_STATUS,
	GET_SUBMISSIONS_STATUS_MTOM,
	
	GET_SUBMISSION_STATUS,
	GET_SUBMISSION_STATUS_MTOM,
	
	SEND_SUBMISSIONS,
	SEND_SUBMISSIONS_MTOM,
	
	ETIN_RETRIEVAL,
	
	ETIN_STATUS,
	
	GET_STATE_PARTICIPANTS_LIST,
	
	GET_SUBMISSION_RECONCILIATION_LIST,
	GET_SUBMISSION_RECONCILIATION_LIST_MTOM,
	
	
	GET_VIN_DATA,
	
	LOGIN,
	

	LOGOUT,
	
	SEND_ACKS,
	SEND_ACKS_MTOM,
	
	SEND_SUBMISSION_RECEIPTS,
	SEND_SUBMISSION_RECEIPTS_MTOM;
	
	private static final Properties atsProps = new Properties();
	private static final Properties prdProps = new Properties();
	private static Logger logger = LogUtil.getLogger(ServiceEndpoints.class.getName());
	static {
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(
					new File(ApplicationContext.getConfigDir(),
							 "ats_endpoints.properties"));
			atsProps.load(inStream);
			inStream.close();
			inStream = new FileInputStream(
				new File(ApplicationContext.getConfigDir(),
						 "prd_endpoints.properties"));
			prdProps.load(inStream);
			inStream.close();
		} catch (FileNotFoundException e) {
			String msg = ErrorMessages.MeFClientSDK000020.getErrorMessage() +
				"; " + ServiceEndpoints.class.getName() + "; " + e.getMessage();
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg, e);
			logger.log(Level.SEVERE, msg, tkre);
			throw tkre;
		} catch (IOException e) {
			String msg = ErrorMessages.MeFClientSDK000026.getErrorMessage() +
				"; " + ServiceEndpoints.class.getName() + "; " + e.getMessage();
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg, e);
			logger.log(Level.SEVERE, msg, tkre);
			throw tkre;
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (Exception ignore) {/*done all we can*/}
			}
		}
	}
	
	/**
	 * @param indicator indicator for desired Test or Production endpoint URL.
	 * @return The URL string associated with the given TestIndicatorType.
	 * 		   If TestIndicatorType.P is given, the returned URL String will be
	 * 		   taken from the prd_endpoints.properties file.  If TestIndicatorType.T is 
	 * 		   given, the returned URL String will be taken from the ats_endpoints.properties
	 * 		   file.
	 */
	public String getEndpoint(gov.irs.a2a.mef.mefheader.TestCdType indicator) {
		String returnMe = null;
		switch (indicator) {
			case P:
				returnMe = prdProps.getProperty(name());
				break;
			case T:
				returnMe = atsProps.getProperty(name());
				break;
			default:
				break;
		}
		return returnMe;
	}

}
