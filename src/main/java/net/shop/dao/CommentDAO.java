package net.shop.dao;

import net.shop.vo.CommentVO;

import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-25
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public interface CommentDAO {
    /*
    해당 게시글의 댓글 전체 개수를 구한다.
    */
    public int selectCount(int boardNumber) throws Exception;

    /*
   특정 카테고리의 해당 게시글의 댓글 전체 개수를 구한다.
    */
    public int selectCount(int boardNumber, String separatorName) throws Exception;

    /*
    댓글을 읽어온다.
     */
    public List<CommentVO> selectList(int boardNumber) throws Exception;

    /*
    특정 카테고리의 댓글을 읽어온다.
     */
    public List<CommentVO> selectList(int boardNumber, String separatorName) throws Exception;

    /*
    전체 댓글 중에서 시작 행(firstRow)과 끝 행(endRow)에 속하는 댓글을 읽어온다.
     */
    public List<CommentVO> selectList(int boardNumber, int firstRow, int endRow) throws Exception;

    /*
    특정 카테고리의 전체 댓글 중에서 시작 행(firstRow)과 끝 행(endRow)에 속하는 댓글을 읽어온다.
     */
    public List<CommentVO> selectList(int boardNumber, int firstRow, int endRow, String separatorName) throws Exception;

    /*
    댓글을 작성한다.
     */
    public int insert(CommentVO commentVO) throws Exception;

    /*
    댓글 수 증가시킨다.
     */
    public void increaseCommentCount(int boardNumber) throws Exception;

    /*
    특정한 ID 값을 사용해 해당 글을 조회한다.
     */
    public CommentVO selectOne(int commentNumber) throws Exception;

    /*
    마지막 순서 번호를 조회한다.
     */
    public String selectLastSequenceNumber(String searchMaxSeqNum, String searchMinSeqNum) throws Exception;

    /*
    글을 수정한다.
     */
    public int update(CommentVO commentVO) throws Exception;

    /*
    글을 삭제한다.
     */
    public int delete(int commentNumber) throws Exception;

    /*
    댓글 수 감소시킨다.
     */
    public void decreaseCommentCount(int commentNumber) throws Exception;

    /*
    그룹 번호를 조회한다.
     */
    public int selectGroupNumber(String groupName) throws Exception;

    /*
    그룹 번호의 크기를 하나 증가시킨다.
     */
    public int updateGroupNumber(String groupName) throws Exception;
}
