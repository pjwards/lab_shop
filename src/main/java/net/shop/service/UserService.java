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
	public UserVO selectOne() throws Exception;

	public List<UserVO> selectList(int start,int end) throws Exception;

	public int count() throws Exception;

	public int insert(UserVO userVO) throws Exception;

	public int update(UserVO userVO) throws Exception;

	public int delete() throws Exception;

    /*
        작성자 : Donghyun Seo
        설명 : 이메일을 통한 유저 번호 조회
     */
    public int selectUserNumberByEmail(String email) throws  Exception;
}
