package com.taotao.pageHelperTest;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.domain.TbItem;
import com.taotao.domain.TbItemExample;
import com.taotao.mapper.TbItemMapper;

public class MyPageHelper {

	@Test
	public void testPageHelper() {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:applicationContext-*.xml");
		TbItemMapper tbItemMapper = app.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(1, 10);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		System.out.println("总页数："+pageInfo.getPages());
		System.out.println("总页数："+pageInfo.getTotal());
	}
}
