package net.shop.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import net.shop.dao.UserDAO;
import net.shop.vo.UserVO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
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
			System.out.println(email);
			UserVO userVO = userDAO.selectOne(email);
			UserDetails user = new User(userVO.getEmail(), userVO.getPassword(),getAuthorities(3));
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
    public Collection<GrantedAuthority> getAuthorities(Integer access) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);

        if (access.compareTo(1) == 0) {
            authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        }
        else{
            authList.add(new GrantedAuthorityImpl("ROLE_USER"));
        }
        return authList;
    }

}
