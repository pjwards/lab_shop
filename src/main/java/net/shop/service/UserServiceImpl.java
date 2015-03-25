package net.shop.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import net.shop.dao.UserDAO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.shop.vo.UserVO;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDAO")
	private UserDAO userDAO;
	
	@Override
	public UserVO selectOne(String email) throws Exception {
		return userDAO.selectOne( email);
	}

	@Override
	public List<UserVO> selectList(int start, int end) throws Exception {
		return userDAO.selectList(start,end);
	}

	@Override
	public int count() throws Exception {
		return userDAO.count();
	}

	@Override
	public int insert(UserVO userVO) throws Exception {
		return userDAO.insert(userVO);
	}

	@Override
	public int update(UserVO userVO) throws Exception {
		return userDAO.update(userVO);
	}

	@Override
	public int delete() throws Exception {
		return userDAO.delete();
	}

    @Override
    public int selectUserNumberByEmail(String email) throws Exception {
        return userDAO.selectUserNumberByEmail(email);
    }

}
