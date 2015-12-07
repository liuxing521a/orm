/**
 * 
 */
package org.itas.common.logger;

import java.io.File;

/**
 * 日志输出器提供者
 * 
 * @author liuzhen<liuxing521a@163.com>
 * @createTime 2015年5月7日下午5:06:14
 */
public interface LoggerFactorySupport 
{

	/**
	 * 获取日志输出器
	 *
	 * @param key 分类键
	 * @return 日志输出器, 后验条件: 不返回null.
	 */
	public Logger getLogger(Class<?> key);
	
	/**
	 * 获取日志输出器
	 *
	 * @param key 分类键
	 * @return 日志输出器, 后验条件: 不返回null.
	 */
	public Logger getLogger(String key);
	
	/**
	 * 获取日志等级
	 * 
	 * @return 日志等级
	 */
	public Level getLevel();
	
	/**
	 * 设置日志输出等级
	 * 
	 * @param level
	 */
	public void setLevel(Level level);
	
	/**
	 * 获取日志文件
	 * 
	 * @return 日志文件
	 */
	public File getFile();
	
	/**
	 * 设置日志文件
	 * 
	 * @param file
	 */
	public void setFile(File file);
}
