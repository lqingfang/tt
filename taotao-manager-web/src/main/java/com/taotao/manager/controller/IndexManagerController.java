package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.search.service.SearchItemService;
import com.taotao.utils.TaotaoResult;

@Controller
public class IndexManagerController {

	@Autowired
	private SearchItemService searchItemService;
	/**
	 * 需求：导入数据库数据到索引库
	 * 请求：dataImport
	 * 参数：无
	 * 返回值：json格式taotaoResult
	 * @return
	 */
	@RequestMapping("dataImport")
	public @ResponseBody TaotaoResult dataImport() {
		TaotaoResult result = searchItemService.dataImport();
		return result;
	}
}
