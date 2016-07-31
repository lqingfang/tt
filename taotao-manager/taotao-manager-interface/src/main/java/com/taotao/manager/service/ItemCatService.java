package com.taotao.manager.service;

import java.util.List;

import com.taotao.utils.EasyUITreeNode;

public interface ItemCatService {
	/*
	 * 需求：根据父Id,查询树形节点信息
	 * 参数：Long id(父id)
	 * 返回值：List<EasyUITreddNode>
	 * @Param parentId
	 * @return 
	 * 业务描述：
	 *    1、根据父Id查询此节点下面的子节点（如果说此节点有子节点，它必然会有parentId）
	 *    2、如果isParent=1:表示此节点有子节点，如果此节点没有子节点：isParent=0
	 */
	public List<EasyUITreeNode> findItemCatList(Long parentId);
}
