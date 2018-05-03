package com.ylb.project.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ylb.project.model.Record;
import com.ylb.project.model.User;
import com.ylb.project.service.UserServiceImpl;



@Controller
@RequestMapping("/")
public class lcontroller {
	@Autowired
	private UserServiceImpl userService;
	/**
	 * Sign in page.
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register() {
		return "register";
	}
	/**
	 * 去购物车页面
	 * @return
	 */
	@RequestMapping(value="/shopcart",method=RequestMethod.GET)
	public String shopcart(Model model) {
		//得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		model.addAttribute("orderList", userService.findListByUser(user));
		return "shopcart";
	}
	@RequestMapping(value="/information",method=RequestMethod.GET)
	public String information() {
		return "information";
	}
	/**
	 * 去到我的账单页面，并显示账单以及分页
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/walletList")
	public String walletList(HttpServletRequest request,Model model) {
		//得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		int pageSize=5;
		if(pageNo!=0){
			pageNo=pageNo-1;
		}
		Page<Record> records;
		records=userService.findListByUser(user, new PageRequest(pageNo, pageSize));
		
		model.addAttribute("records", records);
		model.addAttribute("totalPage", records.getTotalPages());
		model.addAttribute("balance", user.getBalance());
		model.addAttribute("pageNo", pageNo);
		
		return "walletList";
	}
	@RequestMapping(value="/address",method=RequestMethod.GET)
	public String address() {
		return "address";
	}
	@RequestMapping(value="/cardlist",method=RequestMethod.GET)
	public String cardlist() {
		return "cardlist";
	}
	@RequestMapping(value="/order",method=RequestMethod.GET)
	public String order() {
		return "order";
	}
	@RequestMapping(value="/password",method=RequestMethod.GET)
	public String password() {
		return "password";
	}
	@RequestMapping(value="/payPassword",method=RequestMethod.GET)
	public String payPassword() {
		return "payPassword";
	}
	@RequestMapping(value="/personalIndex",method=RequestMethod.GET)
	public String personalIndex() {
		return "personalIndex";
	}
	@RequestMapping(value="/to_addcard",method=RequestMethod.GET)
	public String addcard() {
		return "addcard";
	}
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String introduction() {
		return "search";
	}
	@RequestMapping(value="/pay",method=RequestMethod.GET)
	public String pay() {
		return "pay";
	}
	@RequestMapping(value="/paySuccess",method=RequestMethod.GET)
	public String paySuccess() {
		return "paySuccess";
	}
}
