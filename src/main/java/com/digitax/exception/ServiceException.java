package com.digitax.exception;

public class ServiceException extends Exception{
	private static final long serialVersionUID = -6100152082314073141L;
	private Level severity;
	private String type;
	private String errorClassification;
	private String errorCode;
	private String errorMessage;
	private String hostName;
	
	
	/**
	 * Constructor used by ETEC Transmitter service clients when an exception is encountered during
	 * remote service invocation.
	 * @param message A description indicating what may have caused this exception condition.
	 * @param cause The original exception which triggered this condition if any.
	 * @param severity A java.util.logging.Level in indicate the logging level for this
	 * 		  exception if this exception is to be logged.  If a constructor that does not
	 * 		  accept a Level argument is used to construct the exception, the Level will default to WARNING.
	 */
	public ServiceException(String message,
							gov.irs.a2a.mef.etectransmitterservice.ErrorExceptionDetail cause,
							Level severity) {
		this(message, (Throwable)cause, severity, cause.getFaultInfo());
	}

	/**
	 * Constructor used by ETEC Transmitter service clients when an exception is encountered during
	 * remote service invocation.
	 * @param message A description indicating what may have caused this exception condition.
	 * @param cause The original exception which triggered this condition if any.
	 * @param severity A java.util.logging.Level in indicate the logging level for this
	 * 		  exception if this exception is to be logged.  If a constructor that does not
	 * 		  accept a Level argument is used to construct the exception, the Level will default to WARNING.
	 */
	public ServiceException(String message,
							gov.irs.a2a.mef.etectransmitterservicemtom.ErrorExceptionDetail cause,
							Level severity) {
		this(message, (Throwable)cause, severity, cause.getFaultInfo());
	}
	/**
	 * Constructor used by State service clients when an exception is encountered during
	 * remote service invocation.
	 * @param message A description indicating what may have caused this exception condition.
	 * @param cause The original exception which triggered this condition if any.
	 * @param severity A java.util.logging.Level in indicate the logging level for this
	 * 		  exception if this exception is to be logged.  If a constructor that does not
	 * 		  accept a Level argument is used to construct the exception, the Level will default to WARNING.
	 */
	public ServiceException(String message,
							gov.irs.a2a.mef.mefstateservicemtom.ErrorExceptionDetail cause,
							Level severity) {
		this(message, (Throwable)cause, severity, cause.getFaultInfo());
	}
	/**
	 * Constructor used by State service clients when an exception is encountered during
	 * remote service invocation.
	 * @param message A description indicating what may have caused this exception condition.
	 * @param cause The original exception which triggered this condition if any.
	 * @param severity A java.util.logging.Level in indicate the logging level for this
	 * 		  exception if this exception is to be logged.  If a constructor that does not
	 * 		  accept a Level argument is used to construct the exception, the Level will default to WARNING.
	 */
	public ServiceException(String message,
							gov.irs.a2a.mef.mefstateservice.ErrorExceptionDetail cause,
							Level severity) {
		this(message, (Throwable)cause, severity, cause.getFaultInfo());
	}

	/**
	 * Constructor used by Transmitter service clients when an exception is encountered during
	 * remote service invocation.
	 * @param message A description indicating what may have caused this exception condition.
	 * @param cause The original exception which triggered this condition if any.
	 * @param severity A java.util.logging.Level in indicate the logging level for this
	 * 		  exception if this exception is to be logged.  If a constructor that does not
	 * 		  accept a Level argument is used to construct the exception, the Level will default to WARNING.
	 */
	public ServiceException(String message,
							gov.irs.a2a.mef.meftransmitterservice.ErrorExceptionDetail cause,
							Level severity) {
		this(message, (Throwable)cause, severity, cause.getFaultInfo());
	}


	/**
	 * Constructor used by Transmitter service clients when an exception is encountered during
	 * remote service invocation.
	 * @param message A description indicating what may have caused this exception condition.
	 * @param cause The original exception which triggered this condition if any.
	 * @param severity A java.util.logging.Level in indicate the logging level for this
	 * 		  exception if this exception is to be logged.  If a constructor that does not
	 * 		  accept a Level argument is used to construct the exception, the Level will default to WARNING.
	 */
	public ServiceException(String message,
			gov.irs.a2a.mef.meftransmitterservicemtom.ErrorExceptionDetail cause,
			Level severity) {
			this(message, (Throwable)cause, severity, cause.getFaultInfo());
}

	/**
	 * Constructor used by MSI service clients when an exception is encountered during
	 * remote service invocation.
	 * @param message A description indicating what may have caused this exception condition.
	 * @param cause The original exception which triggered this condition if any.
	 * @param severity A java.util.logging.Level in indicate the logging level for this
	 * 		  exception if this exception is to be logged.  If a constructor that does not
	 * 		  accept a Level argument is used to construct the exception, the Level will default to WARNING.
	 */
	public ServiceException(String message,
							gov.irs.a2a.mef.mefmsiservices.ErrorExceptionDetail cause,
							Level severity) {
		this(message, (Throwable)cause, severity, cause.getFaultInfo());
	}

	
	private ServiceException(String message, Throwable cause, Level severity, ErrorExceptionDetailType errorDetail) {
		super(message, cause);
		this.severity = severity;
		this.type = cause.getClass().getName();
		this.errorClassification = errorDetail.getErrorClassificationCd().value();
		this.errorCode = errorDetail.getErrorMessageCd();
		this.errorMessage = errorDetail.getErrorMessageTxt();
		this.hostName = errorDetail.getHostNm();
	}
	
	/**
	 * @return the current suggested logging Level for this exception.
	 */
	public Level getSeverity() {
		return this.severity;
	}

	/**
	 * @param severity the desired logging Level for this exception.  This is the
	 * 		  suggested logging level for the exception but the toolkit need not
	 *		  strictly adhere to the logging level set in the exception.
	 */
	public void setSeverity(Level severity) {
		this.severity = severity;
	}

	/**
	 * @return the Error Classification from the wrapped exception.
	 */
	public String getErrorClassification() {
		return this.errorClassification;
	}

	/**
	 * @return the Error Code from the wrapped exception.
	 */
	public String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * @return the Error Message from the wrapped exception.
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * @return the class name of the wrapped exception.
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * @return the hostname of the IRS server issuing the fault if available.
	 */
	public String getHostName() {
		return this.hostName;
	}

}
