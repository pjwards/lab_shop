package net.shop.dao;

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

public interface UserDAO {

	public UserVO selectOne() throws Exception;

	public List<UserVO> selectList(int start, int end);

	public int count();

	public int insert(UserVO userVO);

	public int update(UserVO userVO);

	public int delete();

    /*
        작성자 : Donghyun Seo
        설명 : 이메일을 통한 유저 번호 조회
     */
    public int selectUserNumberByEmail(String email) throws  Exception;
}
