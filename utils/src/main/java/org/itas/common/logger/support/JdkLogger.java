/**
 * 
 */
package org.itas.common.logger.support;

import java.util.logging.Level;

import org.itas.common.logger.Logger;

/**
 * java自带日志
 * 
 * @author liuzhen<liuxing521a@163.com>
 * @createTime 2015年5月7日下午5:13:00
 */
public class JdkLogger implements Logger 
{
	private final java.util.logging.Logger logger;
	
	public JdkLogger(java.util.logging.Logger logger)
	{
		this.logger = logger;
	}

	@Override
	public void trace(String msg)
	{
		logger.fine(msg);
	}

	@Override
	public void trace(Throwable e) 
	{
		logger.log(Level.FINER, e.getMessage(), e);
	}

	@Override
	public void trace(String msg, Throwable e) 
	{
		logger.log(Level.FINER, msg, e);
	}

	@Override
	public void trace(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(Level.FINER, tuple.getMessage());
	}
	
	@Override
	public void trace(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(Level.FINER, tuple.getMessage());
	}
	
	@Override
	public void trace(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(Level.FINER, tuple.getMessage());
	}

	@Override
	public void debug(String msg) 
	{
		logger.log(Level.FINE, msg);
	}

	@Override
	public void debug(Throwable e) 
	{
		logger.log(Level.FINE, e.getMessage(), e);
	}

	@Override
	public void debug(String msg, Throwable e) 
	{
		logger.log(Level.FINE, msg, e);
	}
	
	@Override
	public void debug(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(Level.FINE, tuple.getMessage());
	}
	
	@Override
	public void debug(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(Level.FINE, tuple.getMessage());
	}
	
	@Override
	public void debug(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(Level.FINE, tuple.getMessage());
	}

	@Override
	public void info(String msg) 
	{
		logger.log(Level.INFO, msg);
	}

	@Override
	public void info(Throwable e) 
	{
		logger.log(Level.INFO, e.getMessage(), e);
	}

	@Override
	public void info(String msg, Throwable e) 
	{
		logger.log(Level.INFO, msg, e);
	}

	@Override
	public void info(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(Level.INFO, tuple.getMessage());
	}
	
	@Override
	public void info(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(Level.INFO, tuple.getMessage());
	}
	
	@Override
	public void info(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(Level.INFO, tuple.getMessage());
	}
	
	@Override
	public void warn(String msg) 
	{
		logger.log(Level.WARNING, msg);
	}

	@Override
	public void warn(Throwable e) 
	{
		logger.log(Level.WARNING, e.getMessage(), e);
	}

	@Override
	public void warn(String msg, Throwable e) 
	{
		logger.log(Level.WARNING, msg, e);
	}
	
	@Override
	public void warn(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(Level.WARNING, tuple.getMessage());
	}
	
	@Override
	public void warn(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(Level.WARNING, tuple.getMessage());
	}
	
	@Override
	public void warn(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(Level.WARNING, tuple.getMessage());
	}

	@Override
	public void error(String msg) 
	{
		logger.log(Level.SEVERE, msg);
	}

	@Override
	public void error(Throwable e) 
	{
		logger.log(Level.SEVERE, e.getMessage(), e);
	}

	@Override
	public void error(String msg, Throwable e) 
	{
		logger.log(Level.SEVERE, msg, e);
	}
	
	@Override
	public void error(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(Level.SEVERE, tuple.getMessage());
	}
	
	@Override
	public void error(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(Level.SEVERE, tuple.getMessage());
	}
	
	@Override
	public void error(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(Level.SEVERE, tuple.getMessage());
	}

	@Override
	public boolean isTraceEnabled()
	{
		return logger.isLoggable(Level.FINER);
	}

	@Override
	public boolean isDebugEnabled() 
	{
		return logger.isLoggable(Level.FINE);
	}

	@Override
	public boolean isInfoEnabled()
	{
		return logger.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled() 
	{
		return logger.isLoggable(Level.WARNING);
	}
	
	@Override
	public boolean isErrorEnabled() 
	{
		return logger.isLoggable(Level.SEVERE);
	}

}
