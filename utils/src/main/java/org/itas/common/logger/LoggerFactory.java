/**
 * 
 */
package org.itas.common.logger;

import java.io.File;

import org.itas.common.logger.support.FailsafeLogger;
import org.itas.common.logger.support.JdkLoggerFactory;
import org.itas.common.logger.support.Logger4jFactory;
import org.itas.common.logger.support.Sf4jLoggerFactory;

/**
 * 日志产生工厂
 * 
 * @author liuzhen<liuxing521a@163.com>
 * @createTime 2015年5月7日下午5:04:50
 */
public class LoggerFactory 
{
	private static volatile LoggerFactorySupport LOGGER_FACTORY;
	
	static
	{
		try
		{
			setLoggerFactory(new Sf4jLoggerFactory());
		}
		catch(Throwable e)
		{
			try 
			{
				setLoggerFactory(new Logger4jFactory());
			} 
			catch (Throwable e1) 
			{
				setLoggerFactory(new JdkLoggerFactory());
			}
		}
	}
	
	private LoggerFactory()
	{
	}
	
	/**
	 * 设置日志输出器提供者
	 * 
	 * @param loggerFactory 日志输出器提供者
	 */
	public static void setLoggerFactory(LoggerFactorySupport loggerFactory)
	{
		if (loggerFactory != null)
		{
			Logger logger = loggerFactory.getLogger(LoggerFactory.class);
			logger.info("using logger: " + loggerFactory.getClass().getName());
			LoggerFactory.LOGGER_FACTORY = loggerFactory;
		}
	}

	/**
	 * 获取日志输出器
	 * 
	 * @param key  分类键
	 * @return 日志输出器, 后验条件: 不返回null.
	 */
	public static Logger getLogger(Class<?> key)
	{
		return new FailsafeLogger(LOGGER_FACTORY.getLogger(key));
	}
	
	/**
	 * 获取日志输出器
	 * 
	 * @param key 分类键
	 * @return 日志输出器, 后验条件: 不返回null.
	 */
	public static Logger getLogger(String key)
	{
		return new FailsafeLogger(LOGGER_FACTORY.getLogger(key));
	}
	
	/**
	 * 获取日志级别
	 * 
	 * @return 日志级别
	 */
	public static Level getLevel()
	{
		return LOGGER_FACTORY.getLevel();
	}
	
	/**
	 * 动态设置日志级别
	 * 
	 * @param level 日志级别
	 */
	public static void setLevel(Level level)
	{
		LOGGER_FACTORY.setLevel(level);
	}
	
	/**
	 * 获取日志文件
	 * 
	 * @return 日志文件
	 */
	public static File getFile()
	{
		return LOGGER_FACTORY.getFile();
	}
}
