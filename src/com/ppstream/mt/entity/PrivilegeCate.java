package com.ppstream.mt.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "privilege_cates")
public class PrivilegeCate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "cate_id",unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "cate_name",nullable = false, length = 100)
	private String cateName;
	
	@Column(name = "show_nav",nullable = true)
	private Integer showNav;
	
	@Column(name = "sort_index",nullable = true)
	private Integer sortIndex;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="privilegeCate",cascade=CascadeType.ALL)
	@OrderColumn(name="showNav")
	private Set<Privilege> privileges;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)  // one一方的数据不存在,即PrivilegeType一方
    @JoinColumn(name="type_id")
	private PrivilegeType privilegeType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getShowNav() {
		return showNav;
	}

	public void setShowNav(Integer showNav) {
		this.showNav = showNav;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	public PrivilegeType getPrivilegeType() {
		return privilegeType;
	}

	public void setPrivilegeType(PrivilegeType privilegeType) {
		this.privilegeType = privilegeType;
	}

}
