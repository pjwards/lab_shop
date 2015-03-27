package net.shop.dao;

import net.shop.vo.UserVO;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

@Repository("userDAO")
public class UserDAOMySql implements UserDAO {
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public UserVO selectOne(String email) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.UserDao.selectOne",email);
        }finally{
            sqlSession.close();
        }
    }

    public List<UserVO> selectList(int start, int end) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        RowBounds rowBounds = new RowBounds(start,end);
        try{
            return sqlSession.selectList("net.UserDao.selectList", rowBounds);
        }finally{
            sqlSession.close();
        }
    }

    public int count() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.UserDao.count");
        }finally{
            sqlSession.close();
        }
    }

    public int insert(UserVO userVO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.insert("net.UserDao.insert", userVO);
        }finally{
            sqlSession.close();
        }
    }

    public int update(UserVO userVO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("net.UserDao.update", userVO);
        }finally{
            sqlSession.close();
        }
    }

    public int delete(String email) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("net.UserDao.delete",email);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectUserNumberByEmail(String email) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.UserDao.selectUserNumberByEmail", email);
        }finally{
            sqlSession.close();
        }
    }
}
