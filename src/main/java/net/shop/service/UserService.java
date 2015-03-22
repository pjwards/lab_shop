package net.shop.service;

import java.util.List;

import net.shop.vo.UserVO;

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
