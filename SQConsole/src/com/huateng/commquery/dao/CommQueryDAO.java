/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-8       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.commquery.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.huateng.po.CstSysParam;
import com.huateng.po.TxnModel;
import com.huateng.po.TxnModelOnly;
import com.huateng.po.base.AgencyFeeLub;
import com.huateng.po.base.AgencyFeeLubTmp;
import com.huateng.po.base.AgencyInfo;
import com.huateng.po.base.AgencyInfoPK;
import com.huateng.po.base.TblAuitStatus;
import com.huateng.po.base.TblCityCodeCode;
import com.huateng.po.base.TblTradeCode;
import com.huateng.po.mchnt.TblHisDiscAlgo1;
import com.huateng.po.mchnt.TblHisDiscAlgo1Tmp;
import com.huateng.po.risk.RiskBefore;

/**
 * Title:通用查询
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class CommQueryDAO extends HibernateDaoSupport implements ICommQueryDAO {

	public void excute(final String sql) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.getTransaction().begin();
				Query query = session.createSQLQuery(sql);
				int executeColum = query.executeUpdate();
				session.getTransaction().commit();
				return executeColum;
			}
		});
	}

	public List find(String query) {
		return getHibernateTemplate().find(query);
	}


	public List findByHQLQuery(final String hql, final int begin, final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public List findByHQLQuery(final String hql) {
		List data = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
		return data;
	}

	public List findByNamedQuery(String name) {
		return getHibernateTemplate().findByNamedQuery(name);
	}

	public List findByNamedQuery(final String name, final int begin, final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(name);
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public List findByNamedQuery(String name, Map params) {
		return findByNamedQuery(name, params, -1, -1);
	}

	public List findByNamedQuery(final String name, final Map params, final int begin, final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(name);
				if (null != params) {
					for (Iterator i = params.entrySet().iterator(); i.hasNext();) {
						Map.Entry entry = (Map.Entry) i.next();
						query.setParameter((String) entry.getKey(), entry
								.getValue());
					}
				}
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public List findByNamedQuery(String name, Serializable[] params) {
		return findByNamedQuery(name, params, -1, -1);
	}

	public List findByNamedQuery(final String name, final Serializable[] params, final int begin,
			final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(name);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public List findBySQLQuery(final String sql, final int begin, final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}
	public List<TxnModel> findBySQLQueryModel(final String sql, final int begin, final int count) {
			
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
				.addScalar("TERM_ID", Hibernate.STRING)
	    		.addScalar("INST_DATE", Hibernate.STRING)
	    		.addScalar("TXN_TYPE", Hibernate.STRING)
	    		.addScalar("CUP_SSN", Hibernate.STRING)
	    		.addScalar("REFE_NUM", Hibernate.STRING)
	    		.addScalar("ORDER_TRSNUM", Hibernate.STRING)
	    		.addScalar("TXN_STATE", Hibernate.STRING)
	    		.addScalar("PAN", Hibernate.STRING)
	    		.addScalar("TRS_AMOUNT", Hibernate.STRING)
	    		.addScalar("IPS_MERCHT", Hibernate.STRING)
	    		
	    		.setResultTransformer(Transformers.aliasToBean(TxnModel.class));
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				List<TxnModel> list = query.list();
				
				return list;
			}
		});
	}
	
	public List<TxnModelOnly> findBySQLQueryModelAll(final String sql) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
	    		.addScalar("CARD_ACCP_ID", Hibernate.STRING)
	    		.addScalar("TERM_ID", Hibernate.STRING)
	    		.addScalar("INST_DATE", Hibernate.STRING)
	    		.addScalar("UPDT_DATE", Hibernate.STRING)
	    		.addScalar("TXN_TYPE", Hibernate.STRING)
	    		.addScalar("CUP_SSN", Hibernate.STRING)
	    		.addScalar("SYS_SEQ_NUM", Hibernate.STRING)
	    		.addScalar("TERM_SSN", Hibernate.STRING)
	    		.addScalar("REFE_NUM", Hibernate.STRING)
	    		.addScalar("ORDER_TRSNUM", Hibernate.STRING)
	    		.addScalar("TXN_STATE", Hibernate.STRING)
	    		.addScalar("REVSAL_FLAG", Hibernate.STRING)
	    		.addScalar("REVSAL_SSN", Hibernate.STRING)
	    		.addScalar("CANCEL_FLAG", Hibernate.STRING)
	    		.addScalar("CANCEL_SSN", Hibernate.STRING)
	    		.addScalar("PAN", Hibernate.STRING)
	    		.addScalar("TRS_AMOUNT", Hibernate.STRING)
	    		.addScalar("DATE_SETTLE", Hibernate.STRING)
	    		.addScalar("AUTHR_ID_RESP", Hibernate.STRING)
	    		.addScalar("ISS_CODE", Hibernate.STRING)
	    		.addScalar("TLR_NUM", Hibernate.STRING)
	    		
	    		.setResultTransformer(Transformers.aliasToBean(TxnModelOnly.class));
		
				List<TxnModelOnly> list = query.list();
				
				return list;
			}
		});
	}
	            
	public List findBySQLQuery(final String sql) {
		List data = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				
				return query.list();
			}
		});
		return data;
	}
	//机构信息
	public List<AgencyInfo> findBySQLQuery1(final String sql) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
				.addScalar("AGEN_ID", Hibernate.STRING)
				.addScalar("AGEN_NAME", Hibernate.STRING)
				.addScalar("AGEN_TYPE", Hibernate.STRING)
				.addScalar("TRAN_TYPE", Hibernate.STRING)
				.addScalar("CARD_HOME", Hibernate.STRING)
				.addScalar("AGEN_REG_BODY", Hibernate.STRING)
				.addScalar("AGEN_MECH_CAL_TYPE", Hibernate.STRING)
				.addScalar("AGEN_CAL_TYPE", Hibernate.STRING)
				.addScalar("AGEN_CAL_PRIN_CYCLE", Hibernate.STRING)
				.addScalar("AGEN_CAL_PRIN_MODE", Hibernate.STRING)
				.addScalar("AGEN_CAL_HAND_CYCLE", Hibernate.STRING)
				.addScalar("AGEN_CAL_HAND_MODE", Hibernate.STRING)
				.addScalar("AGEN_CAL_LUB_CYCLE", Hibernate.STRING)
				.addScalar("AGEN_CAL_LUB_MODE", Hibernate.STRING)
				.addScalar("AGEN_BRAND_RATIO", Hibernate.STRING)
				.addScalar("AGEN_MIS_RATIO", Hibernate.STRING)
				.addScalar("AGEN_BANK_NUM", Hibernate.STRING)
				.addScalar("AGEN_ENTRY_MODE", Hibernate.STRING)
				.addScalar("BANK_NAME", Hibernate.STRING)
				.addScalar("AGEN_INCOME_ACCOUNT_NAME", Hibernate.STRING)
				.addScalar("AGEN_INCOME_ACCOUNT", Hibernate.STRING)
				.addScalar("AGEN_EXPENSES_ACCOUNT_NAME", Hibernate.STRING)
				.addScalar("AGEN_EXPENSES_ACCOUNT", Hibernate.STRING)
				.addScalar("AGEN_SETTLEMENT_DATE", Hibernate.STRING)
				.addScalar("AGEN_CLEAR_DETAIL", Hibernate.STRING)
				.addScalar("AGEN_PAYMENT_SYSTEM", Hibernate.STRING)
				.addScalar("EXTENSION_FIELD1",Hibernate.STRING)
				.addScalar("EXTENSION_FIELD2",Hibernate.STRING)
				.addScalar("FEE_FLAG",Hibernate.STRING)
				.addScalar("FEE_VALUE",Hibernate.DOUBLE)
				.addScalar("MIN_FEE",Hibernate.DOUBLE)
				.addScalar("MAX_FEE",Hibernate.DOUBLE)
				.addScalar("MIN_TRADE",Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(AgencyInfo.class));
				
				List<AgencyInfo> list = query.list();
				return list;
			}
		});
	}
	
	//机构信息 主键查询
		public List<AgencyInfoPK> findBySQLQuery1PK(final String sql) {
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createSQLQuery(sql)
					.addScalar("AGEN_ID", Hibernate.STRING)
					.addScalar("TRAN_TYPE", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(AgencyInfoPK.class));
					
					List<AgencyInfoPK> list = query.list();
					return list;
				}
			});
		}
	//机构分润
	public List<AgencyFeeLubTmp> findBySQLQuery4(final String sql) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
				.addScalar("FEE_ID", Hibernate.STRING)
				.addScalar("AGEN_ID", Hibernate.STRING)
				.addScalar("AGEN_TYPE", Hibernate.STRING)
				.addScalar("TERM_ID", Hibernate.STRING)
				.addScalar("MTCH_NO", Hibernate.STRING)
				.addScalar("MCC_CODE", Hibernate.STRING)
				.addScalar("TRADE_ACCEPT_REG", Hibernate.STRING)
				.addScalar("AGEN_TARGET_BODY", Hibernate.STRING)
				.addScalar("AGEN_CRE_CARD", Hibernate.STRING)
				.addScalar("CARD_STYLE", Hibernate.STRING)
				.addScalar("CARD_MEDIUM", Hibernate.STRING)
				.addScalar("TRADE_CHANNEL", Hibernate.STRING)
				.addScalar("BUSINESS_TYPE", Hibernate.STRING)
				.addScalar("TRAN_TYPE", Hibernate.STRING)
				.addScalar("RES", Hibernate.STRING)
				.addScalar("MCHT_RATE_TYPE", Hibernate.STRING)
				.addScalar("MCHT_RATE_METHOD", Hibernate.STRING)
				.addScalar("AMOUNT_LIMIT", Hibernate.STRING)
				.addScalar("MCHT_RATE_PARAM", Hibernate.STRING)
				.addScalar("MCHT_PERCENT_LIMIT", Hibernate.STRING)
				.addScalar("MCHT_PERCENT_MAX", Hibernate.STRING)
				.addScalar("MCHT_LUB_TYPE", Hibernate.STRING)
				.addScalar("MCHT_LUB_METHOD", Hibernate.STRING)
				.addScalar("MCHT_LUB_PARAM", Hibernate.STRING)
				.addScalar("MCHT_LUB_PERCENT_LIMIT", Hibernate.STRING)
				.addScalar("MCHT_LUB_PERCENT_MAX", Hibernate.STRING)
				.addScalar("RATE_PARAM1",Hibernate.STRING)
				.addScalar("LUB_PARAM1",Hibernate.STRING)
				.addScalar("EXTEN_FIELD1",Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(AgencyFeeLubTmp.class));
				
				List<AgencyFeeLubTmp> list = query.list();
				return list;
			}
		});
	}
	public List<TblTradeCode> findBySQLQuery5(final String sql) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
				.addScalar("TRADE_CODE", Hibernate.STRING)
				.addScalar("TRADE_NAME", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(TblTradeCode.class));
				List<AgencyFeeLub> list = query.list();
				return list;
			}
		});
	}
	public List<TblAuitStatus> findBySQLQuery2(final String sql) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
				.addScalar("STATUE_ID", Hibernate.STRING)
				.addScalar("STATUE_NAME", Hibernate.STRING)
				.addScalar("TYPE", Hibernate.STRING)
				.addScalar("ID", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(TblAuitStatus.class));		
				List<AgencyInfo> list = query.list();
				return list;
			}
		});
	}
	public List<TblCityCodeCode> findBySQLQuery3(final String sql) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
				.addScalar("CITY_CODE", Hibernate.STRING)
				.addScalar("CITY_DES", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(TblCityCodeCode.class));		
				List<AgencyInfo> list = query.list();
				return list;
			}
		});
	}
	public List<CstSysParam> findBySQLQuery6(final String sql) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
				.addScalar("type", Hibernate.STRING)
				.addScalar("value", Hibernate.STRING)
				.addScalar("descr", Hibernate.STRING)
				.addScalar("reserve", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(CstSysParam.class));		
				List<CstSysParam> list = query.list();
				return list;
			}
		});
	}
	public List<RiskBefore> findBySQLQuery7(final String sql) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
				.addScalar("MCHT_NM", Hibernate.STRING)
				.addScalar("LICENSE_NO",Hibernate.STRING)
				.addScalar("ORG_CODE",Hibernate.STRING)
				.addScalar("IDENTITY",Hibernate.STRING)
				.addScalar("SCORE", Hibernate.STRING)
				.addScalar("GRADE", Hibernate.STRING)
				.addScalar("MCHT_TYPE", Hibernate.STRING)
				.addScalar("MAIN_BUS_NUM", Hibernate.STRING)
				.addScalar("RISK_INDUSTRY", Hibernate.STRING)
				.addScalar("REG_FUND", Hibernate.STRING)
				.addScalar("PREMISES", Hibernate.STRING)
				.addScalar("PARAM1", Hibernate.STRING)
				.addScalar("PARAM2", Hibernate.STRING)
				.addScalar("PARAM3", Hibernate.STRING)
				.addScalar("PARAM4", Hibernate.STRING)
				.addScalar("PARAM5", Hibernate.STRING)
				.addScalar("PARAM6", Hibernate.STRING)
				.addScalar("PARAM7", Hibernate.STRING)
				.addScalar("PARAM8", Hibernate.STRING)
				.addScalar("PARAM9", Hibernate.STRING)
				.addScalar("PARAM10", Hibernate.STRING)
				.addScalar("PARAM11", Hibernate.STRING)
				.addScalar("PARAM12", Hibernate.STRING)
				.addScalar("PARAM13", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(RiskBefore.class));		
				List<RiskBefore> list = query.list();
				return list;
			}
		});
	}
	public List<TblHisDiscAlgo1Tmp> findBySQLQuery8(final String sql) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql)
				.addScalar("DISC_ID", Hibernate.STRING)
				.addScalar("TERM_ID", Hibernate.STRING)
				.addScalar("MCHT_NO", Hibernate.STRING)
				.addScalar("CITY_CODE", Hibernate.STRING)
				.addScalar("TO_BRCH_NO", Hibernate.STRING)
				.addScalar("FK_BRCH_NO", Hibernate.STRING)
				.addScalar("CARD_TYPE", Hibernate.STRING)
				.addScalar("CHANNEL_NO", Hibernate.STRING)
				.addScalar("BUSINESS_TYPE", Hibernate.STRING)
				.addScalar("TXN_NUM", Hibernate.STRING)
				.addScalar("MIN_FEE", Hibernate.DOUBLE)
				.addScalar("MAX_FEE", Hibernate.DOUBLE)
				.addScalar("FLOOR_AMOUNT", Hibernate.DOUBLE)
				.addScalar("FLAG", Hibernate.STRING)
				.addScalar("FEE_VALUE", Hibernate.DOUBLE)
				.addScalar("REC_UPD_USR_ID", Hibernate.STRING)
				.addScalar("REC_UPD_TS", Hibernate.STRING)
				.addScalar("REC_CRT_TS", Hibernate.STRING)
				.addScalar("SA_SATUTE", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(TblHisDiscAlgo1Tmp.class));		
				List<TblHisDiscAlgo1> list = query.list();
				return list;
			}
		});
	}
	public List findBySQLQuery(final String sql, final Map map) {
		List data = getHibernateTemplate().executeFind(
	            new HibernateCallback() { 
	                public Object doInHibernate(Session session) 
	                    throws HibernateException, SQLException { 
	                    Query query = session.createSQLQuery(sql); 
	                    Iterator iter = map.keySet().iterator();
	                    while(iter.hasNext()){
	                    	String key = iter.next().toString();
	                    	Object obj = map.get(key);
	                    	String[] keys = query.getNamedParameters();
	                    	for(int i=0;i<keys.length;i++){
	                    		if(key!=null&&key.equals(keys[i])){
	                    			if(obj instanceof String){
	    	                    		query.setString(key, obj.toString());
	    	                    	}
	    	                    	if(obj instanceof Number){
	    	                    		query.setInteger(key, Integer.parseInt(obj.toString()));
	    	                    	}
	    	                    	if(obj instanceof BigDecimal){
	    	                    		query.setBigDecimal(key, (BigDecimal) obj);
	    	                    	}
	    	                    	if(obj instanceof List){
	    	                    		query.setParameterList(key,(List)obj);
	    	                    	}
	                    		}
	                    	}
	                    }
	                    return query.list(); 
	                } 
	            }
	    );
		return data;
	}

	public List findBySQLQuery(final String sql, final int begin, final int count, final Map map) {
		return getHibernateTemplate().executeFind(
	            new HibernateCallback() { 
	                public Object doInHibernate(Session session) 
	                    throws HibernateException, SQLException { 
	                    Query query = session.createSQLQuery(sql); 
	                    Iterator iter = map.keySet().iterator();
	                    while(iter.hasNext()){
	                    	String key = iter.next().toString();
	                    	Object obj = map.get(key);
	                    	String[] keys = query.getNamedParameters();
	                    	for(int i=0;i<keys.length;i++){
	                    		if(key!=null&&key.equals(keys[i])){
	                    			if(obj instanceof String){
	    	                    		query.setString(key, obj.toString());
	    	                    	}
	    	                    	if(obj instanceof Number){
	    	                    		query.setInteger(key, Integer.parseInt(obj.toString()));
	    	                    	}
	    	                    	if(obj instanceof BigDecimal){
	    	                    		query.setBigDecimal(key, (BigDecimal) obj);
	    	                    	}
	    	                    	if(obj instanceof List){
	    	                    		query.setParameterList(key,(List)obj);
	    	                    	}
	                    		}
	                    	}
	                    }
	                    if( begin >= 0 ) {
	                        query.setFirstResult(begin); 
	                        query.setMaxResults(count); 
	                    }
	                    return query.list(); 
	                } 
	            }
	        );
	}

	public String findCountBySQLQuery(final String countSql) {
		List data = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(countSql);
				return query.list();
			}
		});
		if(data==null||data.size()==0||data.get(0)==null)return "";
		return data.get(0).toString();
	}

	public String findCountBySQLQuery(final String countSql, final Map map) {
		List data = getHibernateTemplate().executeFind(
	            new HibernateCallback() { 
	                public Object doInHibernate(Session session) 
	                    throws HibernateException, SQLException { 
	                    Query query = session.createSQLQuery(countSql); 
	                    Iterator iter = map.keySet().iterator();
	                    while(iter.hasNext()){
	                    	String key = iter.next().toString();
	                    	Object obj = map.get(key);
	                    	String[] keys = query.getNamedParameters();
	                    	for(int i=0;i<keys.length;i++){
	                    		if(key!=null&&key.equals(keys[i])){
	                    			if(obj instanceof String){
	    	                    		query.setString(key, obj.toString());
	    	                    	}
	    	                    	if(obj instanceof Number){
	    	                    		query.setInteger(key, Integer.parseInt(obj.toString()));
	    	                    	}
	    	                    	if(obj instanceof BigDecimal){
	    	                    		query.setBigDecimal(key, (BigDecimal) obj);
	    	                    	}
	    	                    	if(obj instanceof List){
	    	                    		query.setParameterList(key,(List)obj);
	    	                    	}
	                    		}
	                    	}
	                    }
	                    return query.list(); 
	                } 
	            }
	    );
    	return data.get(0).toString();
	}

	public void flush() {
		getHibernateTemplate().flush();
	}
}
