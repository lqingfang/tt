package com.taotao.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.domain.TbItem;
import com.taotao.domain.TbItemDesc;
import com.taotao.domain.TbItemExample;
import com.taotao.domain.TbItemExample.Criteria;
import com.taotao.manager.service.ItemService;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.IDUtils;
import com.taotao.utils.TaotaoResult;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	/**
	 * 需求：根据Id查询商品
	 * 参数： 商品Id,类型long
	 * 返回值：商品对象
	 */
	public TbItem findItemById(Long id) {
		TbItemExample example = new TbItemExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andIdEqualTo(id);
		List<TbItem> list = itemMapper.selectByExample(example);
		TbItem item = null;
		if(list!=null && list.size()>0) {
			item = list.get(0);
		}
		return item;
	}

	@Override
	public EasyUIResult findItemListByPage(Integer page, Integer rows) {
TbItemExample example = new TbItemExample();
		
		//设置分页信息
		PageHelper.startPage(page, rows);
		//分页查询商品列表
		List<TbItem> list = itemMapper.selectByExample(example);
		
		//创建pageInfo对象获取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		//创建一个返回值对像EasyUIResult，封装返回结果
		EasyUIResult result = new EasyUIResult();
		//设置总记录数
		result.setTotal(pageInfo.getTotal());
		//设置查询分页记录
		result.setRows(list);
		
		return result;

	}

	/**
	 * 需求：保存商品信息
	 *  1、tb_item商品表
	 *  2、tb_item_desc 商品描述表
	 * 返回值：{status:200} 包装对象：TaotaoResult
	 */
	public TaotaoResult saveItem(TbItem item, TbItemDesc itemDesc) {
		//思考：商品表Id需要手动生成
		//1.redis自增1
		//2.UUID
		//3.时间戳：毫秒+随机数
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte)1);
		
		Date date = new Date();
		item.setUpdated(date);
		item.setCreated(date);
		
		itemMapper.insert(item);
		
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}

}
