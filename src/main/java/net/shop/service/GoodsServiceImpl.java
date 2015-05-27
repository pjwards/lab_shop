package net.shop.service;

import net.shop.dao.CartDAO;
import net.shop.dao.GoodsDAO;
import net.shop.dao.OrdersDAO;
import net.shop.vo.CartVO;
import net.shop.vo.GoodsVO;
import net.shop.vo.OrdersVO;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 4/16/15 | 10:59 AM
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Resource(name = "goodsDAO")
    private GoodsDAO goodsDAO;
    
    @Resource(name = "ordersDAO")
    private OrdersDAO ordersDAO;
    
    @Resource(name = "cartDAO")
    private CartDAO cartDAO;

    @Override
    public int selectCount(String memberId) throws Exception {
        return goodsDAO.selectCount(memberId);
    }

    @Override
    public GoodsVO selectOne(int goodsNumber) throws Exception {
        return goodsDAO.selectOne(goodsNumber);
    }

    @Override
    public List<GoodsVO> selectList(String memberId, int firstRow, int endRow) throws Exception {
        return goodsDAO.selectList(memberId, firstRow, endRow);
    }

    @Override
    public int insert(GoodsVO goodsVO) {
        return goodsDAO.insert(goodsVO);
    }

    @Override
    public int insert(int boardNumber, int goodsNumber) {
        return goodsDAO.insert(boardNumber, goodsNumber);
    }

    @Override
    public int update(GoodsVO goodsVO) {
        return goodsDAO.update(goodsVO);
    }

    @Override
    public int delete(int goodsNumber) {
        return goodsDAO.delete(goodsNumber);
    }

    @Override
    public void increaseGoodsCount(int boardNumber) throws Exception {
        goodsDAO.increaseGoodsCount(boardNumber);
    }

    @Override
    public void decreaseGoodsCount(int boardNumber) throws Exception {
        goodsDAO.decreaseGoodsCount(boardNumber);
    }
    
    @Override
	public int addorderlist(OrdersVO ordersVO) throws Exception {
		return ordersDAO.insert(ordersVO);
	}

    @Override
    public List<Integer> selectBoardGoodsByBoard(int boardNumber) throws Exception {
        return goodsDAO.selectBoardGoodsByBoard(boardNumber);
    }

    @Override
    public List<Integer> selectBoardGoodsByGoods(int goodsNumber) throws Exception {
        return goodsDAO.selectBoardGoodsByGoods(goodsNumber);
    }

    @Override
    public int deleteBoardGoodsByBoard(int boardNumber) throws Exception {
        return goodsDAO.deleteBoardGoodsByBoard(boardNumber);
    }

    @Override
    public int deleteBoardGoodsByGoods(int goodsNumber) throws Exception {
        return goodsDAO.deleteBoardGoodsByGoods(goodsNumber);
    }

	@Override
	public int addcartlist(CartVO cartVO) throws Exception {
		// TODO Auto-generated method stub
		return cartDAO.insert(cartVO);
	}

	@Override
	public int cartDelete(int number) {
		// TODO Auto-generated method stub
		return cartDAO.delete(number);
	}

	@Override
	public CartVO cartOne(int boardNumber, String email) {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("boardNumber", boardNumber);
		paraMap.put("userEmail", email);
		return cartDAO.selectOne(paraMap);
	}

	@Override
	public int cartDelete(int boardNumber, String email) {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("boardNumber", boardNumber);
		paraMap.put("userEmail", email);
		return cartDAO.deleteMap(paraMap);
	}

	@Override
	public int cartChange(int quantity, int cartNumber) {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("number", cartNumber);
		paraMap.put("quantity", quantity);
		return cartDAO.changeMap(paraMap);
	}
}
