package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.ClassType;
import javassist.bytecode.SignatureAttribute.TypeArgument;

import org.itas.core.annotation.Clazz;
import org.itas.core.annotation.Size;

/**
 * 容器数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日下午4:38:05
 */
abstract class FDContainerProvider extends AbstractFieldProvider {

	protected ClassType getFieldType(final CtField field) throws BadBytecode {
		return (ClassType)SignatureAttribute
			.toFieldSignature(field.getGenericSignature());
	}
	
	protected String newContainer(final CtField field, final String defaultContainer) 
		throws ClassNotFoundException, NotFoundException {
		Object annotiation;
		
		String containerName = defaultContainer;
		if ((annotiation = field.getAnnotation(Clazz.class)) != null) {
			containerName = ((Clazz)annotiation).value().getName();
		} 
			
		CtClass listClass = ClassPool.getDefault().get(containerName);
		try {
			listClass.getDeclaredConstructor(new CtClass[]{javassistType.int_});
			int size = 8;
			if ((annotiation = field.getAnnotation(Size.class)) != null) {
				size = ((Size)annotiation).value();
			}
			return String.format("%s(%s)", containerName, size);
		} catch (NotFoundException e) {
			return String.format("%s()", containerName);
		}
	}
	
	protected String parseFormula(final TypeArgument typeArgument, 
			final String value) throws Exception {
		final CtClass genericType = getCtClass(typeArgument);
		return parseFormula(genericType, value);
	}

	protected String parseFormula(final CtClass genericType, 
		final String value) throws Exception {
		
		if (Type.booleanType.isType(genericType)) {
			return String.format("java.lang.Boolean.valueOf(%s)", value);
		} 

		if (Type.byteType.isType(genericType)) {
			return String.format("java.lang.Byte.valueOf(%s)", value);
		} 

		if (Type.charType.isType(genericType)) {
			return String.format("%s.charAt(0)", value);
		} 

		if (Type.shortType.isType(genericType)) {
			return String.format("java.lang.Short.valueOf(%s)", value);
		} 

		if (Type.intType.isType(genericType)) {
			return String.format("java.lang.Integer.valueOf(%s)", value);
		} 

		if (Type.longType.isType(genericType)) {
			return String.format("java.lang.Long.valueOf(%s)", value);
		} 

		if (Type.floatType.isType(genericType)) {
			return String.format("java.lang.Float.valueOf(%s)", value);
		} 

		if (Type.doubleType.isType(genericType)) {
			return String.format("java.lang.Double.valueOf(%s)", value);
		} 

		if (Type.stringType.isType(genericType)) {
			return value;
		} 

		if (Type.simpleType.isType(genericType)) {
			return String.format("new org.itas.core.Simple(%s)", value);
		} 

		if (Type.resourceType.isType(genericType)) {
			return String.format("org.itas.core.Pool.getResource(%s)", value);
		} 

		if (Type.enumByteType.isType(genericType)) {
			return String.format("parse(%s.class, java.lang.Byte.valueOf(%s))", 
				genericType.getName().replace('$', '.'), value);
		} 

		if (Type.enumIntType.isType(genericType)) {
			return String.format("parse(%s.class, java.lang.Integer.valueOf(%s))", 
				genericType.getName().replace('$', '.'), value);
		} 

		if (Type.enumStringType.isType(genericType)) {
			return String.format("parse(%s.class, %s)", 
				genericType.getName().replace('$', '.'), value);
		} 
		
		if (Type.enumType.isType(genericType)) {
			return String.format("parse(%s.class, %s)", 
				genericType.getName().replace('$', '.'), value);
		} 

		throw new UnsupportException("[" + FDContainerProvider.class.getName() + 
			"] unsuppoted type: " + genericType.getName());
	}
	
	private CtClass getCtClass(TypeArgument typeArgument) 
		throws NotFoundException {
		final ClassType type = (ClassType)typeArgument.getType();
		final StringBuffer clsBuf = new StringBuffer();
		declaringName(type, clsBuf);
		
		return ClassPool.getDefault().get(clsBuf.toString());
	}
	
	private void declaringName(ClassType classType, StringBuffer buffer) {
		if (classType.getDeclaringClass() != null) {
			declaringName(classType.getDeclaringClass(), buffer);
			buffer.append('$');
		}
		
		buffer.append(classType.getName());
	}
}
