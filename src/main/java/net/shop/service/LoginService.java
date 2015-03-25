package net.shop.service;

import javax.annotation.Resource;

import net.shop.dao.UserDAO;
import net.shop.vo.UserVO;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginService implements UserDetailsService{

	@Resource(name="userDAO")
	private UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		try {
			UserVO userVO = userDAO.selectOne(email);
			if(userVO == null){
				return null;
			}
			return userVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
