package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

class MDTotolSizeProvider extends AbstractMethodProvider {
	
	MDTotolSizeProvider() {
		super();
	}
	
	private int size;
	
	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
		size = 0;
		
		buffer.append("public int getCachedSize() throws CalculateSizeException");
		buffer.append(next(1, 2));
		buffer.append("int size = super.getCachedSize();");
	}

	@Override
	public void processField(CtField field) throws Exception {
		buffer.append(next(1, 2));
		Integer valueSize = totalFixedSize(field.getType());
		
		if (valueSize != null) {
			size += valueSize;
			return;
		} 

		String valueStr = totolDynamicSize(field.getType());
		if (valueStr != null) {
			buffer.append(valueStr);
			return;
		}
		
		valueStr = totalContainerSize(field.getType(), field);
		if (valueStr != null) {
			buffer.append(valueStr);
			return;
		}
		
		throw new UnsupportException(field.getType().getName());
	}

	private Integer totalFixedSize(CtClass type) throws Exception {
		if (Type.booleanType.isType(type)) {
			return 1;
		} else if (Type.byteType.isType(type)) {
			return 1;
		} else if (Type.charType.isType(type)) {
			return 2;
		} else if (Type.shortType.isType(type)) {
			return 2;
		} else if (Type.intType.isType(type)) {
			return 4;
		} else if (Type.longType.isType(type)) {
			return 8;
		} else if (Type.floatType.isType(type)) {
			return 4;
		} else if (Type.doubleType.isType(type)) {
			return 8;
		} else if (Type.enumByteType.isType(type)) {
			return 1;
		} else if (Type.enumIntType.isType(type)) {
			return 4;
		} else if (Type.simpleType.isType(type)) {
			return 39;
		} else if (Type.timeStampType.isType(type)) {
			return 91;
		} else {
			return null;
		}
	}
	
	private String totolDynamicSize(CtClass type) throws Exception {
		final StringBuffer sizeBuf = new StringBuffer();
		
		sizeBuf.append(next(1, 2));
		sizeBuf.append("if (");
		sizeBuf.append(ctField.getName());
		sizeBuf.append("!= null) {");
		
		sizeBuf.append(next(1, 3));
		sizeBuf.append("size += ");
		sizeBuf.append(ctField.getName());
		if (Type.stringType.isType(type)) {
			sizeBuf.append(".length();");
		} else if (Type.resourceType.isType(type)) {
			sizeBuf.append(".getId().length();");
		} else if (Type.enumStringType.isType(type)) {
			sizeBuf.append(".key().length();");
		} else if (Type.enumType.isType(type)) {
			sizeBuf.append(".toString().length();");
		} else {
			return null;
		}
		
		sizeBuf.append(next(1, 2));
		sizeBuf.append("}");
		
		return sizeBuf.toString();
	}
	
	private String totalContainerSize(CtClass type, CtField field) throws Exception {
		StringBuffer sizeBuf = new StringBuffer();
		
		sizeBuf.append(next(1, 2));
		sizeBuf.append("if (");
		sizeBuf.append(ctField.getName());
		sizeBuf.append("!= null) {");
		
		sizeBuf.append(next(1, 2));
		if (Type.listType.isType(type)) {
			
		} else if (Type.setType.isType(type)) {
			
		} else if (Type.mapType.isType(type)) {
			
		} else if (Type.singleArrayType.isType(type)) {
			
		} else if (Type.doubleArrayType.isType(type)) {
			
		} else {
			return null;
		}
		
		sizeBuf.append("}");
		return sizeBuf.toString();
	}
	
	@Override
	public void endClass() throws ClassNotFoundException {
		buffer.append(next(1, 2));
		buffer.append("return size;");
		buffer.append(next(1, 1));
		buffer.append("}");
	}

	@Override
	public CtMethod[] toMethod() throws CannotCompileException {
		return new CtMethod[]{CtMethod.make(buffer.toString(), ctClass)};
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}
	
}
