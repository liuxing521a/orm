package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

/**
 * byte数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午4:51:14
 */
class FDDoubleArrayProvider extends FDContainerProvider 
    implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = new StringBuffer()
		.append(next(1, 2))
		.append("state.setString(%s, toString(get%s()));")
		.toString();
	
	private static final String RESULTSET_GET = new StringBuffer()
		.append(next(1, 2)).append("{")
		.append(next(1, 3)).append("String value_ = result.getString(\"%s\");")
		.append(next(1, 3)).append("String[][] valueArray_ = parseArray(value_);")
		.append(next(1, 3)).append("%s[][] valueList_ = new %s[valueArray_.length][valueArray_[0].length];")
		.append(next(1, 3)).append("for (int i = 0; i < valueArray_.length; i ++) {")
		.append(next(1, 4)).append("for (int j = 0; j < valueArray_[i].length; j ++) {")
		.append(next(1, 5)).append("if (valueArray_[i][j] != null && valueArray_[i][j].length > 3)")
		.append(next(1, 5)).append("valueList_[i][j] = %s;")
		.append(next(1, 4)).append("}")
		.append(next(1, 3)).append("}")
		.append(next(1, 3)).append("set%s(valueList_);")
		.append(next(1, 2)).append("}")
		.toString();
	
	public static final FDDoubleArrayProvider PROVIDER = new FDDoubleArrayProvider();
	
	private FDDoubleArrayProvider() {
	}
	
	public boolean isType(Class<?> clazz) {
		if (!clazz.isArray()) {
			return false;
		}
		
		Class<?> componentType = clazz.getComponentType();
		if (!componentType.isArray()) {
			return false;
		}
		
		componentType = componentType.getComponentType();
		if (componentType.isArray()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean isType(CtClass clazz) throws NotFoundException {
		if (!clazz.isArray()) {
			return false;
		}
		
		CtClass componentType = clazz.getComponentType();
		if (!componentType.isArray()) {
			return false;
		}
		
		componentType = componentType.getComponentType();
		if (componentType.isArray()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String sqlType(CtField field) {
		return String.format("`%s` TEXT", field.getName());
	}
	
	@Override
	public String setStatement(int index, CtField field) {
		return String.format(STATEMENT_SET, 
			index, upCase(field.getName()));
	}
	
	@Override
	public String getResultSet(CtField field) throws Exception {
		CtClass typeArgumet = field.getType().getComponentType();
		typeArgumet = typeArgumet.getComponentType();
	
		final String arrayName =  typeArgumet.getName().replace('$', '.');
		System.out.println(arrayName);
		return String.format(RESULTSET_GET,
			field.getName(), arrayName, arrayName, 
			parseFormula(typeArgumet, "valueArray_[i][j]"), upCase(field.getName()));
	}

}
