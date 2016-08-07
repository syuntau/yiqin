package com.yiqin.dao;

import org.springframework.dao.DataAccessException;

import com.yiqin.pojo.Conf;

public interface ISystemDao {

	public void editConf(Conf conf) throws DataAccessException;
	public Conf getConf(String attrId)  throws DataAccessException;
	public Integer saveConf(Conf conf) throws DataAccessException;
	public void deleteConf(Conf conf) throws DataAccessException;
}
