package com.taotao.content.service;

import com.taotao.domain.TbContent;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;


public interface ContentService {

	public EasyUIResult findContentByCategoryId(Long categoryId,Integer page,Integer rows);
	
	public TaotaoResult saveContent(TbContent content);

	public TaotaoResult deleteByContentId(Long ids);
}
