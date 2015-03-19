package net.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.common.dao.MainDAO;
import net.common.vo.ExamVO;

@Service("mainService")
public class MainServiceImpl implements MainService {

	@Resource(name="mainDAO")
	private MainDAO mainDAO;
	
	@Override
	public ExamVO selectOne() throws Exception {
		// TODO Auto-generated method stub
		return mainDAO.selectOne();
	}

}
