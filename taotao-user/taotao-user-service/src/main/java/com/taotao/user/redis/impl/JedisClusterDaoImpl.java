package com.taotao.user.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.JedisCluster;

import com.taotao.user.redis.JedisDao;
@Repository
public class JedisClusterDaoImpl implements JedisDao {
	
	//注入对象：spring创建对象：jedisCluster集群对象
	@Autowired
	JedisCluster jedisCluster;

	@Override
	public String set(String key, String value) {		
		String set = jedisCluster.set(key, value);
		return set;
	}

	@Override
	public String get(String key) {
		
		//从redis服务器，获取值
		String value = jedisCluster.get(key);
		return value;
	}

	@Override
	public Long incr(String key) {
	
		Long incr = jedisCluster.incr(key);
		return incr;
	}

	@Override
	public Long decr(String key) {
		
		Long decr = jedisCluster.decr(key);
		return decr;
	}

	@Override
	public Long hset(String key, String field, String value) {
		
		Long hset = jedisCluster.hset(key, field, value);
		return hset;
	}

	@Override
	public String hget(String key, String field) {
		
		String value = jedisCluster.hget(key, field);
		return value;
	}

	@Override
	public Long hdel(String key, String field) {
		
		Long hdel = jedisCluster.hdel(key, field);
		return hdel;
	}

	@Override
	public Long expire(String key, int seconds) {
		
		Long expire = jedisCluster.expire(key, seconds);
		return expire;
	}

	@Override
	public Long ttl(String key) {
		
		Long ttl = jedisCluster.ttl(key);
		return ttl;
	}

}
