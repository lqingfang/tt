package com.taotao.content.service;

import java.util.List;

import com.taotao.domain.ADItem;
import com.taotao.domain.TbContent;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;


public interface ContentService {

	public EasyUIResult findContentByCategoryId(Long categoryId,Integer page,Integer rows);
	
	public TaotaoResult saveContent(TbContent content);

	public TaotaoResult deleteByContentId(Long ids);
	/*
	 * 需求：根据分类Id,查询分类内容表tb_content,动态维护首页信息
	 * 参数： Long categoryId
	 * 返回值：List<ADItem>
	 */
	public List<ADItem> findContentListByCategoryId(Long categoryId);
	
}
