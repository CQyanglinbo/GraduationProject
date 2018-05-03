package com.ylb.project.dao;



import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ylb.project.model.Orders;
import com.ylb.project.model.User;


/**
 *@author 作者:杨林波   email:CQyanglinbo@gmail.com
 *@version  创建时间:2018年4月24日下午8:16:10
 *
**/
@Repository
public interface OrdersRepository extends CrudRepository<Orders, Integer>{
	@SuppressWarnings("unchecked")
	/* 新增订单 */
	public Orders save(Orders order);
	/* 根据用户寻找订单列表  */
	public List<Orders> findListByUser(User user);
	/* 根据order 删除订单*/
	public void delete(Orders order);
	/* 根据id 找出订单*/
	public Orders findById(int id);
}
