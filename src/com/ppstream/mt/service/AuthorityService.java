package com.ppstream.mt.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppstream.mt.dao.BaseDao;
import com.ppstream.mt.entity.Privilege;
import com.ppstream.mt.entity.PrivilegeCate;
import com.ppstream.mt.entity.PrivilegeType;
import com.ppstream.mt.entity.Role;

@Service
@Transactional
public class AuthorityService {
/**
 * 权限管理 模块的service
 */
	@Autowired  
	private BaseDao baseDao;
	
	/**
	 * 取得二级权限列表
	 */
	public List<PrivilegeCate> getPrivilegeCateList(){
		String hql = "from PrivilegeCate";
		List<PrivilegeCate> lists = baseDao.findByHql(hql, null);
		return lists;
	}
	
	/**
	 *  编辑二级权限
	 */
	public void editPrivilegeCate(Integer cateId,Integer typeId,String newCateName,Integer showNav){
		PrivilegeCate pc = baseDao.load(PrivilegeCate.class, cateId);
		pc.setCateName(newCateName);
		pc.setShowNav(showNav);
		if(pc.getPrivilegeType().getId() != typeId){
			PrivilegeType pt = baseDao.load(PrivilegeType.class, typeId);
			pc.setPrivilegeType(pt);
		}
		baseDao.update(pc);
	}
	
	/*
	 * 新增二级权限
	 */
	public void addPrivilegeCate(Integer typeId,String newCateName,Integer showNav){
		PrivilegeType pt = baseDao.load(PrivilegeType.class, typeId);
		PrivilegeCate pc = new PrivilegeCate();
		pc.setCateName(newCateName);
		pc.setShowNav(showNav);
		pc.setPrivilegeType(pt);
		baseDao.save(pc);
	}
	
	/**
	 * 删除二级权限       
	 */
	public void deletePrivilegeCate(Integer cateId){
		PrivilegeCate pc = baseDao.load(PrivilegeCate.class, cateId);
		baseDao.delete(pc);
	}
	
	/**
	 * 新增一级权限
	 */
	public void addPrivilegeType(String typeName,Integer showNav){
		PrivilegeType pt = new PrivilegeType();
		pt.setTypeName(typeName);
		pt.setShowNav(showNav);
		baseDao.save(pt);
	}
	
	/**
	 * 新增资源
	 */
	public void addPrivilege(Integer cateId,String name,String action,Integer showNav){
		PrivilegeCate pc = baseDao.load(PrivilegeCate.class, cateId);
		Privilege privilege = new Privilege();
		privilege.setName(name);
		privilege.setAction(action);
		privilege.setShowNav(showNav);
		privilege.setPrivilegeCate(pc);
		baseDao.save(privilege);
	}
	
	/**
	 * 取得角色列表
	 */
	public List<Role> getRoleList(){
		String hql = "from Role";
		List<Role> lists = baseDao.findByHql(hql, null);
		return lists;
	}
	/**
	 * 新增角色
	 */
	public void addRole(String roleName){
		Role role = new Role();
		role.setRoleName(roleName);
		baseDao.save(role);
	}
	
	/**
	 * 编辑角色
	 */
	public void editRole(Integer roleId,String roleName){
		Role role = baseDao.load(Role.class, roleId);
		role.setRoleName(roleName);
		baseDao.update(role);
	}
	
	/**
	 * 删除角色
	 */
	public void deleteRole(Integer roleId){
		Role role = baseDao.load(Role.class, roleId);
		baseDao.delete(role);
	}
	
	/**
	 * 设置角色权限
	 */
	public void configRolePrivilege(Integer roleId){
//		Role role = baseDao.load(Role.class, roleId);
//		Set<Privilege> privileges = null;
//		role.setPrivileges(privileges);
	}
}
