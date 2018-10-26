package com.allinfinance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.mapper.TbDictInfoMapper;
import com.allinfinance.model.TbDictInfoExample;
import com.allinfinance.service.TestService;
@Service
public class TestServiceImpl implements TestService {
@Autowired
TbDictInfoMapper TbDictInfoMapper;
	@Override
	public int countByExample(TbDictInfoExample example) {
		return TbDictInfoMapper.countByExample(example);
	}

}
