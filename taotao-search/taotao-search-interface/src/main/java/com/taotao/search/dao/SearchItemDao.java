package com.taotao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.search.pojo.SearchResult;

public interface SearchItemDao {

	//查询索引库dao层
	public SearchResult findIemBySolr(SolrQuery solrQuery);
}
