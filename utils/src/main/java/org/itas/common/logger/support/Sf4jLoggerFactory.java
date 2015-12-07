/**
 * 
 */
package org.itas.common.logger.support;

import java.io.File;

import org.itas.common.logger.Level;
import org.itas.common.logger.Logger;
import org.itas.common.logger.LoggerFactorySupport;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;

/**
 * @author liuzhen<liuxing521a@163.com>
 * @createTime 2015年5月7日下午5:57:01
 */
public class Sf4jLoggerFactory implements LoggerFactorySupport 
{
	private File file;
	
	public Sf4jLoggerFactory()
	{
		
	}
	
	@Override
	public Logger getLogger(Class<?> key) 
	{
		return new Sf4jLogger(LoggerFactory.getLogger(key));
	}

	@Override
	public Logger getLogger(String key) 
	{
		return new Sf4jLogger(LoggerFactory.getLogger(key));
	}

	@Override
	public Level getLevel() 
	{
		ILoggerFactory logcontext = LoggerFactory.getILoggerFactory();
		if (logcontext instanceof LoggerContext)
		{
			LoggerContext context = (LoggerContext)logcontext;
			ch.qos.logback.classic.Logger logger = context.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
			return fromSf4jLevel(logger.getLevel());
		}
		
		return Level.OFF;
	}

	@Override
	public void setLevel(Level level) 
	{
		ILoggerFactory logcontext = LoggerFactory.getILoggerFactory();
		if (logcontext instanceof LoggerContext)
		{
			LoggerContext context = (LoggerContext)logcontext;
			ch.qos.logback.classic.Logger logger = context.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
			logger.setLevel(toSf4jLevel(level));
		}
	}

	@Override
	public File getFile() 
	{
		return file;
	}

	@Override
	public void setFile(File file)
	{
		
	}
	
	private static ch.qos.logback.classic.Level toSf4jLevel(Level level)
	{
		if (level == Level.ALL)
		{
			return ch.qos.logback.classic.Level.ALL;
		}
		else if (level == Level.TRACE)
		{
			return ch.qos.logback.classic.Level.TRACE;
		}
		else if (level == Level.DEBUG)
		{
			return ch.qos.logback.classic.Level.DEBUG;
		} 
		else if (level == Level.INFO)
		{
			return ch.qos.logback.classic.Level.INFO;
		}
		else if (level == Level.WARN)
		{
			return ch.qos.logback.classic.Level.WARN;
		}
		else if (level == Level.ERROR)
		{
			return ch.qos.logback.classic.Level.ERROR;
		} 
		else
		{
			return ch.qos.logback.classic.Level.OFF;
		}
	}
	
	private static Level fromSf4jLevel(ch.qos.logback.classic.Level level)
	{
		if (level == ch.qos.logback.classic.Level.ALL)
		{
			return Level.ALL;
		}
		else if (level == ch.qos.logback.classic.Level.TRACE)
		{
			return Level.TRACE;
		}
		else if (level == ch.qos.logback.classic.Level.DEBUG)
		{
			return Level.DEBUG;
		} 
		else if (level == ch.qos.logback.classic.Level.INFO)
		{
			return Level.INFO;
		}
		else if (level == ch.qos.logback.classic.Level.WARN)
		{
			return Level.WARN;
		}
		else if (level == ch.qos.logback.classic.Level.ERROR)
		{
			return Level.ERROR;
		} 
		else
		{
			return Level.OFF;
		}
	}

}
