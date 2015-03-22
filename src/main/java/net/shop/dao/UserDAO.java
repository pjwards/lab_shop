package net.shop.dao;

import java.util.List;

import net.shop.vo.UserVO;

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
