package org.itas.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.itas.common.Utils.TimeUtil;

/**
 * 定时任务执行器，完全代替TimerTask;使用本执行器的代码禁止使用while(true);for(;;)等死循环体 
 * @author liuzhen
 */
public final class ScheduledExecutor {
	
	private static ScheduledExecutorService workers;

	public static void initialize(int threadCount) {
		workers = new ScheduledThreadPoolExecutor(threadCount);
	}
	
	public static void shutdown() {
		workers.shutdownNow();
	}
	
	private ScheduledExecutor() {
		throw new RuntimeException("can not new instance");
	}

	/**
	 * <p>执行</p>
	 * @param command 执行的Runnable
	 * @return 执行结果
	 */
	public static final Future<?> run(Runnable command) {
		return workers.submit(command);
	}

	/***
	 * <p>延迟执行</p>
	 * @param command 执行的Runnable
	 * @param delayMillis 延迟时间[毫秒]
	 * @return 执行结果
	 */
	public static final ScheduledFuture<?> schedule(Runnable command, long delayMillis) {
		return workers.schedule(command, delayMillis, TimeUnit.MILLISECONDS);
	}

	/**
	 * <p>延迟循环执行</p>
	 * @param command 执行的Runnable
	 * @param delayMillis 延迟时间
	 * @param period 每次执行间隔时间[毫秒]
	 * @return	执行结果
	 */
	public static final ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long delayMillis, long period) {
		return workers.scheduleAtFixedRate(command, delayMillis, period, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 
	 * @param command 执行的Runnable
	 * @param time 定时的时间格式必须是 HH:mm:ss或者yyyy-MM-dd HH:mm:ss
	 * @param period 间隔时间[毫秒]建 议用{@link TimeUtil}中定义
	 * @return
	 */
	public static final ScheduledFuture<?> scheduleAtFixedTime(Runnable command, String time, long period) {
		return scheduleAtFixedRate(command, getDelayMillis(time), period);
	}
	
	private static final long getDelayMillis(String time) {
		Timestamp timeStamp;
		if (time.length() == 19) {
			timeStamp = Timestamp.valueOf(time);
		} else if (time.length() == 8) {
			String currentTime = DATA_FORMAT.get().format(TimeUtil.dateNow());
			currentTime = String.format("%s %s", currentTime, time);
			
			timeStamp = Timestamp.valueOf(currentTime);
		} else {
			throw new IllegalArgumentException("argument must be [yyyy-MM-dd HH:mm:ss] or [HH:mm:ss]");
		}
		
		
		while (timeStamp.getTime() < TimeUtil.systemTime()) {
			timeStamp.setTime(timeStamp.getTime() + TimeUtil.MILLIS_PER_DAY);
		}
		
		return (timeStamp.getTime() - TimeUtil.systemTime());
	}
	
	private static final ThreadLocal<SimpleDateFormat> DATA_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	public static void main(String[] args) throws ParseException {
		initialize(3);
		scheduleAtFixedTime(new Runnable() {
			@Override
			public void run() {
				Logger.trace("aaaaaaaaaaaaaaaaaa");
			}
		}, "16:01:00", 5000);
		scheduleAtFixedTime(new Runnable() {
			@Override
			public void run() {
				Logger.trace("bbbbbbbbbbbb");
			}
		}, "16:01:05", 8000);
	}
}
