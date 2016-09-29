package com.yjh.pss.service;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.yjh.pss.query.BaseQuery;
import com.yjh.pss.query.PageList;

public interface IBaseService<T> {
	public void save(T t);

	public void update(T t);

	public void delete(Serializable id);

	public T get(Serializable id);

	public List<T> getAll();
	
	PageList findByQuery(BaseQuery baseQuery);
	
	// 下载xls文件
	InputStream download(String[] heads, List<String[]> list) throws Exception;
	
	//上传文件
	List<String[]> importFile(File upload) throws Exception;
}
