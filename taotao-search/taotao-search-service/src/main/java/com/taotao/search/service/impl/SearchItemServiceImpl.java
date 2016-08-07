package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.search.dao.SearchItemDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchItemService;
import com.taotao.utils.TaotaoResult;

@Service
public class SearchItemServiceImpl implements SearchItemService{

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private SearchItemMapper searchItemMapper;
	//注入solr dao
	@Autowired
	private SearchItemDao searchItemDao;
		
	@Override
	public TaotaoResult dataImport() {
		List<SearchItem> itemList = searchItemMapper.dataImport();
		for (SearchItem searchItem : itemList) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", searchItem.getId());
			doc.addField("item_title", searchItem.getTitle());
			doc.addField("item_sell_point", searchItem.getSell_point());
			doc.addField("item_price", searchItem.getPrice());
			doc.addField("item_image", searchItem.getImage());
			doc.addField("item_category_name", searchItem.getCategory_name());
			doc.addField("item_desc", searchItem.getItem_desc());
			
			try {
				solrServer.add(doc);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			solrServer.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	/*
	 * 需求：根据查询参数查询索引库
	 * 业务：
	 * 技术：solr服务器
	 * 参数：solr使用SolrQuery封装查询参数
	 * 返回值：SearchResult包装查询分页信息
	 * 
	 */
	public SearchResult findItemBySolr(String qName, Integer page, Integer rows) {
		//创建SolrQuery对象，封装查询参数
		SolrQuery solrQuery = new SolrQuery();
		if(StringUtils.isNotBlank(qName)){
			solrQuery.setQuery(qName);
		}else {
			solrQuery.setQuery("*:*");
		}
		//设置分页信息
		solrQuery.setStart((page-1)*rows);
		solrQuery.setRows(rows);
		
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");		
		solrQuery.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		solrQuery.setHighlightSimplePost("</font>");
		//设置默认查询字段
		solrQuery.set("df", "item_keywords");
		
		SearchResult result = searchItemDao.findIemBySolr(solrQuery);
		//计算页码
		//获取总记录数
		Integer totalRecord = result.getTotalRecord().intValue();
		int pages = totalRecord/rows;
		if(totalRecord%rows>0){
			pages++;
		}
		//设置当前页
		result.setCurPage(page);
		//设置总页码数
		result.setPages(pages);
		
		return result;
	}

}
