package org.itas.util.thread;

import java.util.concurrent.Executor;

import org.itas.util.Logger;


/**
 * 线程池
 * @author liuzhen(liuxing521a@gmail.com)
 * @createTime 2012-4-27上午05:40:28
 */
public final class ThreadPool implements Executor {

    public static final int THREADPOOL_MIN_IDEL_THREADS =  Runtime.getRuntime().availableProcessors();
    public static final int THREADPOOL_MAX_IDEL_THREADS = THREADPOOL_MIN_IDEL_THREADS << 4;
    public static final int THREADPOOL_MIN_MAXTHREADS = THREADPOOL_MAX_IDEL_THREADS;
    public static final int THREADPOOL_MAX_MAXTHREADS = THREADPOOL_MIN_MAXTHREADS << 2;
    public static final int THREADPOOL_WORK_WAIT_TIMEOUT = 60 * 1000;

	private ControlThread[] pool;
	
	private ThreadPoolDog monitor;

	private String name;		// 线程池名称

	private int maxThreads; 	// 线程池中可以打开的最大线程数

	private int minIdelThreads; // 最小空闲线程数

	private int maxIdelThreads; // 最大空闲线程数

	private int poolThreads; 	// 线程池中线程数

	private int busyThreads; 	// 正在使用线程数

	private boolean isFlag; 	// 线程池开关

	private boolean isDaemon;	// 是否守护线程

	private int threadPriority; // 线程优先级

	public ThreadPool() 
	{
		this("Thread-Pool");
	}

	public ThreadPool(String name) 
	{
		this(name, THREADPOOL_MAX_MAXTHREADS,
				THREADPOOL_MIN_IDEL_THREADS,
				THREADPOOL_MAX_IDEL_THREADS);
	}

	public ThreadPool(int maxThreads, int minIdelThreads, int maxIdelThreads) 
	{
		this("thread-pool", maxThreads, minIdelThreads, maxIdelThreads);
	}

	public ThreadPool(String name, int maxThreads, int minIdelThreads, int maxIdelThreads) 
	{
		this.name = name;
		this.maxThreads = maxThreads;
		this.minIdelThreads = minIdelThreads;
		this.maxIdelThreads = maxIdelThreads;
		this.poolThreads = 0;
		this.busyThreads = 0;
		this.isDaemon = true;
		this.isFlag = false;
		this.threadPriority = Thread.NORM_PRIORITY;
		this.start();
	}

	public synchronized void start() 
	{
		isFlag = false;
		poolThreads = 0;
		busyThreads = 0;

		adjustLimits();

		pool = new ControlThread[maxThreads];

		openThreads(minIdelThreads);
		if (maxIdelThreads < maxThreads) 
		{
			monitor = new ThreadPoolDog(this);
		}
	}

	public synchronized void shutdown() 
	{
		if (!isFlag) 
		{
			isFlag = true;
			if (monitor != null) 
			{
				monitor.terminate();
				monitor = null;
			}
			
			for (int i = 0; i < poolThreads - busyThreads; i++) 
			{
				try {
					pool[i].safeStop();
				} catch (Throwable t) {
                    Logger.error("ThreadPool.shutting down exception:", t);
				}
			}
			busyThreads = poolThreads = 0;
			pool = null;
			notifyAll();
		}
	}

	@Override
	public void execute(Runnable r) 
	{
		ControlThread c = obtainFreeRunnable();
		c.execute(r);
	}
	
	public synchronized void setThreadPriority(int threadPriority) 
	{

		if (threadPriority < Thread.MIN_PRIORITY) 
		{
			throw new IllegalArgumentException("new priority < MIN_PRIORITY");
		} else if (threadPriority > Thread.MAX_PRIORITY) 
		{
			throw new IllegalArgumentException("new priority > MAX_PRIORITY");
		}

		this.threadPriority = threadPriority;
		for (ControlThread t : pool)
		{
			t.setPriority(threadPriority);
		}

	}

	public synchronized int getThreadPriority() 
	{
		return threadPriority;
	}

	public void setMaxThreads(int maxThreads) 
	{
		this.maxThreads = maxThreads;
	}

	public int getMaxThreads() 
	{
		return maxThreads;
	}

	public void setMinIdelThreads(int minIdelThreads) 
	{
		this.minIdelThreads = minIdelThreads;
	}

