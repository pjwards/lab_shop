package net.shop.service;

import java.util.List;

import net.shop.vo.OrdersVO;

/*
	First Editor : Jisung Jeon (cbajs20@gmail.com)
	Last Editor  :
	Date         : 2015-04-21
*/

public interface AdminService {
	/*
    Editor : Jisung Jeon
    Decription : Return lists that are searched and lined up between start and end
    */
    public List<OrdersVO> selectList(int start,int end,String keyword) throws Exception;

	/*
    Editor : Jisung Jeon
    Decription : Return the total counts of lists
    */
    public int count(String keyword) throws Exception;

}
