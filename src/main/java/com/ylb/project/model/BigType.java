package com.ylb.project.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table
public class BigType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;//id
	private String typeName;//类型名称
	private String imgUrl;//图片路径
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss",iso=ISO.DATE)
	private Date createDate;//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss",iso=ISO.DATE)
	private Date editDate;//修改时间
	private String detail;//商品类型描述
//	@OneToMany(cascade=CascadeType.ALL)
//	private Set<SmallType> smallTypes=new HashSet<SmallType>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
//	public Set<SmallType> getSmallTypes() {
//		return smallTypes;
//	}
//	public void setSmallTypes(Set<SmallType> smallTypes) {
//		this.smallTypes = smallTypes;
//	}
	
	
}
