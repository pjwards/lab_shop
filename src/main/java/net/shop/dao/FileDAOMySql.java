package net.shop.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.shop.vo.FileVO;

/*
First Editor : Jisung Jeon (cbajs20@gmail.com)
Last Editor  :
Date         : 2015-04-30
*/
@Repository("fileDAO")
public class FileDAOMySql implements FileDAO {
	SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
	@Override
	public FileVO selectOne(int boardNumber) throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.FileDao.selectOne",boardNumber);
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public int insert(FileVO fileVO) throws Exception {
		// TODO Auto-generated method stub
		 SqlSession sqlSession = sqlSessionFactory.openSession();

	     try{
	    	 return sqlSession.insert("net.FileDao.insert", fileVO);
	     }finally{
	    	 sqlSession.close();
	     }
	}

}
