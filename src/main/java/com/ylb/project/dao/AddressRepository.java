package com.ylb.project.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ylb.project.model.Address;
import com.ylb.project.model.User;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer>{
	public List<Address> findByuser(User user);
	public void deleteById(int id);
	public Address findAddressById(int id);
	@SuppressWarnings("unchecked")
	public Address save(Address address);
}
