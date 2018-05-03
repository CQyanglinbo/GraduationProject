package com.ylb.project.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ylb.project.model.BigType;
import com.ylb.project.model.SmallType;
@Repository
public interface SmallTypeRepository extends CrudRepository<SmallType, Integer>{
	//根据大商品类找到小商品类
	public List<SmallType> findByBigType(BigType bigType);
	//根据类型名找到类型实体
	public SmallType findByTypeName(String typeName);
}
