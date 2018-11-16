package com.huateng.common.Thread;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.huateng.system.util.CommonFunction;

public class MonitorTask extends java.util.TimerTask {
	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		long now = System.currentTimeMillis();
		try {
			String monitorTime = parseDateTime(now);
			int period = Integer.parseInt(getPeriod());//单位为分钟
			String startTime = parseDateTime(now - period*60*1000);//此处时间参数period可通过系统参数表来调整
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>联机交易监控开始：" +startTime+"～"+ monitorTime);
			
			int warningLine = Integer.parseInt(getWarningLine());//百分比
			
			Map<String,String> sum = this.getSum(startTime, monitorTime);
			Map<String,String> failure = this.getFailure(startTime, monitorTime);
			Map<String,String> refuse = this.getRefuse(startTime, monitorTime);
			Map<String,String> timeout = this.getTimeout(startTime, monitorTime);
			
			if(sum!=null && sum.size()==0){//交易总数为空的情况
				System.out.println(startTime+"～"+ monitorTime+"统计周期内没有交易");
			}
			
			Iterator<String> iterator = sum.keySet().iterator();
			while(iterator.hasNext()) {//遍历交易总数的查询结果集
				String next = iterator.next().toString();
				int transSum = Integer.parseInt(sum.get(next));
				
				Iterator iterator1 = failure.keySet().iterator();
				while(iterator1.hasNext()){
					String next1 = iterator1.next().toString();
					int failureCount = Integer.parseInt(failure.get(next1).toString());
					
					System.out.println("联机交易监控[" + startTime + "～"+ monitorTime+"]机构:" + next1 +
							"，共" + transSum+"笔，失败" + failureCount + "笔，失败交易比率为" + failureCount*100/transSum + "%");
					//若机构的失败交易比率高于警界线，则保存数据到表tbl_online_trans_monitor中
					if(next.equals(next1) &&  (failureCount*100/transSum) >= warningLine){//20121023修改
						storeMonitor(monitorTime, failureCount, transSum,"0",next1);
						System.out.println("保存联机失败交易比率成功");
					}else
						System.out.println("联机交易监控["+startTime+"～"+ monitorTime+"]机构失败交易比率正常");
				}
				
				Iterator iterator2 = refuse.keySet().iterator();
				while(iterator2.hasNext()){
					String next2 = iterator2.next().toString();
					int refuseCount = Integer.parseInt(refuse.get(next2));
					
					System.out.println("联机交易监控[" + startTime + "～"+ monitorTime+"]机构:" + next2 +
							"，共" + transSum+"笔，拒绝" + refuseCount + "笔，拒绝交易比率为" + refuseCount*100/transSum + "%");
					//若机构的拒绝交易比率高于警界线，则保存数据到表tbl_online_trans_monitor中
					if(next.equals(next2) &&  (refuseCount*100/transSum) >= warningLine){//20121023修改
						storeMonitor(monitorTime, refuseCount, transSum,"1",next2);
						System.out.println("保存联机拒绝交易比率成功");
					}else
						System.out.println("联机交易监控["+startTime+"～"+ monitorTime+"]机构拒绝交易比率正常");
				}
				
				Iterator iterator3 = timeout.keySet().iterator();
				while(iterator3.hasNext()){
					String next3 = iterator3.next().toString();
					int timeoutCount = Integer.parseInt(timeout.get(next3).toString());
					
					System.out.println("联机交易监控[" + startTime + "～"+ monitorTime+"]机构:" + next3 +
							"，共" + transSum+"笔，超时" + timeoutCount + "笔，超时交易比率为" + timeoutCount*100/transSum + "%");
					//若机构的超时交易比率高于警界线，则保存数据到表tbl_online_trans_monitor中
					if(next.equals(next3) &&  (timeoutCount*100/transSum) >= warningLine){//20121023修改
						storeMonitor(monitorTime, timeoutCount, transSum,"2",next3);
						System.out.println("保存联机超时交易比率成功");
					}else
						System.out.println("联机交易监控["+startTime+"～"+ monitorTime+"]机构超时交易比率正常");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args){
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new MonitorTask(), 1000, 60000);//在0秒后执行此任务,每次间隔2秒,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
//        while(true){//这个是用来停止此任务的,否则就一直循环执行此任务了
//            try {
//                int ch = System.in.read();
//                if(ch-'c'==0){
//                    timer.cancel();//退出任务
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
	
	//获取统计周期
	@SuppressWarnings("unchecked")
	public String getPeriod() throws SQLException{
		String sql = "select KEY from CST_SYS_PARAM where OWNER='TIMEINTERVAL' ";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(list == null)
			return null;
		else
			return (String)list.get(0);
	}
	
	//获取统计警界线(比例值)
	@SuppressWarnings("unchecked")
	public String getWarningLine() throws SQLException{
		String sql = "select KEY from CST_SYS_PARAM where OWNER='PROPORTION' ";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(list == null)
			return null;
		else
			return (String)list.get(0);
	}
	
	//获取统计时间段内的交易总数
	@SuppressWarnings("unchecked")
	public Map getSum( String time1,String time2) throws SQLException{
		Map map = new HashMap();
		
		String sql = "select trim(rcvg_code),count(RESP_CODE) from TBL_N_TXN where INST_DATE>='" +time1+ 
				"' and INST_DATE<'" +time2+ "' group By rcvg_code";
		
		List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(list == null)
			return null;
		else{
			try{
				for(int i=0; i<list.size();i++){
					map.put(list.get(i)[0].toString(), list.get(i)[1].toString());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return  map;
	}
	
	//获取统计时间段内的交易失败数
	@SuppressWarnings("unchecked")
	public Map getFailure( String time1,String time2) throws SQLException{
		Map map = new HashMap();
		
		String sql = "select trim(rcvg_code),count(RESP_CODE) from TBL_N_TXN where INST_DATE>='" +time1+ "' and INST_DATE<'" +time2+ 
				"' and RESP_CODE in('01','02','05','06','09','12','19','20','21','23','25','30','39','40','42','44','52','53'," +
				"'56','57','59','60','62','63','64','66','90','91','92','93','94','95','96') group By rcvg_code";
		
		List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(list == null)
			return null;
		else{
			try{
				for(int i=0; i<list.size();i++){
					map.put(list.get(i)[0].toString(), list.get(i)[1].toString());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return  map;
	}
	
	//获取统计时间段内的交易拒绝数
	@SuppressWarnings("unchecked")
	public Map getRefuse( String time1, String time2) throws SQLException{
		Map map = new HashMap();
		
		String sql = "select trim(rcvg_code),count(RESP_CODE) from TBL_N_TXN where INST_DATE>='" +time1+ "' and INST_DATE<'"+time2+
				"' and RESP_CODE in('03','04','07','13','14','15','22','31','33','34','35','36','37','38','41','43','51','54','55'," +
				"'58','61','65','67','75','77','79','97','99','A0')  group By rcvg_code";
		
		List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(list == null)
			return null;
		else{
			try{
				for(int i=0; i<list.size();i++){
					map.put(list.get(i)[0].toString(), list.get(i)[1].toString());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return map;
	}
	
	//获取统计时间段内的交易超时数
	@SuppressWarnings("unchecked")
	public Map getTimeout( String time1, String time2) throws SQLException{
		Map map = new HashMap();
		String sql = "select trim(rcvg_code),count(RESP_CODE) from TBL_N_TXN where INST_DATE>='" +time1+ "' and INST_DATE<='"+time2+
				"' and RESP_CODE in('68','98')  group By rcvg_code";
		
		List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(list == null)
			return null;
		else{
			try{
				for(int i=0; i<list.size();i++){
					map.put(list.get(i)[0].toString(), list.get(i)[1].toString());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return map;
	}
	
	//将超过警界值的联机交易信息插入表tbl_online_trans_monitor
	public void storeMonitor( String time, int failure,int sum,String transType,String instId) throws SQLException{
		String sql = "insert into tbl_online_trans_monitor(MONITOR_TIME,FAILURE_COUNT,SUM_COUNT,TRANS_TYPE,INST_ID) values('"+
			time +"','"+ failure +"','"+ sum +"','"+ transType +"','"+ instId +"')";
		
		CommonFunction.getCommQueryDAO().excute(sql);
	}
	
	private String parseDateTime(long time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date(time);
		return sdf.format(dt);
	}

}
