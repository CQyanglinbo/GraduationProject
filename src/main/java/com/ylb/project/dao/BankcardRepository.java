package com.ylb.project.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ylb.project.model.Bankcard;
import com.ylb.project.model.User;
@Repository
public interface BankcardRepository extends CrudRepository<Bankcard, Integer>{
	public Bankcard save(Bankcard bankcard);
	public List<Bankcard> findByUser(User user);
	public Bankcard findByCardNumber(String cardNumber);
}
