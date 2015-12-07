package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

import org.itas.core.CallBack;



/**
 * 支持数据类型
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日上午10:02:48
 */
public enum Type {
	
	booleanType {

		@Override
		protected TypeProvider provider() {
			return FDBooleanProvider.PROVIDER;
		}
	},
	byteType {

		@Override
		protected TypeProvider provider() {
			return FDByteProvider.PROVIDER;
		}
	},
	charType {

		@Override
		protected TypeProvider provider() {
			return FDCharProvider.PROVIDER;
		}
	},
	shortType {

		@Override
		protected TypeProvider provider() {
			return FDShortProvider.PROVIDER;
		}
	},
	intType {

		@Override
		protected TypeProvider provider() {
			return FDIntProvider.PROVIDER;
		}
	},
	longType {

		@Override
		protected TypeProvider provider() {
			return FDLongProvider.PROVIDER;
		}
	},
	floatType {

		@Override
		protected TypeProvider provider() {
			return FDFloatProvider.PROVIDER;
		}
	},
	doubleType {

		@Override
		protected TypeProvider provider() {
			return FDDoubleProvider.PROVIDER;
		}
	},
	stringType {

		@Override
		protected TypeProvider provider() {
			return FDStringProvider.PROVIDER;
		}
	},
	simpleType {

		@Override
		protected TypeProvider provider() {
			return FDSimpleProvider.PROVIDER;
		}
	},
	resourceType {

		@Override
		protected TypeProvider provider() {
			return FDResourceProvider.PROVIDER;
		}
	},
	enumType {

		@Override
		TypeProvider provider() {
			return FDEnumProvider.PROVIDER;
		}
	},
	enumByteType {

		@Override
		protected TypeProvider provider() {
			return FDEnumByteProvider.PROVIDER;
		}
	},
	enumIntType {

		@Override
		protected TypeProvider provider() {
			return FDEnumIntProvider.PROVIDER;
		}
	},
	enumStringType {

		@Override
		protected TypeProvider provider() {
			return FDEnumStringProvider.PROVIDER;
		}
	},
	singleArrayType {

		@Override
		TypeProvider provider() {
			return FDSingleArrayProvider.PROVIDER;
		}
	},
	doubleArrayType {

		@Override
		TypeProvider provider() {
			return FDDoubleArrayProvider.PROVIDER;
		}
	},
	listType {

		@Override
		protected TypeProvider provider() {
			return FDListProvider.PROVIDER;
		}
	},
	setType {

		@Override
		protected TypeProvider provider() {
			return FDSetProvider.PROVIDER;
		}
	},
	mapType {

		@Override
		protected TypeProvider provider() {
			return FDMapProvider.PROVIDER;
		}
	},
	timeStampType {

		@Override
		protected TypeProvider provider() {
			return FDTimestampProvider.PROVIDER;
		}
	},
	gameObjectType {

		@Override
		protected TypeProvider provider() {
			return FDGameObjectProvider.PROVIDER;
		}
	},
	gameObjectAutoIdType {
		
		@Override
		protected TypeProvider provider() {
			return FDGameObjectAutoProvider.PROVIDER;
		}
	},
	;
	
	private Type() {
	}
	
	abstract TypeProvider provider();
	
	public boolean isType(Class<?> clazz) {
		return provider().isType(clazz);
	}

	public boolean isType(CtClass clazz) throws Exception {
		return provider().isType(clazz);
	}
	
	public String sqlType(CtField field) throws Exception {
		return provider().sqlType(field);
	}
	
	public void process(CallBack<FieldProvider> back) throws Exception {
		back.called((FieldProvider)provider());
	}
	
}