	public int getMinSpareThreads() 
	{
		return minIdelThreads;
	}

	public void setMaxIdelThreads(int maxIdelThreads)
	{
		this.maxIdelThreads = maxIdelThreads;
	}

	public int getMaxIdelThreads() 
	{
		return maxIdelThreads;
	}

	public int getCurrentThreadCount() 
	{
		return poolThreads;
	}

	public int getCurrentThreadsBusy() 
	{
		return busyThreads;
	}

	public boolean isDaemon() 
	{
		return isDaemon;
	}

	public void setDaemon(boolean b)
	{
		isDaemon = b;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}

	private ControlThread obtainFreeRunnable()
	{
		if (isFlag) 
		{
			throw new IllegalStateException();
		}

		ControlThread c = null;
		synchronized (this) 
		{
			while (busyThreads == poolThreads && !isFlag) 
			{
				if (poolThreads < maxThreads)
				{
					openThreads(poolThreads + minIdelThreads);
				} else 
				{
					Logger.warn("threadpool.busy: [currentThreadCount={}],[maxThreads={}]",
							new Object[]{poolThreads, maxThreads});
					try {
						this.wait();
					} catch (InterruptedException e) {
						Logger.error("Unexpected exception", e);
					}
				}
			}
			
			if (0 == poolThreads || isFlag) 
			{
				throw new IllegalStateException();
			}

			int pos = poolThreads - busyThreads - 1;
			c = pool[pos];
			pool[pos] = null;
			busyThreads++;
		}

//		Logger.info("[poolThreads = {}],[busyThreads={}]", new Object[]{poolThreads, busyThreads});
		return c;
	}

	synchronized void returnThread(ControlThread c) 
	{
		if (0 == poolThreads || isFlag) 
		{
			c.safeStop();
			return;
		}

		busyThreads--;
		pool[poolThreads - busyThreads - 1] = c;
		notify();
//		Logger.info("[poolThreads = {}],[busyThreads= {}]",	new Object[]{poolThreads, busyThreads});
	}

	synchronized void releaseFreeThreads()
	{
		if (isFlag) 
		{
			return;
		}

		int tmpFreeThreads = poolThreads - busyThreads;
		if (tmpFreeThreads <= maxIdelThreads)
		{
			return;
		}
		
		int toFree = tmpFreeThreads - maxIdelThreads;
		for (int i = 0; i < toFree; i++) 
		{
			ControlThread c = pool[poolThreads - 1];
			c.safeStop();
			pool[poolThreads - 1] = null;
			poolThreads--;
		}

	}
	
	private void openThreads(int toOpen) 
	{
		if (toOpen > maxThreads) 
		{
			toOpen = maxThreads;
		}

		ControlThread control = null;
		for (int i = poolThreads; i < toOpen; i++) 
		{
			control = new ControlThread(this, i);
			control.start();
			pool[i - busyThreads] = control;
		}

		poolThreads = toOpen;
	}

	synchronized void notifyThreadEnd(ControlThread c) 
	{
		busyThreads--;
		poolThreads--;
		notify();
	}

	private void adjustLimits() 
	{
		if (maxThreads <= 0) 
		{
			maxThreads = THREADPOOL_MAX_MAXTHREADS;
		} else if (maxThreads < THREADPOOL_MIN_MAXTHREADS)
		{
			maxThreads = THREADPOOL_MIN_MAXTHREADS;
		}

		if (maxIdelThreads >= maxThreads)
		{
			maxIdelThreads = maxThreads;
		}

		if (maxIdelThreads <= 0) 
		{
			if (1 == maxThreads) 
			{
				maxIdelThreads = 1;
			} else 
			{
				maxIdelThreads = maxThreads >> 1;
			}
		}

		if (minIdelThreads > maxIdelThreads) 
		{
			minIdelThreads = maxIdelThreads;
		}

		if (minIdelThreads <= 0) 
		{
			if (1 == maxIdelThreads) 
			{
				minIdelThreads = 1;
			} else 
			{
				minIdelThreads = maxIdelThreads >> 1;
			}
		}
	}

    @Override
    public String toString() {
        return String.format( "maxThread=%s,totalthreads=%s,useThreads=%s",
                maxThreads, poolThreads, busyThreads );
    }
}
