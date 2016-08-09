package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.domain.TbItem;
import com.taotao.domain.TbItemDesc;
import com.taotao.manager.service.ItemService;

@Controller
public class ItemDetailController {

	@Autowired
	private ItemService itemService;
	//http://localhost:8087/${item.id }.html
		/**
		 * 需求：根据商品Id查询商品信息，提供给商品详情页面回显
		 * 请求：http://localhost:8087/${item.id }.html
		 * 返回值：返回到商品详情页面：item.jsp
		 * 商品详情页面需要数据：
		 *  1.商品信息
		 *  2.商品描述信息
		 *  3.商品规格参数
		 * @param itemId
		 * @return
		 */
	@RequestMapping("{itemId}")
	public String findItemDetailByID(@PathVariable Long itemId,Model model){
		//查询商品信息
		TbItem item = itemService.findItemById(itemId);
		//查询商品描述信息
		TbItemDesc itemDesc = itemService.findItemDescByID(itemId);
		//页面回显
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		
		return "item";
		
	}
}
