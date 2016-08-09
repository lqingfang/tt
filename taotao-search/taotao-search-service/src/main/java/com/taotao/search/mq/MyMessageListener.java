package com.taotao.search.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
			try {
				System.out.println("接受消息"+ tm.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
