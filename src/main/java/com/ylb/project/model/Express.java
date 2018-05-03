package com.ylb.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@author 作者:杨林波   email:CQyanglinbo@gmail.com
 *@version  创建时间:2018年4月30日下午3:43:22
 *
**/
@Entity
@Table
public class Express {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String companyName; //快递公司
	private String companyNo;	//快递编码
	
	/*
	 * setter and getter
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	
	/*
	 * 构造方法
	 */
	public Express(int id, String companyName, String companyNo) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.companyNo = companyNo;
	}
	public Express(String companyName, String companyNo) {
		super();
		this.companyName = companyName;
		this.companyNo = companyNo;
	}
	public Express(int id) {
		super();
		this.id = id;
	}
	public Express() {
		super();
	}
	
	/*
	 * toString方法
	 */
	@Override
	public String toString() {
		return "Express [id=" + id + ", companyName=" + companyName + ", companyNo=" + companyNo + "]";
	}

}
