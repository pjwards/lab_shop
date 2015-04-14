package net.shop.dao;

import java.util.HashMap;
import java.util.List;

import net.shop.vo.UserVO;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

public interface UserDAO {

	public UserVO selectOne(String email) throws Exception;
	
	public List<UserVO> selectListMap(HashMap<String,Object> paraMap) throws Exception;

	public int count();

	public int insert(UserVO userVO);

	public int update(UserVO userVO);

	public int delete(String email);

	public int updateDate(String email);
}
