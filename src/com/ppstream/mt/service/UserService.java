package com.ppstream.mt.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ppstream.mt.bean.GPrivilege;
import com.ppstream.mt.dao.BaseDao;
import com.ppstream.mt.entity.Group;
import com.ppstream.mt.entity.Privilege;
import com.ppstream.mt.entity.PrivilegeCate;
import com.ppstream.mt.entity.PrivilegeType;
import com.ppstream.mt.entity.Role;
import com.ppstream.mt.entity.User;
import com.ppstream.mt.utils.Codec;
import com.ppstream.mt.utils.pager.TbData;

@Service("userService")
public class UserService {

	@Autowired
	private BaseDao baseDao;
	
	static {
		CacheManager singletonManager = CacheManager.create();
		//最多存1000个Element,达到maxInMemory后不缓存到磁盘,timeToLiveSeconds,120秒存活时间; timeToIdleSeconds,120秒不访问该缓存,则清除
		Cache memoryOnlyCache = new Cache(new CacheConfiguration("userService", 1000).overflowToDisk(false).transactionalMode(CacheConfiguration.TransactionalMode.OFF));
		singletonManager.addCache(memoryOnlyCache);
	}  

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public User getUserByNameAndPwd(String username, String password) {
		String hql = "from User as user left join fetch user.roles where user.userName = ? and user.password = ? and user.status = ?"; // 在启用账户中查找
		List<User> users = baseDao.findByHql(hql, username,Codec.hexMD5(password),1);
		return (users.size() == 0) ? null : users.get(0); // 如果size() > 1,throw new Exception();
	}
	
