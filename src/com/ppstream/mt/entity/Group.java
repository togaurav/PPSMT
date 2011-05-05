package com.ppstream.mt.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_PRI_GROUP")
	@SequenceGenerator(name="SEQ_PRI_GROUP",sequenceName="SEQ_PRI_GROUP")
	@Column(name = "group_id",unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "group_name",nullable = false, length = 100)
	private String groupName;
	
	@OneToMany(mappedBy="group",cascade=CascadeType.ALL)
//	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,include="non-lazy")  
	private Set<User> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
