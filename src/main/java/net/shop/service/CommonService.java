package net.shop.service;

import net.shop.vo.FileVO;

/*
First Editor : Jisung Jeon (cbajs20@gmail.com)
Last Editor  :
Date         : 2015-04-30
*/
public interface CommonService {

	/*
    Editor : Jisung Jeon
    Decription : return FileVO
    */
	public FileVO selectOneVo(int boardNumber) throws Exception;
	
	/*
    Editor : Jisung Jeon
    Decription : Insert the data in DB
    */
    public int insert(FileVO fileVO) throws Exception;
}
