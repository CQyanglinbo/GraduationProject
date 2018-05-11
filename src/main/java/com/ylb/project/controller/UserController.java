package com.ylb.project.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.ylb.project.model.Address;
import com.ylb.project.model.Bankcard;
import com.ylb.project.model.Commodity;
import com.ylb.project.model.Orders;
import com.ylb.project.model.Record;
import com.ylb.project.model.User;
import com.ylb.project.security.MyUserDetailsService;
import com.ylb.project.service.HomeServiceImpl;
import com.ylb.project.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private HomeServiceImpl homeService;
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String do_register(User user,String error,Model model){
		if(userService.findUserByUserName(user.getUserName())!=null){
			model.addAttribute("error", "该用户已存在");
			return "register";
		}else{
			userService.save(user);
		}
		return "login";
	}
	/**
	 * 跳转到地址页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/address","user/address"})
	public ModelAndView to_address(ModelAndView model){
		//得到当前登录用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user1 = (User) auth.getPrincipal();
		//添加地址栏列表到前台
		model.addObject("addressList", userService.findByUser(user1));
		model.setViewName("address");
		return model;
	}
	/**
	 * 增加地址
	 * @param model
	 * @param address
	 * @return
	 */
	@RequestMapping(value={"/add_address","/user/add_address"},method=RequestMethod.POST)
	public String add_address(ModelAndView model,HttpServletRequest request){
		//得到当前登录用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		Address address =new Address();
		//保存新增的地址
		String receiverName=request.getParameter("receiverName");
		String telephone=request.getParameter("telephone");
		String addressDetail=request.getParameter("address");
		address.setReceiverName(receiverName);
		address.setTelephone(telephone);
		address.setAddress(addressDetail);
		address.setUser(user);
		userService.save(address);
		return "redirect:/user/address";
	}
	@ResponseBody
	@RequestMapping(value={"/delete_address","/user/delete_address"})
	public String delete_address(HttpServletRequest request){
		int id=Integer.parseInt(request.getParameter("id"));
		userService.deleteById(id);
		return "删除成功";
	}
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/modifiedPassword","/user/modifiedPassword"})
	public String modifedPassword(HttpServletRequest request){
		//得到参数
		String oldPwd=request.getParameter("oldPwd");
		String newPwd=request.getParameter("newPwd");
		
		return userService.updatePassword(oldPwd, newPwd);
	}
	/**
	 * 检查密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/wrongpwd","/user/wrongpwd"})
	public String wrongPassword(HttpServletRequest request){
		String pwd=request.getParameter("pwd");
		return userService.checkpwd(pwd);
	}
	/**
	 * 支付密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/wrongppwd","/user/wrongppwd"})
	public String wrongPayPassword(HttpServletRequest request){
		String ppwd=request.getParameter("ppwd");
		return userService.checkppwd(ppwd);
	}
	/**
	 * 修改支付密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/modifiedpayPassword","/user/modifiedpayPassword"})
	public String modifedpayPassword(HttpServletRequest request){
		//得到参数
		String oldPwd=request.getParameter("oldPwd");
		String newPwd=request.getParameter("newPwd");
		
		return userService.updatePayPassword(oldPwd, newPwd);
	}
	/**
	 * 上传头像
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/fileUpload","/user/fileUpload"})
	public String fileUpload(MultipartFile file){
		//得到当前登录用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		
		//判断文件是否为空
		if(file.isEmpty()){
			return "false";
		}
		//得到文件的名字
		String fileName=file.getOriginalFilename();
		String path=System.getProperty("user.dir")+"\\src\\main\\resources\\static"+"/upload";
		File dest=new File(path+"/"+fileName);
		if(!dest.getParentFile().exists()){//判断文件父目录是否存在
			dest.getParentFile().mkdir();
		}
		user.setImageUrl("http://localhost:8081/upload/"+fileName);
		userService.save(user);
		//保存文件
		try {
			file.transferTo(dest);
			return "true";
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
	}
	/**
	 * 修改用户信息
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={"/modifiedUser","/user/modifiedUser"},method=RequestMethod.POST)
	public String modifiedUser(HttpServletRequest request) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String nickname=request.getParameter("nickname");
		String realName=request.getParameter("realName");
		String gender=request.getParameter("gender");
		Date birthday=sf.parse(request.getParameter("birthday"));
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		String payPassword=request.getParameter("payPassword");
		
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		user.setNickname(nickname);
		user.setGender(gender);
		user.setRealName(realName);
		user.setBirthday(birthday);
		user.setEditDate(new Date());
		user.setPhone(phone);
		user.setEmail(email);
		user.setPayPassword(payPassword);
		userService.updateUser(user);
		
		return "personalIndex";
	}
	/**
	 * 去到银行卡列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/cardlist","/user/cardlist"})
	public ModelAndView cardList(ModelAndView model){
		//得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		List<Bankcard> cardlist=userService.findBankcardListByUser(user);
		model.addObject("cardlist", cardlist);
		model.setViewName("cardlist");
		return model;
	}
	/**
	 * 新增银行卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/add_bankcard","/user/add_bankcard"})
	public String addcard(HttpServletRequest request){
		//得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		//得到参数
		String realName=request.getParameter("realName");
		String IDnumber=request.getParameter("IDnumber");
		String cardNumber=request.getParameter("cardNumber");
		String telephone=request.getParameter("telephone");
		String bankName=request.getParameter("bankName");
		//设置参数
		Bankcard bankcard=new Bankcard();
		bankcard.setBankName(bankName);
		bankcard.setCardNumber(cardNumber);
		bankcard.setIDnumber(IDnumber);
		bankcard.setRealName(realName);
		bankcard.setTelephone(telephone);
		bankcard.setBanlance(2000);
		bankcard.setUser(user);
		userService.save(bankcard);
		return "redirect:/user/cardlist";
	}
	/**
	 * 去充值页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/to_recharge","/user/to_recharge"})
	public ModelAndView to_recharge(ModelAndView model){
		//得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		List<Bankcard> cardlist=userService.findBankcardListByUser(user);
		model.addObject("cardlist",cardlist);
		model.setViewName("recharge");
		return model;
	}
	/**
	 * 充值
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/recharge","/user/recharge"})
	public String recharge(Model model,HttpServletRequest request){
		//得到当前登录的用户
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		
		double money=Integer.parseInt(request.getParameter("money"));
		String payPassword=request.getParameter("pwd");
		String cardNo=request.getParameter("bankCard");
		Bankcard bankcard=userService.findByCardNumber(cardNo);
		if(money>bankcard.getBanlance()){
			System.out.println("银行卡余额不足");
			return "redirect:/user/to_recharge";
		}else if(!payPassword.equals(user.getPayPassword())){
			System.out.println("支付密码输入错误");
			return "redirect:/user/to_recharge";
		}else{
			bankcard.setBanlance(bankcard.getBanlance()-money);
			userService.save(bankcard);
			user.setBalance(user.getBalance()+money);
			userService.save(user);
			Record record=new Record();
			record.setCreatetime(new Date());
			record.setTotalFee(money);
			record.setType("充值");
			record.setBanlance(user.getBalance());
			record.setUser(user);
			userService.save(record);
			return "redirect:/walletList?pageNo=0";
		}
	}
}
