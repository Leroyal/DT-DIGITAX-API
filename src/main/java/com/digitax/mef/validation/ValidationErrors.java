package com.digitax.mef.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ValidationErrors implements Iterable<ValidationError>{
	private List<ValidationError> errors;
	public ValidationErrors() {
		this.errors = new ArrayList<ValidationError>();
	}

	
	public boolean isEmpty() {
		return errors != null && errors.isEmpty();
	}

	
	public void add(ValidationError error) {
		this.errors.add(error);
	}

	public Iterator<ValidationError> iterator() {
		return this.errors.iterator();
	}
}
