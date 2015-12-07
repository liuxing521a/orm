package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

class MDUpdateSQLProvider extends AbstractMethodProvider {
	
	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
		
		buffer.append("protected String updateSQL() {");
		
		buffer.append("return \"UPDATE `");
		buffer.append(tableName(clazz));
		buffer.append("` SET ");
	}
	
	@Override
	public void processField(CtField field) {
		if (!isProcesAble(field) || "Id".equals(field.getName())) {
			return;
		}

		buffer.append("`");
		buffer.append(field.getName());
		buffer.append("`");
		buffer.append(" = ?, ");
	}

	@Override
	public void endClass() throws Exception {
		buffer.delete(buffer.length() - 2, buffer.length());
		buffer.append(" WHERE `Id` = ?;\";");
		
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

