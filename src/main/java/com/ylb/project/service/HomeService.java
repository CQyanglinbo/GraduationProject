package com.ylb.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ylb.project.model.BigType;
import com.ylb.project.model.Commodity;
import com.ylb.project.model.SmallType;

public interface HomeService {
	public Commodity save(Commodity commodity);
	public Iterable<BigType> findBTList();
	public List<SmallType> findSTList(BigType bigType);
	public List<Commodity> findBybigType(BigType bigType);
	public Commodity findByproductId(int productId);
	public Page<Commodity> findBySmallType(SmallType smallType,Pageable pageable);
	
	//查询，搜索
	public BigType findByType(String typeName);
	public SmallType findByTypeName(String typeName);
	public Page<Commodity> findByBigType(BigType bigType,Pageable pageable);
	public Page<Commodity> findByproductNameContaining(String productName,Pageable pageable);
}
