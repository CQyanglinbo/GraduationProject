package com.ylb.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ylb.project.model.BigType;
import com.ylb.project.model.Commodity;
import com.ylb.project.model.SmallType;
@Repository
public interface CommodityRepository extends CrudRepository<Commodity, Integer>{
	public Commodity save(Commodity commodity);
	public List<Commodity> findBybigType(BigType bigType);
	public Commodity findByproductId(int productId);
	//public List<Commodity> findBySmallType(SmallType smallType);
	public Page<Commodity> findBySmallType(SmallType smallType,Pageable pageable);
	public Page<Commodity> findByBigType(BigType bigType,Pageable pageable);
	public Page<Commodity> findByproductNameContaining(String productName,Pageable pageable);
}
