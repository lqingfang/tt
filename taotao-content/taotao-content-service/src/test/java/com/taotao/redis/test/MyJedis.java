package com.taotao.redis.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MyJedis {

	@Test
	public void testRedis01() {
		Jedis jedis = new Jedis("192.168.227.136",6379);
		jedis.set("itemName", "洗脸盆2");
		System.out.println(jedis.get("itemName"));
	}
	@Test
	public void testPoolredis(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(20);
		poolConfig.setMaxTotal(1000);
		
		JedisPool jPool = new JedisPool(poolConfig,"192.168.227.136",6379);
		Jedis jedis = jPool.getResource();
		jedis.set("hello", "你好");
		System.out.println(jedis.get("hello"));
	}
	@Test
	public void jedisClusterTest() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(20);
		poolConfig.setMaxTotal(1000);
		
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.227.136",7001));
		nodes.add(new HostAndPort("192.168.227.136",7002));
		nodes.add(new HostAndPort("192.168.227.136",7003));
		nodes.add(new HostAndPort("192.168.227.136",7004));
		nodes.add(new HostAndPort("192.168.227.136",7005));
		nodes.add(new HostAndPort("192.168.227.136",7006));
		nodes.add(new HostAndPort("192.168.227.136",7007));
		nodes.add(new HostAndPort("192.168.227.136",7008));
		
		JedisCluster jCluster = new JedisCluster(nodes,poolConfig);
		
		jCluster.set("kitty", "凯蒂");
		System.out.println(jCluster.get("kitty"));
	}
}
