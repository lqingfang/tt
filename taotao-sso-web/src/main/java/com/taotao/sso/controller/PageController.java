package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	//跳转到注册页面
		@RequestMapping("/page/register")
		public String showRegister(){
			return "register";
		}
		
		//跳转登录页面
		@RequestMapping("/page/login")
		public String showLogin(){
			return "login";
		}
}
