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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "privilege")
public class Privilege implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_PRIVILEGE")
	@SequenceGenerator(name="SEQ_PRIVILEGE",sequenceName="SEQ_PRIVILEGE")
	@Column(name = "privilege_id",unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "name",nullable = false, length = 100)
	private String name;
	
	@Column(name = "action",nullable = false, length = 100)
	private String action;

	@Column(name = "show_nav",nullable = false)
	private Integer showNav;        // 是否显示在左侧的树中
	
	@Column(name = "sort_index",nullable = true)
	private Integer sortIndex;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cate_id")
	private PrivilegeCate privilegeCate;
	
	
	// 被控
	@ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},  
        fetch=FetchType.EAGER,
        mappedBy = "privileges",
        targetEntity = Role.class
    )
	private Set<Role> roles;
	
	// 被控
	@ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        fetch=FetchType.EAGER,
        mappedBy = "privileges",
        targetEntity = User.class
    )
	private Set<User> users;
	
	

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public PrivilegeCate getPrivilegeCate() {
		return privilegeCate;
	}

	public void setPrivilegeCate(PrivilegeCate privilegeCate) {
		this.privilegeCate = privilegeCate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
	
}
