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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ylb.project.model.Commodity;
import com.ylb.project.model.Orders;
import com.ylb.project.model.Record;
import com.ylb.project.model.User;
import com.ylb.project.service.HomeServiceImpl;
import com.ylb.project.service.UserServiceImpl;

/**
 * @author 作者:杨林波 email:CQyanglinbo@gmail.com
 * @version 创建时间:2018年4月27日上午10:22:06
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
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/buy", "/user/buy" })
	public String buyRightNow(HttpServletRequest request, Model model) {
		// 得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		User user = (User) auth.getPrincipal();
		// 从前台传来商品id
		int id = Integer.parseInt(request.getParameter("param"));
		Commodity commodity = homeService.findByproductId(id);
		// 要购买的商品的数量
		int count = Integer.parseInt(request.getParameter("count"));
		// 添加地址列表到结账页面
		model.addAttribute("addressList", userService.findByUser(user));
		// 添加订单
		Orders order = new Orders();
		order.setCommodity(commodity);
		order.setMailingFee("包邮");
		order.setCreateDate(new Date());
		order.setUser(user);
		order.setTotal_fee(commodity.getPrice() * count);
		order.setCount(count);
		order.setOrderStatus("待付款");
		userService.save(order);

		model.addAttribute("order", order);

		return "payRightNow";
	}

	/**
	 * 加入购物车
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/addShopcart", "/user/addShopcart" })
	public String addShopcart(HttpServletRequest request, Model model) {
		// 得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		User user = (User) auth.getPrincipal();
		// 从前台传来商品id
		int id = Integer.parseInt(request.getParameter("param"));
		Commodity commodity = homeService.findByproductId(id);
		// 要购买的商品的数量
		int count = Integer.parseInt(request.getParameter("count"));
		// 添加订单
		Orders order = new Orders();
		order.setCommodity(commodity);
		order.setMailingFee("包邮");
		order.setCreateDate(new Date());
		order.setUser(user);
		order.setTotal_fee(commodity.getPrice() * count);
		order.setCount(count);
		order.setOrderStatus("待付款");
		userService.save(order);
		// 找到对应用户的订单,并传给前台
		model.addAttribute("orderList", userService.findListOrderStatus(user));

		return "shopcart";
	}

	/**
	 * 去订单管理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/to_order", "/user/to_order" })
	public String to_order(Model model) {
		// 得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		User user = (User) auth.getPrincipal();
		// 所有的订单
		List<Orders> orders = userService.findListByUser(user);
		model.addAttribute("allOrders", orders);
		// 待付款
		List<Orders> noPay = new ArrayList<>();
		for (Orders orders2 : orders) {
			if (orders2.getOrderStatus().equals("待付款")) {
				noPay.add(orders2);
			}
		}
		model.addAttribute("noPay", noPay);
		// 待发货
		List<Orders> pendingDelivery = new ArrayList<>();
		for (Orders orders3 : orders) {
			if (orders3.getOrderStatus().equals("待发货")) {
				pendingDelivery.add(orders3);
			}
		}
		model.addAttribute("pendingDelivery", pendingDelivery);
		// 待收货
		List<Orders> noReceive = new ArrayList<>();
		for (Orders orders4 : orders) {
			if (orders4.getOrderStatus().equals("待收货")) {
				noReceive.add(orders4);
			}
		}
		model.addAttribute("noReceive", noReceive);
		// 已收货
		List<Orders> received = new ArrayList<>();
		for (Orders orders5 : orders) {
			if (orders5.getOrderStatus().equals("已收货")) {
				noReceive.add(orders5);
			}
		}
		model.addAttribute("received", received);

		return "order";
	}

	/**
	 * 根据id删除订单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/delete_order", "/user/delete_order" })
	public String deleteOrder(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		Orders order = userService.findOrderById(id);
		order.setCommodity(null);
		order.setAddress(null);
		userService.save(order);
		userService.deleteOrderById(order);
		return "删除成功";
	}

	/**
	 * 去支付页面
	 * 
	 * @param idArray
	 * @return
	 */
	@RequestMapping(value = { "/to_pay", "/user/to_pay" })
	public String to_pay(String[] idArray, Model model) {
		// 得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		User user = (User) auth.getPrincipal();

		model.addAttribute("addressList", userService.findByUser(user));

		List<Orders> orders = new ArrayList<>();
		for (int i = 0; i < idArray.length; i++) {
			Orders order = userService.findOrderById(Integer.parseInt(idArray[i]));
			orders.add(order);
		}
		model.addAttribute("orders", orders);
		return "pay";
	}

	/**
	 * 结算
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/account", "/user/account" }, method = RequestMethod.POST)
	public String account(HttpServletRequest request, String[] orderId) {
		// 得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		User user = (User) auth.getPrincipal();
		String ppwd = request.getParameter("ppwd");
		String addressId = request.getParameter("addressId");// 地址id
		String logistics = request.getParameter("logistics");// 物流
		String message = request.getParameter("message");// 用户留言
		double totalMoney = 0;// 订单总金额
		for (String string : orderId) {
			Orders order = userService.findOrderById(Integer.parseInt(string));
			totalMoney += order.getTotal_fee();
		}
		// 支付密码正确
		if (userService.checkppwd(ppwd) == null) {
			// 减去账户余额
			if (user.getBalance() > totalMoney) {
				user.setBalance(user.getBalance() - totalMoney);
				userService.save(user);
				// 设置订单
				for (String string : orderId) {
					Orders order = userService.findOrderById(Integer.parseInt(string));
					order.setAddress(userService.findAddressById(Integer.parseInt(addressId)));
					order.setLogistics(logistics);
					order.setMessage(message);
					order.setOrderStatus("待发货");
					userService.save(order);
					// 减去商品的数量
					Commodity commodity = order.getCommodity();
					commodity.setNumber(commodity.getNumber() - order.getCount());
					homeService.save(commodity);
				}
				// 增加消费记录
				Record record = new Record();
				record.setUser(user);
				record.setCreatetime(new Date());
				record.setTotalFee(totalMoney);
				record.setType("消费");
				record.setBanlance(user.getBalance());
				userService.save(record);
			} else {
				return "余额不足，请充值";
			}
		}
		return "结算成功";
	}
}
