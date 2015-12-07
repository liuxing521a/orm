package org.itas.core.resources;

public class FieldConfigFormatException extends RuntimeException {
	
	private static final long serialVersionUID = -5078365276466487872L;
	
	private String message;

	public FieldConfigFormatException() {
		super();
	}

	public FieldConfigFormatException(String message) {
		super(message);
	}

	public FieldConfigFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldConfigFormatException(Throwable cause) {
		super(cause);
	}
	
	public FieldConfigFormatException setMessage(String message) {
		this.message = message;
		return this;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
