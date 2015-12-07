package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

class MDDoAlterProvider extends AbstractMethodProvider 
	implements org.itas.core.util.Next {
	
	MDDoAlterProvider() {
  }

  @Override
  public void startClass(CtClass clazz) throws Exception {
  	super.startClass(clazz);
  	buffer.append("protected void doAlter(java.sql.Statement stmt, java.util.Set columns) throws java.sql.SQLException {");
  	buffer.append(next(1, 2));
  	buffer.append("java.util.Map alterMap = new java.util.HashMap();");
  }

  @Override
  public void processField(CtField field) throws Exception {
  	if (!isProcesAble(field)) {
  		return;
  	}
  	
  	buffer.append(next(1, 2));
  	buffer.append("alterMap.put(\"").append(field.getName()).append("\", \"");
  	buffer.append(String.format(
			"ALTER TABLE `%s` ADD %s;", tableName(ctClass), getType(field).sqlType(field)));
  	buffer.append("\");");
  }

  @Override
  public void endClass() throws Exception {
  	buffer.append(next(1, 2));
  	buffer.append("java.util.Iterator it = alterMap.entrySet().iterator();");
  	buffer.append(next(1, 2));
  	buffer.append("java.util.Map.Entry entry;");
  	buffer.append(next(1, 2));
  	buffer.append("while (it.hasNext()) {");
  	buffer.append(next(1, 3));
  	buffer.append("entry = (java.util.Map.Entry)it.next();");
  	buffer.append(next(1, 3));
  	buffer.append("if (!columns.contains((String)entry.getKey()))");
  	buffer.append(next(1, 3));
  	buffer.append("stmt.addBatch((String)entry.getValue());");
  	buffer.append(next(1, 2));
  	buffer.append("}");
  	buffer.append(next(1, 1));
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
