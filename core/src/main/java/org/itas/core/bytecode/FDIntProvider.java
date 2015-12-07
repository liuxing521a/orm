package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * int数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:11:23
 */
class FDIntProvider extends AbstractFieldProvider 
		implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = new StringBuffer()
		.append(next(1, 2)).append("state.setInt(%s, get%s());")
		.toString();
	
	private static final String RESULTSET_GET = new StringBuffer()
		.append(next(1, 2)).append("set%s(result.getInt(\"%s\"));")
		.toString();
	
	public static final FDIntProvider PROVIDER = new FDIntProvider();
	
	private FDIntProvider() {
	}
	
	@Override
	public String setStatement(int index, CtField field) {
		return String.format(STATEMENT_SET, index, upCase(field.getName()));
	}

	@Override
	public String getResultSet(CtField field) {
		return String.format(RESULTSET_GET, upCase(field.getName()), field.getName());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.int_ == clazz || javaType.intWrap == clazz;
	}
	
	@Override
	public boolean isType(CtClass clazz) {
		return javassistType.int_ == clazz || javassistType.intWrap == clazz;
	}

	@Override
	public String sqlType(CtField field) {
		return String.format("`%s` INT(11) NOT NULL DEFAULT '0'", field.getName());
	}
	
}
