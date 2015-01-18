package com.yiqin.shop.mongo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.yiqin.util.LogWriter;

/**
 * 该类用于初始化数据库连接
 * 
 * @author LiuJun
 * 
 */
public class InitMongoDBConnection implements ServletContextListener {

	// 线程池大小
	private static final int POOL_SIZE = 1;

	// 线程服务
	private static ExecutorService executor;

	// mongoDB标识
	private static final String MONGODB = "mongoDB";

	// DB配置属性名
	private static final String MONGO_DB_CONFIG = "MongoDBConfig";

	// 数据库对象
	private DB mongoDB = null;

	// 设置连接池大小
	static {
		System.setProperty("MONGO.POOLSIZE", "50");
	}

	/**
	 * 供外部获取DB连接对象
	 * 
	 * @param sc
	 *            ServletContext
	 * @return DB连接对象
	 */
	public DB getMongoDB(ServletContext sc) {

		// 判断DB对象是否为空
		if (mongoDB != null) {
			System.out.println("已经有了" + mongoDB);
			return mongoDB;
		} else {
			getContextDB(sc);
		}
		return mongoDB;
	}

	/**
	 * 从context中获取DB对象
	 * 
	 * @param sc
	 *            ServletContext
	 */
	private void getContextDB(ServletContext sc) {
		System.out.println("获取Context中的DB");
		mongoDB = (DB) sc.getAttribute(MONGODB);
		if (mongoDB == null) {
			System.out.println("Context中的DB已被销毁，请重启服务");
		}
	}

	/**
	 * 获取新的线程服务
	 * 
	 * @return ExecutorService
	 */
	public static ExecutorService getExecutor() {
		if (executor == null) {
			executor = Executors.newFixedThreadPool(POOL_SIZE);
		}
		return executor;
	}

	/**
	 * 读取设置连接池的属性文件,建立连接
	 */
	public void contextInitialized(ServletContextEvent sce) {
		try {
			System.out.println("启动连接初始化");

			// 获取applicationContext
			WebApplicationContext applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(sce.getServletContext());

			// 获取配置属性 bean
			MongoDBConfig config = (MongoDBConfig) applicationContext
					.getBean(MONGO_DB_CONFIG);

			// 构建数据库连接对象
			DBAddress dba = new DBAddress(config.getDbhost(), config
					.getDbport(), config.getDatabase());

			// 获取DB对象值
			mongoDB = Mongo.connect(dba);

			// 连接指定数据库
			boolean auth = mongoDB.authenticate(config.getDbUser(), config
					.getDbPassword().toCharArray());

			// 判断数据库连接初始化是否成功
			if (!auth) {
				mongoDB = null;

				// 记录日志
				LogWriter.logError("mongoDB Connection fail");
				System.out.println("mongoDB Connection fail");
				return;
			} else {
				// 存储DB对象
				sce.getServletContext().setAttribute(MONGODB, mongoDB);

				// 记录日志
				LogWriter.logInfo("mongoDB Connection success");
				System.out.println("mongoDB Connection success---" + mongoDB);
			}
		} catch (Exception e) {
			LogWriter.logException(e);
			e.printStackTrace();
		}
	}

	/**
	 * 容器关闭销毁DB对象
	 */
	public void contextDestroyed(ServletContextEvent sce) {

		// 销毁DB对象
		mongoDB = null;
		sce.getServletContext().removeAttribute(MONGODB);

		// 记录日志
		LogWriter.logInfo("mongoDB is destroyed");
		System.out.println("mongoDB is destroyed");
	}
}
