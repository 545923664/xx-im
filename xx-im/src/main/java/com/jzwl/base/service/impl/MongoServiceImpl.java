package com.jzwl.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Component
public class MongoServiceImpl implements MongoService {

	private Gson gson = new Gson();

	/**
	 * spring mongodb　集成操作类　
	 */
	@Autowired
	protected MongoTemplate mongoTemplate;

	@Override
	public void save(String table, Object obj) {
		DBCollection collection = mongoTemplate.getCollection(table);
		DBObject dbObject = (DBObject) JSON.parse(gson.toJson(obj));
		collection.insert(dbObject);
	}

	@Override
	public void save(String table, Object obj, String key, boolean isUpdate) {
		if (isUpdate && null != key) {// 更新

			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(obj));
			// 更新
			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put(key, dbObject.get(key));

			update(table, cond, dbObject.toMap());
		} else {// 增加
			save(table, obj);
		}

	}

	@Override
	public List find(String table) {
		DBCollection collection = mongoTemplate.getCollection(table);

		DBCursor cursorDoc = collection.find();

		List<DBObject> list = new ArrayList<DBObject>();

		while (cursorDoc.hasNext()) {
			DBObject object = cursorDoc.next();

			list.add(object);
		}

		return list;
	}

	@Override
	public List findList(String table,  Map<String, Object> cond) {
		DBCollection collection = mongoTemplate.getCollection(table);

		DBCursor cursorDoc = collection.find(new BasicDBObject(cond));

		List<DBObject> list = new ArrayList<DBObject>();

		while (cursorDoc.hasNext()) {
			DBObject object = cursorDoc.next();

			list.add(object);
		}
		return list;
	}

	@Override
	public void update(String table,  Map<String, Object> cond,  Map<String, Object> updateValue) {
		DBCollection collection = mongoTemplate.getCollection(table);

		DBObject updateCondition = new BasicDBObject(cond);

		DBObject updatedValue = new BasicDBObject(updateValue);

		DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
		/**
		 * update insert_test set headers=3 and legs=4 where name='fox'
		 * updateCondition:更新条件 updateSetValue:设置的新值
		 */
		System.out.println(collection.update(updateCondition, updateSetValue,
				true, true).toString());
	}


	@Override
	public void del(String table,DBObject object) {
		DBCollection collection = mongoTemplate.getCollection(table);
		
		collection.remove(object);
		
	}


}
