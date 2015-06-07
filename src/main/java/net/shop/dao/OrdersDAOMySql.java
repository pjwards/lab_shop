package net.shop.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.shop.vo.OrdersVO;

@Repository("ordersDAO")
public class OrdersDAOMySql implements OrdersDAO {
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
	@Override
	public List<OrdersVO> selectListMap(HashMap<String, Object> paraMap)
			throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();
		  
	     try{
	         return sqlSession.selectList("net.OrdersDao.selectList", paraMap);
	     }finally{
	    	 sqlSession.close();
	     }
	}

	/*
	First Editor : Donghyun Seo (egaoneko@naver.com)
	Last Editor  :
	Date         : 2015-06-06
	*/
	@Override
	public List<OrdersVO> selectTotalListMap(HashMap<String, Object> map) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.selectList("net.OrdersDao.selectTotalList", map);
		}finally{
			sqlSession.close();
		}
	}

	@Override
	public int count() throws Exception{
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.OrdersDao.count");
        }finally{
            sqlSession.close();
        }
	}
	
	@Override
	public int count(String keyword) throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("net.OrdersDao.countByKeyword",keyword);
        }finally{
            sqlSession.close();
        }
	}

	/*
	First Editor : Donghyun Seo (egaoneko@naver.com)
	Last Editor  :
	Date         : 2015-06-06
	*/
	@Override
	public int countTotalList(HashMap<String,Object> map) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.selectOne("net.OrdersDao.countTotalList",map);
		}finally{
			sqlSession.close();
		}
	}

	@Override
	public int delete(int number) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.insert("net.OrdersDao.delete", number);
		}finally{
			sqlSession.close();
		}
	}

	@Override
	public int insert(OrdersVO ordersVO) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.insert("net.OrdersDao.insert", ordersVO);
		}finally{
			sqlSession.close();
		}
	}
}
