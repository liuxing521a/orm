package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.common.ItasException;
import org.itas.core.CallBack;


/**
 *  select 预处理方法字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:29
 */
class MDDoDeleteProvider extends AbstractMethodProvider {
	
	private CtField ctField;
	
	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
	}
	
	@Override
	public void processField(CtField field) throws Exception {
		if ("Id".equals(field.getName())) {
			ctField = field;
		}
	}
	
	@Override
	public void endClass() throws Exception {
		Type type = getType(ctField);
		buffer.append("protected void doDelete(java.sql.PreparedStatement state) throws java.sql.SQLException {");
		type.process(new CallBack<FieldProvider>() {
			@Override
			public void called(FieldProvider callback) {
				try {
					buffer.append(callback.setStatement(1, ctField));
				} catch (Exception e) {
					throw new ItasException(e);
				}
			}
		});
		
		buffer.append(next(1, 0));
		buffer.append("}");
	}
	
	@Override
	public CtMethod[] toMethod() throws Exception {
		return new CtMethod[]{CtMethod.make(buffer.toString(), ctClass)};
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}

}
