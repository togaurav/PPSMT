package com.ppstream.mt.service;

import java.util.HashSet;
import java.util.Iterator;
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

/**
 * 权限管理 模块的service
 */
@Service
@Transactional
public class AuthorityService {
	
	@Autowired  
	private BaseDao baseDao;
	
	/**
	 * 取得大分类列表
	 */
	public List<PrivilegeType> getPrivilegeTypeList(){
		String hql = "from PrivilegeType";
		List<PrivilegeType> lists = baseDao.findByHql(hql, null);
		return lists;
	}
	
	/**
	 * 取得二级权限列表
	 */
	public List<PrivilegeCate> getPrivilegeCateList(){
		String hql = "from PrivilegeCate";
		List<PrivilegeCate> lists = baseDao.findByHql(hql, null);
		return lists;
	}
	
	/**
	 * 根据id取得资源分类记录
	 */
	public PrivilegeCate getPrivilegeCateById(Integer cateId){
		return baseDao.get(PrivilegeCate.class, cateId);
	}
	
	/**
	 * 根据id取得资源记录
	 */
	public Privilege getPrivilegeById(Integer privilegeId){
		return baseDao.get(Privilege.class, privilegeId);
	}
	
	/**
	 *  编辑二级权限
	 */
	public void editPrivilegeCate(Integer cateId,Integer typeId,String newCateName,Integer showNav){
		PrivilegeCate pc = this.getPrivilegeCateById(cateId); 
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
		PrivilegeCate pc = this.getPrivilegeCateById(cateId); 
		PrivilegeType type = pc.getPrivilegeType();
		type.getPrivilegeCates().remove(pc);  // 删除关联
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
		PrivilegeCate pc = this.getPrivilegeCateById(cateId); 
		Privilege privilege = new Privilege();
		privilege.setName(name);
		privilege.setAction(action);
		privilege.setShowNav(showNav);
		privilege.setPrivilegeCate(pc);
		baseDao.save(privilege);
	}
	
	/**
	 * 编辑资源
	 */
	public void editPrivilege(Integer privilegeId,Integer cateId,String name,String action,Integer showNav){
		Privilege privilege = this.getPrivilegeById(privilegeId);
		PrivilegeCate pc = this.getPrivilegeCateById(cateId);
		privilege.setPrivilegeCate(pc);
		privilege.setAction(action);
		privilege.setName(name);
		privilege.setShowNav(showNav);
		baseDao.update(privilege);
	}
	
	
	/**
	 * 删除资源
	 */
	public void deletePrivilege(Integer privilegeId){
		Privilege privilege = this.getPrivilegeById(privilegeId);
		PrivilegeCate cates = privilege.getPrivilegeCate();
		cates.getPrivileges().remove(privilege);  // 先从一的一方删除关联
		baseDao.delete(privilege);
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
	 * 根据id取得角色记录
	 */
	public Role getRole(Integer roleId){
		return baseDao.get(Role.class, roleId);
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
	 * 根据角色id取得拥有权限
	 */
	public Set<Privilege> getPrivilegesByRoleId(Integer roleId){
		Role role = baseDao.load(Role.class, roleId);
		return role.getPrivileges();
	}
	
	/**
	 * 根据角色id取得拥有权限id集合
	 */
	public Set<Integer> getPrivilegeIdsByRoleId(Integer roleId){
		Set<Integer> privilegeIds = new HashSet<Integer>();
		Set<Privilege> privileges = getPrivilegesByRoleId(roleId);
		Iterator<Privilege> iterator = privileges.iterator();
		while(iterator.hasNext()){
			Privilege privilege = iterator.next();
			privilegeIds.add(privilege.getId());
		}
		return privilegeIds;
	}
	
	/**
	 * 设置角色权限
	 * -------------------- [增:需要事务控制]
	 */
	public void configRolePrivilege(Integer roleId,String privilegeIds){
		Role role = baseDao.get(Role.class, roleId);
		// 全移除
		role.getPrivileges().removeAll(role.getPrivileges());
		// 添加
		String[] ids = privilegeIds.split(",");
		for(int i=0;i<ids.length;i++){
			Privilege privilege = baseDao.get(Privilege.class, Integer.valueOf(ids[i].trim()));
			if(!role.getPrivileges().contains(privilege)){
				role.getPrivileges().add(privilege);
			}
		}
		System.out.println("size 1 : "+role.getPrivileges().size());
		System.out.println("length 1 : "+ids.length);
//		Set<Privilege> privilegeSet = role.getPrivileges();
//		// 不包含，则增加
//		String[] ids = privilegeIds.split(privilegeIds);
//		for(int i=0;i<ids.length;i++){
//			Privilege privilege = baseDao.get(Privilege.class, ids[i]);
//			if(!role.getPrivileges().contains(privilege)){
//				role.getPrivileges().add(privilege);
//			}
//		}
//		// 如原有的已不被选择，则删除
//		Iterator<Privilege> iterator = privilegeSet.iterator();
//		while(iterator.hasNext()){
//			Privilege privilege = iterator.next();
//			if(privilegeIds.indexOf(privilege.getId()) < 0){
//				role.getPrivileges().remove(privilege);
//			}
//		}
		// 更新
		baseDao.saveOrUpdate(role);
	}
}
