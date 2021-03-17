package com.digitax.service.data;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.digitax.exception.ErrorMessages;
import com.digitax.exception.ToolkitRuntimeException;
import com.digitax.util.LogUtil;

public class ETIN {
	private static final String className = ETIN.class.getName();
	private static Logger logger = LogUtil.getLogger(className);
	private String value;
	
	public static final String ETIN_RESTRICTION = "\\d{5}";

	
	public ETIN(String ETIN) {
		setValue(ETIN);
	}
	
	
	public String getValue() {
		return this.value;
	}

	private void setValue(String anETIN) {
		String methodName = "setValue()";
		if (ETIN.isValidETIN(anETIN)) {
			this.value = anETIN;
			return;
		}
		String msg = ErrorMessages.MeFClientSDK000007.getErrorMessage() +
			"; " + className + "; " + methodName + "; " + anETIN;
		ToolkitRuntimeException tkre = new ToolkitRuntimeException(msg);
		logger.log(Level.WARNING, msg, tkre);
		throw tkre;
	}
	
	
	@Override
	public String toString() {
		return getValue();
	}
	
	
	public static boolean isValidETIN(String anETIN) {
		return anETIN != null && Pattern.matches(ETIN_RESTRICTION, anETIN);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o instanceof ETIN) {
			ETIN etin = (ETIN) o;
			return this.getValue().equals(etin.getValue());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Integer.parseInt(getValue());
	}

}
