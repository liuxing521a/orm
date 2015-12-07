package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

import org.itas.core.annotation.Size;

/**
 * String数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:35:21
 */
class FDStringProvider extends AbstractFieldProvider
		implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = new StringBuffer()
		.append(next(1, 2)).append("state.setString(%s, get%s());")
		.toString();
	
	private static final String RESULTSET_GET = new StringBuffer()
		.append(next(1, 2)).append("set%s(result.getString(\"%s\"));")
		.toString();
	
	public static final FDStringProvider PROVIDER = new FDStringProvider();
	
	private FDStringProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.string_ == clazz;
	}
	
	@Override
	public boolean isType(CtClass clazz) {
		return javassistType.string_ == clazz;
	}

	@Override
	public String sqlType(CtField field) throws Exception {
		Object aSize = field.getAnnotation(Size.class);
		int size = (aSize == null) ? 36 : ((Size)aSize).value();
		return String.format("`%s` VARCHAR(%s) NOT NULL DEFAULT ''", field.getName(), size);
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
