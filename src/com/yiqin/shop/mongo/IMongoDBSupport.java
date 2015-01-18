package com.yiqin.shop.mongo;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.mongodb.DB;

/**
 * 数据库操作封装
 * 
 * @author LiuJun
 */
public interface IMongoDBSupport {

	/**
	 * 获取DB对象
	 * 
	 * @return DB对象
	 */
	public abstract DB getDb();

	/**
	 * 销毁MongoDB对象
	 * 
	 * @param sce
	 *            ServletContextEvent
	 */
	public abstract void destroyedMongoDB(ServletContextEvent sce);

	/**
	 * 指定要操作的表名
	 * 
	 * @param collection
	 *            表名
	 * @param sc
	 *            ServletContext
	 */
	public abstract void setcollection(String collection, ServletContext sc);

}
