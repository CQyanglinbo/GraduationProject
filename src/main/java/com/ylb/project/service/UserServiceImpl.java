package com.ylb.project.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ylb.project.dao.AddressRepository;
import com.ylb.project.dao.BankcardRepository;
import com.ylb.project.dao.OrdersRepository;
import com.ylb.project.dao.RecordRepository;
import com.ylb.project.dao.RoleRepository;
import com.ylb.project.dao.UserRepository;
import com.ylb.project.model.Address;
import com.ylb.project.model.Bankcard;
import com.ylb.project.model.Orders;
import com.ylb.project.model.Record;
import com.ylb.project.model.Role;
import com.ylb.project.model.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private BankcardRepository bankcardRepository;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private RecordRepository recordRepository;
	/**
	 * 根据用户名找到用户
	 */
	@Transactional
	@Override
	public User findUserByUserName(String userName) {
		
		return userRepository.findByUserName(userName);
	}
	/**
	 * 注册用户
	 */
	@Transactional
	@Override
	public void save(User user) {
		Set<Role> roles=new HashSet<>();
		Role role=roleRepository.findByRoleName("ROLE_USER");
		roles.add(role);
		user.setRole(role);//给注册用户赋予角色
		user.setPassword(passwordEncoder.encode(user.getPassword()));//对密码进行加密
	    user.setCreateDate(new Date());
		userRepository.save(user);	
	}
	/**
	 * 找出该用户的所有地址
	 */
	@Transactional
	@Override
	public List<Address> findByUser(User user) {
		return addressRepository.findByuser(user);
	}
	/**
	 * 新增地址
	 */
	@Transactional
	@Override
	public void save(Address address) {
		addressRepository.save(address);	
	}
	/**
	 * 根据id找到地址
	 */
	@Transactional
	@Override
	public Address findAddressById(int id) {
		return addressRepository.findAddressById(id);
	}
	/**
	 * 检查登录密码是否正确
	 */
	@Transactional
	@Override
	public String checkpwd(String pwd) {
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		if(!passwordEncoder.matches(pwd.trim(), user.getPassword())){
			return "密码错误";
		}
		return "密码正确";
	}
	/**
	 * 检查支付密码是否正确
	 */
	@Transactional
	@Override
	public String checkppwd(String ppwd) {
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		if(!user.getPayPassword().equals(ppwd)){
			return "支付密码错误";
		}
		return "支付密码正确";
	}
	/**
	 * 修改密码,修改成功返回登录页面，失败则还是在修改密码页面
	 */
	@Transactional
	@Override
	public String updatePassword(String oldPwd, String newPwd) {
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		String result=null;

		if(passwordEncoder.matches(oldPwd.trim(), user.getPassword())){
			user.setPassword(passwordEncoder.encode(newPwd));
			userRepository.save(user);
			result="login";
		}else{
			System.out.println("密码错误");
			result="password";
		}
		return result;
	}
	/**
	 * 修改支付密码
	 */
	@Transactional
	@Override
	public String updatePayPassword(String oldPwd, String newPwd) {
		SecurityContext ctx = SecurityContextHolder.getContext();  
		Authentication auth = ctx.getAuthentication();  
		User user= (User) auth.getPrincipal();
		String result=null;
		if(oldPwd.equals(user.getPayPassword())){
			user.setPayPassword(newPwd);
			userRepository.save(user);
			result="personalIndex";
		}else{
			System.out.println("原支付密码输入错误");
			result="payPassword";
		}
		return result;
	}

	/**
	 *修改个人信息
	 */
	@Transactional
	@Override
	public void updateUser(User user) {
		userRepository.save(user);	
	}
	/**
	 * 添加银行卡
	 */
	@Transactional
	@Override
	public Bankcard save(Bankcard bankcard) {
		return bankcardRepository.save(bankcard);
	}
	/**
	 * 解绑银行卡
	 */
	@Transactional
	@Override
	public void deleteCardById(int id) {
		bankcardRepository.deleteById(id);	
	}
	/**
	 * 根据用户找出他所绑定的银行卡
	 */
	@Transactional
	@Override
	public List<Bankcard> findBankcardListByUser(User user) {
		return bankcardRepository.findByUser(user);
	}
	/**
	 * 生成订单
	 */
	@Transactional
	@Override
	public Orders save(Orders order) {
		return ordersRepository.save(order);
	}
	/**
	 * 删除订单
	 */
	@Transactional
	@Override
	public void deleteOrderById(Orders order) {
		ordersRepository.delete(order);	
	}
	/**
	 * 根据用户信息找到相应的订单列表
	 */
	@Transactional
	@Override
	public List<Orders> findListByUser(User user) {
		return ordersRepository.findListByUser(user);
	}
	/**
	 * 返回订单状态为“待付款”的订单
	 */
	@Transactional
	@Override
	public List<Orders> findListOrderStatus(User user) {
		List<Orders> orders=ordersRepository.findListByUser(user);
		List<Orders> statusOrders=new ArrayList<>();
		for (Orders orders2 : orders) {
			if(orders2.getOrderStatus().equals("待付款")){
				statusOrders.add(orders2);
			}
		}
		return statusOrders;
	}
	/**
	 * 根据卡号找到银行卡
	 */
	@Transactional
	@Override
	public Bankcard findByCardNumber(String cardNumber) {
		return bankcardRepository.findByCardNumber(cardNumber);
	}
	/**
	 * 新增账单记录
	 */
	@Transactional
	@Override
	public Record save(Record record) {
		return recordRepository.save(record);
	}
	/**
	 * 找到对应的账单，并分页
	 */
	@Transactional
	@Override
	public Page<Record> findListByUser(User user, Pageable pageable) {
		return recordRepository.findListByUser(user, pageable);
	}
	/**
	 * 根据id删除地址
	 */
	@Transactional
	@Override
	public void deleteById(int id) {
		addressRepository.deleteById(id);
		
	}
	/**
	 * 根据id找到order
	 */
	@Transactional
	@Override
	public Orders findOrderById(int id) {
		return ordersRepository.findById(id);	
	}

}
