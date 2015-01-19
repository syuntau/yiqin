package com.yiqin.shop.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.yiqin.util.LogWriter;

/**
 * 数据库操作封装
 * 
 * @author LiuJun
 */
public class MongoDBSupport {
	private MongoDBConfig mongoDBConfig;

	public MongoDBConfig getMongoDBConfig() {
		return mongoDBConfig;
	}

	public void setMongoDBConfig(MongoDBConfig mongoDBConfig) {
		this.mongoDBConfig = mongoDBConfig;
	}

	// 数据库对象
	private static DB mongoDB = null;

	/**
	 * 获取DB连接对象
	 * 
	 * @return DB连接对象
	 */
	private DB getMongoDB() {

		// 判断DB对象是否为空
		if (mongoDB != null) {
			System.out.println("已经有了" + mongoDB);
			return mongoDB;
		} else {
			initializedDB();
		}
		return mongoDB;
	}

	/**
	 * 读取设置连接池的属性文件,建立连接
	 */
	private void initializedDB() {
		try {
			System.out.println("启动连接初始化");
			// db日志
			if (mongoDBConfig.getDbLog() != null
					&& mongoDBConfig.getDbLog().equals("true")) {
				System.setProperty("DEBUG.MONGO", "true");
				System.setProperty("DB.TRACE", "true");
			}

			// 构建数据库连接对象
			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			build.connectionsPerHost(100);
			build.threadsAllowedToBlockForConnectionMultiplier(10);
			build.connectTimeout(300);
			build.maxWaitTime(1000);
			build.maxConnectionLifeTime(1000);
			build.maxConnectionIdleTime(1000);
			build.cursorFinalizerEnabled(true);
			build.autoConnectRetry(true);
			MongoClientOptions options = build.build();

			ServerAddress serverAddress = new ServerAddress(
					mongoDBConfig.getDbhost(), mongoDBConfig.getDbport());
			MongoClient mongoClient = new MongoClient(serverAddress, options);

			// 获取DB对象值
			mongoDB = mongoClient.getDB(mongoDBConfig.getDatabase());

			// Mongo认证
			boolean auth = mongoDB.authenticate(mongoDBConfig.getDbUser(),
					mongoDBConfig.getDbPassword().toCharArray());

			// 认证是否成功 失败阻止
			if (!auth) {
				mongoDB = null;
				mongoClient.close();

				// 记录日志
				LogWriter.logError("mongoDB Connection fail");
				System.out.println("mongoDB Connection fail");
				return;
			} else {
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
	 * 获取集合
	 * 
	 * @param collection
	 *            表名
	 * @return DBCollection
	 */
	public DBCollection getCollection(String collection) {
		return getMongoDB().getCollection(collection);
	}
}
