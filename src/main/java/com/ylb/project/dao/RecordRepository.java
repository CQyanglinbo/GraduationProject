package com.ylb.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ylb.project.model.Address;
import com.ylb.project.model.Record;
import com.ylb.project.model.User;

/**
 *@author 作者:杨林波   email:CQyanglinbo@gmail.com
 *@version  创建时间:2018年4月30日下午4:30:12
 *
**/
@Repository
public interface RecordRepository extends CrudRepository<Record, Integer>{
	public Record findByUser(User user);
	public Record save(Record record);
	public Page<Record> findListByUser(User user,Pageable pageable);
}
