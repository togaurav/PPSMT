package com.ppstream.mt.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;  

@Entity
@Table(name = "camuser")
@XStreamAlias("camuser")  
public class User extends BaseEntity implements Serializable{
	
	@Column(nullable = false, length = 12)
	private String userName;
	
	@Column(nullable = false)
	private String password;
    
	@Column(nullable = false, length = 50)
	private String emailAddress;


//	@OneToMany(fetch=FetchType.LAZY,mappedBy="camUser")
//	private List<VideoInfoBean> videos;


	@Override
	public String toString(){
		return "SubTagList [userId=" + super.getId() + ", userName=" + userName + ", emailAddress=" + emailAddress + "]";
	}
	
}
