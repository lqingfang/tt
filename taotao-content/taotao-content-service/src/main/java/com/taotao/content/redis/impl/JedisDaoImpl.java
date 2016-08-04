//package com.taotao.content.redis.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//import com.taotao.content.redis.JedisDao;
//
//@Repository
//public class JedisDaoImpl implements JedisDao {
//
//	@Autowired
//	JedisPool jPool;
//	
//	@Override
//	public String set(String key, String value) {
//		Jedis jedis = jPool.getResource();
//		String set = jedis.set(key, value);
//		return set;
//	}
//
//	@Override
//	public String get(String key) {
//		Jedis jedis = jPool.getResource();
//		String value = jedis.get(key);
//		return value;
//	}
//
//	@Override
//	public Long incr(String key) {
//		Jedis jedis = jPool.getResource();
//		Long incr = jedis.incr(key);
//		return incr;
//	}
//
//	@Override
//	public Long decr(String key) {
//		Jedis jedis = jPool.getResource();
//		Long incr = jedis.decr(key);
//		return incr;
//	}
//
//	@Override
//	public Long hset(String key, String field, String value) {
//		Jedis jedis = jPool.getResource();
//		Long hset = jedis.hset(key, field, value);
//		return hset;
//	}
//
//	@Override
//	public String hget(String key, String field) {
//		Jedis jedis = jPool.getResource();
//		String value = jedis.hget(key, field);
//		return value;
//	}
//
//	@Override
//	public Long hdel(String key, String field) {
//		Jedis jedis = jPool.getResource();
//		Long hdel = jedis.hdel(key, field);
//		return hdel;
//	}
//
//	@Override
//	public Long expire(String key, int seconds) {
//		Jedis jedis = jPool.getResource();
//		Long expire = jedis.expire(key, seconds);
//		return expire;
//	}
//
//	@Override
//	public Long ttl(String key) {
//		Jedis jedis = jPool.getResource();
//		Long ttl = jedis.ttl(key);
//		return ttl;
//	}
//
//}
