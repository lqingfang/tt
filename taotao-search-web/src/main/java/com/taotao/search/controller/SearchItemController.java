package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchItemService;

@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemService searchItemService;
	
	/*
	 * 需求：根据页面传递搜索参数，查询索引库 
	 * 请求：http://localhost:8085/search.html?q=搜索参数
	 */
	
  	@RequestMapping("search")
  	public String search(@RequestParam(value = "q") String qName,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "60") Integer rows,Model model) {
		//解决乱码
		try {
			qName = new String(qName.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SearchResult result = searchItemService.findItemBySolr(qName, page, rows);
		//回显查询参数
		model.addAttribute("query", qName);
		//回显商品列表
		model.addAttribute("itemList", result.getItemList());
		//回显当前页
		model.addAttribute("page", result.getCurPage());
		//回显总页码
		model.addAttribute("totalPages", result.getPages());
		return "search";
	}
	
}
