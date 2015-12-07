package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * 枚举关键字为byte数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午4:51:14
 */
class FDEnumByteProvider extends AbstractFieldProvider 
    implements FieldProvider, TypeProvider {
	
	private static final String STATEMENT_SET = new StringBuffer()
		.append(next(1, 2)).append("{")
		.append(next(1, 3)).append("byte value_ = 0;")
		.append(next(1, 3)).append("if (get%s() != null) {")
		.append(next(1, 4)).append("value_ = get%s().key();")
		.append(next(1, 3)).append("}")
		.append(next(1, 3)).append("state.setByte(%s, value_);")
		.append(next(1, 2)).append("}")
		.toString();

	private static final String RESULTSET_GET = new StringBuffer()
		.append(next(1, 2))
		.append("set%s(parse(%s.class, result.getByte(\"%s\")));")
		.toString();
	
	public static final FDEnumByteProvider PROVIDER = new FDEnumByteProvider();
	
	private FDEnumByteProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.enumByte_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.enumByte_);
	}

	@Override
	public String sqlType(CtField field) {
		return String.format("`%s` TINYINT(4) NOT NULL DEFAULT '0'", field.getName());
	}

	@Override
	public String setStatement(int index, CtField field) {
		return String.format(STATEMENT_SET, 
			upCase(field.getName()), upCase(field.getName()), index);
	}

	@Override
	public String getResultSet(CtField field) throws Exception {
		return String.format(RESULTSET_GET, upCase(field.getName()), 
			field.getType().getName().replace('$', '.'), field.getName());
	}

}
