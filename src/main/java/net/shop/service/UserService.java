package net.shop.service;

import java.util.List;

import net.shop.vo.UserVO;

public interface UserService {
	public UserVO selectOne() throws Exception;

	public List<UserVO> selectList(int start,int end) throws Exception;

	public int count() throws Exception;

	public int insert(UserVO userVO) throws Exception;

	public int update(UserVO userVO) throws Exception;

	public int delete() throws Exception;
}
