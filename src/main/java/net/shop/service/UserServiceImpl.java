package net.shop.service;

import java.util.List;

import javax.annotation.Resource;

import net.shop.dao.UserDAO;
import org.springframework.stereotype.Service;

import net.shop.vo.UserVO;

/**
 * 
 * @author jisung
 * 
 *
 */

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDAO")
	private UserDAO userDAO;
	
	@Override
	public UserVO selectOne() throws Exception {
		return userDAO.selectOne();
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
