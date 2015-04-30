package net.shop.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shop.dao.FileDAO;
import net.shop.vo.FileVO;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Resource(name="fileDAO")
	private FileDAO fileDAO;
	
	@Override
	public FileVO selectOneVo(int boardNumber) throws Exception {
		// TODO Auto-generated method stub
		return fileDAO.selectOne(boardNumber);
	}

	@Override
	public int insert(FileVO fileVO) throws Exception {
		// TODO Auto-generated method stub
		return fileDAO.insert(fileVO);
	}

}
