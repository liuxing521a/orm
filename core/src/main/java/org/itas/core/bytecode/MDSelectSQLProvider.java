package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

class MDSelectSQLProvider extends AbstractMethodProvider {
	
	MDSelectSQLProvider() {
		super();
	}
	
	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
		
		buffer.append("protected String selectSQL() {");
		buffer.append("return \"SELECT ");
	}

	@Override
	public void processField(CtField field) {
		if (!isProcesAble(field)) {
			return;
		}
		
		buffer.append("`");
		buffer.append(field.getName());
		buffer.append("`");
		buffer.append(", ");
	}

	@Override
	public void endClass() throws ClassNotFoundException {
		buffer.delete(buffer.length() - 2, buffer.length());
		buffer.append(" FROM `");
		buffer.append(tableName(ctClass));
		buffer.append("` WHERE Id = ?;\";");
		
		buffer.append("}");
	}

	@Override
	public CtMethod[] toMethod() throws CannotCompileException {
		return new CtMethod[]{CtMethod.make(buffer.toString(), ctClass)};
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}
	
}
