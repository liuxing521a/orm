package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * long数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:11:36
 */
class FDLongProvider extends AbstractFieldProvider 
		implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = new StringBuffer()
		.append(next(1, 2)).append("state.setLong(%s, get%s());")
		.toString();
	
	private static final String RESULTSET_GET = new StringBuffer()
		.append(next(1, 2)).append("set%s(result.getLong(\"%s\"));")
		.toString();
	

	public static final FDLongProvider PROVIDER = new FDLongProvider();
	
	private FDLongProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.long_ == clazz || javaType.longWrap == clazz;
	}

	@Override
	public boolean isType(CtClass clazz) {
		return javassistType.long_ == clazz || javassistType.longWrap == clazz;
	}

	@Override
	public String sqlType(CtField field) {
		return String.format("`%s` BIGINT(20) NOT NULL DEFAULT '0'", field.getName());
	}
	
	@Override
	public String setStatement(int index, CtField field) {
		return String.format(STATEMENT_SET, index, upCase(field.getName()));
	}

	@Override
	public String getResultSet(CtField field) {
		return String.format(RESULTSET_GET, upCase(field.getName()), field.getName());
	}

}
