package com.taotao.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Ignore;
import org.junit.Test;

public class MySendMessageMQ {

	// 点对点发送消息
	@Ignore
	@Test
	public void sendMessageOne() throws JMSException {
		//创建消息工厂：参数，协议，地址，端口
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.227.136:61616");
		//获取连接
		Connection connection = cf.createConnection();
		//开启连接
		connection.start();
		
		//从连接中获取Session,
		//参数1：消息事务；参数2：事务使用自动应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//获取消息发送目的地
		//创建消息发送目的地，相当于在JMS服务器中开辟一块空间，空间名称oneQueue，发送消息就发送到oneQueue消息目的地
		Queue queue = session.createQueue("oneQueue");
		//创建消息发送者
		MessageProducer producer = session.createProducer(queue);
		//创建模拟消息
		TextMessage message = new ActiveMQTextMessage();
		message.setText("我是中国人");
		
		//发送
		producer.send(message);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void oneSendMessageTopic() throws JMSException {
		//创建消息工厂：参数，协议，地址，端口
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.227.136:61616");
		//获取连接
		Connection connection = cf.createConnection();
		//开启连接
		connection.start();
		
		//从连接中获取Session,
		//参数1：消息事务；参数2：事务使用自动应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//获取消息发送目的地
		//创建消息发送目的地，相当于在JMS服务器中开辟一块空间，空间名称oneQueue，发送消息就发送到oneQueue消息目的地
		Topic topic = session.createTopic("mytopic");
		//创建消息发送者
		MessageProducer producer = session.createProducer(topic);
		//创建模拟消息
		TextMessage message = new ActiveMQTextMessage();
		message.setText("我是中国人");
		
		//发送
		producer.send(message);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}
}
