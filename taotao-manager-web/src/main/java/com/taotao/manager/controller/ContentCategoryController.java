package com.taotao.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.content.service.ContentCategoryService;
import com.taotao.utils.EasyUITreeNode;
import com.taotao.utils.TaotaoResult;

@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	/**
	 * 请求：/content/category/list
	 * 参数：EasyUITree发送参数名称Id,首先加载最高级菜单：设置默认查询参数0
	 * 返回值：json 格式 List<EasyUITreeNode>
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/content/category/list") public @ResponseBody List<EasyUITreeNode> list(
			@RequestParam(defaultValue = "0", value= "id") Long parentId
			) {
		List<EasyUITreeNode> list = contentCategoryService.findContentCategoryList(parentId);
		return list;
	}
	/**
	 * 需求：创建新的首页内容分类
	 * 请求：/content/category/create
	 * 参数：Long parentId(上一级节点Id) String  name (新建节点名称)
	 * 返回值：json 格式 TaotaoResult.ok(contentCategory)
	 * 业务：
	 * 如果创建节点父节点是子节点：
	 * 	子节点就变成父节点，修改isParent状态值，isParnet=1
	 * 如果节点父节点本身就是父节点
	 * 	直接创建节点即可。
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/create")
	public @ResponseBody TaotaoResult createNode(Long parentId,String name) {
		TaotaoResult result = contentCategoryService.createNode(parentId, name);
		return result;
	}
}
