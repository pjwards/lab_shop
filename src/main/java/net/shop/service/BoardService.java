package net.shop.service;

import net.shop.vo.BoardVO;

import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-20
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public interface BoardService {

    /*
    게시글의 전체 개수를 구한다.
     */
    public int selectCount() throws Exception;

    /*
    전체 게시글 중에서 시작 행(firstRow)과 끝 행(endRow)에 속하는 게시글을 읽어온다.
     */
    public List<BoardVO> selectList(int firstRow, int endRow) throws Exception;

    /*
    게시글을 작성한다.
     */
    public int insert(BoardVO boardVO) throws Exception;

    /*
    특정한 ID 값을 사용해 해당 글을 조회한다.
     */
    public BoardVO selectOne(int boardNumber) throws Exception;

    /*
    조회수를 증가시킨다.
     */
    public void increaseReadCount(int boardNumber) throws Exception;

    /*
    마지막 순서 번호를 조회한다.
     */
    public String selectLastSequenceNumber(String searchMaxSeqNum, String searchMinSeqNum) throws Exception;

    /*
    글을 수정한다.
     */
    public int update(BoardVO boardVO) throws Exception;

    /*
    글을 삭제한다.
     */
    public int delete(int boardNumber) throws Exception;
}
