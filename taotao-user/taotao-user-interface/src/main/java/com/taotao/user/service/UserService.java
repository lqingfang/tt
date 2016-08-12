package com.taotao.user.service;

import com.taotao.domain.TbUser;
import com.taotao.utils.TaotaoResult;

public interface UserService {
	/**
	 * 检测注册数据是否可以使用：
	 * 检测参数：
	 * 1、用户名是否可用
	 * 2、手机号
	 * 3、邮箱
	 * 传递参数：/user/check/{param}/{type}
	 * param:代表参数值
	 * type:参数类型
	 */
	public TaotaoResult dataCheck(String param,Integer type);
	/**
	 * 需求：接受注册参数，注册用户
	 * 业务：
	 * 注册时，密码需要加密后，写入数据库
	 * @param user
	 * @return
	 */
	public TaotaoResult register(TbUser user);
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
	public TaotaoResult login(String username,String password);
	
	/**
	 * 需求：根据token查询redis中用户身份信息
	 */
	public TaotaoResult tokenCheck(String token);
}
