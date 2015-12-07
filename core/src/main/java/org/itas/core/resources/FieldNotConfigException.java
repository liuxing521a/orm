package org.itas.core.resources;

public class FieldNotConfigException extends RuntimeException {
	
	private static final long serialVersionUID = -5078365276466487872L;
	
	private String message;

	public FieldNotConfigException() {
		super();
	}

	public FieldNotConfigException(String message) {
		super(message);
	}

	public FieldNotConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldNotConfigException(Throwable cause) {
		super(cause);
	}
	
	public FieldNotConfigException setMessage(String message) {
		this.message = message;
		return this;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
