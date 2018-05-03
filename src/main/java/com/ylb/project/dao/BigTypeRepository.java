package com.ylb.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ylb.project.model.BigType;
@Repository
public interface BigTypeRepository extends CrudRepository<BigType, Integer>{
	public BigType findByTypeName(String typeName);
}
