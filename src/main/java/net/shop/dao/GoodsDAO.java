package net.shop.dao;

import net.shop.vo.GoodsVO;

import java.util.List;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 4/16/15 | 10:44 AM
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

public interface GoodsDAO {

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
}
