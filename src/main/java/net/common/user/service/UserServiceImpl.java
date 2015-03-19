package net.common.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.common.user.dao.UserDAO;
import net.common.user.vo.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDAO")
	private UserDAO userDAO;
	
	@Override
	public UserVO selectOne() throws Exception {
		// TODO Auto-generated method stub
		return userDAO.selectOne();
	}

	@Override
	public List<UserVO> selectList(int start, int end) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.selectList(start,end);
	}

	@Override
	public int count() throws Exception {
		// TODO Auto-generated method stub
		return userDAO.count();
	}

}
