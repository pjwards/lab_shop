package net.shop.dao;

import java.util.List;

import net.shop.vo.UserVO;

public interface UserDAO {

	public UserVO selectOne() throws Exception;

	public List<UserVO> selectList(int start, int end);

	public int count();

	public int insert(UserVO userVO);

	public int update(UserVO userVO);

	public int delete();
}
