package net.shop.service;

import net.shop.dao.BoardDAO;
import net.shop.vo.BoardVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-20
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
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
    public int selectCount(String separatorName) throws Exception {
        return boardDAO.selectCount(separatorName);
    }

    @Override
    public int selectCount(String separatorName, String keyword) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("separatorName", separatorName);
        map.put("keyword", keyword);

        return boardDAO.selectCount(map);
    }

    @Override
    public List<BoardVO> selectList(int firstRow, int endRow) throws Exception {
        return boardDAO.selectList(firstRow, endRow);
    }

    @Override
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName) throws Exception {
        return boardDAO.selectList(firstRow, endRow, separatorName);
    }

    @Override
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName, String keyword) throws Exception {

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);


        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("separatorName", separatorName);
        map.put("rowBounds", rowBounds);
        map.put("keyword", keyword);
        return boardDAO.selectList(map);
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

    @Override
    public int generateNextGroupNumber(String groupName) throws Exception {
        boardDAO.updateGroupNumber(groupName);
        return boardDAO.selectGroupNumber(groupName);
    }

    @Override
    public int selectLastBoardNumberByEmail(String userEmail) throws Exception {
        return boardDAO.selectLastBoardNumberByEmail(userEmail);
    }

    @Override
    public int setGoodsCountZero(int boardNumber) throws Exception {
        return boardDAO.setGoodsCountZero(boardNumber);
    }
}
