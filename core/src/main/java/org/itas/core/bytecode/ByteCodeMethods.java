//package org.itas.core.bytecode;
//
//import org.itas.core.Ioc;
//
//import com.google.inject.Singleton;
//
//import javassist.CtClass;
//import javassist.CtField;
//import javassist.CtMethod;
//
//@Singleton
//class ByteCodeMethods implements Ioc {
//	
//	private MethodProvider[] methods;
//	
//	public Class<?>[] modifyMethodProviders() {
//		return new Class<?>[] {
//			
//			MethodSQLSelectProvider.class,
//			MethodSQLInsertProvider.class,
//			MethodSQLUpdateProvider.class,
//			MethodSQLDeleteProvider.class,
//			
//			MethodDoCreateProvider.class,
//			MethodDoAlterProvider.class,
//			MethodDoSelectProvider.class,
//			MethodDoInsertProvider.class,
//			MethodDoUpdateProvider.class,
//			MethodDoDeleteProvider.class,
//			MethodDoFillProvider.class,
//
//			MethodTableNameProvider.class,
//			CloneProvider.class,
//			MethodSetMethodModifyProvider.class
//		};
//	}
//
//	public void begin(CtClass clazz) throws Exception {
//		Class<?  extends MethodProvider>[] clazzes = 
//			(Class<? extends MethodProvider>[]) modifyMethodProviders();
//		
//		for (int i = clazzes.length; i >= 0; i --) {
//			methods[i] = Ioc.getInstance(clazzes[i]);
//			methods[i].begin(clazz);
//		}
//	}
//
//	public void append(CtField field) throws Exception {
//		for (MethodProvider method : methods) {
//			method.append(field);
//		}
//	}
//
//	public void end() throws Exception {
//		for (MethodProvider method : methods) {
//			method.end();
//		}
//	}
//
//	public CtMethod[] toMethods() throws Exception {
//		CtMethod[] ctMethods = new CtMethod[methods.length];
//		
//		for (int i = 0; i < methods.length; i ++) {
//			ctMethods[i] = methods[i].toMethod();
//		}
//		
//		return ctMethods;
//	}
//
//}
