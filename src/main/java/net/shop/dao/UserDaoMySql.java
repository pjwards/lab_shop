package net.shop.dao;

import net.shop.vo.UserVO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author jisung
 * 
 *
 */

@Repository("userDAO")
public class UserDaoMySql implements UserDAO {
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public UserVO selectOne() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.UserDao.selectOne");
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

    public int delete() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("net.UserDao.delete");
        }finally{
            sqlSession.close();
        }
    }
}
