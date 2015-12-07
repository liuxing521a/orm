package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;
import javassist.bytecode.SignatureAttribute.ClassType;
import javassist.bytecode.SignatureAttribute.TypeArgument;

/**
 * set数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日下午3:38:22
 */
class FDSetProvider extends FDContainerProvider 
		implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = new StringBuffer()
		.append(next(1, 2))
		.append("state.setString(%s, toString(get%s()));")
		.toString();


	private static final String RESULTSET_GET = new StringBuffer()
		.append(next(1, 2)).append("{")
		.append(next(1, 3)).append("String value_ = result.getString(\"%s\");")
		.append(next(1, 3)).append("String[] valueArray_ = parseArray(value_);")
		.append(next(1, 3)).append("%s valueList_ = new %s;")
		.append(next(1, 3)).append("for (int i = 0; i < valueArray_.length; i ++) {")
		.append(next(1, 4)).append("valueList_.add(%s);")
		.append(next(1, 3)).append("}")
		.append(next(1, 3)).append("set%s(valueList_);")
		.append(next(1, 2)).append("}")
		.toString();
	
	public static final FDSetProvider PROVIDER = new FDSetProvider();
	
	private FDSetProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.set_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.set_);
	}

	@Override
	public String sqlType(CtField field) throws Exception {
		return String.format("`%s` TEXT", field.getName());
	}
	
	@Override
	public String setStatement(int index, CtField field) throws Exception {
		return String.format(STATEMENT_SET, index, upCase(field.getName()));
	}

	@Override
	public String getResultSet(CtField field) throws Exception {
		final ClassType definType = getFieldType(field);
		final TypeArgument typeArgument = definType.getTypeArguments()[0];
		final String containerName = newContainer(field, "java.util.HashSet");
		
		return String.format(RESULTSET_GET, 
			field.getName(), definType.getName(),	containerName, 
			parseFormula(typeArgument, "valueArray_[i]"), upCase(field.getName()));
	}
	
}
