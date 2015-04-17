package net.shop.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.shop.dao.UserDAO;

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
	public boolean selectOne(String email) throws Exception {
		UserVO userVO = userDAO.selectOne(email);
		if(userVO != null){
			return true;
		}
		return false;
	}

	@Override
	public List<UserVO> selectList(int start, int end, String order,String keyword) throws Exception {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("order", order);
		paraMap.put("offset", start);
		paraMap.put("limit", end);
		paraMap.put("keyword", keyword);
		return userDAO.selectListMap(paraMap);
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
	public int delete(String email) throws Exception {
		return userDAO.delete(email);
	}

	@Override
	public int updateDate(String email) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.updateDate(email);
	}

	@Override
	public int selectOneNo(String email) throws Exception {
		// TODO Auto-generated method stub
		UserVO userVO = userDAO.selectOne(email);
		
		return userVO.getNumber();
	}

	@Override
	public UserVO selectOneVo(String email) throws Exception {
		// TODO Auto-generated method stub
		UserVO userVO = userDAO.selectOne(email);
		return userVO;
	}

	@Override
	public int updateAuth(String email,String auth) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("email", email);
		paraMap.put("authority",auth);
		return userDAO.updateAuth(paraMap);
	}

}
