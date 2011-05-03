package com.ppstream.mt.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;

@Entity
@Table(name = "USERS")
@Cache(usage=org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY)  
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_USERS")
	@SequenceGenerator(name="SEQ_USERS",sequenceName="SEQ_USERS")
	@Column(name = "user_id",unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "username",nullable = false, length = 100)
	private String userName;
	
	@Column(name = "pswd",nullable = false, length = 50)
	private String password;
    
	@Column(name = "email",nullable = true, length = 200)
	private String email;

	@Column(name = "reg_time",nullable = true)
	private Integer regTime;
	
	@Column(name = "reg_ip",nullable = true, length = 20)
	private String regIp;
	
	@Column(name = "last_visit",nullable = true)
	private Integer lastVisit;
	
	@Column(name = "last_ip",nullable = true, length = 20)
	private String lastIp;
	
	@Column(name = "is_company",nullable = false)
	private Integer isCompany;
	
	@Column(name = "group_leader",nullable = true)
	private Integer groupLeader;
	
	@ManyToOne
    @JoinColumn(name="group_id")
	private Group group;

	@Column(name = "nick_name",nullable = true, length = 100)
	private String nickName;
	
	@Column(name = "sub_phone",nullable = true, length = 100)
	private String subPhone;
	
	@Column(name = "status",nullable = true)
	private Integer status;
	
	@Column(name = "token",nullable = true, length = 32)
	private String token;
	
	@Column(name = "ikey_account",nullable = true, length = 32)
	private String iKeyAccount;
	
	@Column(name = "is_default",nullable = true)
	private Integer isDefault;

	/**
	 * name表示中间表的列,referencedColumnName表示实体类的关联列,默认是主键
                  保存  PERSIST 、删除  REMOVE 、修改  MERGE 、刷新  REFRESH、全部 ALL
       EAGER 即时加载,默认是LAZY
	 */
	// 主控
	@ManyToMany(
        targetEntity=Role.class
    )
	@JoinTable(
		name="users_has_roles",
		joinColumns=@JoinColumn(name="user_id"), 
		inverseJoinColumns=@JoinColumn(name="role_id") 
	)
	private Set<Role> roles;
	
	
	// 主控
	@ManyToMany(
        targetEntity=Privilege.class
    )
	@JoinTable(
		name="privilege_role_user",
		joinColumns=@JoinColumn(name="user_id"), 
		inverseJoinColumns=@JoinColumn(name="privilege_id") 
	)
	private Set<Privilege> privileges;

	
	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}


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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getRegTime() {
		return regTime;
	}


	public void setRegTime(Integer regTime) {
		this.regTime = regTime;
	}


	public String getRegIp() {
		return regIp;
	}


	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}


	public Integer getLastVisit() {
		return lastVisit;
	}


	public void setLastVisit(Integer lastVisit) {
		this.lastVisit = lastVisit;
	}


	public String getLastIp() {
		return lastIp;
	}


	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}


	public Integer getIsCompany() {
		return isCompany;
	}


	public void setIsCompany(Integer isCompany) {
		this.isCompany = isCompany;
	}


	public Integer getGroupLeader() {
		return groupLeader;
	}


	public void setGroupLeader(Integer groupLeader) {
		this.groupLeader = groupLeader;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getSubPhone() {
		return subPhone;
	}


	public void setSubPhone(String subPhone) {
		this.subPhone = subPhone;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getiKeyAccount() {
		return iKeyAccount;
	}


	public void setiKeyAccount(String iKeyAccount) {
		this.iKeyAccount = iKeyAccount;
	}


	public Integer getIsDefault() {
		return isDefault;
	}


	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}


	public Set<Role> getRoles() {
		return roles;
	}



	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
