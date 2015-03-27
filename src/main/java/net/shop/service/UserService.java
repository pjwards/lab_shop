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
    Decription : Return list that has email and password of this parameters
    */
    public boolean selectOne(String email) throws Exception;

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
    Editor : Donghyun Seo
    Decription : 이메일을 통한 유저 번호 조회
    */
    public int selectUserNumberByEmail(String email) throws  Exception;
}
