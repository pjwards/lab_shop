package net.shop.service;

import net.shop.dao.BoardDAO;
import net.shop.vo.BoardVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-20
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */

@Service("boardService")
public class BoardServiceImpl implements BoardService {

    @Resource(name = "boardDAO")
    private BoardDAO boardDAO;

    @Override
    public int selectCount() throws Exception {
        return boardDAO.selectCount();
    }

    @Override
    public List<BoardVO> selectList(int firstRow, int endRow) throws Exception {
        return boardDAO.selectList(firstRow, endRow);
    }

    @Override
    public int insert(BoardVO boardVO) throws Exception {
        return boardDAO.insert(boardVO);
    }

    @Override
    public BoardVO selectOne(int boardNumber) throws Exception {
        return boardDAO.selectOne(boardNumber);
    }

    @Override
    public void increaseReadCount(int boardNumber) throws Exception {
        boardDAO.increaseReadCount(boardNumber);
    }

    @Override
    public String selectLastSequenceNumber(String searchMaxSeqNum, String searchMinSeqNum) throws Exception {
        return boardDAO.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
    }

    @Override
    public int update(BoardVO boardVO) throws Exception {
        return boardDAO.update(boardVO);
    }

    @Override
    public int delete(int boardNumber) throws Exception {
        return boardDAO.delete(boardNumber);
    }
}
