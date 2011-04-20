package com.ppstream.mt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ppstream.mt.dao.BaseDao;
import com.ppstream.mt.entity.User;
import com.ppstream.mt.utils.Codec;

@Service
@Transactional
public class UserService {

	@Autowired  
	private BaseDao baseDao;
	
	public User getUserByNameAndPwd(String username,String password){
		String hql = "from User where userName = ? and password = ?";   
		List<User> users = baseDao.findByHql(hql, username,Codec.hexMD5(password));
		

//		String sql = "select * from user where userName = ? and password = ?";
//		List<User> users = (List<User>) baseDao.findBySql(sql, User.class, username,Codec.hexMD5(password));
		
		return (users.size() == 0) ? null : users.get(0);       // 如果size() > 1,throw new Exception();
	}
	
	
}
