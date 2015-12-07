package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * char数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:02:35
 */
class FDCharProvider extends AbstractFieldProvider 
    implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = new StringBuffer()
		.append(next(1, 2)).append("state.setString(%s, String.valueOf(get%s()));")
		.toString();
	
	private static final String RESULTSET_GET = new StringBuffer()
		.append(next(1, 2)).append("set%s(result.getString(\"%s\").charAt(0));")
		.toString();


	public static final FDCharProvider PROVIDER = new FDCharProvider();
	
	private FDCharProvider() {
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
		return javaType.char_ == clazz || javaType.charWrap == clazz;
	}

	@Override
	public boolean isType(CtClass clazz) {
		return javassistType.char_ == clazz || javassistType.charWrap == clazz;
	}

	@Override
	public String sqlType(CtField field) {
		return String.format("`%s` CHAR(1) NOT NULL DEFAULT ' '", field.getName());
	}

}
