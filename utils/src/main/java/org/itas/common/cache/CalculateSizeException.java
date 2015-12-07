package org.itas.common.cache;

public class CalculateSizeException extends RuntimeException {
	
    public static final long serialVersionUID = 1754096832201752388L;

    public CalculateSizeException() {
    }

    public CalculateSizeException(Object o) {
        super( "Unable to determine size of " + o.getClass() + " instance" );
    }
}
