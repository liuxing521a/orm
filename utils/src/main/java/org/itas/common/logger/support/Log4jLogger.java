/**
 * 
 */
package org.itas.common.logger.support;

import org.apache.log4j.Level;
import org.itas.common.logger.Logger;

/**
 * apache log4j实现
 * 
 * @author liuzhen<liuxing521a@163.com>
 * @createTime 2015年5月8日上午9:37:13
 */
public class Log4jLogger implements Logger 
{
	private static final String FQCN = FailsafeLogger.class.getName();
	
	private final org.apache.log4j.Logger logger;
	
	public Log4jLogger(org.apache.log4j.Logger logger)
	{
		this.logger = logger;
	}

	@Override
	public void trace(String msg) 
	{
		logger.log(FQCN, Level.TRACE, msg, null);
	}

	@Override
	public void trace(Throwable e) 
	{
		logger.log(FQCN, Level.TRACE, e.getMessage(), e);
	}

	@Override
	public void trace(String msg, Throwable e) 
	{
		logger.log(FQCN, Level.TRACE, msg, e);
	}

	@Override
	public void trace(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(FQCN, Level.TRACE, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void trace(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(FQCN, Level.TRACE, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void trace(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(FQCN, Level.TRACE, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void debug(String msg)
	{
		logger.log(FQCN, Level.DEBUG, msg, null);
	}

	@Override
	public void debug(Throwable e) 
	{
		logger.log(FQCN, Level.DEBUG, e.getMessage(), e);
	}

	@Override
	public void debug(String msg, Throwable e) 
	{
		logger.log(FQCN, Level.DEBUG, msg, e);
	}
	
	@Override
	public void debug(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(FQCN, Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void debug(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(FQCN, Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void debug(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(FQCN, Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
	}

	@Override
	public void info(String msg) 
	{
		logger.log(FQCN, Level.INFO, msg, null);
	}

	@Override
	public void info(Throwable e) 
	{
		logger.log(FQCN, Level.INFO, e.getMessage(), e);
	}

	@Override
	public void info(String msg, Throwable e) 
	{
		logger.log(FQCN, Level.INFO, msg, e);
	}

	@Override
	public void info(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(FQCN, Level.INFO, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void info(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(FQCN, Level.INFO, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void info(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(FQCN, Level.INFO, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void warn(String msg) 
	{
		logger.log(FQCN, Level.WARN, msg, null);
	}

	@Override
	public void warn(Throwable e) 
	{
		logger.log(FQCN, Level.WARN, e.getMessage(), e);
	}

	@Override
	public void warn(String msg, Throwable e) 
	{
		logger.log(FQCN, Level.WARN, msg, e);
	}

	@Override
	public void warn(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(FQCN, Level.WARN, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void warn(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(FQCN, Level.WARN, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void warn(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(FQCN, Level.WARN, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void error(String msg) {
		logger.log(FQCN, Level.ERROR, msg, null);
	}

	@Override
	public void error(Throwable e) 
	{
		logger.log(FQCN, Level.ERROR, e.getMessage(), e);
	}

	@Override
	public void error(String msg, Throwable e) 
	{
		logger.log(FQCN, Level.ERROR, msg, e);
	}
	
	@Override
	public void error(String format, Object argument) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, argument);
		logger.log(FQCN, Level.ERROR, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void error(String format, Object arg1, Object arg2) 
	{
		FormattingTuple tuple = FormattingTuple.format(format, arg1, arg2);
		logger.log(FQCN, Level.ERROR, tuple.getMessage(), tuple.getThrowable());
	}
	
	@Override
	public void error(String format, Object...msgs) 
	{
		FormattingTuple tuple = FormattingTuple.arrayFormat(format, msgs);
		logger.log(FQCN, Level.ERROR, tuple.getMessage(), tuple.getThrowable());
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
		return logger.isEnabledFor(Level.WARN);
	}

	@Override
	public boolean isErrorEnabled() 
	{
		return logger.isEnabledFor(Level.ERROR);
	}

}
