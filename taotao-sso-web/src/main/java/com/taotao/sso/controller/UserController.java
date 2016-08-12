package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.domain.TbUser;
import com.taotao.sso.utils.CookieUtils;
import com.taotao.user.service.UserService;
import com.taotao.utils.TaotaoResult;

@Controller
public class UserController {

	/**
	 * 检测注册数据是否可以使用： 检测参数： 1、用户名是否可用 2、手机号 3、邮箱 请求：/user/check/{param}/{type}
	 * 返回值： { status: 200 //200 成功 msg: "OK" // 返回信息消息 data: false //
	 * 返回数据，true：数据可用，false：数据不可用 } param:代表参数值 type:参数类型
	 */
	@Autowired
	private UserService userService;
	
	@Value("${TT_TOKEN}")
	private String TT_TOKEN;
	
	@RequestMapping("/user/check/{param}/{type}")
	public @ResponseBody TaotaoResult dataCheck(
			@PathVariable String param,@PathVariable Integer type) {
		TaotaoResult result = userService.dataCheck(param, type);
		return result;
	}
	/**
	 * 需求：接受注册参数，注册用户 请求：/user/register 业务： 注册时，密码需要加密后，写入数据库
	 * 
	 * @param user
	 * @return 注册成功：taotaoResult.ok(); status=200,msg="注册成功"，data=null
	 *         注册失败：status=400,msg=注册失败. 请校验数据后请再提交数据.data=null
	 * 
	 */
	@RequestMapping("/user/register")
	public @ResponseBody
	TaotaoResult register(TbUser user) {
		TaotaoResult register = userService.register(user);
		return register;
	}
	
	/**
	 * 需求：登录 请求：/user/login 
	 * 业务流程：
	 *  1、根据用户名查询系统（验证此用户是否存在） 
	 *  > 如果系统中有数据，获取查询数据 
	 *  > 判断密码是否相等(需要给密码进行md5加密，然后判断是否相等) 
	 *  > 如果验证通过，
	 *  登录成功 
	 *  2、把用户信息放入redis
	 * 3、返回token，token就是redis存储用户身份信息的key
	 *  4、把token写入cookie中 需要用户身份信息写入redis：
	 * redis数据结构：
	 * key：value key业务值：
	 * SESSION＿KEY:token value:User对象json格式
	 * 
	 * @param username
	 * @param password
	 * @return
	 * 
	 * 登录成功：把token写入cookie中，cookie存储数据结构：key=value
	 * cookie的key设计：key设计：TT_TOKEN=token字符串
	 * 
	 */
	@RequestMapping("/user/login")
	public @ResponseBody
	TaotaoResult login(HttpServletRequest request,
			HttpServletResponse response, String username, String password) {
		//如果是普通请求
		TaotaoResult result = userService.login(username, password);
		CookieUtils.setCookie(request, response, TT_TOKEN, result.getData().toString(), true);
		return result;
	}
	/**
	 * 需求：根据token查询用户身份信息
	 * 请求：http://localhost:8088/user/token/" + _ticket
	 * 返回值：根据js代码回调函数判断需要返回什么值：taotaoResult(包含User对象)	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping("/user/token/{token}")
	public @ResponseBody Object tokenCheck(@PathVariable String token,String callback) {
		TaotaoResult result = userService.tokenCheck(token);
		//如果是普通请求
		if(StringUtils.isBlank(callback)) {
			return result;
		} else {
			//否则是跨域请求
			MappingJacksonValue mjv = new MappingJacksonValue(result);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
	}
}
