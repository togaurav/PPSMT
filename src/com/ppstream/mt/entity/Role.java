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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "role_id",unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "role_name",nullable = false, length = 100)
	private String roleName;
	
	@ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        fetch=FetchType.EAGER,
        mappedBy = "roles",
        targetEntity = User.class
    )
	private Set<User> users;
	
	
	@ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        fetch=FetchType.EAGER,
        mappedBy = "roles",
        targetEntity = Privilege.class
    )
	private Set<Privilege> privileges;
	
	
	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}