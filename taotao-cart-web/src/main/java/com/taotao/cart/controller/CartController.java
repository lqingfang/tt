package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.utils.CookieUtils;
import com.taotao.domain.TbItem;
import com.taotao.domain.TbUser;
import com.taotao.manager.service.ItemService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Controller
public class CartController {

	@Value("${CART_KEY}")
	private String CART_KEY;
	
	// 注入商品服务service
	@Autowired
	private ItemService itemService;
	/**
	 * 需求：添加购物车(未登录) 未登录：cookie是购物车
	 * 请求：/add/cart/${item.id}/"+$("#buy-num").val()+".html 参数：ＵＲＬ模版映射
	 * 返回值：String 返回到购物车添加成功页面 业务流程： 1、先查询购物车(cookie)商品列表 >
	 * 判断购物车当中是否有此时购买的商品，如果有，商品数量相加 > 否则没有，直接添加cookie中
	 */
	@RequestMapping("/add/cart/{itemId}/{num}")
	public String addCart(@PathVariable Long itemId,@PathVariable Long num,
			HttpServletRequest request, HttpServletResponse response){
		// 查询购物车(cookie)
		List<TbItem> itemList = this.getCookieCartList(request);
		// 判断购物车中是否已有该商品
		boolean flag = false;
		if(itemList != null) {
			for (TbItem tbItem : itemList) {
				if (tbItem.getId() == itemId.longValue()) {
					// 如果有id相等，说明存在，直接添加数量即可，此时循环停止，标识变为true
					tbItem.setNum(tbItem.getNum() + num.intValue());
					flag = true;
					break;
				}
			}
		}
		
		// 循环完了还为false，说明不存在该商品，那么就要将该商品信息添加到购物车中
		if (!flag) {
			// 查询要添加的商品详情
			TbItem item = itemService.findItemById(itemId);
			// 设置商品数量
			item.setNum(num.intValue());
			
			// 把商品添加到购物车列表中
			itemList.add(item);
		}
		// 把购物车列表信息回写到cookie（购物车）
		CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(itemList), true);
		// 返回添加购物车成功页面
		return "cartSuccess";
	}
	
	/**
	 * 需求：查询购物列表信息，在购物车列表中回显 请求：/cart/cart.html 参数：model
	 * 返回值：返回到order-cart购物车列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String cartList(HttpServletRequest request, Model model) {
		// 从cookie中获取购物车列表
		List<TbItem> cartList = this.getCookieCartList(request);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	/**
	 * 需求：购物车列表增加，减少商品购买数量，价格随之改变 
	 * 请求：/cart/update/num/{itemId}/{num}.html"
	 * 参数：URL模版映射，request,response 
	 * 返回值：Json格式TaoTaoResult.ok()
	 * 
	 * @param request
	 * @return 业务流程分析：
	 *  1、从cookie中获取购物车列表 
	 *  2、根据cookie中商品，判断需要修改那个商品
	 *  3、把修改后商品列表写回到购物车cookie
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	public @ResponseBody TaotaoResult updatItemNum(@PathVariable Long itemId,
			@PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 1、从cookie中获取购物车列表
		List<TbItem> cartList = this.getCookieCartList(request);
		//2、根据cookie中商品，判断需要修改那个商品
		//循环商品列表，判断需要修改那个商品
		for (TbItem tbItem : cartList) {
			//如果商品Id相等，此商品需要修改
			if(tbItem.getId()==itemId.longValue()) {
				tbItem.setNum(num);
				break;
			}
		}
		//3、把修改后商品列表写回到购物车cookie
		CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList),true);
		
		return TaotaoResult.ok();
	}
	
	/**
	 * 需求：根据商品Id，删除购物车当中商品
	 * 请求：/cart/delete/{cart.id}.html
	 * 参数：URL模版映射
	 * 返回值：重定向到购物车列表页面
	 * @param request
	 * @return
	 * 业务：
	 * 1、从cookie中获取购物车列表
	 * 2、判断需要删除购物车中那个商品
	 * 3、删除完毕，需要把商品列表从新放入cookie(购物车)
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartList(@PathVariable Long itemId,
			HttpServletRequest request,
			HttpServletResponse response) {
		// 1、从cookie中获取购物车列表
		List<TbItem> cartList = this.getCookieCartList(request);
		//2、根据cookie中商品，判断需要修改那个商品
		//循环商品列表，判断需要修改那个商品
		for (TbItem tbItem : cartList) {
			//如果商品Id相等，此商品需要修改
			if(tbItem.getId()==itemId.longValue()) {
				cartList.remove(tbItem);
				break;
			}
		}
		//3、把修改后商品列表写回到购物车cookie
		CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList),true);
		
		return "redirect:/cart/cart.html";
	}
	
	/**
	 * 需求：去结算：跳转到结算详情页面
	 * 请求：/order/order-cart.html
	 * 返回值：返回到订单详情页面（String）
	 * @param request
	 * @return
	 */
	@RequestMapping("/order/order-cart")
	public String orderCart(Model model,HttpServletRequest request){
		//从cookie中获取购物车列表
		List<TbItem> cartList = this.getCookieCartList(request);
		//从request中获取用户身份信息（收货人信息）
		TbUser user = (TbUser) request.getAttribute("user");
		//页面回显
		model.addAttribute("cartList", cartList);
		model.addAttribute("user", user);
		
		return  "order-cart";
		
	}
	
	private List<TbItem> getCookieCartList(HttpServletRequest request) {
		// 使用cookieUtils工具类获取商品列表
		String cartJson = CookieUtils.getCookieValue(request, CART_KEY,true);
		// 判断购物车是否为空
		if (StringUtils.isBlank(cartJson)) {
			//返回空购物车列表
			return new ArrayList<TbItem>();
		}
		// 否则购物车不为空
		// 把购物车json格式信息，转换成list集合
		List<TbItem> list = JsonUtils.jsonToList(cartJson, TbItem.class);
		
		return list;
	}
}
