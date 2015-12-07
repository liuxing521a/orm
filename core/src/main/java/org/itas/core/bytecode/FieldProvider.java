package org.itas.core.bytecode;

import javassist.CtField;

public interface FieldProvider extends TypeProvider, Provider {

	String setStatement(int index, CtField field) throws Exception;
	
	String getResultSet(CtField field) throws Exception;

}
