package com.huateng.univer.issuer.productWithAccount.Package;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

public class PackageAction extends BaseAction {
	private Logger logger = Logger.getLogger(PackageAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1837698783709852876L;

	private PackageDTO packageDTO = new PackageDTO();

	private PackageQueryDTO packageQueryDTO = new PackageQueryDTO();

	private PageDataDTO pageDataDTO = new PageDataDTO();

	private List<String> choose = new ArrayList<String>();

	private int totalRows = 0;

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public PackageDTO getPackageDTO() {
		return packageDTO;
	}

	public void setPackageDTO(PackageDTO packageDTO) {
		this.packageDTO = packageDTO;
	}

	public PackageQueryDTO getPackageQueryDTO() {
		return packageQueryDTO;
	}

	public void setPackageQueryDTO(PackageQueryDTO packageQueryDTO) {
		this.packageQueryDTO = packageQueryDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public List<String> getChoose() {
		return choose;
	}

	public void setChoose(List<String> choose) {
		this.choose = choose;
	}

	/**
	 * 包装查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		try {
			ListPageInit(null, packageQueryDTO);
			if (packageQueryDTO.isQueryAll()) {
				packageQueryDTO.setQueryAll(false);
				packageQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
			}
			pageDataDTO = (PageDataDTO) sendService(ConstCode.PACKAGE_SERVICE_INQUERY, packageQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	/**
	 * 转向添加包装
	 * @return
	 * @throws Exception
	 */
	public String redirectAdd()throws Exception{
		packageDTO=new PackageDTO();
		if(this.hasErrors()){
			return "input";
		}
		return "add";
	}
    /**
     * 添加包装
     * @return
     * @throws Exception
     */
	public String insert()throws Exception{
		packageDTO.setCreateUser(getUser().getUserId());
		//packageDTO.setPackageFee((new BigDecimal(packageDTO.getPackageFee()).multiply(new BigDecimal("100"))).toString());
		sendService(ConstCode.PACKAGE_SERVICE_INSERT, packageDTO);
		if(this.hasErrors()){
			return "input";
		}else{
			addActionMessage("包装信息添加成功！");
		}
		return query();
	}
	
	/**
	 * 转向编辑页面
	 * @return
	 * @throws Exception
	 */
	public String load()throws Exception{
		String id=choose.get(0);
		packageDTO=new PackageDTO();
		packageDTO.setPackageId(id);
		packageDTO=(PackageDTO)sendService(ConstCode.PACKAGE_EDIT_SERVICE_VIEW, packageDTO).getDetailvo();
		if(this.hasErrors()){
			
			return query();
		
	}
		if(packageDTO.getPackageFee()!=null){
			packageDTO.setPackageFee((new BigDecimal(packageDTO.getPackageFee())).divide(new BigDecimal("100")).toString());
		}
		return "edit";
	}
	/**
	 * 更新包装
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception{
		packageDTO.setModifyUser(getUser().getUserId());
		//packageDTO.setPackageFee((new BigDecimal(packageDTO.getPackageFee()).multiply(new BigDecimal("100"))).toString());
		sendService(ConstCode.PACKAGE_SERVICE_UPDATE, packageDTO);
		if(this.hasErrors()){
			return "input";
		}else{
			addActionMessage("包装修改成功！");
		}
		return query();
	}
     
   /**
    * 删除包装
    * @return
    * @throws Exception
    */
	public String delete()throws Exception{
		List<PackageDTO> packageDTOs=new ArrayList<PackageDTO>();
		for(String id:choose){
			packageDTO=new PackageDTO();
			packageDTO.setPackageId(id);
			packageDTOs.add(packageDTO);
		}
		packageDTO=new PackageDTO();
		packageDTO.setPackageDTOs(packageDTOs);
		sendService(ConstCode.PACKAGE_SERVICE_DELETE,packageDTO);
		if(this.hasErrors()){
			return query();
		}else{
			addActionMessage("包装删除成功！");
		}
		return query();
	}
	/**
	 * 查看包装信息
	 * @return
	 * @throws Exception
	 */
	public String view()throws Exception{
		String id=getRequest().getParameter("id");
		packageDTO=new PackageDTO();
		packageDTO.setPackageId(id);
		packageDTO=(PackageDTO)sendService(ConstCode.PACKAGE_SERVICE_VIEW, packageDTO).getDetailvo();
		if(this.hasErrors()){
			return INPUT;
		}
		return "view";
	}
	
	
}
