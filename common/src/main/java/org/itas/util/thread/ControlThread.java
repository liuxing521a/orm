package org.itas.util.thread;

import org.itas.util.Logger;


/**
 * 线程池执行线程
 * @author liuzhen(liuxing521a@gmail.com)
 * @createTime 2012-4-27上午05:43:02
 */
final class ControlThread extends Thread 
{

	ThreadPool p; 		// 使用的线程池
	Runnable run; 		// 执行的线程
	boolean isFlag; 	// 停止线程
	boolean isRun; 		// 激活执行动作

	ControlThread(ThreadPool p, int index)
	{
		isFlag = false;
		isRun = false;
		this.p = p;
		this.setName(String.format("%s-%d", p.getName(), index));
	}

	@Override
	public void run() 
	{
		while (!isFlag) 
		{
			try {
				synchronized (this) 
				{
					while (!isRun) 
					{
						this.wait();
					}
				}

				try {
					if (isRun && run != null) 
					{
						run.run();
					}
				} catch (Throwable t) {
                    Logger.error(this.getClass().getName(), t);
					isFlag = true;
					isRun = false;
					p.notifyThreadEnd(this);
				} finally {
					if (isRun) 
					{
						isRun = false;
						p.returnThread(this);
					}
				}
			} catch (InterruptedException e) {
				Logger.error("Unexpected exception:" + this.getClass().getName(), e);
			}
		}
	}

	synchronized void execute(Runnable toRun) 
	{
		this.run = toRun;
		this.isRun = true;
		this.notify();
	}

	@Override
	public synchronized void start() 
	{
		if (isFlag || this.isAlive()) 
		{
			safeStop();
		}

		isFlag = false;
		super.start();
	}

	synchronized void safeStop() 
	{
		isFlag = true;
		this.notify();
	}

}
