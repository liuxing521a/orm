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
class MDDoUpdateProvider extends AbstractMethodProvider {
	
	private CtField idField;
	
	private int index;
	
	MDDoUpdateProvider() {
	}
	
	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
		
		this.index = 0;
		buffer.append("protected void doUpdate(java.sql.PreparedStatement state) throws java.sql.SQLException {");
	}

	@Override
	public void processField(final CtField field) throws Exception {
		if (!isProcesAble(field)) {
			return;
		}
		
		if ("Id".equals(field.getName())) {
			this.idField = field;
			return;
		}
		
		Type type = getType(field);
		type.process(new CallBack<FieldProvider>() {
			@Override
			public void called(FieldProvider callback) {
				try {
					buffer.append(callback.setStatement((++ index), field));
				} catch (Exception e) {
					throw new ItasException(e);
				}
			}
		});
	}
	
	@Override
	public void endClass() throws Exception {
		Type type = getType(idField);
		type.process(new CallBack<FieldProvider>() {
			@Override
			public void called(FieldProvider callback) {
				try {
					buffer.append(callback.setStatement((++ index), idField));
				} catch (Exception e) {
					throw new ItasException(e);
				}
			}
		});
		
		buffer.append("\n}");
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
