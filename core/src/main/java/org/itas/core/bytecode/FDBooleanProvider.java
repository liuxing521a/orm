package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * boolean数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午4:51:14
 */
class FDBooleanProvider extends AbstractFieldProvider 
    implements FieldProvider, TypeProvider {
	
	private static final String STATEMENT_SET = new StringBuffer()
		.append(next(1, 2)).append("state.setBoolean(%s, %s%s());")
		.toString();
	
	private static final String RESULTSET_GET = new StringBuffer()
		.append(next(1, 2)).append("set%s(result.getBoolean(\"%s\"));")
		.toString();

	public static final FDBooleanProvider PROVIDER = new FDBooleanProvider();
	
	private FDBooleanProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.boolean_ == clazz || javaType.booleanWrap == clazz;
	}

	@Override
	public boolean isType(CtClass clazz) {
		return javassistType.boolean_ == clazz || javassistType.booleanWrap == clazz;
	}

	@Override
	public String sqlType(CtField field) {
		return String.format("`%s` TINYINT(1) ZEROFILL NOT NULL DEFAULT '0'", field.getName());
	}
	
	@Override
	public String setStatement(int index, CtField field) {
		if (field.getName().startsWith("is")) {
			return String.format(STATEMENT_SET, index, "", field.getName());
		} else {
			return String.format(STATEMENT_SET, index, "is", upCase(field.getName()));
		}
	}

	@Override
	public String getResultSet(CtField field) {
		String name = field.getName();
		if (field.getName().startsWith("is")) {
			name = name.substring(2, name.length());
		}

		return String.format(RESULTSET_GET, upCase(name), field.getName());
	}

}
