package org.itas.core;


/**
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-14
 */
public interface Hook {
	
	/**
	 * <p>获取当前线程钩子</p>
	 * @return 当前线程钩子
	 */
	Hook current();
	
	/**
	 * <p>执行之前</p>
	 * @param excutor 执行器
	 * @param o 附带参数
	 */
	void onBeforeExecute(Object o);
	
	/**
	 * <p>执行之后</p>
	 * @param excutor 执行器
	 * @param o 附带参数
	 */
	void onAfterExecute(Object o);
	
}
