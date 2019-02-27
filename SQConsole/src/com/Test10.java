package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Test10 {

	public static void main(String[] args){
		
		Connection conn = null;
		Statement state = null;
//		PreparedStatement state = null;
		
		try {
			//动态导入数据库的驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//获取数据库链接
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.131.19.192:1521:cxf", "posp", "posp");
			//开启事务
	        //false: 设置关闭自动提交   true: 设置开启自动提交
			conn.setAutoCommit(false);
			
			String sql1 = "insert into TBL_MCHT_SETTLE_RESERVE_TMP(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, RESERVE_AUDIT_NAME, RESERVE_BATCH) VALUES ('1000005','20190221','21','0.21','0.32',null,null,null,null,null,null,null,null)";
			String sql2 = "insert into TBL_MCHT_SETTLE_RESERVE_TMP(RESERVE_ID, RESERVE_TIME, REDEMPTION_MONEY, RESERVE_SETTLE_MONEY, RESERVE_MONEY, RESERVE_STATUS, RESERVE_SETTLE_STATUS, RESERVE_PAY_STATUS, RESERVE_LAUNCH_TIME, RESERVE_LAUNCH_NAME, RESERVE_AUDIT_TIME, RESERVE_AUDIT_NAME, RESERVE_BATCH) VALUES ('1000006','20190221','45','0.14','0.54',null,null,null,null,null,null,null,null,null)";
			
			//执行SQL语句
			state = conn.createStatement();
			//需要构造带有占位符的sql 占位符位为问号(?)
//			state = conn.prepareStatement(sql1);
			//索引从1开始,左边索引右边值,根据sql不同的字段类型使用setInt或setString方法
//			state.setInt(1, 21);
//			state.setString(2, "123");
			//执行修改数据库的操作
			state.executeUpdate(sql1);
			System.out.println("新增成功!!!");
			state.executeUpdate(sql2);
			System.out.println("新增成功!!!");
			//事务提交
			conn.commit();
			System.out.println("事务提交成功!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("出现错误!!!");
			try {
				//事务回滚
				//撤销上面对事务的所有操作
				conn.rollback();
				System.out.println("事务回滚成功!!!");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				//关闭Statement
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//关闭Connection
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
