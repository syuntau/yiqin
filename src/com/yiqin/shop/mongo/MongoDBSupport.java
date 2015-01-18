package com.yiqin.shop.mongo;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * 数据库操作封装
 * 
 * @author LiuJun
 */
public class MongoDBSupport implements IMongoDBSupport {
	// 数据库连接bean
	private InitMongoDBConnection mongoDBConn;

	public InitMongoDBConnection getMongoDBConn() {
		return mongoDBConn;
	}

	public void setMongoDBConn(InitMongoDBConnection mongoDBConn) {
		this.mongoDBConn = mongoDBConn;
	}

	// 数据库连接对象
	private DB db;

	// 表名
	private String collection;

	/**
	 * 指定要操作的表名
	 * 
	 * @param collection
	 *            表名
	 * @param sc
	 *            ServletContext
	 */
	public void setcollection(String collection, ServletContext sc) {

		this.db = mongoDBConn.getMongoDB(sc);
		this.collection = collection;
	}

	/**
	 * 获取DB对象
	 * 
	 * @return DB对象
	 */
	public DB getDb() {

		return db;
	}

	/**
	 * 销毁MongoDB对象
	 * 
	 * @param sce
	 *            ServletContextEvent
	 */
	public void destroyedMongoDB(ServletContextEvent sce) {
		mongoDBConn.contextDestroyed(sce);
	}

	

	/**
	 * 获得集合
	 * 
	 * @return DBCollection
	 */
	public DBCollection getCollection() {

		return db.getCollection(collection);
	}
}
