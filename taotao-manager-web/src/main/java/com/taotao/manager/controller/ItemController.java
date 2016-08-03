package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.domain.TbItem;
import com.taotao.domain.TbItemDesc;
import com.taotao.manager.service.ItemService;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;

@Controller
public class ItemController {

	// 注入Service对象：面向服务架构：通过dubbo注入对象
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
	
	@RequestMapping("/item/list")
	public @ResponseBody EasyUIResult findItemByPage(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "30") Integer rows) {
		
		EasyUIResult result = itemService.findItemListByPage(page, rows);
		
		return result;

	}
	/**
	 * 请求：$.post("/item/save"
	 * 参数：需要保存2张表数据，商品表，商品描述表。
	 * 返回值：同过ajax回调函数判断需要返回值类型
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	@RequestMapping("/item/save")
	public @ResponseBody TaotaoResult saveItem(TbItem item,TbItemDesc itemDesc) {
		TaotaoResult result = itemService.saveItem(item, itemDesc);
		return result;
	}

}
