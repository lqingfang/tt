package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.content.service.ContentCategoryService;
import com.taotao.domain.TbContentCategory;
import com.taotao.domain.TbContentCategoryExample;
import com.taotao.domain.TbContentCategoryExample.Criteria;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.utils.EasyUITreeNode;
import com.taotao.utils.TaotaoResult;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<EasyUITreeNode> findContentCategoryList(Long parentId) {
		//创建example对象
		TbContentCategoryExample example = new TbContentCategoryExample();
		//创建criteria对象
		Criteria createCriteria = example.createCriteria();
		//设置参数
		createCriteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> clist = tbContentCategoryMapper.selectByExample(example);
		//创建List<EasyUITreeNode>集合，封装查询结果
		List<EasyUITreeNode> treeNodeList = new ArrayList<EasyUITreeNode>();
		//循环查询结果，封装节点信息
		for (TbContentCategory contentCategory : clist) {
			//创建EasyUITrddNode对象，封装树形节点信息
			EasyUITreeNode treeNode = new EasyUITreeNode();
			//设置节点Id
			treeNode.setId(contentCategory.getId().intValue());
			//设置节点名称
			treeNode.setText(contentCategory.getName());
			//设置节点状态
			treeNode.setState(contentCategory.getIsParent()?"closed":"open");
			//节点信息封装treeNodeList集合
			treeNodeList.add(treeNode);
		}
		return treeNodeList;
	}
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
	public TaotaoResult createNode(Long parentId, String name) {
		// 创建内容分类对象，补全属性
		TbContentCategory contentCategory = new TbContentCategory();
		// 设置内容分类父Id
		contentCategory.setParentId(parentId);
		// 设置内容分类名称
		contentCategory.setName(name);
		// 状态。可选值:1(正常),2(删除)
		contentCategory.setStatus(1);
		//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		contentCategory.setSortOrder(1);
		contentCategory.setIsParent(false);
		Date date = new Date();
		contentCategory.setCreated(date);
		contentCategory.setUpdated(date);
		//保存
		tbContentCategoryMapper.insert(contentCategory);
		
		
		// 判断上级节点是否是父节点，如果不是，修改isParent状态值，isParent = 1
		TbContentCategory cCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!cCategory.getIsParent()) {
			cCategory.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(cCategory);
		}
		return TaotaoResult.ok(contentCategory);
	}

}
