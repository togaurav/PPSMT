package com.ppstream.mt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service("userService")
@Transactional
public class UserService {

	@Autowired
	private BaseDao baseDao;

	public User getUserByNameAndPwd(String username, String password) {
		String hql = "from User as user left join fetch user.roles where user.userName = ? and user.password = ? and user.status = ?"; // 在启用账户中查找
		List<User> users = baseDao.findByHql(hql, username,Codec.hexMD5(password),1);
		return (users.size() == 0) ? null : users.get(0); // 如果size() > 1,throw new Exception();
	}
	
	public Set<Privilege> getPrivilegeByRoleId(Integer roleId){
		String hql = "from Role as role left join fetch role.privileges where role.id = ?";
		List<Role> roles = baseDao.findByHql(hql, roleId);
		return roles.get(0).getPrivileges();
	}
	
	public Set<Privilege> getPrivilegeByUserId(Integer userId){
		String hql = "from User as user left join fetch user.privileges where user.id = ?";
		List<User> users = baseDao.findByHql(hql, userId);
		return users.get(0).getPrivileges();
	}

	public List<User> getUserList() {
		String hql = "from User as user left join fetch user.roles";
		List<User> users = baseDao.findByHql(hql, null);
		Set<User> sets = new HashSet(users);
		return new ArrayList(sets);
	}

	public List<Group> getGroupList() {
		String hql = "from Group";
		List<Group> groups = baseDao.findByHql(hql, null);
		return groups;
	}

	public void changeUserStatus(Integer userId, Integer status) {
		User user = baseDao.get(User.class, userId);
		user.setStatus(status);
		baseDao.update(user);
	}

	public User getUserById(Integer userId) {
		String hql = "from User as user left join fetch user.roles where user.id = ?";
		List<User> users = baseDao.findByHql(hql, userId);
		return users.get(0);
	}

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

	public void configRolePrivilege(Integer userId, String privilegeIds) {
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
