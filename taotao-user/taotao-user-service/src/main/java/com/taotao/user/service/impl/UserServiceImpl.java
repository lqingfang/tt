package com.taotao.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.domain.TbUser;
import com.taotao.domain.TbUserExample;
import com.taotao.domain.TbUserExample.Criteria;
import com.taotao.mapper.TbUserMapper;
import com.taotao.user.redis.JedisDao;
import com.taotao.user.service.UserService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisDao jedisDao;
	
	@Value("${SESSION_KEY}")
	private String SESSION_KEY;
	
	//注入redis的SESSION过期时间
	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;
	
	@Override
	public TaotaoResult dataCheck(String param, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		if(type==1){
			criteria.andUsernameEqualTo(param);
		}else if(type==2) {
			criteria.andPhoneEqualTo(param);
		}else if(type==3) {
			criteria.andEmailEqualTo(param);
		}
		//
		List<TbUser> uList = userMapper.selectByExample(example);
		//
		if(uList == null || uList.isEmpty() || uList.size() == 0) {
			return TaotaoResult.ok(true);
		}
		return TaotaoResult.ok(false);
	}

	/**
	 * 需求：接受注册参数，注册用户
	 * 业务：
	 * 注册时，密码需要加密后，写入数据库
	 * @param user
	 * @return
	 */
	public TaotaoResult register(TbUser user) {
		try {
			
			//补全时间属性
			Date date = new Date();
			user.setCreated(date);
			user.setUpdated(date);
			
			//User用户密码加密
			String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
			//把加密后密码设置User对象中
			user.setPassword(md5Password);
			userMapper.insert(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return TaotaoResult.build(400, "注册失败. 请校验数据后请再提交数据", null);
			
		}
		return TaotaoResult.ok();
	
	}

	/**
	 * 需求：登录
	 * 业务流程：
	 * 1、根据用户名查询系统（验证此用户是否存在）
	 * 	 > 如果系统中有数据，获取查询数据
	 * 	 > 判断密码是否相等(需要给密码进行md5加密，然后判断是否相等)
	 *   > 如果验证通过，登录成功
	 * 2、把用户信息放入redis
	 * 3、返回token，token就是redis存储用户身份信息的key
	 * 4、把token写入cookie中
	 * 需要用户身份信息写入redis：
	 * redis数据结构：key：value
	 * key业务值：SESSION＿KEY:token
	 * value:User对象json格式
	 * @param username
	 * @param password
	 * @return
	 */
	public TaotaoResult login(String username, String password) {
		//创建example对象
		TbUserExample example = new TbUserExample();
		//创建criteria对象
		Criteria criteria = example.createCriteria();
		//设置查询参数：根据用户名进行查询数据库
		criteria.andUsernameEqualTo(username);
		//执行查询
		List<TbUser> uList = userMapper.selectByExample(example);
		//判断查询结果集合是否为空
		if(uList==null || uList.isEmpty() || uList.size()==0){
			return TaotaoResult.build(201, "用户名或密码错误!");
		}
		//获取用户身份信息
		TbUser user = uList.get(0);
		//判断密码是否相等
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
		if(!md5DigestAsHex.equals(user.getPassword())){
			return TaotaoResult.build(201, "用户名或密码错误!");
		}
		
		//登录成功
		//生成UUID
		String token = UUID.randomUUID().toString();
		//把用户身份信息设置到redis服务器：redis服务器就是SESSION
		jedisDao.set(SESSION_KEY+":"+token, JsonUtils.objectToJson(user));
		//给redis服务器用户身份信息设置过期时间
		jedisDao.expire(SESSION_KEY+":"+token, EXPIRE_TIME);
		//返回值：返回token
		return TaotaoResult.ok(token);
	}

	/**
	 * 需求：根据token查询redis中用户身份信息
	 */
	public TaotaoResult tokenCheck(String token) {
		//根据token去redis服务器查询用户身份信息
		String userJson = jedisDao.get(SESSION_KEY+":"+token);
		//判断redis中是否有值
		if(StringUtils.isNotBlank(userJson)){
			//如果有值
			TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
			
			//重置用户身份信息过期时间
			jedisDao.expire(SESSION_KEY+":"+token, EXPIRE_TIME);
			
			//返回包装User对象的TaotaoResult
			return TaotaoResult.ok(user);
		}
		return TaotaoResult.build(400, "用户身份信息过期!");
	}

}
