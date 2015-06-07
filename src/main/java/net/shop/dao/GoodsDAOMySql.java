package net.shop.dao;

import net.shop.vo.GoodsVO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 4/16/15 | 10:45 AM
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

@Repository("goodsDAO")
public class GoodsDAOMySql implements GoodsDAO {
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public int selectCount(String memberId) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.GoodsDao.selectCount", memberId);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectCount(HashMap<String, Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.GoodsDao.selectCountByKeyword", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public GoodsVO selectOne(int goodsNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.GoodsDao.selectOne", goodsNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<GoodsVO> selectList(String memberId, int firstRow, int endRow) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rowBounds", rowBounds);
        map.put("memberId", memberId);

        try{
            return sqlSession.selectList("net.GoodsDao.selectList", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<GoodsVO> selectList(HashMap<String, Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("net.GoodsDao.selectListByKeyword", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int insert(GoodsVO goodsVO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.insert("net.GoodsDao.insert", goodsVO);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int insert(int boardNumber, int goodsNumber) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardNumber", boardNumber);
        map.put("goodsNumber", goodsNumber);

        try{
            return sqlSession.insert("net.GoodsDao.insertBoardGoods", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int update(GoodsVO goodsVO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("net.GoodsDao.update", goodsVO);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int delete(int goodsNumber) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("net.GoodsDao.delete", goodsNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public void increaseGoodsCount(int boardNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("net.GoodsDao.increaseGoodsCount", boardNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public void decreaseGoodsCount(int boardNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("net.GoodsDao.decreaseGoodsCount", boardNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<Integer> selectBoardGoodsByBoard(int boardNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("net.GoodsDao.selectBoardGoodsByBoard", boardNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<Integer> selectBoardGoodsByGoods(int goodsNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("net.GoodsDao.selectBoardGoodsByGoods", goodsNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int deleteBoardGoodsByBoard(int boardNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("net.GoodsDao.deleteBoardGoodsByBoard", boardNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int deleteBoardGoodsByGoods(int goodsNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("net.GoodsDao.deleteBoardGoodsByGoods", goodsNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectCountForStock(HashMap<String, Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.GoodsDao.selectCountForStock", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<GoodsVO> selectListForStock(HashMap<String, Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("net.GoodsDao.selectListForStock", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public void decreaseGoodsStock(HashMap<String,Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("net.GoodsDao.decreaseStockCount", map);
        }finally{
            sqlSession.close();
        }
    }
}
