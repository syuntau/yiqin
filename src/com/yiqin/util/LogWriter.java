package com.yiqin.util;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 * 日志系统管理类
 * 
 * @author liujun
 * 
 */
public class LogWriter {
	public static void logInfo(String message) {
		if (message == null) {
			return;
		}

		Logger logger = Logger.getLogger(LogWriter.class.getName());
		if ((logger != null) && (logger.isInfoEnabled())) {
			StackTraceElement[] stack = new Throwable().getStackTrace();
			if ((stack != null) && (stack.length > 1)) {
				String moduleName = stack[1].getClassName();

				String methodName = stack[1].getMethodName() + ":"
						+ stack[1].getLineNumber();

				MDC.put("type", "信息");
				MDC.put("module", moduleName);
				MDC.put("method", methodName);
				MDC.put("message", message);

				logger.info("info");

				MDC.remove("type");
				MDC.remove("module");
				MDC.remove("method");
				MDC.remove("message");
			}
		}
	}

	public static void logWarn(String message) {
		if (message == null) {
			return;
		}

		Logger logger = Logger.getLogger(LogWriter.class.getName());
		if (logger != null) {
			StackTraceElement[] stack = new Throwable().getStackTrace();
			if ((stack != null) && (stack.length > 1)) {
				String moduleName = stack[1].getClassName();

				String methodName = stack[1].getMethodName() + ":"
						+ stack[1].getLineNumber();

				MDC.put("type", "警告");
				MDC.put("module", moduleName);
				MDC.put("method", methodName);
				MDC.put("message", message);

				logger.warn("");

				MDC.remove("type");
				MDC.remove("module");
				MDC.remove("method");
				MDC.remove("message");
			}
		}
	}

	public static void logError(String message) {
		if (message == null) {
			return;
		}

		Logger logger = Logger.getLogger(LogWriter.class.getName());
		if (logger != null) {
			StackTraceElement[] stack = new Throwable().getStackTrace();
			if ((stack != null) && (stack.length > 1)) {
				String moduleName = stack[1].getClassName();

				String methodName = stack[1].getMethodName() + ":"
						+ stack[1].getLineNumber();

				MDC.put("type", "错误");
				MDC.put("module", moduleName);
				MDC.put("method", methodName);
				MDC.put("message", message);

				logger.error("");

				MDC.remove("type");
				MDC.remove("module");
				MDC.remove("method");
				MDC.remove("message");
			}
		}
	}

	public static void logException(Exception e) {
		if (e == null) {
			return;
		}

		Logger logger = Logger.getLogger(LogWriter.class.getName());
		if (logger != null) {
			StackTraceElement[] stack = new Throwable().getStackTrace();
			if ((stack != null) && (stack.length > 1)) {
				String moduleName = stack[1].getClassName();

				String methodName = stack[1].getMethodName() + ":"
						+ stack[1].getLineNumber();

				ByteArrayOutputStream os = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(os);
				e.printStackTrace(ps);

				MDC.put("type", "异常");
				MDC.put("module", moduleName);
				MDC.put("method", methodName);
				MDC.put("message", os.toString());

				logger.error("");

				MDC.remove("type");
				MDC.remove("module");
				MDC.remove("method");
				MDC.remove("message");
			}
		}
	}
}
