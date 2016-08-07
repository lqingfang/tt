package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchItemDao;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;

@Repository
public class SearchItemDaoImpl implements SearchItemDao {

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public SearchResult findIemBySolr(SolrQuery solrQuery) {
		//创建SearchResult对象，封装查询结果
		SearchResult result = new SearchResult();
		
		//创建集合对象：List<SearchItem>
		List<SearchItem> itemList = new ArrayList<SearchItem>();
		try {
			//执行查询索引库
			QueryResponse response = solrServer.query(solrQuery);
			//获取结果集合文档对象
			SolrDocumentList solrDocumentList = response.getResults();
			//获取查询总记录数
			long numFound = solrDocumentList.getNumFound();
			//设置查询总记录数
			result.setTotalRecord(numFound);
			//循环集合，获取查询索引库索引数据
			for (SolrDocument solrDocument : solrDocumentList) {
				//获取文档对象值，把值设置SearchItem对象中
				SearchItem item = new SearchItem();
				
				//设置Id值
				item.setId(Long.parseLong((String)solrDocument.get("id")));
				
				//获取商品标题
				String itemTitle = (String) solrDocument.get("item_title");
				
				//获取高亮显示
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				Map<String, List<String>> map = highlighting.get((String)solrDocument.get("id"));
				List<String> list = map.get("item_title");
				if(list!=null && list.size()>0) {
					itemTitle = list.get(0);
				}
				item.setTitle(itemTitle);
				//获取商品买点
				item.setSell_point((String)(solrDocument.get("item_sell_point")));
				//获取商品价格
				item.setPrice((Long)solrDocument.get("item_price"));
				//获取商品类别名称
				item.setCategory_name((String)(solrDocument.get("item_category_name")));
				//获取商品描述信息
				item.setItem_desc((String)(solrDocument.get("item_desc")));
				
				itemList.add(item);
			}
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//查询结果设置到集合中
		result.setItemList(itemList);
		
		return result;
	}

}
