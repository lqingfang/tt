package com.taotao.item.mq;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.domain.TbItem;
import com.taotao.domain.TbItemDesc;
import com.taotao.manager.service.ItemService;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class AddItemFreeMarkerListener implements MessageListener{

	//注入商品Service对象
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${GEN_HTML_PATH}")
	private String GEN_HTML_PATH;
	
	@Override
	public void onMessage(Message message) {
		//接受消息
		if(message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
			//获取消息
			
				try {
					String itemId = tm.getText();
					//根据消息商品Id,查询商品信息
					TbItem tbItem = itemService.findItemById(Long.parseLong(itemId));
					TbItemDesc itemDesc = itemService.findItemDescByID(Long.parseLong(itemId));
					Configuration configuration = freeMarkerConfigurer.getConfiguration();
					Template template = configuration.getTemplate("item.ftl");
					//创建map对象，封装数据
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("item", tbItem);
					maps.put("itemDesc", itemDesc);
					
					//输出Writer对向，指定输出服务器地址
					Writer out = new FileWriter(new File(GEN_HTML_PATH+itemId+".html"));
					//生成
					
					template.process(maps, out);
					 
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TemplateNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedTemplateNameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (TemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}

}
