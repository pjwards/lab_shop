package net.shop.service;

import java.util.List;

import net.shop.vo.UserVO;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

public interface UserService {

    /*
    Editor : Jisung Jeon
    Decription : check the account by email
    */
    public boolean selectOne(String email) throws Exception;
    
    /*
    Editor : Jisung Jeon
    Decription : return user_no
    */
    public int selectOneNo(String email) throws Exception;
    
    /*
    Editor : Jisung Jeon
    Decription : Return lists between start and end
    */
    public List<UserVO> selectList(int start,int end) throws Exception;

    /*
    Editor : Jisung Jeon
    Decription : Return the total counts of lists
    */
    public int count() throws Exception;

    /*
    Editor : Jisung Jeon
    Decription : Insert the data in DB
    */
    public int insert(UserVO userVO) throws Exception;

    /*
    Editor : Jisung Jeon
    Decription : Update the data in DB
    */
    public int update(UserVO userVO) throws Exception;

    /*
    Editor : Jisung Jeon
    Decription : Delete the date in DB
    */
    public int delete(String email) throws Exception;
    
    /*
    Editor : Jisung Jeon
    Decription : Update last_date when login
    */
    public int updateDate(String email) throws Exception;
}
