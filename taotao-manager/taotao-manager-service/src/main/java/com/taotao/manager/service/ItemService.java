package com.taotao.manager.service;

import com.taotao.domain.TbItem;


public interface ItemService {

	/**
	 * 需求：根据Id查询商品
	 */
	public TbItem findItemById(Long id);
}
