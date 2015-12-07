package org.itas.core.bytecode;

import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.core.annotation.Index;
import org.itas.core.annotation.Primary;
import org.itas.core.annotation.Unique;
import org.itas.core.util.Next;

class MDDoCreateProvider extends AbstractMethodProvider implements Next {

	private List<String> keys;
	
	private StringBuffer sqlBuffer;
	
	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
		this.sqlBuffer = new StringBuffer();
		this.keys = new ArrayList<String>(4);
		
		

		sqlBuffer.append("CREATE TABLE IF NOT EXISTS `");
		sqlBuffer.append(tableName(clazz));
		sqlBuffer.append("`(");
		
		sqlBuffer.append("{sign}");
		sqlBuffer.append("`Id` VARCHAR(36) NOT NULL DEFAULT '',");
	}

	@Override
	public void processField(CtField field) throws Exception {
		if (!isProcesAble(field)) {
			return;
		}
		
		if (!"Id".equals(field.getName())) {
			sqlBuffer.append("{sign}");
			sqlBuffer.append(getType(field).sqlType(field));
			sqlBuffer.append(",");
		}
		
		if (field.hasAnnotation(Primary.class)) {
			keys.add(String.format("PRIMARY KEY `%s` (`%s`)", field.getName(), field.getName()));
		} else if (field.hasAnnotation(Unique.class)) {
			keys.add(String.format("UNIQUE KEY `%s` (`%s`)", field.getName(), field.getName()));
		} else if (field.hasAnnotation(Index.class)) {
			keys.add(String.format("KEY `%s` (`%s`)", field.getName(), field.getName()));
		}
	}
	
	@Override
	public void endClass() {
		for (String index : keys) {
			sqlBuffer.append("{sign}");
			sqlBuffer.append(index);
			sqlBuffer.append(",");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
		
		sqlBuffer.append("{sign}");
		sqlBuffer.append(") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
		
		buffer.append("protected void doCreate(java.sql.Statement state) throws java.sql.SQLException {");
		buffer.append(next(1, 1));
		buffer.append("String sql = \"");
		buffer.append(sqlBuffer.toString().replace("{sign}", ""));
		buffer.append("\";");
		buffer.append("state.addBatch(sql);");
		buffer.append(next(1, 0));
		buffer.append("}");
	}
	
	@Override
	public String toString() {
		return sqlBuffer.toString().replace("{sign}", next(1, 1));
	}

	@Override
	public CtMethod[] toMethod() throws CannotCompileException  {
		return new CtMethod[]{CtMethod.make(buffer.toString(), ctClass)};
	}


}
