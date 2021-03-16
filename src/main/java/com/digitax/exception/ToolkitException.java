package com.digitax.exception;

import java.util.logging.Level;

public class ToolkitException extends Exception{
	private static final long serialVersionUID = 3648436687591862268L;
	private Level severity;
	public ToolkitException(String message, Throwable cause) {
		this(message, cause, Level.WARNING);
	}
	public ToolkitException(String message) {
		this(message, Level.WARNING);
	}
	public ToolkitException(String message, Throwable cause, Level severity) {
		super(message, cause);
		this.severity = severity;
	}
	public ToolkitException(String message, Level severity) {
		super(message);
		this.severity = severity;
	}
	public Level getSeverity() {
		return this.severity;
	}
	public void setSeverity(Level severity) {
		this.severity = severity;
	}
}
