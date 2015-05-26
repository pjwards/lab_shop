package net.shop.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.shop.vo.CartVO;

@Repository("cartDAO")
public class CartDAOMySql implements CartDAO {
	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
	
	@Override
	public int insert(CartVO cartVO) throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.insert("net.CartDao.insert", cartVO);
		}finally{
			sqlSession.close();
		}
	}

	@Override
	public List<CartVO> selectList(String email) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();
		  
	     try{
	         return sqlSession.selectList("net.CartDao.selectList", email);
	     }finally{
	    	 sqlSession.close();
	     }
	}

	@Override
	public int delete(int number) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("net.CartDao.delete", number);
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public CartVO selectOne(HashMap<String, Object> paraMap) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.CartDao.selectOne",paraMap);
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public int deleteMap(HashMap<String, Object> paraMap) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("net.CartDao.deleteMap",paraMap);
        }finally{
            sqlSession.close();
        }
	}

}
