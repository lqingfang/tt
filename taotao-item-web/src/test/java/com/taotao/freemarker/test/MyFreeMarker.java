package com.taotao.freemarker.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MyFreeMarker {

	@Test
	public void testFreeMarkerOne() throws IOException, TemplateException {
//		1）	创建配置对象，配置Freemarker版本
		Configuration configuration = new Configuration(Configuration.getVersion());
//		2）	指定模版文件路径
		configuration.setDirectoryForTemplateLoading(new File("E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\test"));
//		3）	指定模版文件默认编码
		configuration.setDefaultEncoding("UTF-8");
//		4）	获取模版对象
		Template template = configuration.getTemplate("hello.ftl");
//		5）	创建模拟数据
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("hello", "hello world");
//		6）	创建一个输出对象：生成模版文件
		Writer out = new FileWriter(new File("E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\out\\hello.html"));
//		7）	生成
		template.process(maps,out);
//		8）	关闭对象
		out.close();
	}
	
	@Test
	public void testFreeMarkerTwo() throws IOException, TemplateException {
//		1）	创建配置对象，配置Freemarker版本
		Configuration configuration = new Configuration(Configuration.getVersion());
//		2）	指定模版文件路径
		configuration.setDirectoryForTemplateLoading(new File("E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\test"));
//		3）	指定模版文件默认编码
		configuration.setDefaultEncoding("UTF-8");
//		4）	获取模版对象
		Template template = configuration.getTemplate("pojo.ftl");
//		5）	创建模拟数据
		Map<String, Object> maps = new HashMap<String, Object>();
		
		Person p = new Person();
		p.setId(1);
		p.setUsername("张三");
		p.setAddress("花山");
		
		maps.put("p", p);
//		6）	创建一个输出对象：生成模版文件
		Writer out = new FileWriter(new File("E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\out\\pojo.html"));
//		7）	生成
		template.process(maps,out);
//		8）	关闭对象
		out.close();
	}
	
	@Test
	public void testFreeMarkerThree() throws IOException, TemplateException {
//		1）	创建配置对象，配置Freemarker版本
		Configuration configuration = new Configuration(Configuration.getVersion());
//		2）	指定模版文件路径
		configuration.setDirectoryForTemplateLoading(new File("E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\test"));
//		3）	指定模版文件默认编码
		configuration.setDefaultEncoding("UTF-8");
//		4）	获取模版对象
		Template template = configuration.getTemplate("list.ftl");
//		5）	创建模拟数据
		Map<String, Object> maps = new HashMap<String, Object>();
		
		List<Person> list = new ArrayList<Person>();
		
		Person p1 = new Person();
		p1.setId(1);
		p1.setUsername("张三");
		p1.setAddress("花山");
		
		Person p2 = new Person();
		p2.setId(1);
		p2.setUsername("张三");
		p2.setAddress("花山");
		
		Person p = new Person();
		p.setId(1);
		p.setUsername("张三");
		p.setAddress("花山");
		
		list.add(p);
		list.add(p1);
		list.add(p2);
		
		maps.put("list", list);
//		6）	创建一个输出对象：生成模版文件
		Writer out = new FileWriter(new File("E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\out\\list.html"));
//		7）	生成
		template.process(maps,out);
//		8）	关闭对象
		out.close();
	}
	@Test
	public void testFreeMarkerFour() throws IOException, TemplateException {
//		1）	创建配置对象，配置Freemarker版本
		Configuration configuration = new Configuration(Configuration.getVersion());
//		2）	指定模版文件路径
		configuration.setDirectoryForTemplateLoading(new File("E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\test"));
//		3）	指定模版文件默认编码
		configuration.setDefaultEncoding("UTF-8");
//		4）	获取模版对象
		Template template = configuration.getTemplate("data.ftl");
//		5）	创建模拟数据
		Map<String, Object> maps = new HashMap<String, Object>();
		
		Date date = new Date();
		maps.put("today", date);
//		6）	创建一个输出对象：生成模版文件
		Writer out = new FileWriter(new File("E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\out\\data.html"));
//		7）	生成
		template.process(maps,out);
//		8）	关闭对象
		out.close();
	}
}
