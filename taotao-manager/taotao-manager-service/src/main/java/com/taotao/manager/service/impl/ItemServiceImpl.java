package com.taotao.manager.service.impl;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.domain.TbItem;
import com.taotao.domain.TbItemDesc;
import com.taotao.domain.TbItemExample;
import com.taotao.domain.TbItemExample.Criteria;
import com.taotao.manager.redis.JedisDao;
import com.taotao.manager.service.ItemService;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.IDUtils;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	//注入JMS消息发送模版
	@Autowired
	private JmsTemplate jmsTemplate;
	
	//注入JedisDao对象
	@Autowired
	private JedisDao jedisDao;
	
	@Value("${ITEM_DETAIL_CHACHE}")
	private String ITEM_DETAIL_CHACHE;
	
	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;
	
	//注入JMS消息发送目的地
	@Autowired
	private ActiveMQTopic activeMQTopic;
	/**
	 * 需求：根据Id查询商品
	 * 参数： 商品Id,类型long
	 * 返回值：商品对象
	 * 查询商品信息，添加缓存：
	 * 添加缓存目的
	 * 1.提高查询效率
	 * 2.减轻数据库服务器压力
	 * 业务流程：
	 * 查询商品详情信息，先查询缓存
	 * 如果说缓存中没有数据，再查询数据库
	 * 查询数据库完毕，需要把数据放入缓存中。
	 * 
	 * 商品详情信息：设置过期时间
	 * 商品详情过期时间:一天（86400秒）
	 * redis:只有Strings类型能设置过期时间
	 * 
	 * 业务设计：redis存储结构是key：value
	 *    商品信息：key = ITEM_DETAIL:BASE:itemId
	 *    		 value =  商品json格式数据
	 *    商品描述信息：key=  ITEM_DETAIL:DESC:itemId
	 *    		   value= 商品描述json格式字符串
	 */
	public TbItem findItemById(Long id) {
		String itemJson = jedisDao.get(ITEM_DETAIL_CHACHE+":BASE:"+id);
		//判断缓存中是否有数据
				try {
					if(StringUtils.isNoneBlank(itemJson)){
						
						//把json格式数据转换商品对象
						TbItem tbItem = JsonUtils.jsonToPojo(itemJson, TbItem.class);
						
						return tbItem;
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		//创建example对象
		TbItemExample example = new TbItemExample();
		//创建criteria对象
		Criteria createCriteria = example.createCriteria();
		//设置查询参数
		createCriteria.andIdEqualTo(id);
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		TbItem item = null;
		//根据Id查询，返回单个对象
		if(list!=null && list.size()>0) {
			item = list.get(0);
		}
		//添加缓存
		jedisDao.set(ITEM_DETAIL_CHACHE+":BASE:"+id, JsonUtils.objectToJson(item));
		//设置缓存过期时间
		jedisDao.expire(ITEM_DETAIL_CHACHE+":BASE:"+id, EXPIRE_TIME);
				
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
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte)1);
		
		Date date = new Date();
		item.setUpdated(date);
		item.setCreated(date);
		
		//保存
		itemMapper.insert(item);
		
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		
		itemDescMapper.insert(itemDesc);
		
		
		
		//使用JMS消息模版发送消息,activeMQTopic是消息发送的地址
		jmsTemplate.send(activeMQTopic,new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(itemId+"");
			}
			
		});
		return TaotaoResult.ok();
	}

	/**
	 * 需求：根据商品Id查询商品描述信息
	 * * 查询商品描述信息，添加缓存：
	 * 添加缓存目的
	 * 1.提高查询效率
	 * 2.减轻数据库服务器压力
	 * 业务流程：
	 * 查询商品详情信息，先查询缓存
	 * 如果说缓存中没有数据，再查询数据库
	 * 查询数据库完毕，需要把数据放入缓存中。
	 */
	public TbItemDesc findItemDescByID(Long itemId) {
		String itemJson = jedisDao.get(ITEM_DETAIL_CHACHE+":DESC:"+itemId);
		//判断缓存中是否有数据
		try {
			if(StringUtils.isNoneBlank(itemJson)){
				
				//把json格式数据转换商品对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(itemJson, TbItemDesc.class);
				
				return itemDesc;
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		
		//添加缓存
		jedisDao.set(ITEM_DETAIL_CHACHE+":DESC:"+itemId, JsonUtils.objectToJson(itemDesc));
		//设置缓存过期时间
		jedisDao.expire(ITEM_DETAIL_CHACHE+":DESC:"+itemId, EXPIRE_TIME);
		
		return itemDesc;
	}

}
