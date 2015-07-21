package com.jzwl.base.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.jzwl.base.service.RedisService;
import com.jzwl.instant.util.IC;

/**
 * 封装redis 缓存服务器服务接口
 * 
 * @author hk
 * 
 *         2012-12-16 上午3:09:18
 */
@Component
public class RedisServiceImpl implements RedisService {


	public RedisServiceImpl() {

	}

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * @param key
	 */
	public long del(final String... keys) {
		return (Long) redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				long result = 0;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());
				}
				return result;
			}
		});
	}

	/**
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(final byte[] key, final byte[] value, final long liveTime) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return 1L;
			}
		});
	}

	/**
	 * @param key
	 * @param liveTime
	 */
	public void expire(final byte[] key, final long liveTime) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				if (liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return 1L;
			}
		});
	}

	/**
	 * @param key
	 * @param liveTime
	 */
	public void expire(String key, long liveTime) {
		this.expire(key.getBytes(), liveTime);
	}

	/**
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, long liveTime) {
		this.set(key.getBytes(), value.getBytes(), liveTime);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		this.set(key, value, 0L);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void set(byte[] key, byte[] value) {
		this.set(key, value, 0L);
	}

	/**
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return (String) redisTemplate.execute(new RedisCallback<Object>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				try {
					byte[] result = connection.get(key.getBytes());
					if (null != result) {
						return new String(result, IC.redisCode);
					} else {
						return null;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return "";
			}
		});
	}

	/**
	 * @param pattern
	 * @return
	 * @return
	 */
	public Set<String> Setkeys(String pattern) {
		return redisTemplate.keys(pattern);

	}

	/**
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
	}

	/**
	 * @return
	 */
	public String flushDB() {
		return (String) redisTemplate.execute(new RedisCallback<Object>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	/**
	 * @return
	 */
	public long dbSize() {
		return (Long) redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	/**
	 * @return
	 */
	public String ping() {
		return (String) redisTemplate.execute(new RedisCallback<Object>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {

				return connection.ping();
			}
		});
	}

	/**
	 * 压栈
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long push(String key, String value) {
		return redisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 * 出栈
	 * 
	 * @param key
	 * @return
	 */
	public String pop(String key) {
		return (String) redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 入队
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long in(String key, String value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 出队
	 * 
	 * @param key
	 * @return
	 */
	public String out(String key) {
		return (String) redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 栈/队列长
	 * 
	 * @param key
	 * @return
	 */
	public Long length(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/**
	 * 范围检索
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> range(String key, int start, int end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 移除
	 * 
	 * @param key
	 * @param i
	 * @param value
	 */
	public void remove(String key, long i, String value) {
		redisTemplate.opsForList().remove(key, i, value);
	}

	/**
	 * 检索
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public String index(String key, long index) {
		return (String) redisTemplate.opsForList().index(key, index);
	}

	/**
	 * 置值
	 * 
	 * @param key
	 * @param index
	 * @param value
	 */
	public void set(String key, long index, String value) {
		redisTemplate.opsForList().set(key, index, value);
	}

	/**
	 * 裁剪
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public void trim(String key, long start, int end) {
		redisTemplate.opsForList().trim(key, start, end);
	}
	
	

}