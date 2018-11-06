package com.allinfinance.shangqi.gateway.dao;

import java.sql.SQLException;
import java.util.List;

import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.huateng.framework.ibatis.model.Cardholder;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.ibm.db2.jcc.am.SqlException;


public interface ApplyAndBindDao {
	/*查询在库卡的数量*/
	public int StockCardCountQuery(String statement,ApplyAndBindCardDTO stockCountDTO) throws SQLException;
	/*获取可用卡的卡号*/
	public List<EntityStock> getStockCards(String statement,ApplyAndBindCardDTO stockCountDTO)throws SQLException;
	/*查询持卡人信息*/
	public List<ApplyAndBindCardDTO> getCardHolderMsg(String statement,ApplyAndBindCardDTO stockCountDTO) throws SQLException;
	/*查看指定申请卡相关信息*/
	public ApplyAndBindCardDTO  getApplyAndBindCard(String statement,ApplyAndBindCardDTO stockCountDTO) throws SQLException;
	/*查看需审核的记录*/
	public List<ApplyAndBindCardDTO> getApplyAndBindCardDTOList(String statement,ApplyAndBindCardDTO stockCountDTO) throws SQLException;
	/*更新申请信息的审核状态*/
	public int updateApplyAndBindCardMsg(String statement,ApplyAndBindCardDTO stockCountDTO) throws SQLException;
	/*插入持卡人信息*/
	public Object insertCardHolderMsg(String statement,Cardholder stockCountDTO) throws SQLException;
	/*插入订单信息*/
	public Object insertSellOrderMsg(String statement,SellOrder stockCountDTO) throws SQLException;
	/*插入订单与持卡人关联信息*/
	public Object insertSellOrderCardList(String statement,SellOrderCardList stockCountDTO) throws SQLException;
	/*插入申请卡信息*/
	public Object insertApplyCard(String statement,ApplyAndBindCardDTO stockCountDTO) throws SQLException;
	/*插入订单流程表*/
	public int insertOrderFlow(String statement,ApplyAndBindCardDTO stockCountDTO) throws SQLException;
	/*更新申请卡信息*/
	public int update(String statement,ApplyAndBindCardDTO stockCountDTO)throws SQLException;
	/*获得关联ID*/
	public String getID(String statement,ApplyAndBindCardDTO stockCountDTO)throws SQLException;
	/*获得持卡人信息*/
	public CardholderDTO getCardholderDTO(String statement, CardholderDTO cardholderDTO)throws SQLException;
	/*获得发卡记录*/
	public int getAccCardholderInfoByCardholderId(String statement,CardholderDTO cardholderDTO) throws SQLException;
}
