package com.ylb.project.service;

import java.util.List;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.ylb.project.dao.BigTypeRepository;
import com.ylb.project.dao.CommodityRepository;
import com.ylb.project.dao.SmallTypeRepository;
import com.ylb.project.model.BigType;
import com.ylb.project.model.Commodity;
import com.ylb.project.model.SmallType;

@Service
public class HomeServiceImpl implements HomeService{
	@Autowired
	private BigTypeRepository bigTypeRepository;
	@Autowired
	private SmallTypeRepository smallTypeRepository;
	@Autowired
	private CommodityRepository commodityRepository;
	/**
	 * 找出所有的大类型
	 */
	@Override
	@Transactional
	public Iterable<BigType> findBTList() {  
		return bigTypeRepository.findAll();
	}
	/**
	 * 根据大类别找到所有的小类别
	 */
	@Transactional
	@Override
	public List<SmallType> findSTList(BigType bigType) {
		return smallTypeRepository.findByBigType(bigType);
	}
	/**
	 * 根据大类别找到属于该大类别的所有商品
	 */
	@Transactional
	@Override
	public List<Commodity> findBybigType(BigType bigType) {
		return commodityRepository.findBybigType(bigType);
	}
	/**
	 * 根据productId找到对应的商品
	 */
	@Transactional
	@Override
	public Commodity findByproductId(int productId) {
		return commodityRepository.findByproductId(productId);
	}
	/**
	 * 根据小类型找到商品
	 */
	@Transactional
	@Override
	public Page<Commodity> findBySmallType(SmallType smallType,Pageable pageable) {
		return commodityRepository.findBySmallType(smallType,pageable);
	}
	/**
	 * 根据小类型名称找到对应的实体
	 */
	@Transactional
	@Override
	public SmallType findByTypeName(String typeName) {
		return smallTypeRepository.findByTypeName(typeName);
	}
	/**
	 * 根据大类型找到商品，并分页
	 */
	@Transactional
	@Override
	public Page<Commodity> findByBigType(BigType bigType, Pageable pageable) {
		
		return commodityRepository.findByBigType(bigType, pageable);
	}
	/**
	 * 根据商品名称进行模糊查询，并分页
	 */
	@Transactional
	@Override
	public Page<Commodity> findByproductNameContaining(String productName, Pageable pageable) {
		
		return commodityRepository.findByproductNameContaining(productName, pageable);
	}
	/**
	 * 根据大类名称找到对应的实体
	 */
	@Transactional
	@Override
	public BigType findByType(String typeName) {
		
		return bigTypeRepository.findByTypeName(typeName);
	}

	

}
