package net.shop.service;

import net.shop.vo.BoardVO;

import java.util.List;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-20
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

public interface BoardService {

    /*
    게시글의 전체 개수를 구한다.
     */
    public int selectCount() throws Exception;

    /*
    특정 카테고리의 게시글 전체 개수를 구한다.
     */
    public int selectCount(String separatorName) throws Exception;

    /*
    HashMap 을 사용하여 게시글 전체 개수를 구한다.
     */
    public int selectCount(String separatorName, String keyword) throws Exception;

    /*
    전체 게시글 중에서 시작 행(firstRow)과 끝 행(endRow)에 속하는 게시글을 읽어온다.
     */
    public List<BoardVO> selectList(int firstRow, int endRow) throws Exception;

    /*
    특정 카테고리의 전체 게시글 중에서 시작 행(firstRow)과 끝 행(endRow)에 속하는 게시글을 읽어온다.
     */
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName) throws Exception;

    /*
    HashMap 을 사용하여 게시글을 읽어온다.
     */
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName, String keyword) throws Exception;

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

    /*
    다음 그룹 번호를 생성한다.
     */
    public int generateNextGroupNumber(String groupName) throws Exception;

    /*
    작성한 글 번호 조회한다.
     */
    public int selectLastBoardNumberByEmail(String userEmail) throws Exception;

    /*
    상품 개수를 초기화 한다.
     */
    public int setGoodsCountZero(int boardNumber) throws Exception;
}
