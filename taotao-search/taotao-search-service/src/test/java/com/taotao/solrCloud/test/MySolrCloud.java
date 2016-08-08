package com.taotao.solrCloud.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Ignore;
import org.junit.Test;

public class MySolrCloud {

	//测试solrCloud集群添加索引库
	@Ignore
	@Test
	public void addSolrCloudDoc() {
		//指定服务协调者地址  zookeeper地址
		String zkHost = "192.168.227.136:2182,192.168.227.136:2183,192.168.227.136:2184";
		//创建集群对象
		CloudSolrServer cloudSolrServer = new CloudSolrServer(zkHost);
		//设置默认添加索引库
		cloudSolrServer.setDefaultCollection("collection2");
		//创建模拟文档
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "134466666666666");
		doc.addField("item_title", "牙套");
		
		try {
			cloudSolrServer.add(doc);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cloudSolrServer.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void querySolrCloudDoc() {
		//指定服务协调者地址  zookeeper地址
		String zkHost = "192.168.227.136:2182,192.168.227.136:2183,192.168.227.136:2184";
		//创建集群对象
		CloudSolrServer cloudSolrServer = new CloudSolrServer(zkHost);
		//设置默认添加索引库
		cloudSolrServer.setDefaultCollection("collection2");
		//创建封装查询参数对象solrQuery
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("id:134466666666666");
		
		try {
			QueryResponse response = cloudSolrServer.query(solrQuery);
			//获取查询结果文档集合
			SolrDocumentList solrDocumentList = response.getResults();
			System.out.println(solrDocumentList.getNumFound());
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
