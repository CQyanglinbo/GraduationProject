package com.ylb.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ylb.project.model.User;
@Repository
public interface UserRepository extends CrudRepository<User,Integer>{
	public User findByUserNameAndPassword(String userName,String password);
	public User findByUserName(String userName);
	public User save(User user);
}
