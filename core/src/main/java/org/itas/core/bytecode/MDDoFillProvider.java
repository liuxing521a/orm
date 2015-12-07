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
class MDDoFillProvider extends AbstractMethodProvider {

	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);

		buffer.append("protected void doFill(java.sql.ResultSet result) throws java.sql.SQLException {");
	}
	
	@Override
	public void processField(final CtField field) throws Exception {
		if (!isProcesAble(field) || "Id".equals(field.getName())) {
			return;
		}
		
		Type type = getType(field);
		type.process(new CallBack<FieldProvider>() {
			@Override
			public void called(FieldProvider provider) {
				try {
					buffer.append(provider.getResultSet(field));
				} catch (Exception e) {
					throw new ItasException(e);
				}
			}
		});
	}
	
	@Override
	public void endClass() throws Exception {
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
