package net.shop.service;

import net.shop.dao.GoodsDAO;
import net.shop.vo.GoodsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
