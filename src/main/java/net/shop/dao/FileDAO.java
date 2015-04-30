package net.shop.dao;

import net.shop.vo.FileVO;

/*
First Editor : Jisung Jeon (cbajs20@gmail.com)
Last Editor  :
Date         : 2015-04-30
*/
public interface FileDAO {

	public FileVO selectOne(int boardNumber) throws Exception;
	
	public int insert(FileVO fileVO) throws Exception;
}
