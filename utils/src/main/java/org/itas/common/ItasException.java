package org.itas.common;

public class ItasException extends RuntimeException {
	
	private static final long serialVersionUID = 453845752665234220L;

	public ItasException() {
		super();
	}

	public ItasException(String message) {
		super(message);
	}

	public ItasException(String message, Throwable cause) {
		super(message, cause);
	}

	public ItasException(Throwable cause) {
		super(cause);
	}
}
