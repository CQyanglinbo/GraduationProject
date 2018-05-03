package com.ylb.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ylb.project.model.Address;
import com.ylb.project.model.Bankcard;
import com.ylb.project.model.Orders;
import com.ylb.project.model.Record;
import com.ylb.project.model.User;

public interface UserService {
	//根据用户名找到用户
	public User findUserByUserName(String userName);
	//新增或修改用户
	public void save(User user);
	//根据用户信息找到所对应的地址列表
	public List<Address> findByUser(User user);
	//新增或修改地址
	public void save(Address address);
	//检查密码
	public String checkpwd(String pwd);
	//修改密码
	public String updatePassword(String oldPwd,String newPwd);
	//修改支付密码
	public String updatePayPassword(String oldPwd,String newPwd);
	//修改用户信息
	public void updateUser(User user);
	//新增银行卡信息
	public Bankcard save(Bankcard bankcard);
	//根据用户信息找出对应的银行卡列表
	public List<Bankcard> findBankcardListByUser(User user);
	//新增或修改订单
	public Orders save(Orders order);
	//根据order删除订单
	public void deleteOrderById(Orders order);
	//根据用户找出对应的订单列表
	public List<Orders> findListByUser(User user);
	//根据卡号找到银行卡
	public Bankcard findByCardNumber(String cardNumber);
	//新增记录
	public Record save(Record record);
	//找到对应的账单记录，并分页
	public Page<Record> findListByUser(User user,Pageable pageable);
	//通过id删除地址
	public void deleteById(int id);
	//根据id找出order
	public Orders findOrderById(int id);
}
