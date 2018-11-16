package com.huateng.dao.impl.mchnt;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.huateng.dao._RootDAO;
import com.huateng.dao.iface.mchnt.ITblMchtUpLoadAddBatchDAO;
import com.huateng.po.TblTermInfTmp;

import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;

public class TblMchtUpLoadAddBatchDAO extends _RootDAO implements ITblMchtUpLoadAddBatchDAO{

	public Object upLoadAddBatch(final List<TblMchtBaseInfTmp> MchtBaseInfListAdd,
			final List<TblMchtSupp1Tmp> MchtSupp1ListAdd,
			final List<TblHisDiscAlgo2Tmp> HisDiscAlgo2ListAdd,
			final List<TblMchtSettleInfTmp> MchtSettleInfTmpListAdd) throws Exception {
		
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				for (int i = 0; i < MchtBaseInfListAdd.size(); i++) {
					Object bp1 = MchtBaseInfListAdd.get(i);
					Object bp2 = MchtSupp1ListAdd.get(i);
					Object bp3 = HisDiscAlgo2ListAdd.get(i);
//					Object bp4 = MCHTEvalInfListAdd.get(i);
					Object bp5 = MchtSettleInfTmpListAdd.get(i);
//					Object bp6 = TblWfInfList.get(i);
//					Object bp7 = TblWfHistoryList.get(i);
					session.save(bp1);
					session.save(bp2);
					session.save(bp3);
//					session.save(bp4);
					session.save(bp5);
//					session.save(bp6);
//					session.save(bp7);
					
					if (i % 10 == 0) { // 1000
						// 与JDBC批量设置相同
						// flush a batch of inserts and release memory:
						// 将本批插入的对象立即写入数据库并释放内存
						session.flush();
						session.clear();
					}
				}
				return MchtBaseInfListAdd.size();
			}
		});
	}
	
	
	
	public Object upLoadBFZBatch(final List<TblTermInfTmp> TblTermInfTmpList) throws Exception {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				for (int i = 0; i < TblTermInfTmpList.size(); i++) {
					Object bp1 = TblTermInfTmpList.get(i);
					session.save(bp1);
					if (i % 10 == 0) { // 1000
						// 与JDBC批量设置相同
						// flush a batch of inserts and release memory:
						// 将本批插入的对象立即写入数据库并释放内存
						session.flush();
						session.clear();
					}
				}
				return TblTermInfTmpList.size();
			}
		});
	}
	

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
