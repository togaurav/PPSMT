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
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "privilege_types")
public class PrivilegeType implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_PRIVILEGE_TYPES")
	@SequenceGenerator(name="SEQ_PRIVILEGE_TYPES",sequenceName="SEQ_PRIVILEGE_TYPES")
	@Column(name = "type_id",unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "type_name",nullable = true, length = 50)
	private String typeName;
	
	@Column(name = "show_nav",nullable = true)
	private Integer showNav;
	
	@Column(name = "sort_index",nullable = true)
	private Integer sortIndex;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="privilegeType",cascade=CascadeType.ALL)
	@OrderColumn(name="sortIndex")
	private Set<PrivilegeCate> privilegeCates;

	public Set<PrivilegeCate> getPrivilegeCates() {
		return privilegeCates;
	}

	public void setPrivilegeCates(Set<PrivilegeCate> privilegeCates) {
		this.privilegeCates = privilegeCates;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
