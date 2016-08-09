package com.taotao.search.mq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.pojo.SearchItem;

public class AddItemMessageListener implements MessageListener{

	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Autowired
	private SolrServer solrServer;

	/**
	 * 接受消息，同步索引库
	 */
	public void onMessage(Message message) {
		if(message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
			try {
				String itemId = tm.getText();
				//根据消息，查询数据库，同步索引库
				SearchItem searchItem = searchItemMapper.findSearchItemByID(Long.parseLong(itemId));
				//创建文档对象
				SolrInputDocument doc = new SolrInputDocument();
				//设置值:设置文档Id
				doc.addField("id", searchItem.getId());
				//设置商品标题
				doc.addField("item_title", searchItem.getTitle());
				//设置商品买点
				doc.addField("item_sell_point", searchItem.getSell_point());
				//设置商品价格
				doc.addField("item_price", searchItem.getPrice());
				//设置图片地址
				doc.addField("item_image", searchItem.getImage());
				//设置商品类别名称
				doc.addField("item_category_name", searchItem.getCategory_name());
				//设置商品描述信息
				doc.addField("item_desc", searchItem.getItem_desc());
				
				try {
					solrServer.add(doc);
					solrServer.commit();
				} catch (SolrServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
