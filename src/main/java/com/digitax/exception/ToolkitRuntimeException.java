package com.digitax.exception;

public class ToolkitRuntimeException extends RuntimeException{
	
	private static final long serialVersionUID = 6572919089755507981L;
	private boolean logged = true;
	public ToolkitRuntimeException (String msg) {
        super(msg);
    }
	public ToolkitRuntimeException (String msg, Throwable cause) {
    	super(msg, cause);
    }
	public boolean isLogged() {
		return this.logged;
	}
	public void setLogged(boolean wasLogged) {
		this.logged = wasLogged;
	}
}
