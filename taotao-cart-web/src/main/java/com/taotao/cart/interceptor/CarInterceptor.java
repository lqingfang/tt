package com.taotao.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.redis.JedisDao;
import com.taotao.cart.utils.CookieUtils;
import com.taotao.domain.TbUser;
import com.taotao.utils.JsonUtils;

public class CarInterceptor implements HandlerInterceptor{

	@Value("${TT_TOKEN}")
	private String TT_TOKEN;
	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Value("${SESSION_KEY}")
	private String SESSION_KEY;
	
	//注入Jedis服务
	@Autowired
	private JedisDao jedisService;
	/**
	 * 1.判断cookie当中是否有token
	 * 	 > 有，表示登录过
	 * 	 > 没有，必须从新登录，登录成功后，必须跳转历史操作页面
	 * 2、判断redis的用户身份信息是否过期
	 * 	 > 没有过期，放行，登录成功
	 * 	 > 从新登录，登录成功后，必须跳转历史操作页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//获取cookie当中用户身份标识：token
		String token = CookieUtils.getCookieValue(request, TT_TOKEN,true);
		//判断token是否为空
		if(StringUtils.isBlank(token)) {
			//如果为空，需要重新登录
			//获取当前操作请求地址
			String url = request.getRequestURL().toString();
			//跳转登录页面，携带历史操作地址
			response.sendRedirect(SSO_URL+"?redirectURL="+url);
			//拦截
			return false;
		}
		//如果cookie中token存在，判断redis中用户身份证信息是否过期
		//获取redis服务器中用户身份信息
		String userJson = jedisService.get(SESSION_KEY+":"+token);
		//判断使用身份是否过期
		if(StringUtils.isBlank(userJson)){
			//如果为空，需要从新登录
			//获取当前操作请求地址
			String url = request.getRequestURL().toString();
			//跳转登录页面，携带历史操作地址
			response.sendRedirect(SSO_URL+"?redirectURL="+url);
			return false;
		}
		
		//登录成功
		//把用户身份信息放入request域中
		request.setAttribute("user", JsonUtils.jsonToPojo(userJson, TbUser.class));
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
