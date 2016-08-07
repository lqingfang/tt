package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;
import com.taotao.utils.TaotaoResult;

public interface SearchItemService {

	//导入索引库Service接口方法
	public TaotaoResult dataImport();
	
	//查询索引库
	public SearchResult findItemBySolr(String qName,Integer page,Integer rows);
}
