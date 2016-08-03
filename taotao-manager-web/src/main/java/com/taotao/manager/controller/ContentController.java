package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.content.service.ContentService;
import com.taotao.domain.TbContent;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	/**
	 * 需求：根据内容分类Id，查询内容分类下面内容表 
	 * 请求：/content/query/list
	 *  参数：Long categoryId
	 * 分页参数：EasyUI框架自动传递：integer page,integer rows 
	 * 返回值：json格式EasyUIResult
	 * easyUI框架需要分页返回值信息格式如下： {total:6,rows:[{},{},{}]}
	 */
	@RequestMapping("/content/query/list")
	public @ResponseBody EasyUIResult findContentByCategoryId(Long categoryId, Integer page, Integer rows) {
		EasyUIResult easyUIResult = contentService.findContentByCategoryId(categoryId, page, rows);
		return easyUIResult;
	}
	/**
	 * 需求：根据分类Id，添加此分类Id下面内容信息
	 * 请求：/content/save
	 * 参数：TbContent
	 * 返回值：json 格式 Taotaoresult
	 * 
	 */
	@RequestMapping("/content/save")
	public @ResponseBody TaotaoResult saveContent(TbContent content) {
		TaotaoResult result = contentService.saveContent(content);
		return result;
	}
	
	@RequestMapping("/content/delete")
	public @ResponseBody TaotaoResult saveContent(Long ids) {
		TaotaoResult result = contentService.deleteByContentId(ids);
		return result;
	}
}
