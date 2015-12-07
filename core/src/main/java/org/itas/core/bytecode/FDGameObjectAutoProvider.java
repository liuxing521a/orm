package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class FDGameObjectAutoProvider extends AbstractFieldProvider 
		implements FieldProvider, TypeProvider {
	
	public static final FDGameObjectAutoProvider PROVIDER = new FDGameObjectAutoProvider();

	private FDGameObjectAutoProvider(){
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.gameBaseAotuID.isAssignableFrom(clazz);
	}

	@Override
	public boolean isType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.gameBaseAotuID);
	}

	@Override
	public String sqlType(CtField field) throws Exception {
		throw new UnsupportException("auto game Object not supported sqlType");
	}
	
	@Override
	public String setStatement(int index, CtField field) throws Exception {
		throw new UnsupportException("auto game Object not supported setStatement");
	}

	@Override
	public String getResultSet(CtField field) throws Exception {
		throw new UnsupportException("auto game Object not supported getResultSet");
	}

}
