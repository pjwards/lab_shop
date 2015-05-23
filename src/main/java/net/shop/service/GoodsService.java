package net.shop.service;

import net.shop.vo.CartVO;
import net.shop.vo.GoodsVO;
import net.shop.vo.OrdersVO;

import java.util.List;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 4/16/15 | 10:59 AM
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

public interface GoodsService {

    /*
    유저에 대한 상품 전체 개수를 구한다.
     */
    public int selectCount(String memberId) throws Exception;

    /*
    특정한 ID 값을 사용해 해당 상품을 조회한다.
     */
    public GoodsVO selectOne(int goodsNumber) throws Exception;

    /*
    전체 상품 리스트를 읽어온다.
     */
    public List<GoodsVO> selectList(String memberId, int firstRow, int endRow) throws Exception;

    /*
    상품을 등록한다.
     */
    public int insert(GoodsVO goodsVO);

    /*
    게시글-상품 테이블에 정보를 등록한다.
     */
    public int insert(int boardNumber, int goodsNumber);

    /*
    상품에 대한 정보를 수정한다.
     */
    public int update(GoodsVO goodsVO);

    /*
    상품을 삭제한다.
     */
    public int delete(int goodsNumber);

    /*
    게시글에 상품에 대한 개수를 늘린다.
     */
    public void increaseGoodsCount(int boardNumber) throws Exception;

    /*
    게시글에 상품에 대한 개수를 줄인다.
     */
    public void decreaseGoodsCount(int boardNumber) throws Exception;
    
    /*
    Editor : Jisung Jeon
    Decription : insert data in orderlist
    */
	public int addorderlist(OrdersVO ordersVO) throws Exception;
	
	/*
    Editor : Jisung Jeon
    Decription : insert data in cart
    */
	public int addcartlist(CartVO cartVO) throws Exception;

    /*
    게시글에 포함된 상품 번호를 구한다.
     */
    public List<Integer> selectBoardGoodsByBoard(int boardNumber) throws Exception;

    /*
    상품이 포함된 게시글 번호를 구한다.
     */
    public List<Integer> selectBoardGoodsByGoods(int goodsNumber) throws Exception;

    /*
    게시글-상품 테이블에서 게시글을 삭제한다.
     */
    public int deleteBoardGoodsByBoard(int boardNumber) throws Exception;

    /*
    게시글-상품 테이블에서 상품을 삭제한다.
     */
    public int deleteBoardGoodsByGoods(int goodsNumber) throws Exception;
    
    /*
  		Delete cart of the number
     */
	public int cartDelete(int number);
	
	/*
		Find cart of the number
	 */
	public CartVO cartOne(int boardNumber, String email);
}
