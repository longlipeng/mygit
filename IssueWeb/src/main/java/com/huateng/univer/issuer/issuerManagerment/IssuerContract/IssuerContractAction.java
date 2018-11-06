package com.huateng.univer.issuer.issuerManagerment.IssuerContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyAcctypeContractDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractQueryDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyProdContractDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.seller.seller.ContractBaseAction;

/**
 * 
 * @author fengfeng.shi
 *
 */
public class IssuerContractAction extends ContractBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LoyaltyContractDTO layaltyContractDTO=new LoyaltyContractDTO();
	
	private LoyaltyContractQueryDTO loyaltyContractQueryDTO=new LoyaltyContractQueryDTO();
	
	private LoyaltyProdContractDTO loyaltyProdContractDTO=new LoyaltyProdContractDTO();
	
	private ProductDTO productDTO = new ProductDTO();
	
	private LoyaltyAcctypeContractDTO loyaltyAcctypeContractDTO = new LoyaltyAcctypeContractDTO();
	
	private List<String> issuerContractIds =new ArrayList<String>();
	
	private List<LoyaltyProdContractDTO> loyaltyProductContractDTOs = new ArrayList<LoyaltyProdContractDTO>();
	

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public LoyaltyProdContractDTO getLoyaltyProdContractDTO() {
		return loyaltyProdContractDTO;
	}

	public void setLoyaltyProdContractDTO(
			LoyaltyProdContractDTO loyaltyProdContractDTO) {
		this.loyaltyProdContractDTO = loyaltyProdContractDTO;
	}

	public LoyaltyAcctypeContractDTO getLoyaltyAcctypeContractDTO() {
		return loyaltyAcctypeContractDTO;
	}

	public void setLoyaltyAcctypeContractDTO(
			LoyaltyAcctypeContractDTO loyaltyAcctypeContractDTO) {
		this.loyaltyAcctypeContractDTO = loyaltyAcctypeContractDTO;
	}

	public List<LoyaltyProdContractDTO> getLoyaltyProductContractDTOs() {
		return loyaltyProductContractDTOs;
	}

	public void setLoyaltyProductContractDTOs(
			List<LoyaltyProdContractDTO> loyaltyProductContractDTOs) {
		this.loyaltyProductContractDTOs = loyaltyProductContractDTOs;
	}

	public List<String> getIssuerContractIds() {
		return issuerContractIds;
	}

	public void setIssuerContractIds(List<String> issuerContractIds) {
		this.issuerContractIds = issuerContractIds;
	}

	public LoyaltyContractDTO getLayaltyContractDTO() {
		return layaltyContractDTO;
	}

	public void setLayaltyContractDTO(LoyaltyContractDTO layaltyContractDTO) {
		this.layaltyContractDTO = layaltyContractDTO;
	}

	public LoyaltyContractQueryDTO getLoyaltyContractQueryDTO() {
		return loyaltyContractQueryDTO;
	}

	public void setLoyaltyContractQueryDTO(
			LoyaltyContractQueryDTO loyaltyContractQueryDTO) {
		this.loyaltyContractQueryDTO = loyaltyContractQueryDTO;
	}

	@Override
	protected void inqueryInit() {}

	@Override
	protected void insertInit() {}
	protected void initNameSpace(){}
	/**
	 * 查询发行机构合同
	 * @return
	 * @throws Exception
	 */
	public String queryIssuerContract()throws Exception{
		ListPageInit(null, loyaltyContractQueryDTO);
		pageDataDTO = (PageDataDTO) sendService(ConstCode.ISSUER_CONTRACT_QUERY, loyaltyContractQueryDTO).getDetailvo();
		if (null != pageDataDTO && pageDataDTO.getTotalRecord() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		if (hasErrors()) {
			return INPUT;
		}
        
		return "list";
	}
	/**
	 * 转向发行机构合同添加页面
	 * @return
	 * @throws Exception
	 */
	public String toIssuerConstractAdd()throws Exception{
		if (hasErrors()) {
			return INPUT;
		}
		return "readd";
	}
	/**
	 * 添加发行机构合同
	 * @return
	 * @throws Exception
	 */
	
	public String issuerContractAdd()throws Exception{
		
		layaltyContractDTO = (LoyaltyContractDTO) this.sendService(
				ConstCode.ISSUER_CONTRACT_INSERT, layaltyContractDTO).getDetailvo();
		if (hasActionErrors()) {
			return INPUT;
		}
		this.addActionMessage("添加合同成功！");
		
      return issuerConstractEdit();
	}
	
	
	/**
	 *编辑发行机构合同
	 * @return
	 * @throws Exception
	 */
	public String issuerConstractEdit()throws Exception{
		
		if(issuerContractIds!=null&&issuerContractIds.size()>0){
			String issuerContractId=issuerContractIds.get(0);
			layaltyContractDTO.setLoyaltyContractId(issuerContractId);
		}else{
			layaltyContractDTO.setLoyaltyContractId(layaltyContractDTO.getLoyaltyContractId());
		}
		
		layaltyContractDTO = (LoyaltyContractDTO) sendService(ConstCode.ISSUER_CONTRACT_EDIT, layaltyContractDTO).getDetailvo();

		if (null != layaltyContractDTO) {
			loyaltyProductContractDTOs = layaltyContractDTO.getLoyaltyProdContractDTO();
		}

		if (hasActionErrors()) {
			return INPUT;
		}
		return "edit";
	}
	
	/**
	 * 查看发行机构合同
	 * @return
	 * @throws Exception
	 */
	public String constractView()throws Exception {
		layaltyContractDTO.setLoyaltyContractId(layaltyContractDTO.getLoyaltyContractId());
		layaltyContractDTO = (LoyaltyContractDTO) sendService(ConstCode.ISSUER_CONTRACT_EDIT, layaltyContractDTO).getDetailvo();

		if (null != layaltyContractDTO) {
			if (null != layaltyContractDTO.getExpiryDate()) {
				DateFormat df = new SimpleDateFormat("yyyyMMdd");
				layaltyContractDTO.setExpiryDateDate(df.parse(layaltyContractDTO.getExpiryDate()));
			}
			loyaltyProductContractDTOs = layaltyContractDTO.getLoyaltyProdContractDTO();
		}

		
		if (hasActionErrors()) {
			return INPUT;
		}
		return "view";
		
	}
	
	
	
	/**
	 * 转向添加产品
	 * @return
	 * @throws Exception
	 */
	public String productAdd()throws Exception{
		return "add";
	}
	
	
	
	
	/**
	 * 查询产品以及产品关联的服务
	 */
	public String issuerProductView() throws Exception {
		productDTO = (ProductDTO) sendService(ConstCode.PRODUCT_SERVICE_VIEW,productDTO).getDetailvo();
		// 加载产品服务信息
		loyaltyProdContractDTO.setProductId(productDTO.getProductId());
		loyaltyProdContractDTO.setProductDTO(productDTO);
		productServices = productDTO.getServices();
		return "add";
	}
	

	
	/**
	 * 添加产品账户明细
	 * @return
	 * @throws Exception
	 */
	public String productAndServiceAdd()throws Exception{
		// 添加合同服务明细
		if (null != serviceIdList && serviceIdList.length > 0) {
			List<LoyaltyAcctypeContractDTO> accDTOs = new ArrayList<LoyaltyAcctypeContractDTO>();
			for (int i = 0; i < serviceIdList.length; i++) {
				LoyaltyAcctypeContractDTO accDTO = new LoyaltyAcctypeContractDTO();
				accDTO.setLoyaltyContractId(layaltyContractDTO.getLoyaltyContractId());
				accDTO.setProductId(loyaltyProdContractDTO.getProductId());
				accDTO.setAcctypeId(serviceIdList[i]);
				accDTO.setRuleNo(ruleNoList[i]);
				// 交易类型暂定为0000
				accDTO.setTxnNum("0000");
				accDTOs.add(accDTO);
			}
			loyaltyProdContractDTO.setAccDTOs(accDTOs);
		}
		loyaltyProdContractDTO.setLoyaltyContractId(layaltyContractDTO.getLoyaltyContractId());

		this.sendService(ConstCode.ISSUER_CONTRACT_PRODUCTANDSERVICE_INSERT,loyaltyProdContractDTO);
		if (this.hasActionErrors()) {
			return INPUT;
		}
		this.addActionMessage("添加合同明细成功！");
		return "success";
	}
	
	
	public String issuerConstractView()throws Exception{
		layaltyContractDTO.setLoyaltyContractId(layaltyContractDTO.getLoyaltyContractId());
		layaltyContractDTO = (LoyaltyContractDTO) sendService(ConstCode.ISSUER_CONTRACT_EDIT, layaltyContractDTO).getDetailvo();

		if (null != layaltyContractDTO) {
			if (null != layaltyContractDTO.getExpiryDate()) {
				DateFormat df = new SimpleDateFormat("yyyyMMdd");
				layaltyContractDTO.setExpiryDateDate(df.parse(layaltyContractDTO.getExpiryDate()));
			}
			loyaltyProductContractDTOs = layaltyContractDTO.getLoyaltyProdContractDTO();
		}

		if (hasActionErrors()) {
			return INPUT;
		}
		this.addActionMessage("添加合同明细成功！");
		return "edit";
	}
	
	/**
	 * 转向合同产品明细，卡费、年费的修改
	 * @return
	 * @throws Exception
	 */
	public String editIssuerProduct()throws Exception{
	  loyaltyProdContractDTO=(LoyaltyProdContractDTO)sendService(ConstCode.ISSUER_CONTRACT_PRODUCTS_VIEW, loyaltyProdContractDTO).getDetailvo();
      return "edit";
	}
	
	/**
	 * 修改合同产品明细，卡费、年费
	 * @return
	 * @throws Exception
	 */
	public String updateIssuerProduct()throws Exception{
	   loyaltyProdContractDTO.setModifyUser(this.getUser().getLoginUserId());
	   sendService(ConstCode.ISSUER_CONTRACT_PRODUCTS_EDIT, loyaltyProdContractDTO);
		if (this.hasErrors()) {
			return INPUT;
		}
		return "success";		
	}

	/**
	 * 转向服务合同明细，计费规则的修改
	 * @return
	 * @throws Exception
	 */
	public String editIssuerService()throws Exception{
		loyaltyAcctypeContractDTO.setId(loyaltyAcctypeContractDTO.getId());
		loyaltyAcctypeContractDTO.setRuleNo(loyaltyAcctypeContractDTO.getRuleNo());
		return "edit";	
	}
	
	/**
	 * 服务合同明细，计费规则
	 * @return
	 * @throws Exception
	 */
	public String updateIssuerService()throws Exception{
	   loyaltyAcctypeContractDTO.setModifyUser(this.getUser().getLoginUserId());
	   sendService(ConstCode.ISSUER_CONTRACT_SERVICE_EDIT, loyaltyAcctypeContractDTO);
		if (this.hasErrors()) {
			return INPUT;
		}
		return "success";		
	}
	
	/**
	 * 更新发行机构合同信息
	 * @return
	 * @throws Exception
	 */
	public String updateIssuerConstract()throws Exception{
		layaltyContractDTO.setModifyUser(this.getUser().getLoginUserId());
		sendService(ConstCode.ISSUER_CONTRACT_UPDATE, layaltyContractDTO);
		
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("发行机构合同信息修改成功！");
		}
		return queryIssuerContract();
	}
	
	
}
