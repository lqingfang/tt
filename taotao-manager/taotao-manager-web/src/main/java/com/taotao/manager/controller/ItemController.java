package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.domain.TbItem;
import com.taotao.manager.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * 需求：根据Id查询商品
	 * 参数：long Id
	 * 返回值：json格式item对象(@ResponseBody)
	 */
	@RequestMapping("/item/findItemById/{id}")
	public @ResponseBody TbItem findItemById(@PathVariable Long id) {
		TbItem item = itemService.findItemById(id);
		System.out.println(item);
		return item;
	}
}
