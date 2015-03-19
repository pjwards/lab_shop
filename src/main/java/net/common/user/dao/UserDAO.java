package net.common.user.dao;

import java.util.List;

import net.common.user.vo.UserVO;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAO {
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
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();
		RowBounds rowBounds = new RowBounds(start,end);
		try{
			return sqlSession.selectList("net.UserDao.selectList", rowBounds);
		}finally{
			sqlSession.close();
		}
	}

	public int count() {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			return sqlSession.selectOne("net.UserDao.count");
		}finally{
			sqlSession.close();
		}
	}
}
