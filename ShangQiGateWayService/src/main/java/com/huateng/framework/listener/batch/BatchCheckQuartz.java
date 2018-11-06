package com.huateng.framework.listener.batch;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.BatchSumDAO;
import com.huateng.framework.ibatis.model.BatchSum;
import com.huateng.framework.ibatis.model.BatchSumExample;

/**
 * 后台轮询线程 该类实现了ApplicationContextAware接口，在spring加载的时候，会将context注入
 * 
 * @author flypal
 * 
 */
public class BatchCheckQuartz implements ApplicationContextAware {
	private static final Logger log = Logger.getLogger(BatchCheckQuartz.class);

	private DataSourceTransactionManager transactionManager;
	private BatchSumDAO batchSumDao;
	// private RegistService<BatchListener> registServices;
	private static ConcurrentHashMap<String, BatchListener> events = new ConcurrentHashMap<String, BatchListener>();
	private static ApplicationContext context;

	// public RegistService<BatchListener> getRegistServices() {
	// return registServices;
	// }
	//
	// public void setRegistServices(RegistService<BatchListener>
	// registServices) {
	// this.registServices = registServices;
	// }

	public BatchSumDAO getBatchSumDao() {
		return batchSumDao;
	}

	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setBatchSumDao(BatchSumDAO batchSumDao) {
		this.batchSumDao = batchSumDao;
	}

	/**
	 * 增加任务
	 * @param batchNo
	 * @param listener
	 */
	public void addEvent(String batchNo, BatchListener listener) {
		if (listener == null) {
			log.error("batchNo :" +  batchNo + "listener is null");
			return;
		}
		events.putIfAbsent(batchNo, listener);
	}

	/**
	 * 根据批次号和批次类型增加任务 这个功能可以完成在极端异常情况下(例如：后台刚刚将批次状态同步为03，这时候应用down掉)
	 */
	public void addSingleTask(String batchNo, String batchType) throws BizServiceException {
		Map<String, BatchListener> registServices = getRegistServices();
		addEvent(batchNo, registServices.get(batchType));
	}

	/**
	 * 轮询处理函数
	 */
	public void process() {

		for (String key : events.keySet()) {
			BatchListener listener = events.get(key);
			log.info("start doing batchNo: " + key);
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = transactionManager.getTransaction(def);
			try {
				if (listener.checkBatchState(key)) {
					events.remove(key);
					log.info("finished batchNo:" + key);
				}
				transactionManager.commit(status);
			} catch (Exception ex) {
				log.error(ex.getMessage());
				transactionManager.rollback(status);
			}
		}

	}

	/**
	 * javabean 初始化的时候，会读取spring中所有BatchListener的bean，并且将这些bean与
	 * 数据库中尚未完成的batch任务配对； 然后放到events中进行维护。 这样可以防止如果web应用down掉之后，任务丢失的情况。
	 * 
	 * @throws BizServiceException
	 */
	public void init() throws BizServiceException {
		/**
		 * 1.读取ioc中配置的batchservice
		 */
		Map<String, BatchListener> registServices = getRegistServices();

		/**
		 * 2.读取数据库里所有的未完成的batch
		 */
		BatchSumExample queryVo = new BatchSumExample();
		// 将所有未完成的任务取出来
		queryVo.createCriteria().andBatchStateNotEqualTo(
				BatchFileDTO.BATCH_STATE_END);
		List<BatchSum> list = batchSumDao.selectByExample(queryVo);
		if (list == null || list.size() == 0) {
			return;
		}
		for (BatchSum vo : list) {
			if (registServices.get(vo.getBatchType()) == null) {
				log.error("batch type not register! " + vo.getBatchType());
				continue;
			}
			addEvent(vo.getBatchNo(), registServices.get(vo.getBatchType()));
			log.info("insert batchNo:" + vo.getBatchNo() + "batchType:"
					+ vo.getBatchType());
		}

	}

	/**
	 * 获取注册的批量服务
	 * @return
	 * @throws BizServiceException
	 */
	private Map<String, BatchListener> getRegistServices() throws BizServiceException {
		Map map = context.getBeansOfType(BatchListener.class);
		Map<String, BatchListener> registServices = new HashMap<String, BatchListener>();
		Collection values = map.values();
		for (Object service : values) {
			registService(registServices,
					((BatchListener) service).getBatchType(),
					(BatchListener) service);
		}
		return registServices;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		if (context == null) {
			context = applicationContext;
		}
	}

	private void registService(Map<String, BatchListener> map,
			String batchType, BatchListener service) throws BizServiceException {
		BatchListener temp = map.get(batchType);
		if (temp != null) {
			log.error("service conficted ! batchType:" + batchType);
			throw new BizServiceException("service conflicted!");
		}
		map.put(batchType, service);
	}

}
