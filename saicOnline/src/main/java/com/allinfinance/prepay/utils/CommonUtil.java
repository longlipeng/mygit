package com.allinfinance.prepay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.mapper.svc_mng.AccCardInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityStockMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellerMapper;
import com.allinfinance.prepay.model.AccCardInfo;
import com.allinfinance.prepay.model.AccCardInfoExample;
import com.allinfinance.prepay.model.EntityStock;
import com.allinfinance.prepay.model.EntityStockExample;
import com.allinfinance.prepay.model.Seller;
import com.allinfinance.prepay.model.SellerExample;
import com.allinfinance.prepay.service.EntityStockService;
@Service
public class CommonUtil {
	@Autowired
	private AccCardInfoMapper accCardInfoMapper;
	@Autowired
	private EntityStockMapper entityStockMapper;
	@Autowired
	private SellerMapper sellerMapper;
	
	public void updateStockStat(String cardNo,String entityId){
		EntityStockExample ex = new EntityStockExample();
		ex.createCriteria()
				.andEntityIdEqualTo(entityId)
				.andCardNoEqualTo(cardNo);
		EntityStock enStock = new EntityStock();
		enStock.setStockState("1");
		entityStockMapper.updateByExampleSelective(enStock, ex);
	}
	public void updateAccCardInfo(String cardNo){
		AccCardInfo acc=new AccCardInfo();
		AccCardInfoExample ex =new AccCardInfoExample();
		ex.createCriteria().andCardNoEqualTo(cardNo);
		accCardInfoMapper.updateCardHolderByExample(acc, ex);
	}
	
	public String getDateFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String returnValue = null;
		try {
			returnValue = simpleDateFormat.format(date);
		} catch (Exception e) {

		}
		return returnValue;
	}

	public boolean checkIssueId(String id){
		SellerExample ex=new SellerExample();
		ex.createCriteria().andEntityIdEqualTo(id).andDataStateEqualTo("1").andSellerStateEqualTo("1");
		List<Seller> list=sellerMapper.selectByExample(ex);
		
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
		
	}

	
	public String checkData(String cardNo,String defaultEntityId) {
		String msg=null;
		// 报文里的卡号，机构号
		EntityStockExample entityStockEx = new EntityStockExample();
		entityStockEx.createCriteria().andCardNoEqualTo(cardNo)
				.andStockStateEqualTo("1").andDataStateEqualTo("1")
				.andFunctionRoleIdEqualTo("3").andEntityIdEqualTo(defaultEntityId);
		List<EntityStock> entityStock = entityStockMapper
				.selectByExample(entityStockEx);
		if (entityStock.size() == 0) {
			msg="营销库存中没有这张卡！";
			return msg;
		}

		return null;

	}
	

}
