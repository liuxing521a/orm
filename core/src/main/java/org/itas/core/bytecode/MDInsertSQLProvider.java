package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

class MDInsertSQLProvider extends AbstractMethodProvider {
	
	private int count;
	
	MDInsertSQLProvider() {
	}
	
	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
		this.count = 0;
		buffer.append("protected String insertSQL() {");
		buffer.append("return \"INSERT INTO `");
		buffer.append(tableName(clazz));
		buffer.append("` (");
	}
	
	@Override
	public void processField(CtField field) {
		if (!isProcesAble(field)) {
			return;
		}
		this.count ++;
		
		buffer.append("`");
		buffer.append(field.getName());
		buffer.append("`");
		buffer.append(", ");
	}
	
	@Override
	public void endClass() throws Exception {
		buffer.delete(buffer.length() - 2, buffer.length());
		buffer.append(") VALUES (");
		
		for (int i = 0; i < count; i++) {
			buffer.append("?, ");
		}
		buffer.delete(buffer.length() - 2, buffer.length());
		buffer.append(");\";");
		
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
