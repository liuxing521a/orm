/**
 * 
 */
package org.itas.common.logger.support;

import org.itas.common.logger.Logger;

/**
 * sf4j日志实现
 * 
 * @author liuzhen<liuxing521a@163.com>
 * @createTime 2015年5月7日下午5:23:22
 */
public class Sf4jLogger implements Logger 
{
	private final org.slf4j.Logger logger;
	
	public Sf4jLogger(org.slf4j.Logger logger) 
	{
		this.logger = logger;
	}
	
	@Override
	public void trace(String msg)
	{
		logger.trace(msg);
	}

	@Override
	public void trace(Throwable e) 
	{
		logger.trace(e.getMessage(), e);
	}

	@Override
	public void trace(String msg, Throwable e) 
	{
		logger.trace(msg, e);
	}
	
	@Override
	public void trace(String format, Object argument) 
	{
		logger.trace(format, argument);
	}
	
	@Override
	public void trace(String format, Object argument1, Object argument2)
	{
		logger.trace(format, argument1, argument2);
	}
	
	@Override
	public void trace(String format, Object... arguments) 
	{
		logger.trace(format, arguments);
	}

	@Override
	public void debug(String msg) 
	{
		logger.debug(msg);
	}

	@Override
	public void debug(Throwable e) 
	{
		logger.debug(e.getMessage(), e);
	}

	@Override
	public void debug(String msg, Throwable e) 
	{
		logger.debug(msg, e);
	}

	@Override
	public void debug(String format, Object argument) 
	{
		logger.debug(format, argument);
	}
	
	@Override
	public void debug(String format, Object argument1, Object argument2)
	{
		logger.debug(format, argument1, argument2);
	}
	
	@Override
	public void debug(String format, Object... arguments) 
	{
		logger.debug(format, arguments);
	}
	
	@Override
	public void info(String msg) 
	{
		logger.info(msg);
	}

	@Override
	public void info(Throwable e) 
	{
		logger.info(e.getMessage(), e);
	}

	@Override
	public void info(String msg, Throwable e) 
	{
		logger.info(msg, e);
	}
	
	@Override
	public void info(String format, Object argument) 
	{
		logger.info(format, argument);
	}
	
	@Override
	public void info(String format, Object argument1, Object argument2)
	{
		logger.info(format, argument1, argument2);
	}
	
	@Override
	public void info(String format, Object... arguments) 
	{
		logger.info(format, arguments);
	}

	@Override
	public void warn(String msg) 
	{
		logger.warn(msg);
	}

	@Override
	public void warn(Throwable e)
	{
		logger.warn(e.getMessage(), e);
	}

	@Override
	public void warn(String msg, Throwable e) 
	{
		logger.warn(msg, e);
	}

	@Override
	public void warn(String format, Object argument) 
	{
		logger.warn(format, argument);
	}
	
	@Override
	public void warn(String format, Object argument1, Object argument2)
	{
		logger.warn(format, argument1, argument2);
	}
	
	@Override
	public void warn(String format, Object... arguments) 
	{
		logger.warn(format, arguments);
	}
	
	@Override
	public void error(String msg) 
	{
		logger.error(msg);
	}

	@Override
	public void error(Throwable e)
	{
		logger.error(e.getMessage(), e);
	}

	@Override
	public void error(String msg, Throwable e) 
	{
		logger.error(msg, e);
	}

	@Override
	public void error(String format, Object argument) 
	{
		logger.error(format, argument);
	}
	
	@Override
	public void error(String format, Object argument1, Object argument2)
	{
		logger.error(format, argument1, argument2);
	}
	
	@Override
	public void error(String format, Object... arguments) 
	{
		logger.error(format, arguments);
	}
	
	@Override
	public boolean isTraceEnabled() 
	{
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isDebugEnabled() 
	{
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() 
	{
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() 
	{
		return logger.isWarnEnabled();
	}

	@Override
	public boolean isErrorEnabled() 
	{
		return logger.isErrorEnabled();
	}

}
