package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class FDGameObjectProvider extends AbstractFieldProvider 
		implements FieldProvider, TypeProvider {
	
	public static final FDGameObjectProvider PROVIDER = new FDGameObjectProvider();

	private FDGameObjectProvider(){
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.gameObject.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.gameObject);
	}

	@Override
	public String sqlType(CtField field) throws Exception {
		throw new UnsupportException("game Object not supported sqlType");
	}

	@Override
	public String setStatement(int index, CtField field) throws Exception {
		throw new UnsupportException("game Object not supported setStatement");
	}

	@Override
	public String getResultSet(CtField field) throws Exception {
		throw new UnsupportException("game Object not supported getResultSet");
	}

}
