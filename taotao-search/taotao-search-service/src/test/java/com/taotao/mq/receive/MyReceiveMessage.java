package com.taotao.mq.receive;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class MyReceiveMessage {

	//接受消息：同步模式
		@Test
		public void receiveMessageOne() throws Exception{
			// 创建消息工厂：参数：协议，地址，端口
			ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.227.136:61616");
			//获取连接
			Connection connection = cf.createConnection();
			//开启连接
			connection.start();
			
			//从连接中获取Session
			//第一个参数：消息事务
			//第二个参数：事务使用自动应答模式
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//获取消息发送目的地：
			//创建消息发送目的地，相当于在JMS服务器中开辟一块空间，空间名称oneQueue
			//发送消息就发送到oneQueue消息目的地
			Queue queue = session.createQueue("oneQueue");
			//创建消息接受者
			MessageConsumer consumer = session.createConsumer(queue);
			//同步接受
			Message message = consumer.receive();
			if(message instanceof TextMessage){
				TextMessage tm = (TextMessage) message;
				System.out.println(tm.getText());
			}
			//关闭资源
			consumer.close();
			session.close();
			connection.close();	
		}
		@Test
		public void receiveMessageTwo() throws JMSException, IOException {
			// 创建消息工厂：参数：协议，地址，端口
			ConnectionFactory cf = new ActiveMQConnectionFactory(
					"tcp://192.168.227.136:61616");
			//获取连接
			Connection connection = cf.createConnection();
			//开启连接
			connection.start();
			
			//从连接中获取Session
			//第一个参数：消息事务
			//第二个参数：事务使用自动应答模式
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//获取消息发送目的地：
			//创建消息发送目的地，相当于在JMS服务器中开辟一块空间，空间名称oneQueue
			//发送消息就发送到oneQueue消息目的地
			Queue queue = session.createQueue("oneQueue");
			//创建消息接受者
			MessageConsumer consumer = session.createConsumer(queue);
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					
					if(message instanceof TextMessage){
						TextMessage tm = (TextMessage) message;
						try {
							System.out.println(tm.getText());
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			System.in.read();
			//关闭资源
			consumer.close();
			session.close();
			connection.close();	
	}

	@Test
	public void receiveMessageSycrTopic() throws JMSException, IOException {
		// 创建消息工厂：参数：协议，地址，端口
		ConnectionFactory cf = new ActiveMQConnectionFactory(
				"tcp://192.168.227.136:61616");
		//获取连接
		Connection connection = cf.createConnection();
		//开启连接
		connection.start();
		
		//从连接中获取Session
		//第一个参数：消息事务
		//第二个参数：事务使用自动应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//获取消息发送目的地：
		//创建消息发送目的地，相当于在JMS服务器中开辟一块空间，空间名称oneQueue
		//发送消息就发送到oneQueue消息目的地
		Topic topic = session.createTopic("mytopic");
		//创建消息接受者
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				
				if(message instanceof TextMessage){
					TextMessage tm = (TextMessage) message;
					try {
						System.out.println(tm.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		System.in.read();
	}
}
