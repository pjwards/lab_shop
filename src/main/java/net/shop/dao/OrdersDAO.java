package net.shop.dao;

import java.util.HashMap;
import java.util.List;

import net.shop.vo.OrdersVO;

/*
First Editor : Jisung Jeon (cbajs20@gmail.com)
Last Editor  :
Date         : 2015-04-21
*/

public interface OrdersDAO {
	
	public List<OrdersVO> selectListMap(HashMap<String,Object> paraMap) throws Exception;

	public int count()throws Exception;
	
	public int count(String keyword) throws Exception;

	public int delete(int number);
	
	public int insert(OrdersVO ordersVO);

}
