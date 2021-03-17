package com.digitax.service.data;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.digitax.exception.ErrorMessages;
import com.digitax.exception.ToolkitRuntimeException;
import com.digitax.util.LogUtil;

public class MessageID {
	private static final String className = MessageID.class.getName();
	private static Logger logger = LogUtil.getLogger(className);
	private ETIN etin;
	private StringBuilder randomPart;
	private StringBuilder value;
	private GregorianCalendar today;
	
	
	public static final String MESSAGE_ID_RESTRICTION = "[0-9]{12}[a-z0-9]{8}";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyDDD");
	private static final int sequenceLength = 8;
	private static final int msgIDLength = 20;
	
	
	public MessageID(ETIN anETIN) {

		this();
		this.etin = anETIN;
		init();
	}
	
	
	public MessageID(String aMessageID) {

		this();
		setValue(aMessageID);
	}

	private MessageID() {
		this.today = new GregorianCalendar();
	}

	
	public String getValue() {
		return this.value.toString();
	}

	
	public void setValue(String aMessageID) {
		String methodName = "setValue()";
		if (MessageID.isValidMessageID(aMessageID)) {
			this.setEtin(new ETIN(aMessageID.substring(0, 5)));
			this.value = new StringBuilder(aMessageID);
			return;
		}
		String msg = ErrorMessages.MeFClientSDK000008.getErrorMessage() +
			"; " + className + "; " + methodName + "; " + aMessageID;
		ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
		logger.log(Level.WARNING, msg, tkre);
		throw tkre;
	}

	
	public ETIN getEtin() {
		return this.etin;
	}

	
	public void setEtin(ETIN etin) {
		this.etin = etin;
	}

	
	public String regenerate() {
		String methodName = "regenerate()";
		if (this.etin == null) {
			String msg = ErrorMessages.MeFClientSDK000007.getErrorMessage() +
				"; " + className + "; " + methodName;
			ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg, new IllegalStateException(msg));
			logger.log(Level.WARNING, msg, tkre);
			throw tkre;
		}
		init();
		return this.getValue();
		
	}

	
	public GregorianCalendar getCreationInstant() {
		return (GregorianCalendar) this.today.clone();
	}

	
	public static boolean isValidMessageID(String aMessageID) {
		return aMessageID != null && Pattern.matches(MESSAGE_ID_RESTRICTION, aMessageID);
	}

	
	@Override
	public String toString() {
		return this.getValue();
	}

	private void buildValue() {
		this.value = new StringBuilder(msgIDLength);
		this.value.append(this.etin);
		this.value.append(dateFormat.format(this.today.getTime()));
		this.value.append(this.randomPart);
	}

	private void init() {
		
		this.randomPart = new StringBuilder(sequenceLength);
		for (int i = 0; i < sequenceLength; ++i) {
			this.randomPart.append('0');
		}
		
		
		long randomNumber = Math.round(Math.random() * Math.pow(36, sequenceLength));
		
		
		convertBase(randomNumber, this.randomPart);
		buildValue();
	}

	
	private void convertBase(long input, StringBuilder valueBuilder) {
		BigInteger val = BigInteger.valueOf(input);
		String base36 = val.toString(36);
		valueBuilder.replace(valueBuilder.length() - base36.length(), valueBuilder.length(), base36);
	}


}
