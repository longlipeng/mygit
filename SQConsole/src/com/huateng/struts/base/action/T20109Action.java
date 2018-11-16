package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.base.T20109BO;
import com.huateng.common.Constants;
import com.huateng.po.base.TblRspCodeMap;
import com.huateng.po.base.TblRspCodeMapPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T20109Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private T20109BO t20109BO = (T20109BO)ContextUtil.getBean("T20109BO");
	private String srcId;
    private String destId;
    private String srcRspCode;
    private Integer srcRspCodeLOld;
    private String destRspCode;
    private Integer destRspCodeLOld;
    private String rspCodeDspOld;
    private String statusId;
    private String parameterList;
	
    public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {
				rspCode = add();			
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "，应答码维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	/**新增应答码
	 * add rsp_code
	 * @return
	 */
	public String add() throws Exception{
		TblRspCodeMapPK key=new TblRspCodeMapPK();
		key.setDestId(getDestId());
		key.setDestRspCode(getDestRspCode());
		key.setSrcId(getSrcId());
		key.setSrcRspCode(getSrcRspCode());
		if(t20109BO.get(key)!= null) {
			  return "您所添加的应答码已经被使用";
		}
		TblRspCodeMap tblRspCodeMap = new TblRspCodeMap();
		tblRspCodeMap.setId(key);
		tblRspCodeMap.setSrcRspCodeL(getSrcRspCodeLOld());
		tblRspCodeMap.setDestRspCodeL(getDestRspCodeLOld());
		tblRspCodeMap.setRspCodeDsp(getRspCodeDspOld());
		
		tblRspCodeMap.setDestRspCodeLOld(String.valueOf(getDestRspCodeLOld()));
		tblRspCodeMap.setSrcRspCodeLOld(String.valueOf(getSrcRspCodeLOld()));
		tblRspCodeMap.setRspCodeDspOld(getRspCodeDspOld());
		//2012.07.03 add column
		tblRspCodeMap.setStatusId(ADD_TO_CHECK);//新增未审核
		tblRspCodeMap.setOprId(operator.getOprId());
		tblRspCodeMap.setOprTime(CommonFunction.getCurrentDateTime());
		
		t20109BO.add(tblRspCodeMap);
		return Constants.SUCCESS_CODE;
	}

	/**删除应答码
	 * delete rsp_code
	 * @return
	 */
	public String delete() {
		TblRspCodeMapPK key=new TblRspCodeMapPK();
		key.setDestId(getDestId());
		key.setDestRspCode(getDestRspCode());
		key.setSrcId(getSrcId());
		key.setSrcRspCode(getSrcRspCode());
		if(t20109BO.get(key)==null) {
			return "没有找到要删除的应答码信息";
		}
		if(DELETE.equals(t20109BO.get(key).getStatusId().trim())) {
			return "该应答码已是删除状态，请勿重复删除";
		}
		if(ADD_TO_CHECK.equals(t20109BO.get(key).getStatusId().trim())){
			t20109BO.delete(key);
		}else{
			TblRspCodeMap tblRspCodeMap = t20109BO.get(key);
			tblRspCodeMap.setOprTime(CommonFunction.getCurrentDateTime());
			tblRspCodeMap.setOprId(operator.getOprId());
			tblRspCodeMap.setStatusId(DELETE_TO_CHECK);
			t20109BO.update(tblRspCodeMap);
		}
		return Constants.SUCCESS_CODE;
	}

	/**更新应答码
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	*/
	public String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {		
	   jsonBean.parseJSONArrayData(getParameterList());
	   int len = jsonBean.getArray().size();
	   List<TblRspCodeMap> tblRspCodeMapList = new ArrayList<TblRspCodeMap>(len);	
			for(int i = 0; i < len; i++) {					   
			    jsonBean.setObject(jsonBean.getJSONDataAt(i));	
			    String destId=jsonBean.getJSONDataAt(i).getString("destId");
			    String destRspCode=jsonBean.getJSONDataAt(i).getString("destRspCode");
			    String srcId=jsonBean.getJSONDataAt(i).getString("srcId");
			    String srcRspCode=jsonBean.getJSONDataAt(i).getString("srcRspCode");
			    TblRspCodeMapPK key=new TblRspCodeMapPK();
				key.setDestId(destId);
				key.setDestRspCode(destRspCode);
				key.setSrcId(srcId);
				key.setSrcRspCode(srcRspCode);
				
			    TblRspCodeMap tblRspCodeMap = t20109BO.get(key);
				BeanUtils.setObjectWithPropertiesValue(tblRspCodeMap, jsonBean, false);
				//只有正常状态在修改的时候要改状态
				if(tblRspCodeMap.getStatusId().trim().equals(NORMAL)){
					tblRspCodeMap.setStatusId(MODIFY_TO_CHECK);
				}
				tblRspCodeMap.setOprId(operator.getOprId());
				tblRspCodeMap.setOprTime(CommonFunction.getCurrentDateTime());
				tblRspCodeMapList.add(tblRspCodeMap);
			}
		t20109BO.update(tblRspCodeMapList);
		return Constants.SUCCESS_CODE;
	}
	
	public String getParameterList() {
		return parameterList;
	}

	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getSrcRspCode() {
		return srcRspCode;
	}

	public void setSrcRspCode(String srcRspCode) {
		this.srcRspCode = srcRspCode;
	}

	public Integer getSrcRspCodeLOld() {
		return srcRspCodeLOld;
	}

	public void setSrcRspCodeLOld(Integer srcRspCodeLOld) {
		this.srcRspCodeLOld = srcRspCodeLOld;
	}

	public String getDestRspCode() {
		return destRspCode;
	}

	public void setDestRspCode(String destRspCode) {
		this.destRspCode = destRspCode;
	}

	public Integer getDestRspCodeLOld() {
		return destRspCodeLOld;
	}

	public void setDestRspCodeLOld(Integer destRspCodeLOld) {
		this.destRspCodeLOld = destRspCodeLOld;
	}

	public String getRspCodeDspOld() {
		return rspCodeDspOld;
	}

	public void setRspCodeDspOld(String rspCodeDspOld) {
		this.rspCodeDspOld = rspCodeDspOld;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public T20109BO getT20109BO() {
		return t20109BO;
	}

	public void setT20109BO(T20109BO t20109bo) {
		t20109BO = t20109bo;
	}
    
}
