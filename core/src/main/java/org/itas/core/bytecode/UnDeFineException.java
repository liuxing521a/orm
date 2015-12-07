package org.itas.core.bytecode;

public class UnDeFineException extends RuntimeException {
	
  private static final long serialVersionUID = 8089161970865572779L;

  public UnDeFineException() {
	super();
  }

  public UnDeFineException(String message) {
	super(message);
  }

  public UnDeFineException(String message, Throwable cause) {
	super(message, cause);
  }

  public UnDeFineException(Throwable cause) {
	super(cause);
  }
}
