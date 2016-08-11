package com.taotao.item.controller;


import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class FreeMarkerController {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@RequestMapping("genHtml")
	public @ResponseBody String genHtml() throws Exception {
		//获取配置对象
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		//获取配置文件中模版路径下面模版对象
		Template template = configuration.getTemplate("hello.ftl");
		//模拟数据
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("hello", "freeMarker整合spring");
		//创建Writer对象
		Writer out = new FileWriter(new File("E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl_out\\hello.html"));
		template.process(maps, out);
		out.close();
		return "OK";
	}
}
