package com.taotao.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.content.service.ContentService;
import com.taotao.domain.ADItem;
import com.taotao.utils.JsonUtils;

@Controller
public class PageController {

	@Autowired
	private ContentService contentService;
	
	//注入大广告位ID
	@Value("${CATEGORYID}")
	private Long CATEGORYID;
	
	@RequestMapping("index")
	public String showIndex(Model model) {
		List<ADItem> adList = contentService.findContentListByCategoryId(CATEGORYID);
		String adJson = JsonUtils.objectToJson(adList);
		model.addAttribute("ad1",adJson);
		return "index";
	}
}
