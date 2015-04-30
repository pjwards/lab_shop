package net.shop.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.shop.dao.OrdersDAO;
import net.shop.dao.UserDAO;
import net.shop.dao.WishlistDAO;

import org.springframework.stereotype.Service;

import net.shop.vo.OrdersVO;
import net.shop.vo.UserVO;
import net.shop.vo.WishlistVO;

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
	
	@Resource(name="wishlistDAO")
	private WishlistDAO wishlistDAO;
	
	@Resource(name="ordersDAO")
	private OrdersDAO ordersDAO;
	
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
	public int count(String keyword) throws Exception {
		return userDAO.count(keyword);
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
	public int selectOneNo(String email) throws Exception {
		// TODO Auto-generated method stub
		UserVO userVO = userDAO.selectOne(email);
		
		return userVO.getNumber();
	}

	@Override
	public UserVO selectOneVo(String email) throws Exception {
		// TODO Auto-generated method stub
		
		return userDAO.selectOne(email);
	}
	
	@Override
	public int updateDate(String email) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.updateDate(email);
	}
	
	@Override
	public int updateAuth(String email,String auth) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("email", email);
		paraMap.put("authority",auth);
		return userDAO.updateData(paraMap);
	}

	@Override
	public int updatePassword(String email, String password) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("email", email);
		paraMap.put("password",password);
		return userDAO.updateData(paraMap);
	}

	@Override
	public int addWishlist(String email, int number) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userEmail", email);
		paraMap.put("goodsNumber",number);
		return wishlistDAO.insert(paraMap);
	}

	@Override
	public int wishCount() throws Exception{
		// TODO Auto-generated method stub
		return wishlistDAO.count();
	}

	@Override
	public int wishCount(String keyword) throws Exception{
		// TODO Auto-generated method stub
		return wishlistDAO.count(keyword);
	}
	
	@Override
	public List<WishlistVO> wishList(int start, int end, String keyword) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("offset", start);
		paraMap.put("limit", end);
		paraMap.put("keyword", keyword);
		return wishlistDAO.selectListMap(paraMap);
	}

	@Override
	public int delWishlist(String email, int number) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userEmail", email);
		paraMap.put("goodsNumber",number);
		return wishlistDAO.delete(paraMap);
	}

	@Override
	public boolean checkWishlist(String email, int number) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userEmail", email);
		paraMap.put("goodsNumber",number);
		WishlistVO wishlistVO = wishlistDAO.selectOne(paraMap);
		if(wishlistVO != null){
			return true;
		}
		return false;
	}

	@Override
	public List<OrdersVO> ordersList(int start, int end,String email) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("offset", start);
		paraMap.put("limit", end);
		paraMap.put("userEmail", email);
		return ordersDAO.selectListMap(paraMap);
	}

	@Override
	public int orderCount() throws Exception {
		// TODO Auto-generated method stub
		return ordersDAO.count();
	}
	
	@Override
	public int orderCount(String keyword) throws Exception {
		// TODO Auto-generated method stub
		return ordersDAO.count(keyword);
	}

	@Override
	public int delorderlist(String email, int no) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userEmail", email);
		paraMap.put("goodsNumber",no);
		return ordersDAO.delete(paraMap);
	}

}
