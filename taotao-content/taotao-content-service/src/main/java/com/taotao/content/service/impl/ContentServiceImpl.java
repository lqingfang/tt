package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.content.redis.JedisDao;
import com.taotao.content.service.ContentService;
import com.taotao.domain.ADItem;
import com.taotao.domain.TbContent;
import com.taotao.domain.TbContentExample;
import com.taotao.domain.TbContentExample.Criteria;
import com.taotao.mapper.TbContentMapper;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${WIDTH}")
	private Integer WIDTH;
	@Value("${WIDTHB}")
	private Integer WIDTHB;
	@Value("${HEIGHT}")
	private Integer HEIGHT;
	@Value("${HEIGHTB}")
	private Integer HEIGHTB;
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisDao jedisDao;
	
	@Value("${AD_CHACHE}")
	private String AD_CHACHE;
	/*
	 * 需求：根据内容分类Id,查询内容分类下面内容表 
	 * 参数：Long categoryId
	 * 返回值：EasyUIResult
	 * 
	 * 
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
	 * 同步缓存：
	 * 添加新分类内容时，删除缓存当中缓存。
	 * 当再次查询缓存时，从新查询数据库，把新的数据放入缓存，做到缓存同步。
	 */
	public TaotaoResult saveContent(TbContent content) {
		
		//执行新添加内容任务时，先删除缓存:缓存同步
		jedisDao.hdel(AD_CHACHE, content.getCategoryId().toString());
		
		//补全时间类型
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}

	/*
	 *同步缓存：
	 * 添加新分类内容时，删除缓存当中缓存。
	 * 当再次查询缓存时，从新查询数据库，把新的数据放入缓存，做到缓存同步。 
	 * *
	 */
	@Override
	public TaotaoResult deleteByContentId(Long ids) {
		
		TbContent tbContent = contentMapper.selectByPrimaryKey(ids);
		
		//执行新添加内容任务时，先删除缓存:缓存同步
		jedisDao.hdel(AD_CHACHE, tbContent.getCategoryId().toString());
		
		
		contentMapper.deleteByPrimaryKey(ids);
		return TaotaoResult.ok();
	}

	/*
	 * 需求：根据分类Id,查询分类内容表tb_content,动态维护首页信息
	 * 参数： Long categoryId
	 * 返回值：List<ADItem>
	 * 
	 * 为了提高项目并发量和查询效率(QPS Query per second)
	 * 给首页加上缓存：缓存是内存板redis
	 * redis数据存储结构：key:value 
	 * redis key 设计：
	 * 采用hash类型存储首页缓存
	 *  key:AD_CHACHE
	 *  field:categoryId
	 *  value:json格式数组字符串
	 */
	public List<ADItem> findContentListByCategoryId(Long categoryId) {
		//查询数据库之前，先查询缓存
		//1、如果缓存中有数据，直接返回所需要数据
		//2、如果缓存中没有数据，查询数据库
		try {
			String json = jedisDao.hget(AD_CHACHE, categoryId.toString());
			if (StringUtils.isNotBlank(json)) {
				List<ADItem> jsonToList = JsonUtils.jsonToList(json, ADItem.class);
				return jsonToList;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		
		List<TbContent> contentList = contentMapper.selectByExample(example);
		
		List<ADItem> adList = new ArrayList<ADItem>();
		for (TbContent tbContent : contentList) {
			ADItem adItem = new ADItem();
			adItem.setAlt(tbContent.getTitle());
			adItem.setSrc(tbContent.getPic());
			adItem.setSrcB(tbContent.getPic2());
			adItem.setHref(tbContent.getUrl());
			
			adItem.setHeight(HEIGHT);
			adItem.setHeightB(HEIGHTB);
			adItem.setWidth(WIDTH);
			adItem.setWidthB(WIDTHB);
			
			adList.add(adItem);
		}
		jedisDao.hset(AD_CHACHE, categoryId.toString(), JsonUtils.objectToJson(adList));
		return adList;
	}

}
