package com.ppstream.mt.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ppstream.mt.dao.BaseDao;
import com.ppstream.mt.entity.Privilege;
import com.ppstream.mt.entity.PrivilegeCate;
import com.ppstream.mt.entity.PrivilegeType;
import com.ppstream.mt.entity.Role;
import com.ppstream.mt.entity.User;

/**
 * 权限管理 模块的service
 */
@Service("authorityService")
public class AuthorityService {
	
	@Autowired  
	private BaseDao baseDao;
	
	static {
		CacheManager singletonManager = CacheManager.create();
		//最多存1000个Element,达到maxInMemory后不缓存到磁盘,timeToLiveSeconds,120秒存活时间; timeToIdleSeconds,120秒不访问该缓存,则清除
		Cache memoryOnlyCache = new Cache(new CacheConfiguration("authorityService", 1000).overflowToDisk(false).transactionalMode(CacheConfiguration.TransactionalMode.OFF));
		singletonManager.addCache(memoryOnlyCache);
	}
	
	/**
	 * 取得大分类列表
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<PrivilegeType> getPrivilegeTypeList(){
		String hql = "from PrivilegeType";
		List<PrivilegeType> lists = baseDao.findByHql(hql, null);
		return lists;
	}
	
	/**
	 * 取得二级权限列表
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED,isolation=Isolation.READ_COMMITTED)
	public List<PrivilegeCate> getPrivilegeCateList(){
		List<PrivilegeCate> result;
		// 缓存需同步，增删改的时候，清缓存
		Cache authorityServiceCache = CacheManager.create().getCache("authorityService");
		Element element = authorityServiceCache.get("privilegeCateList");
		if(element != null){
			result = (List<PrivilegeCate>) element.getObjectValue();
			System.out.println("缓存中取出");
		}else{
			String hql = "from PrivilegeCate as pc left join fetch pc.privileges";
			List<PrivilegeCate> lists = baseDao.findByHql(hql, null);
			HashSet set = new HashSet(lists);
			result = new ArrayList(set);
			
			element = new Element("privilegeCateList", result);
			authorityServiceCache.put(element);
			System.out.println("查询生成");
		}
		return result;
	}
	
	/**
	 * 根据id取得资源分类记录
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PrivilegeCate getPrivilegeCateById(Integer cateId){
		return baseDao.get(PrivilegeCate.class, cateId);
	}
	
	/**
	 * 根据id取得资源记录
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Privilege getPrivilegeById(Integer privilegeId){
		return baseDao.get(Privilege.class, privilegeId);
	}
	
	/**
	 *  编辑二级权限
	 */
	@Transactional(propagation = Propagation.REQUIRED)
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
	@Transactional(propagation = Propagation.REQUIRED)
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
	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePrivilegeCate(Integer cateId){
		PrivilegeCate pc = this.getPrivilegeCateById(cateId); 
		PrivilegeType type = pc.getPrivilegeType();
		type.getPrivilegeCates().remove(pc);  // 删除关联
		baseDao.delete(pc);
	}
	
	/**
	 * 修改资源分类的排序
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void editPrivilegeCateSortIndex(Integer cateId,Integer sortIndex){
		PrivilegeCate pc = this.getPrivilegeCateById(cateId); 
		pc.setSortIndex(sortIndex);
		baseDao.save(pc);
	}
	
	/**
	 * 新增一级权限
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addPrivilegeType(String typeName,Integer showNav){
		PrivilegeType pt = new PrivilegeType();
		pt.setTypeName(typeName);
		pt.setShowNav(showNav);
		baseDao.save(pt);
	}
	
	/**
	 * 新增资源
	 */
	@Transactional(propagation = Propagation.REQUIRED)
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
	@Transactional(propagation = Propagation.REQUIRED)
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
	 * 修改资源排序
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void editPrivilegeSortIndex(Integer privilegeId,Integer sortIndex){
		Privilege privilege = this.getPrivilegeById(privilegeId); 
		privilege.setSortIndex(sortIndex);
		baseDao.save(privilege);
	}
	
	
	/**
	 * 删除资源
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePrivilege(Integer privilegeId){
		Privilege privilege = this.getPrivilegeById(privilegeId);
		PrivilegeCate cate = privilege.getPrivilegeCate();
		cate.getPrivileges().remove(privilege);  // 先从一的一方删除关联
		
		Set<Role> roles = privilege.getRoles();
		Iterator<Role> roleIterator = roles.iterator();
		while(roleIterator.hasNext()){
			Role role = roleIterator.next();
			role.getPrivileges().remove(privilege);
			baseDao.update(role);
		}
		
		Set<User> users = privilege.getUsers();
		Iterator<User> userIterator = users.iterator();
		while(userIterator.hasNext()){
			User user = userIterator.next();
			user.getPrivileges().remove(privilege);
			baseDao.update(user);
		}
		
		baseDao.delete(privilege);
	}
	
	
	/**
	 * 取得角色列表
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Role> getRoleList(){
		String hql = "from Role";
		List<Role> lists = baseDao.findByHql(hql, null);
		return lists;
	}
	
	/**
	 * 新增角色
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addRole(String roleName){
		Role role = new Role();
		role.setRoleName(roleName);
		baseDao.save(role);
	}
	
	/**
	 * 根据id取得角色记录
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Role getRole(Integer roleId){
		return baseDao.get(Role.class, roleId);
	}
	
	/**
	 * 编辑角色
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void editRole(Integer roleId,String roleName){
		Role role = baseDao.load(Role.class, roleId);
		role.setRoleName(roleName);
		baseDao.update(role);
	}
	
	/**
	 * 删除角色
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(Integer roleId){
		Role role = baseDao.load(Role.class, roleId);
		baseDao.delete(role);
	}
	
	/**
	 * 根据角色id取得拥有权限
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Set<Privilege> getPrivilegesByRoleId(Integer roleId){
		Role role = baseDao.load(Role.class, roleId);
		return role.getPrivileges();
	}
	
	/**
	 * 根据角色id取得拥有权限id集合
	 */
	public Set<Integer> getPrivilegeIdsByRoleId(Integer roleId){
		Set<Integer> privilegeIds = new HashSet<Integer>();
		Set<Privilege> privileges = this.getPrivilegesByRoleId(roleId);
		Iterator<Privilege> iterator = privileges.iterator();
		while(iterator.hasNext()){
			Privilege privilege = iterator.next();
			privilegeIds.add(privilege.getId());
		}
		return privilegeIds;
	}
	
	/**
	 * 设置角色权限
	 */
	@Transactional(propagation = Propagation.REQUIRED)
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
		// 更新
		baseDao.saveOrUpdate(role);
	}
}
