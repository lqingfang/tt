package com.taotao.manager.service;

import com.taotao.domain.TbItem;
import com.taotao.domain.TbItemDesc;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;


public interface ItemService {

	/**
	 * 需求：根据Id查询商品
	 */
	public TbItem findItemById(Long id);
	
	/*
	 * 需求：查询商品列表，分页查询
	 * 参数：当前页：integer page,页码尺寸：integer rows
	 * 返回值：{total:3224,rows:[{},{}]}
	 * 使用包装对象(EasyUIResult)：Long total ,List<?> rows
	 * 使用注解@responseBody自动转换成json格式数据
	 */
	public EasyUIResult findItemListByPage(Integer page, Integer rows);
	
	/**
	 * 需求：保存商品信息
	 *  1、tb_item商品表
	 *  2、tb_item_desc 商品描述表
	 * 返回值：{status:200} 包装对象：TaotaoResult
	 */
	public TaotaoResult saveItem(TbItem item,TbItemDesc itemDesc);

	public TbItemDesc findItemDescByID(Long itemId);
}
