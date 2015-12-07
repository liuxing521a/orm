package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtField;
import javassist.CtMethod;

class MDTableNameProvider extends AbstractMethodProvider {

	@Override
	public void processField(CtField field) throws Exception {
	}
	
	@Override
	public void endClass() throws Exception {
		buffer.append("protected String tableName() {");
		buffer.append("return \"").append(tableName(ctClass)).append("\";");
		buffer.append("}");
	}

	@Override
	public CtMethod[] toMethod() throws CannotCompileException, ClassNotFoundException {
		return new CtMethod[]{CtMethod.make(buffer.toString(), ctClass)};
	}
	
}
