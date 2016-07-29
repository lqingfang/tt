package com.taotao.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.domain.TbItem;
import com.taotao.domain.TbItemExample;
import com.taotao.domain.TbItemExample.Criteria;
import com.taotao.manager.service.ItemService;
import com.taotao.mapper.TbItemMapper;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	
	/**
	 * 需求：根据Id查询商品
	 * 参数： 商品Id,类型long
	 * 返回值：商品对象
	 */
	public TbItem findItemById(Long id) {
		TbItemExample example = new TbItemExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andIdEqualTo(id);
		List<TbItem> list = itemMapper.selectByExample(example);
		TbItem item = null;
		if(list!=null && list.size()>0) {
			item = list.get(0);
		}
		return item;
	}

}
