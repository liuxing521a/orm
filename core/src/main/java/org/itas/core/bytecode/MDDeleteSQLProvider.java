package org.itas.core.bytecode;

import javassist.CtField;
import javassist.CtMethod;

class MDDeleteSQLProvider extends AbstractMethodProvider {
	
	MDDeleteSQLProvider() {
		super();
	}
	
	@Override
	public void processField(CtField field) throws Exception {
		
	}
	
	@Override
	public void endClass() throws Exception {
		buffer.append("protected String deleteSQL() {");
		
		buffer.append("return \"DELETE FROM `");
		buffer.append(tableName(ctClass));
		buffer.append("` WHERE Id = ?;\";");
		
		buffer.append("}");
	}

	@Override
	public CtMethod[] toMethod() throws Exception {
		return new CtMethod[]{CtMethod.make(buffer.toString(), ctClass)};
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}

}