	/**
	 * 根绝roleId取得资源列表
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Set<Privilege> getPrivilegeByRoleId(Integer roleId){
		String hql = "from Role as role left join fetch role.privileges where role.id = ?";
		List<Role> roles = baseDao.findByHql(hql, roleId);
		return roles.get(0).getPrivileges();
	}
	
	/**
	 * 根绝userId取得资源列表
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Set<Privilege> getPrivilegeByUserId(Integer userId){
		String hql = "from User as user left join fetch user.privileges where user.id = ?";
		List<User> users = baseDao.findByHql(hql, userId);
		return users.get(0).getPrivileges();
	}

	/**
	 * 分页查询用户列表
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public TbData getUserList(Integer currentPage,Integer pageSize) {
		String hql = "select distinct user from User as user left join fetch user.roles";
		int totalSize = baseDao.getRows(hql);
	    TbData tbData = baseDao.runHQL(totalSize, pageSize, currentPage, hql, null);
		return tbData;
	}
	
	/**
	 * 取得部门集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Group> getGroupList() {
		String hql = "from Group";
		List<Group> groups = baseDao.findByHql(hql, null);
		return groups;
	}
	
	/**
	 * 改变用户状态
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void changeUserStatus(Integer userId, Integer status) {
		User user = baseDao.get(User.class, userId);
		user.setStatus(status);
		baseDao.update(user);
	}
	
	/**
	 * 根据userId取得用户记录
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public User getUserById(Integer userId) {
		String hql = "from User as user left join fetch user.roles where user.id = ?";
		List<User> users = baseDao.findByHql(hql, userId);
		return users.get(0);
	}
	
	/**
	 * 新增或编辑用户记录 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addOrUpdateUser(Integer userId, String userName,
			String password, String email, String roleIds, Integer groupLeader,
			Integer groupId, String nickName, String subPhone) {
		User user = null;
		if (userId == null) {
			user = new User();
		} else {
			user = baseDao.get(User.class, userId);
		}
		user.setUserName(userName);
		user.setPassword(Codec.hexMD5(password));
		user.setEmail(email);

		String[] ids = roleIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			Role role = baseDao.get(Role.class, Integer.valueOf(ids[i].trim()));
			if (user.getRoles() != null) {
				user.getRoles().add(role);
			} else {
				Set<Role> roleSets = new HashSet<Role>();
				roleSets.add(role);
				user.setRoles(roleSets);
			}
		}

		user.setGroupLeader(groupLeader);
		user.setGroup(baseDao.get(Group.class, groupId));
		user.setNickName(nickName);
		user.setSubPhone(subPhone);
		// user.setRegTime(null);
		// user.setRegIp(ip);
		user.setStatus(1);
		user.setIsCompany(0);
		baseDao.saveOrUpdate(user);
	}
	
	
	public Set<Integer> getPrivilegeIdsByUserId(Integer userId) {
		Set<Integer> privilegeIds = new HashSet<Integer>();
		// 用户关联出权限
		User user = this.getUserById(userId);
		Set<Privilege> userPrivileges = user.getPrivileges();
		Iterator<Privilege> userPrivilegeIterator = userPrivileges.iterator();
		while (userPrivilegeIterator.hasNext()) {
			Privilege privilege = userPrivilegeIterator.next();
			privilegeIds.add(privilege.getId());
		}
		// 从角色中取得权限
		Set<Role> roles = user.getRoles();
		Iterator<Role> roleIterator = roles.iterator();
		while (roleIterator.hasNext()) {
			Role role = roleIterator.next();
			Set<Privilege> privileges = role.getPrivileges();
			Iterator<Privilege> rolePrivilegeIterator = privileges.iterator();
			while (rolePrivilegeIterator.hasNext()) {
				Privilege privilege = rolePrivilegeIterator.next();
				privilegeIds.add(privilege.getId());
			}
		}
		return privilegeIds;
	}

	/**
	 * 为用户赋予权限
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void configUserPrivilege(Integer userId, String privilegeIds) {
		User user = baseDao.get(User.class, userId);
		// 移除
		user.getPrivileges().removeAll(user.getPrivileges());
		// 添加
		String[] ids = privilegeIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			Privilege privilege = baseDao.get(Privilege.class,
					Integer.valueOf(ids[i].trim()));
			if (!user.getPrivileges().contains(privilege)) {
				user.getPrivileges().add(privilege);
			}
		}
		// 更新
		baseDao.saveOrUpdate(user);
	}

	public HashMap<String, HashMap<String, HashSet<GPrivilege>>> setPrivilegeToMap(
			HashMap<String, HashMap<String, HashSet<GPrivilege>>> maps,
			Set<Privilege> privileges) {
		Iterator<Privilege> privilegeIterators = privileges.iterator();
		while (privilegeIterators.hasNext()) {
			Privilege privilege = privilegeIterators.next();
			String privilegeName = privilege.getName();
			String privilegeAction = privilege.getAction();
			Integer showNav = privilege.getShowNav();
			PrivilegeCate pc = privilege.getPrivilegeCate();
			String cateName = pc.getCateName();
			PrivilegeType pt = pc.getPrivilegeType();
			String typeName = "未分类";
			if (pt != null) {
				typeName = pt.getTypeName();
			}

			// 将拥有的权限放入一个集合中 
			GPrivilege gp = new GPrivilege(privilegeName, privilegeAction,
					showNav);
			if (maps.containsKey(typeName)) {
				HashMap<String, HashSet<GPrivilege>> cateMap = maps
						.get(typeName);
				if (cateMap.containsKey(cateName)) { // 新增小类
					HashSet<GPrivilege> privilegeSet = cateMap.get(cateName);
					if (!this.judgeContain(privilegeSet, gp)) {
						privilegeSet.add(gp); // 不判断会重复
					}
				} else { // 新增中类
					HashSet<GPrivilege> privilegeSet = new HashSet<GPrivilege>();
					privilegeSet.add(gp);
					cateMap.put(cateName, privilegeSet);
				}
			} else { // 新增大类
				HashMap<String, HashSet<GPrivilege>> cateMap = new HashMap<String, HashSet<GPrivilege>>();
				HashSet<GPrivilege> gprivilegeSet = new HashSet<GPrivilege>();
				gprivilegeSet.add(gp);
				cateMap.put(cateName, gprivilegeSet);
				maps.put(typeName, cateMap);
			}

		}
		return maps;
	}
	
	public boolean judgeContain(HashSet<GPrivilege> privilegeSet,GPrivilege gp){
		boolean flag = false;
		Iterator<GPrivilege> iterator = privilegeSet.iterator();
		while(iterator.hasNext()){
			GPrivilege gPrivilege = iterator.next();
			if(gPrivilege.getName().equalsIgnoreCase(gp.getName())){
				flag = true;
			}
		}
		return flag;
	}

}
