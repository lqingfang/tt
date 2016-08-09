package com.taotao.mq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MySpringSendMessage {
	// 点对点模式发送消息
	@Test
	public void pointToPoint() {
		// 加载spring 配置文件
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:applicationContext-mq.xml");
		// 获取发送消息模版对象JMSTemplate
		JmsTemplate jmsTemplate = app.getBean(JmsTemplate.class);
		// 获取消息发送目的地
		//Destination destination = (Destination) app.getBean("myqueue");
		Destination destination = (Destination) app.getBean("mytopic");
		// 发送消息
		jmsTemplate.send(destination,new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage("我爱吃梨");
			}
		});
	}
}
