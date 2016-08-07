package com.yiqin.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiqin.dao.ISystemDao;
import com.yiqin.pojo.Conf;
import com.yiqin.util.Util;


public class SystemDao extends HibernateDaoSupport implements ISystemDao {

	@Override
	public void editConf(Conf conf) throws DataAccessException {
		getHibernateTemplate().update(conf);
	}

	@Override
	public Conf getConf(String attrId) throws DataAccessException {
		String sql = " from Conf where attribute = ?";
		@SuppressWarnings("unchecked")
		List<Conf> list = getHibernateTemplate().find(sql, attrId);
		if (Util.isEmpty(list)) {
			return null;
		} else {
			return (Conf) list.get(0);
		}
	}

	@Override
	public Integer saveConf(Conf conf) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(conf);
	}

	@Override
	public void deleteConf(Conf conf) throws DataAccessException {
		getHibernateTemplate().delete(conf);
	}

}
