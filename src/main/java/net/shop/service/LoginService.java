package net.shop.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import net.shop.dao.UserDAO;
import net.shop.vo.UserVO;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-25
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

@Service("loginService")
public class LoginService implements UserDetailsService{

	@Resource(name="userDAO")
	private UserDAO userDAO;
	
	@Resource(name="passwordEncoder")
	private ShaPasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		try {
			UserVO userVO = userDAO.selectOne(email);
			if(userVO == null){
				return null;
			}
			String password = userVO.getPassword();
			String role = userVO.getAuthority();
			
			Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
			if("ROLE_USER".equals(role)){
				roles.add(new SimpleGrantedAuthority("ROLE_USER"));
			}else{
				roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
			UserDetails user = new User(email,password,roles);
			return user;
 		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String encoding(String str){
		return encoder.encodePassword(str,null);
	}
}
