package com.ylb.project.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ylb.project.model.Commodity;
import com.ylb.project.model.Orders;
import com.ylb.project.model.User;
import com.ylb.project.service.HomeServiceImpl;
import com.ylb.project.service.UserServiceImpl;

/**
 *@author 作者:杨林波   email:CQyanglinbo@gmail.com
 *@version  创建时间:2018年4月27日上午10:22:06
 *
**/
@Controller
@RequestMapping("/user")
public class ShopController {
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private HomeServiceImpl homeService;
	/**
	 * 立即购买
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/buy","/user/buy"})
	public String buyRightNow(HttpServletRequest request,Model model){
		//得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		//从前台传来商品id
		int id=Integer.parseInt(request.getParameter("param"));
		Commodity commodity=homeService.findByproductId(id);
		//要购买的商品的数量
		int count=1;
		//添加地址列表到结账页面
		model.addAttribute("addressList", userService.findByUser(user));
		//添加订单
		Orders order=new Orders();
		order.setCommodity(commodity);
		order.setMailingFee("包邮");
		order.setCreateDate(new Date());
		order.setUser(user);
		order.setTotal_fee(commodity.getPrice()*count);
		order.setOrderStatus("待付款");
		userService.save(order);
		model.addAttribute("order", order);
		
		return "payRightNow";
	}
	/**
	 * 加入购物车
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/addShopcart","/user/addShopcart"})
	public String addShopcart(HttpServletRequest request,Model model){
		//得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		//从前台传来商品id
		int id=Integer.parseInt(request.getParameter("param"));
		Commodity commodity=homeService.findByproductId(id);
		//要购买的商品的数量
		int count=1;
		//添加订单
		Orders order=new Orders();
		order.setCommodity(commodity);
		order.setMailingFee("包邮");
		order.setCreateDate(new Date());
		order.setUser(user);
		order.setTotal_fee(commodity.getPrice()*count);
		order.setCount(count);
		order.setOrderStatus("待付款");
		userService.save(order);
		//找到对应用户的订单,并传给前台
		model.addAttribute("orderList", userService.findListByUser(user));
		
		return "shopcart";
	}
	/**
	 * 去订单管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/to_order","user/to_order"})
	public String to_order(Model model){
		//得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		 //所有的订单
		List<Orders> orders=userService.findListByUser(user);
		model.addAttribute("allOrders", orders);
		//待付款
		List<Orders> noPay = new ArrayList<>();
		for (Orders orders2 : orders) {
			if(orders2.getOrderStatus().equals("待付款")){
				noPay.add(orders2);
			}
		}
		model.addAttribute("noPay", noPay);
		//待发货
		List<Orders> pendingDelivery = new ArrayList<>();
		for (Orders orders3 : orders) {
			if(orders3.getOrderStatus().equals("代发货")){
				pendingDelivery.add(orders3);
			}
		}
		model.addAttribute("pendingDelivery", pendingDelivery);
		//待收货
		List<Orders> noReceive = new ArrayList<>();
		for (Orders orders4 : orders) {
			if(orders4.getOrderStatus().equals("待收货")){
				noReceive.add(orders4);
			}
		}
		model.addAttribute("noReceive", noReceive);
		//已收货
		List<Orders> received = new ArrayList<>();
		for (Orders orders5 : orders) {
			if(orders5.getOrderStatus().equals("已收货")){
				noReceive.add(orders5);
			}
		}
		model.addAttribute("received", received);
		
		return "order";
	}
	@ResponseBody
	@RequestMapping(value={"/delete_order","/user/delete_order"})
	public String deleteOrder(HttpServletRequest request){
		int id=Integer.parseInt(request.getParameter("id"));
		Orders order=userService.findOrderById(id);
		order.setCommodity(null);
		order.setAddress(null);
		userService.save(order);
		userService.deleteOrderById(order);
		return "删除成功";
	}
	@ResponseBody
	@RequestMapping(value={"/accounts","/user/accounts"})
	public String accounts(String[] idArrays){
		if(idArrays!=null){
			System.out.println("hello");
		}
		return "";
	}
}
