package com.taotao.mq.receive;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MySpringReceiveMessage {

	//通过spring接受消息：点对点模式接受消息
	@Test
	public void receiveMessageP2p() {
		//加载spring配置文件
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:applicationContext-mq.xml");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
