package org.itas.core;

public class DoubleException extends RuntimeException {
	
	private static final long serialVersionUID = 453845752665234220L;

	public DoubleException() {
		super();
	}

	public DoubleException(String message) {
		super(message);
	}

	public DoubleException(String message, Throwable cause) {
		super(message, cause);
	}

	public DoubleException(Throwable cause) {
		super(cause);
	}
}
