package net.shop.dao;

import net.shop.vo.CommentVO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-25
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */

@Repository("commentDAO")
public class CommentDAOMySql implements CommentDAO {

    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public int selectCount(int boardNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.CommentDao.selectCount", boardNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectCount(int boardNumber, String separatorName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardNumber", boardNumber);
        map.put("separatorName", separatorName);

        try{
            return sqlSession.selectOne("net.CommentDao.selectCountBySeparator", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<CommentVO> selectList(int boardNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("net.CommentDao.selectList", boardNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<CommentVO> selectList(int boardNumber, String separatorName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardNumber", boardNumber);
        map.put("separatorName", separatorName);

        try{
            return sqlSession.selectList("net.CommentDao.selectListBySeparator", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<CommentVO> selectList(int boardNumber, int firstRow, int endRow) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardNumber", boardNumber);
        map.put("rowBounds", rowBounds);

        try{
            return sqlSession.selectList("net.CommentDao.selectListByRow", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<CommentVO> selectList(int boardNumber, int firstRow, int endRow, String separatorName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardNumber", boardNumber);
        map.put("rowBounds", rowBounds);
        map.put("separatorName", separatorName);

        try{
            return sqlSession.selectList("net.CommentDao.selectListByRowSeparator", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int insert(CommentVO commentVO) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.insert("net.CommentDao.insert", commentVO);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public void increaseCommentCount(int boardNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("net.CommentDao.increaseCommentCount", boardNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public CommentVO selectOne(int commentNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.CommentDao.selectOne", commentNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public String selectLastSequenceNumber(String searchMaxSeqNum, String searchMinSeqNum) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("searchMaxSeqNum", searchMaxSeqNum);
        map.put("searchMinSeqNum", searchMinSeqNum);

        try{
            return sqlSession.selectOne("net.CommentDao.selectLastSequenceNumber", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int update(CommentVO commentVO) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("net.CommentDao.update", commentVO);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public int delete(int commentNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("net.CommentDao.delete", commentNumber);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public void decreaseCommentCount(int commentNumber) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("net.CommentDao.decreaseCommentCount", commentNumber);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectGroupNumber(String groupName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.CommentDao.selectGroupNumber", groupName);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public int updateGroupNumber(String groupName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("net.CommentDao.updateGroupNumber", groupName);
        } finally{
            sqlSession.close();
        }
    }
}
