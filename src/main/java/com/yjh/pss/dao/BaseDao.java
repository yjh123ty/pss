package com.yjh.pss.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yjh.pss.query.BaseQuery;
import com.yjh.pss.query.PageList;

public class BaseDao<T> extends HibernateDaoSupport {

	public void save(T t) {
		getHibernateTemplate().save(t);
	}

	public void update(T t) {
		getHibernateTemplate().update(t);
	}

	public void delete(Class<T> entityClass, Serializable id) {
		T t = get(entityClass, id);
		if (t != null) {
			getHibernateTemplate().delete(t);
		}
	}

	public T get(Class<T> entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	public List<T> getAll(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	public PageList findByQuery(final BaseQuery baseQuery){
		// 必须先配置org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
		// getHibernateTemplate().executeWithNativeSession(action)，我们使用这种
		// getHibernateTemplate().executeWithNewSession(action),每次都是一个新的session
		
		// 1.查询count
		Long countLong = getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				// 传入count的hql
				Query query = session.createQuery(baseQuery.getCountHql());
				// 设置查询条件
				List paramList = baseQuery.getParamList();
				for (int i = 0; i < paramList.size(); i++) {
					query.setParameter(i, paramList.get(i));
				}
				return (Long) query.uniqueResult();
			}
		});
		// 2.优化
		if (countLong.intValue() == 0) {
			return new PageList();
		}
		int currentPage = baseQuery.getCurrentPage();
		int pageSize = baseQuery.getPageSize();
		int totalCount = countLong.intValue();
		// 3.limit
		final PageList pageList = new PageList(currentPage,pageSize,totalCount);
		List rows = getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List>() {
			@Override
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				// 必须修改hql语句：传入limit的hql
				Query query = session.createQuery(baseQuery.getLimitHql());
				// 设置查询条件
				List paramList = baseQuery.getParamList();
				for (int i = 0; i < paramList.size(); i++) {
					query.setParameter(i, paramList.get(i));
				}
				// 设置分页条件
				// 从哪个位置开始取数据，索引从0开始，默认值0
				//必须修改baseQuery为pageList，因为pageList经过特殊处理
				int firstResult = (pageList.getCurrentPage() - 1) * pageList.getPageSize();
				// 取多少条，===每页的条数
				int maxResults = pageList.getPageSize();
				// 方法链编程
				query.setFirstResult(firstResult).setMaxResults(maxResults);
				return query.list();
			}
		});
		pageList.setRows(rows);
		return pageList;
	}
	
	public List findByHql(String hql,Object... objects) {
	    System.out.println("baseDao hql:" + hql);
	    System.out.println("baseDao param:" + Arrays.toString(objects));
		return getHibernateTemplate().find(hql, objects);
	}
	
	 // 没有条件参数的可以使用此方法
	  public List findCacheByHql(final String hql) {
	    return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List>() {
	      public List doInHibernate(Session session) throws HibernateException, SQLException {
		Query query = session.createQuery(hql);
		// 查询缓存生效
		query.setCacheable(true);
		return query.list();
	      }
	    });
	  }


}
