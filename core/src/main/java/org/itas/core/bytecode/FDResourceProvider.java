package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * 资源数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:30:17
 */
class FDResourceProvider extends AbstractFieldProvider 
		implements FieldProvider, TypeProvider {
	
	private static final String STATEMENT_SET = new StringBuffer()
	  .append(next(1, 2)).append("{")
	  .append(next(1, 3)).append("String value_ = \"\";")
	  .append(next(1, 3)).append("if (get%s() != null) {")
	  .append(next(1, 4)).append("value_ = get%s().getId();")
	  .append(next(1, 3)).append("}")
	  .append(next(1, 3)).append("state.setString(%s, value_);")
	  .append(next(1, 2)).append("}")
	  .toString();


	private static final String RESULTSET_GET = new StringBuffer()
		.append(next(1, 2)).append("{")
		.append(next(1, 3)).append("String value_ = result.getString(\"%s\");") 
		.append(next(1, 3)).append("if (value_ != null && value_.length() > 0) {") 
		.append(next(1, 4)).append("set%s(org.itas.core.Pool.getResource(value_));") 
		.append(next(1, 3)).append("}") 
		.append(next(1, 2)).append("}") 
		.toString();
	
	public static final FDResourceProvider PROVIDER = new FDResourceProvider();
	
	private FDResourceProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.resource_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.resource_);
	}

	@Override
	public String sqlType(CtField field) throws Exception {
		return String.format("`%s` VARCHAR(24) NOT NULL DEFAULT ''", field.getName());
	}

	
	@Override
	public String setStatement(int index, CtField field) {
		return String.format(STATEMENT_SET, 
			upCase(field.getName()), upCase(field.getName()), index);
	}

	@Override
	public String getResultSet(CtField field) {
		return String.format(RESULTSET_GET, 
			field.getName(), upCase(field.getName()));
	}

}
