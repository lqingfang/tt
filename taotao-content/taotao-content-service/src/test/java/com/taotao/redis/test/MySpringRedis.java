package com.taotao.redis.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class MySpringRedis {

	// 测试单机版redis
	@Test
	public void testSpringRedis() {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:applicationContext-redis.xml");
		JedisPool jedisPool = app.getBean(JedisPool.class);
		Jedis jedis = jedisPool.getResource();
		jedis.set("小猫", "爱吃鱼");
		System.out.println(jedis.get("小猫"));
	}
	// 测试集群版redis
	@Test
	public void testSpringRedisCluster() {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:applicationContext-redis.xml");
		
		JedisCluster jedisCluster = app.getBean(JedisCluster.class);
		jedisCluster.set("小狗", "爱吃肉");
		System.out.println(jedisCluster.get("小狗"));
	}
}
