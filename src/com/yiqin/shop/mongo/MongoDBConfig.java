package com.yiqin.shop.mongo;

/**
 * 该类用于接收数据库连接配置的属性值
 * 
 * @author LiuJun
 * 
 */
public class MongoDBConfig {
	// 服务器ip
	private String dbhost = null;

	// 服务器端口
	private int dbport = 0;

	// 数据库名
	private String database = null;

	// 用户名
	private String dbUser = null;

	// 密码
	private String dbPassword = null;

	// dblog
	private String dbLog = null;

	// 默认构造方法
	public MongoDBConfig() {

	}

	// 构造方法
	public MongoDBConfig(String dbhost, int dbport, String database,
			String dbUser, String dbPassword) {

		this.dbhost = dbhost;
		this.dbport = dbport;
		this.database = database;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}

	public String getDatabase() {

		return database;
	}

	public String getDbhost() {

		return dbhost;
	}

	public String getDbPassword() {

		return dbPassword;
	}

	public int getDbport() {

		return dbport;
	}

	public String getDbUser() {

		return dbUser;
	}

	public void setDatabase(String database) {

		this.database = database;
	}

	public void setDbhost(String dbhost) {

		this.dbhost = dbhost;
	}

	public void setDbPassword(String dbPassword) {

		this.dbPassword = dbPassword;
	}

	public void setDbport(int dbport) {

		this.dbport = dbport;
	}

	public void setDbUser(String dbUser) {

		this.dbUser = dbUser;
	}

	public String getDbLog() {
		return dbLog;
	}

	public void setDbLog(String dbLog) {
		this.dbLog = dbLog;
	}
}
