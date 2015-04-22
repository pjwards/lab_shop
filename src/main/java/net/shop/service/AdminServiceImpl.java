package net.shop.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shop.dao.OrdersDAO;
import net.shop.vo.OrdersVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Resource(name="ordersDAO")
	private OrdersDAO ordersDAO;
	
	@Override
	public List<OrdersVO> selectList(int start, int end, String keyword)
			throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("offset", start);
		paraMap.put("limit", end);
		paraMap.put("keyword", keyword);
		return ordersDAO.selectListMap(paraMap);
	}

	@Override
	public int count() throws Exception {
		// TODO Auto-generated method stub
		return ordersDAO.count();
	}

}
