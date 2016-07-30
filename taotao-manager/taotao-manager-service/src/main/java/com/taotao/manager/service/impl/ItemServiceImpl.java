package com.taotao.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.domain.TbItem;
import com.taotao.domain.TbItemExample;
import com.taotao.domain.TbItemExample.Criteria;
import com.taotao.manager.service.ItemService;
import com.taotao.mapper.TbItemMapper;
import com.taotao.utils.EasyUIResult;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	
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

}
