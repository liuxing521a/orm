/**
 * 
 */
package org.itas.common.logger.support;

import org.itas.common.logger.Logger;

/**
 * @author liuzhen<liuxing521a@163.com>
 * @createTime 2015年5月7日下午5:29:22
 */
public class FailsafeLogger implements Logger 
{
	private final org.itas.common.logger.Logger logger;
	
	public FailsafeLogger(org.itas.common.logger.Logger logger) 
	{
		this.logger = logger;
	}
	
	@Override
	public void trace(String msg)
	{
		try 
		{
			logger.trace(msg);
		} 
		catch (Throwable e) 
		{
		}
	}

	@Override
	public void trace(Throwable e) 
	{
		try 
		{
			logger.trace(e.getMessage(), e);
		} 
		catch (Throwable e1) 
		{
		}
	}

	@Override
	public void trace(String msg, Throwable e) 
	{
		try 
		{
			logger.trace(msg, e);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void trace(String msg, Object arg) 
	{
		try 
		{
			logger.trace(msg, arg);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void trace(String msg, Object arg1, Object arg2) 
	{
		try 
		{
			logger.trace(msg, arg1, arg2);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void trace(String msg, Object...args) 
	{
		try 
		{
			logger.trace(msg, args);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void debug(String msg) 
	{
		try 
		{
			logger.debug(msg);
		} 
		catch (Throwable e) 
		{
		}
	}

	@Override
	public void debug(Throwable e) 
	{
		try 
		{
			logger.debug(e.getMessage(), e);
		} 
		catch (Throwable e1) 
		{
		}
	}

	@Override
	public void debug(String msg, Throwable e) 
	{
		try 
		{
			logger.debug(msg, e);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void debug(String msg, Object arg) 
	{
		try 
		{
			logger.debug(msg, arg);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void debug(String msg, Object arg1, Object arg2) 
	{
		try 
		{
			logger.debug(msg, arg1, arg2);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void debug(String msg, Object...args) 
	{
		try 
		{
			logger.debug(msg, args);
		} 
		catch (Throwable e1) 
		{
		}
	}

	@Override
	public void info(String msg) 
	{
		try 
		{
			logger.info(msg);
		} 
		catch (Throwable e) 
		{
		}
	}

	@Override
	public void info(Throwable e) 
	{
		try 
		{
			logger.info(e.getMessage(), e);
		} 
		catch (Throwable e1) 
		{
		}
	}

	@Override
	public void info(String msg, Throwable e) 
	{
		try 
		{
			logger.info(msg, e);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void info(String msg, Object arg) 
	{
		try 
		{
			logger.info(msg, arg);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void info(String msg, Object arg1, Object arg2) 
	{
		try 
		{
			logger.info(msg, arg1, arg2);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void info(String msg, Object...args) 
	{
		try 
		{
			logger.info(msg, args);
		} 
		catch (Throwable e1) 
		{
		}
	}

	@Override
	public void warn(String msg) 
	{
		try 
		{
			logger.warn(msg);
		} 
		catch (Throwable e) 
		{
		}
	}

	@Override
	public void warn(Throwable e)
	{
		try 
		{
			logger.warn(e.getMessage(), e);
		} 
		catch (Throwable e1) 
		{
		}
	}

	@Override
	public void warn(String msg, Throwable e) 
	{
		try 
		{
			logger.warn(msg, e);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void warn(String msg, Object arg) 
	{
		try 
		{
			logger.warn(msg, arg);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void warn(String msg, Object arg1, Object arg2) 
	{
		try 
		{
			logger.warn(msg, arg1, arg2);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void warn(String msg, Object...args) 
	{
		try 
		{
			logger.warn(msg, args);
		} 
		catch (Throwable e1) 
		{
		}
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
		try 
		{
			logger.error(msg, e);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void error(String msg, Object arg) 
	{
		try 
		{
			logger.error(msg, arg);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void error(String msg, Object arg1, Object arg2) 
	{
		try 
		{
			logger.error(msg, arg1, arg2);
		} 
		catch (Throwable e1) 
		{
		}
	}
	
	@Override
	public void error(String msg, Object...args) 
	{
		try 
		{
			logger.error(msg, args);
		} 
		catch (Throwable e1) 
		{
		}
	}

	@Override
	public boolean isTraceEnabled() 
	{
		try 
		{
			return logger.isTraceEnabled();
		} 
		catch (Throwable e) 
		{
			return false;
		}
	}

	@Override
	public boolean isDebugEnabled() 
	{
		try 
		{
			return logger.isDebugEnabled();
		} 
		catch (Throwable e) 
		{
			return false;
		}
	}

	@Override
	public boolean isInfoEnabled() 
	{
		try 
		{
			return logger.isInfoEnabled();
		} 
		catch (Throwable e) 
		{
			return false;
		}
	}

	@Override
	public boolean isWarnEnabled() 
	{
		try 
		{
			return logger.isWarnEnabled();
		} 
		catch (Throwable e)
		{
			return false;
		}
	}

	@Override
	public boolean isErrorEnabled() 
	{
		try 
		{
			return logger.isErrorEnabled();
		} 
		catch (Throwable e) 
		{
			return false;
		}
	}

}
