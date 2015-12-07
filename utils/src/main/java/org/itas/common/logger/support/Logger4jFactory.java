/**
 * 
 */
package org.itas.common.logger.support;

import java.io.File;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.itas.common.logger.Level;
import org.itas.common.logger.Logger;
import org.itas.common.logger.LoggerFactorySupport;

/**
 * @author liuzhen<liuxing521a@163.com>
 * @createTime 2015年5月8日上午9:46:59
 */
public class Logger4jFactory implements LoggerFactorySupport
{
	
	private File file;
	
	public Logger4jFactory()
	{
		org.apache.log4j.Logger logger = LogManager.getRootLogger();
		if (logger != null)
		{
			@SuppressWarnings("unchecked")
			Enumeration<Appender> appenders = logger.getAllAppenders();
			for (Appender appender; (appenders != null && appenders.hasMoreElements()); ) 
			{
				appender = appenders.nextElement();
				if (appender instanceof FileAppender)
				{
					file = new File(((FileAppender)appender).getFile());
					break;
				}
			}
		}
	}

	@Override
	public Logger getLogger(Class<?> key) 
	{
		return new Log4jLogger(LogManager.getLogger(key));
	}

	@Override
	public Logger getLogger(String key) 
	{
		return new Log4jLogger(LogManager.getLogger(key));
	}

	@Override
	public Level getLevel() 
	{
		return fromLog4JLevel(LogManager.getRootLogger().getLevel());
	}

	@Override
	public void setLevel(Level level) 
	{
		LogManager.getRootLogger().setLevel(toLog4JLevel(level));
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
	
	private static org.apache.log4j.Level toLog4JLevel(Level level)
	{
		if (level == Level.ALL)
		{
			return org.apache.log4j.Level.ALL;
		}
		else if (level == Level.TRACE)
		{
			return org.apache.log4j.Level.TRACE;
		}
		else if (level == Level.DEBUG)
		{
			return org.apache.log4j.Level.DEBUG;
		} 
		else if (level == Level.INFO)
		{
			return org.apache.log4j.Level.INFO;
		}
		else if (level == Level.WARN)
		{
			return org.apache.log4j.Level.WARN;
		}
		else if (level == Level.ERROR)
		{
			return org.apache.log4j.Level.ERROR;
		} 
		else
		{
			return org.apache.log4j.Level.OFF;
		}
	}
	
	private static Level fromLog4JLevel(org.apache.log4j.Level level)
	{
		if (level == org.apache.log4j.Level.ALL)
		{
			return Level.ALL;
		}
		else if (level == org.apache.log4j.Level.TRACE)
		{
			return Level.TRACE;
		}
		else if (level == org.apache.log4j.Level.DEBUG)
		{
			return Level.DEBUG;
		} 
		else if (level == org.apache.log4j.Level.INFO)
		{
			return Level.INFO;
		}
		else if (level == org.apache.log4j.Level.WARN)
		{
			return Level.WARN;
		}
		else if (level == org.apache.log4j.Level.ERROR)
		{
			return Level.ERROR;
		} 
		else
		{
			return Level.OFF;
		}
	}

}
