package org.itas.core.bytecode;

public class UnsupportException extends RuntimeException {
	
	private static final long serialVersionUID = -5078365276466487872L;

	public UnsupportException() {
		super();
	}

	public UnsupportException(String message) {
		super(message);
	}

	public UnsupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportException(Throwable cause) {
		super(cause);
	}
}
