package net.common.dao;

import java.util.List;

import net.common.vo.ExamVO;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("mainDAO")
public class MainDAO {
	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public ExamVO selectOne() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			return sqlSession.selectOne("net.ExamDao.selectList");
		}finally{
			sqlSession.close();
		}
	}
}
