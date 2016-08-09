package com.taotao.search.mapper;

import java.util.List;

import com.taotao.search.pojo.SearchItem;

public interface SearchItemMapper {

	//导入数据方法
	public List<SearchItem> dataImport();
	//根据Id查询商品信息
	public SearchItem findSearchItemByID(Long itemId);
}
