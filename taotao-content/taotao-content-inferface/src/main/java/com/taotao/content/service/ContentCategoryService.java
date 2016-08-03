package com.taotao.content.service;

import java.util.List;

import com.taotao.utils.EasyUITreeNode;
import com.taotao.utils.TaotaoResult;

public interface ContentCategoryService {

	/**
	 * 需求：加载门户系统商城首页商品内容分类树形菜单
	 * 参数：父id Long parentId
	 * 返回值：[{id:1,text:"树形节点名称",state:"closed"},{},{}]==> List转换成json数组
	 * 	    List<EasyUITreeNode>
	 */
	public List<EasyUITreeNode> findContentCategoryList(Long parentId);
	/*
	 * 需求：创建心得首页内容分类
	 * 参数：Long parentId(上一级节点Id) String name(新建节点名称)
	 * 返回值 ：TaotaoResult.ok(contentCategory)
	 * 业务：
	 *    如果当前在本身是子节点下创建新的节点：
	 *       子节点变成父节点，修改isParent状态值，isParent = 1
	 *    如果在本身就是父节点下创建心得节点：
	 *       直接创建节点即可。
	 *  @param parentId
	 *  @param name
	 *  @return
	 */
	 public TaotaoResult createNode(Long parentId, String name);
	 
	 }
