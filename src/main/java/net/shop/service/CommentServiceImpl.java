package net.shop.service;

import net.shop.dao.CommentDAO;
import net.shop.vo.CommentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-25
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource(name = "commentDAO")
    private CommentDAO commentDAO;

    @Override
    public int selectCount(int boardNumber) throws Exception {
        return commentDAO.selectCount(boardNumber);
    }

    @Override
    public int selectCount(int boardNumber, String separatorName) throws Exception {
        return commentDAO.selectCount(boardNumber, separatorName);
    }

    @Override
    public List<CommentVO> selectList(int boardNumber) throws Exception {
        return commentDAO.selectList(boardNumber);
    }

    @Override
    public List<CommentVO> selectList(int boardNumber, String separatorName) throws Exception {
        return commentDAO.selectList(boardNumber, separatorName);
    }

    @Override
    public List<CommentVO> selectList(int boardNumber, int firstRow, int endRow) throws Exception {
        return commentDAO.selectList(boardNumber, firstRow, endRow);
    }

    @Override
    public List<CommentVO> selectList(int boardNumber, int firstRow, int endRow, String separatorName) throws Exception {
        return commentDAO.selectList(boardNumber, firstRow, endRow, separatorName);
    }

    @Override
    public int insert(CommentVO commentVO) throws Exception {
        return commentDAO.insert(commentVO);
    }

    @Override
    public void increaseCommentCount(int boardNumber) throws Exception {
        commentDAO.increaseCommentCount(boardNumber);
    }

    @Override
    public CommentVO selectOne(int commentNumber) throws Exception {
        return commentDAO.selectOne(commentNumber);
    }

    @Override
    public String selectLastSequenceNumber(String searchMaxSeqNum, String searchMinSeqNum) throws Exception {
        return commentDAO.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
    }

    @Override
    public int update(CommentVO commentVO) throws Exception {
        return commentDAO.update(commentVO);
    }

    @Override
    public int delete(int commentNumber) throws Exception {
        return commentDAO.delete(commentNumber);
    }

    @Override
    public void decreaseCommentCount(int commentNumber) throws Exception {
        commentDAO.decreaseCommentCount(commentNumber);
    }

    @Override
    public int selectGroupNumber(String groupName) throws Exception {
        return commentDAO.selectGroupNumber(groupName);
    }

    @Override
    public int updateGroupNumber(String groupName) throws Exception {
        return commentDAO.updateGroupNumber(groupName);
    }

    @Override
    public int generateNextGroupNumber(String groupName) throws Exception {
        commentDAO.updateGroupNumber(groupName);
        return commentDAO.selectGroupNumber(groupName);
    }
}
