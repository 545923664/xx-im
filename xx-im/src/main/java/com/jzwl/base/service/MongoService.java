package com.jzwl.base.service;

import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

public interface MongoService {

	public void save(String table, Object obj);

	public void save(String table, Object obj, String key, boolean isUpdate);

	public List<DBObject> find(String table);

	public List<DBObject> findList(String table, Map<String, Object> cond);

	public void del(String table,DBObject object);
	
	public void update(String table, Map<String, Object> cond,
			Map<String, Object> updateValue);

}
