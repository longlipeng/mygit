package com.allinfinance.prepay.batch;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allinfinance.prepay.batch.service.BacthIssueCardService;
import com.allinfinance.prepay.batch.service.BacthIssueCardServiceImpl;
import com.allinfinance.prepay.dao.BatchInfoDAO;
import com.allinfinance.prepay.mapper.svc_mng.SaicOnlineBatchInfoMapper;
import com.allinfinance.prepay.model.SaicOnlineBatchInfo;
import com.allinfinance.prepay.model.SaicOnlineBatchInfoExample;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;


public class BatchIssueCard {
	private static Logger logger = Logger.getLogger(BatchIssueCard.class);
	@Autowired
	private SaicOnlineBatchInfoMapper saicOnlineBatchInfoMapper;
	@Autowired
	private BacthIssueCardService bacthIssueCardService;
	@Autowired
	private BatchInfoDAO batchInfoDAO;

	  public void batchIssueCardQuartz()  
      {  
		  logger.info("Quartz��������ȣ�����"+(new Date()).toString());
		  SaicOnlineBatchInfoExample ex=new SaicOnlineBatchInfoExample();
		  //BatchStat //00������ 01������ 02����ɹ� 03 ����ʧ��
		  ex.createCriteria().andDataStateEqualTo(DataBaseConstant.DEFAULT_FLAG_YES)
		  	.andBatchStatEqualTo("00");
		  List<SaicOnlineBatchInfo> list=saicOnlineBatchInfoMapper.selectByExample(ex);
		  if(list!=null&&list.size()!=0){
			  //�޸�����״̬������
			  batchInfoDAO.batchUpdateInfo("batchUpdateBatchStatWithList",list);
		  }
		  
		  SaicOnlineBatchInfo saicOnlineBatchInfo=new SaicOnlineBatchInfo();
		  for (SaicOnlineBatchInfo info :list) {
			  try {
				bacthIssueCardService.issueCard(info);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("batchId="+info.getBatchId()+",user_id="+info.getLeaguerId()+",error:"+e.getMessage());
				ex.clear();
				ex.createCriteria().andBatchIdEqualTo(info.getBatchId());
				saicOnlineBatchInfo.setBatchStat("03");
				saicOnlineBatchInfo.setUpdateTime(DateUtil.getCurrentTime());
				saicOnlineBatchInfoMapper.updateByExampleSelective(saicOnlineBatchInfo, ex);
			}
		}
		  
      }  
	  
	  
	  public static void main(String[] args)  
      {  
          System.out.println("Test start.");  
          ApplicationContext ctx=new ClassPathXmlApplicationContext("application-context.xml");  
          //��������ļ��н�startQuertz bean��lazy-init����Ϊfalse ����ʵ����  
//          batchIssueCard qj=(batchIssueCard)ctx.getBean("batchIssueCard");  
//          qj.work();  
          System.out.println("��������Ϣ��");  
          Scanner input=new Scanner(System.in);  
          int x=input.nextInt();  
          System.out.println(x);  
          System.out.print("Test end..");  
            

      }  

}
