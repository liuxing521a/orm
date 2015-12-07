package org.itas.util;

import org.slf4j.LoggerFactory;

public final class Logger {

	private static final org.slf4j.Logger trace = LoggerFactory.getLogger("log-trace");

	private static final org.slf4j.Logger debug = LoggerFactory.getLogger("log-debug");

	private static final org.slf4j.Logger info = LoggerFactory.getLogger("log-info");

	private static final org.slf4j.Logger warn = LoggerFactory.getLogger("log-warn");

	private static final org.slf4j.Logger error = LoggerFactory.getLogger("log-error");

	private static final org.slf4j.Logger charge = LoggerFactory.getLogger("log-charge");

	private static final org.slf4j.Logger record = LoggerFactory.getLogger("log-record");

	// ================================================================
	// trace日志
	// ================================================================

	public static void trace(String msg) {
		trace.trace(msg);
	}

	public static void trace(String format, Object arg) {
		trace.trace(format, arg);
	}

	public static void trace(String format, Object arg1, Object arg2) {
		trace.trace(format, arg1, arg2);
	}

	public static void trace(String format, Object[] argArray) {
		trace.trace(format, argArray);
	}

	public static void trace(String msg, Throwable t) {
		trace.trace(msg, t);
	}

	// ================================================================
	// debug日志
	// ================================================================
	public static void debug(String msg) {
		debug.debug(msg);
	}

	public static void debug(String format, Object arg) {
		debug.debug(format, arg);
	}

	public static void debug(String format, Object arg1, Object arg2) {
		debug.debug(format, arg1, arg2);
	}

	public static void debug(String format, Object[] argArray) {
		debug.debug(format, argArray);
	}

	public static void debug(String msg, Throwable t) {
		debug.debug(msg, t);
	}

	// ================================================================
	// info日志
	// ================================================================
	public static void info(String msg) {
		info.info(msg);
	}

	public static void info(String format, Object arg) {
		info.info(format, arg);
	}

	public static void info(String format, Object arg1, Object arg2) {
		info.info(format, arg1, arg2);
	}

	public static void info(String format, Object[] argArray) {
		info.info(format, argArray);
	}

	public static void info(String msg, Throwable t) {
		info.info(msg, t);
	}

	// ================================================================
	// charge日志
	// ================================================================
	public static void charge(String msg) {
		charge.info(msg);
	}

	public static void charge(String format, Object arg) {
		charge.info(format, arg);
	}

	public static void charge(String format, Object arg1, Object arg2) {
		charge.info(format, arg1, arg2);
	}

	public static void charge(String format, Object[] argArray) {
		charge.info(format, argArray);
	}

	public static void charge(String msg, Throwable t) {
		charge.info(msg, t);
	}

	// ================================================================
	// record日志
	// ================================================================
	public static void record(String msg) {
		record.info(msg);
	}

	public static void record(String format, Object arg) {
		record.info(format, arg);
	}

	public static void record(String format, Object arg1, Object arg2) {
		record.info(format, arg1, arg2);
	}

	public static void record(String format, Object[] argArray) {
		record.info(format, argArray);
	}

	// ================================================================
	// warn日志
	// ================================================================

	public static void warn(String msg) {
		warn.warn(msg);
	}

	public static void warn(String format, Object arg) {
		warn.warn(format, arg);
	}

	public static void warn(String format, Object[] argArray) {
		warn.warn(format, argArray);
	}

	public static void warn(String format, Object arg1, Object arg2) {
		warn.warn(format, arg1, arg2);
	}

	public static void warn(String msg, Throwable t) {
		warn.warn(msg, t);
	}

	// ================================================================
	// error日志
	// ================================================================

	public static void error(String msg) {
		error.error(msg);
	}

	public static void error(String format, Object arg) {
		error.error(format, arg);
	}

	public static void error(String format, Object arg1, Object arg2) {
		error.error(format, arg1, arg2);
	}

	public static void error(String format, Object[] argArray) {
		error.error(format, argArray);
	}

	public static void error(String msg, Throwable t) {
		error.error(msg, t);
	}

}
