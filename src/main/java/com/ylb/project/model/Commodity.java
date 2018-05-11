package com.ylb.project.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table
public class Commodity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int productId;//id
	private String productName;//商品名字
	private int number;//商品数量
	private double price;//单价
	private String descr;//商品描述
	private String imgUrl;//商品图片
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss",iso=ISO.DATE)
	private Date createDate;//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss",iso=ISO.DATE)
	private Date editDate;//修改时间
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="small_id")
	private SmallType smallType;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="b_id")
	private BigType bigType;
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	
	public SmallType getSmallType() {
		return smallType;
	}
	public void setSmallType(SmallType smallType) {
		this.smallType = smallType;
	}
	public BigType getBigType() {
		return bigType;
	}
	public void setBigType(BigType bigType) {
		this.bigType = bigType;
	}
	
	
	
}
