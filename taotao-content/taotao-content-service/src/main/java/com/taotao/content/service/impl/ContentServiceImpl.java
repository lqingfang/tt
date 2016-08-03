package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.content.service.ContentService;
import com.taotao.domain.TbContent;
import com.taotao.domain.TbContentExample;
import com.taotao.domain.TbContentExample.Criteria;
import com.taotao.mapper.TbContentMapper;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	/*
	 * 需求：根据内容分类Id,查询内容分类下面内容表 
	 * 参数：Long categoryId
	 * 返回值：EasyUIResult
	 */
	public EasyUIResult findContentByCategoryId(Long categoryId, Integer page,
			Integer rows) {
		// 创建example对象
		TbContentExample example = new TbContentExample();
		// 创建Criteria对象
		Criteria createCriteria = example.createCriteria();
		// 设置查询参数
		createCriteria.andCategoryIdEqualTo(categoryId);
		// 设置分页查询
		PageHelper.startPage(page, rows);
		// 执行查询
		List<TbContent> contentList = contentMapper.selectByExample(example);
		// 创建pageInfo 获取分页信息
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contentList);
		// 创建EasyUIResult封装分页结果信息
		EasyUIResult result = new EasyUIResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(contentList);
		return result;
	}

	/**
	 * 需求：根据分类Id，添加此分类Id下面内容信息
	 * 参数：TbContent
	 * 返回值：Taotaoresult
	 * 
	 */
	public TaotaoResult saveContent(TbContent content) {
		//补全时间类型
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteByContentId(Long ids) {
		contentMapper.deleteByPrimaryKey(ids);
		return TaotaoResult.ok();
	}

}
