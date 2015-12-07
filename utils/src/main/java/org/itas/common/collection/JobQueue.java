package org.itas.common.collection;


public final class JobQueue {
	
	private int count;								// ��ǰ��������
	private int state;								// ״̬
    private int processedJobCount;					// �����������
    private int waitedTimes;						// �ȴ�ʱ��
    private Thread runner;							// ִ����
    private Runnable[] jobs;						// ���񼯺�
    private Runnable currentJob;					// ��ʱ����

    public JobQueue() {
    	jobs = new Runnable[1000];
    }

    private class Runner extends Thread {
    	
    	public Runner() {
    		super("processorThread");
    	}

    	@Override
        public void run() {
            while(runner == this) {
                state = 1;
                Runnable runnable = getNextJob();
                if(runnable == null)
                    continue;
                if(runner != this)
                    break;
                try {
                    state = 2;
                    currentJob = runnable;
                    runnable.run();
                    state = 0;
                    currentJob = null;
                    processedJobCount++;
                } catch(Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            state = 0;
            runner = null;
        }

    }

    public void addJob(Runnable runnable) {
        synchronized(this) {
            if(count >= jobs.length) {
                Runnable[] arunnable = new Runnable[count * 2];
                System.arraycopy(jobs, 0, arunnable, 0, count);
                jobs = arunnable;
            }
            jobs[count++] = runnable;
            notify();
        }
    }
    
    Runnable getNextJob() {
        synchronized(this) {
            if(count <= 0){
            	try {
            		wait();
            		waitedTimes++;
            	} catch(InterruptedException e) {
            		e.printStackTrace();
            	}
            }
            if(count <= 0) {
                Runnable runnable1 = null;
                return runnable1;
            }
            Runnable runnable = jobs[0];
            for(int i = 1; i < count; i++)
                jobs[i - 1] = jobs[i];

            jobs[--count] = null;
            return runnable;
        }
    }

    public void start() {
        if(runner == null) {
            runner = new Runner();
            runner.setDaemon(true);
            runner.start();
        }
    }
    
    public void stop() {
        if(runner != null) {
            runner.interrupt();
            runner = null;
        }
        synchronized(this) {
            for(int i = 0; i < count; i++)
                jobs[i] = null;
            count = 0;
            notify();
        }
    }

    public Runnable getCurrentJob() {
        return currentJob;
    }

    public int getState() {
        return state;
    }

    public int getJobCount() {
        return count;
    }

    public int getWaitedTimes() {
        return waitedTimes;
    }

    public int getCompletedJobCount() {
        return processedJobCount;
    }

}
