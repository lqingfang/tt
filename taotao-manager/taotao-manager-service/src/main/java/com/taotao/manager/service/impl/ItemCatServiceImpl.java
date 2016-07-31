package com.taotao.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.domain.TbItemCat;
import com.taotao.domain.TbItemCatExample;
import com.taotao.domain.TbItemCatExample.Criteria;
import com.taotao.manager.service.ItemCatService;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.utils.EasyUITreeNode;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	/**
	 * 需求：根据父Id，查询树形节点信息
	 * 参数：Long id（父id）
	 * 返回值：List<EasyUITreeNode>
	 * @param parentId
	 * @return
	 * 业务描述：
	 * 	1、根据父Id查询此节点下面的子节点(如果说此节点有子节点，它必然会有parentId)
	 * 	2、如果isParent=1：表示此节点有子节点，如果此节点没有子节点：isParent=0
	 */
	public List<EasyUITreeNode> findItemCatList(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> itemCatlist = itemCatMapper.selectByExample(example);
		List<EasyUITreeNode> treeNodelist = new ArrayList<EasyUITreeNode>();
		for (TbItemCat tbItemCat : itemCatlist) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(tbItemCat.getId().intValue());
			treeNode.setText(tbItemCat.getName());
			treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			treeNodelist.add(treeNode);
		}
		return treeNodelist;
	}

}
