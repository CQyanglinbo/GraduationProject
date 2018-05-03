package com.ylb.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author 作者:杨林波 email:CQyanglinbo@gmail.com
 * @version 创建时间:2018年5月1日下午2:47:29
 *
 **/
@Entity
@Table
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userName;
	private String password;

	/*
	 * getter and setter
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * 构造方法
	 */
	public Admin(int id, String userName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	public Admin(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public Admin(int id) {
		super();
		this.id = id;
	}

	public Admin() {
		super();
	}

	/*
	 * toString方法
	 */
	@Override
	public String toString() {
		return "AdminModel [id=" + id + ", userName=" + userName + ", password=" + password + "]";
	}

}
