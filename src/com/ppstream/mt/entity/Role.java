package com.ppstream.mt.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_ROLES")
	@SequenceGenerator(name="SEQ_ROLES",sequenceName="SEQ_ROLES")
	@Column(name = "role_id",unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "role_name",nullable = false, length = 100)
	private String roleName;
	
	// 被控
	@ManyToMany(
        fetch=FetchType.EAGER,
        mappedBy = "roles",
        targetEntity = User.class
    )
	private Set<User> users;
	
	// 主控
    @ManyToMany(
        targetEntity=Privilege.class
    )
	@JoinTable(
		name="privilege_role_user",
		joinColumns=@JoinColumn(name="role_id"), 
		inverseJoinColumns=@JoinColumn(name="privilege_id") 
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
