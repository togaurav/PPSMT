package com.ppstream.mt.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailServiceImpl implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
