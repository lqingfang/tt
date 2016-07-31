package com.taotao.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.service.ItemCatService;
import com.taotao.utils.EasyUITreeNode;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	/*
	 * 需求：根据父Id,查询树形节点信息
	 * 请求：/item/cat/list
	 * 参数：父Id 
	 *  1、第一次查询，没有传递参数，默认查询顶级节点：parent=0
	 * 	2、使用注解@requestParam设置默认参数
	 *  返回值：[{id:1,text:"",state:""},{}]
	 * @param parentId
	 * @return
	 */
	@RequestMapping("item/cat/list")
	public @ResponseBody List<EasyUITreeNode> findItemCatList(@RequestParam(defaultValue = "0" ,value="id" )Long parentId) {
		List<EasyUITreeNode> itemCatList = itemCatService.findItemCatList(parentId);
		return itemCatList;
	}
}
