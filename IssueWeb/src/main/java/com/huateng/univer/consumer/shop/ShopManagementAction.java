package com.huateng.univer.consumer.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopQueryDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.SystemInfo;

/**
 * 门店管理Action
 * @author zengfenghua
 *
 */
public class ShopManagementAction extends BaseAction {

	private static final long serialVersionUID = 2557316269583995047L;

    /**
     * 门店查询DTO
     */
	private ShopQueryDTO shopQueryDTO;

    /**
     * 门店DTO
     */
	private ShopDTO shopDTO;

    /**
     * 门店PosDTO
     */	
//	private ShopPosDTO shopPosDTO;

    /**
     * 查询数据存放DTO
     */	
	private PageDataDTO pageDataDTO = new PageDataDTO();

    /**
     * 门店ID存放List
     */
	private List<String> shopIdList;
	
	private List<String> entityIdList;

    /**
     * PosID存放List
     */
	private List<Long> posIdList;

    /**
     * 联系人DTO
     */	
	private ContactDTO contactDTO;

    /**
     * 联系人IDList
     */	
	private List<String> contactIdList;

    /**
     * 传给子画面的action名字
     */	
	private String actionName;

    /**
     * 门店ID
     */	
	private String shopId;

    /**
     * 城市，区域列表，以JSON形式传给前台
     */		
	private JSONObject city;

    /**
     * 门店类型、子类列表，以JSON形式传给前台
     */	
	private JSONObject category;

	private List<String> shopIdAll;

	private List<String> merchantIdList;

	private List<String> stateList;
//	private AuthorityCardDTO authorityCardDTO;
	private List<Long> authorityCardIdList;
	
/**	public AuthorityCardDTO getAuthorityCardDTO() {
		return authorityCardDTO;
	}

	public void setAuthorityCardDTO(AuthorityCardDTO authorityCardDTO) {
		this.authorityCardDTO = authorityCardDTO;
	}
*/
	public List<Long> getAuthorityCardIdList() {
		return authorityCardIdList;
	}

	public void setAuthorityCardIdList(List<Long> authorityCardIdList) {
		this.authorityCardIdList = authorityCardIdList;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public ShopQueryDTO getShopQueryDTO() {
		return shopQueryDTO;
	}

	public void setShopQueryDTO(ShopQueryDTO shopQueryDTO) {
		this.shopQueryDTO = shopQueryDTO;
	}

	public ShopDTO getShopDTO() {
		return shopDTO;
	}

	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}

/**	public ShopPosDTO getShopPosDTO() {
		return shopPosDTO;
	}

	public void setShopPosDTO(ShopPosDTO shopPosDTO) {
		this.shopPosDTO = shopPosDTO;
	}
*/
	public int getTotalRows() {
		return pageDataDTO.getTotalRecord();
		
	}

   

	public List<String> getShopIdList() {
	return shopIdList;
}

public void setShopIdList(List<String> shopIdList) {
	this.shopIdList = shopIdList;
}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}

	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
	}
    
	public List<String> getContactIdList() {
		return contactIdList;
	}

	public void setContactIdList(List<String> contactIdList) {
		this.contactIdList = contactIdList;
	}

	public List<Long> getPosIdList() {
		return posIdList;
	}

	public void setPosIdList(List<Long> posIdList) {
		this.posIdList = posIdList;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
    
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public JSONObject getCity() {
		return city;
	}

	public void setCity(JSONObject city) {
		this.city = city;
	}

	public JSONObject getCategory() {
		return category;
	}

	public void setCategory(JSONObject category) {
		this.category = category;
	}

	

	public List<String> getShopIdAll() {
		return shopIdAll;
	}

	public void setShopIdAll(List<String> shopIdAll) {
		this.shopIdAll = shopIdAll;
	}

	public List<String> getMerchantIdList() {
		return merchantIdList;
	}

	public void setMerchantIdList(List<String> merchantIdList) {
		this.merchantIdList = merchantIdList;
	}

	public List<String> getStateList() {
		return stateList;
	}

	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

    /**
     * 添加门店编辑页面进入的action
     * @return
     * @throws Exception
     */
	public String add() throws Exception {
		shopDTO = new ShopDTO();
        if (hasErrors()) {
            return "input";
        }
        //获取级联的门店类型，门店子类列表
        getCategoryList();

        //获取级联的门店城、区域列表
       getCityList();
		return "add";
	}

    /**
     * 修改门店编辑页面进入的action
     * @return
     * @throws Exception
     */
	public String edit() throws Exception {
		//能否编辑check
		checkEdit();
		shopDTO = new ShopDTO();
		if (shopIdList != null && shopIdList.size() != 0)
			shopDTO.setShopId(shopIdList.get(0));
		if (shopId != null) {
			shopDTO.setShopId(shopId);
		}
		if(entityIdList !=null && entityIdList.size()!=0){
			shopDTO.setEntityId(entityIdList.get(0));
		}
		this.shopDTO = (ShopDTO)sendService("8008020002",shopDTO).getDetailvo();
		shopDTO.setCreateTime(DateUtil.formatStringDate(shopDTO.getCreateTime()));
        if (hasErrors()) {
            return "input";
        }
        //获取级联的门店类型，门店子类列表
      getCategoryList();

     //   //获取级联的门店城、区域列表
        getCityList();
		return "edit";
	}

    /**
     * 查看门店的action
     * @return
     * @throws Exception
     */
	public String view() throws Exception {
		shopDTO = (ShopDTO)sendService("8008020002",shopDTO).getDetailvo();
        if (hasErrors()) {
            return "input";
        }
		return "view";
	}
	
	/**
	 * list页面不进行查询
	 * @return
	 * @throws Exception
	 */
	public String shopList() throws Exception {
		
		inquiry();
		return "list";
	}
	

    /**
     * 查询门店的action
     * @return
     * @throws Exception
     */
	public String inquiry() throws Exception {
    	
			if (shopQueryDTO == null) {
				shopQueryDTO = new ShopQueryDTO();
			}
			shopQueryDTO.setConsumerId(getUser().getEntityId());
			ListPageInit(null,shopQueryDTO);
			Object ob=sendService("8008020000",shopQueryDTO).getDetailvo();
	 		if ( ob!= null) {
				this.pageDataDTO = (PageDataDTO)ob;
			}
	
	        //获取级联的门店城、区域列表
	        getCityList();
	        if (hasErrors()) {
	            return "input";
	        }
	       // shopQueryDTO.setMoblie("");
	       // shopQueryDTO.setShopTelephone("");
			return "list";
		
	}
	
	/**
	 * 门店地址短信发送action
	 * added by sff 2010-6-25
	 * @return
	 * @throws Exception
	 */
	public String sendSms() throws Exception{
		if(shopDTO==null){
			shopDTO = new ShopDTO();
		}
		if (shopIdList != null && shopIdList.size() != 0) {
			shopDTO.setShopId(shopIdList.get(0));
		}
	//	shopDTO.setMobile(shopQueryDTO.getMoblie());
		shopDTO.setCreateUser(getUser().getUserId());
		sendService("2002031303", shopDTO);

		if (hasErrors()) {
			return "input";
		} else {
			addActionMessage("短信发送成功！");
		}
		return inquiry();
	}
	

    /**
     * 新增门店的action
     * @return
     * @throws Exception
     */
	public String insertShop() throws Exception {
	    if(!checkFileds()) {
	        getCityList();
            getCategoryList();
            return "input";
	    }
		shopDTO.setConsumerId(getUser().getEntityId());
		//shopDTO.setShopCode("0001");
		sendService("8008020001",shopDTO);
        if (hasErrors()) {
            //获取级联的门店城、区域列表
            getCityList();
            getCategoryList();
            return "input";
        }
        addActionMessage("新增门店成功！");
		return inquiry();
	}

    /**
     * 更新门店的action
     * @return
     * @throws Exception
     */
	public String update() throws Exception {  
	    if(!checkFileds()) {
            getCityList();
            getCategoryList();
            return "input";
        }
		shopDTO.setConsumerId(getUser().getEntityId());
		sendService("8008020003",shopDTO);
        if (hasErrors()) {
            //获取级联的门店城、区域列表
            getCityList();
            getCategoryList();
            List<String> shopIdStringList=new  ArrayList<String>();
            shopIdStringList.add(0, shopDTO.getShopId());
            this.setShopIdList(shopIdStringList);
            edit();
            return "input";
        }
        addActionMessage("更新门店成功！");
		return inquiry();
	}
	
	private boolean checkFileds() {
	    boolean flag = true;
	    if(StringUtils.isBlank(shopDTO.getShopTelephone())) {
            this.addFieldError("shopTelephone", "电话必须输入！");
            flag = false;
        }
        else {
            if(shopDTO.getShopTelephone().length() > 32) {
                this.addFieldError("shopTelephone", "电话不能超过32个字符！");
                flag = false;
            } 
            if(!shopDTO.getShopTelephone().matches("^{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$")) {
                this.addActionError("电话格式不正确,正确格式是（区号-电话号码）或者只输电话号码！");
                flag = false;
            }
        }
        if(StringUtils.isBlank(shopDTO.getShopFax())) {
            this.addFieldError("shopFax", "传真必须输入！");
            flag = false;
        }
        else {
            if(shopDTO.getShopFax().length() > 32) {
                this.addFieldError("shopFax", "传真不能超过32个字符！");
                flag = false;
            }
            if(!shopDTO.getShopFax().matches("^{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$")) {
                this.addActionError("传真格式不正确,传真格式是（区号-传真）或者只输传真！");
                flag = false;
            }  
        }
        return flag;
	}

    /**
     * 删除门店的action
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        List<ShopDTO> shopDTOList = new ArrayList<ShopDTO>();
        for (String shopId : shopIdList) {
        	ShopDTO dto = new ShopDTO();
        	
//        	if(shopId.length() == 4){
//             	addActionError("内部门店不能删除");
//             	this.inquiry();
//                return "input";
//             }
        	
            dto.setShopId(shopId);
            shopDTOList.add(dto);
        }
        shopDTO = new ShopDTO();
        shopDTO.setShopDTOList(shopDTOList);
        sendService("8008020004", shopDTO);

        if (hasErrors()) {
        	this.inquiry();
            return "input";
        }
        addActionMessage("删除门店成功！");
        return inquiry();
    }

    /**
     * 添加联系人的action
     * @return
     * @throws Exception
     */
	public String addContact() throws Exception {
		contactDTO = new ContactDTO();
		contactDTO.setEntityId(shopDTO.getShopId());
        if (hasErrors()) {
            return "input";
        }
		return "addContact";
	}

    /**
     * 新增联系人的action
     * @return
     * @throws Exception
     */
	public String insertContact() throws Exception {
		sendService("8008020005",contactDTO);
        if (hasErrors()) {
            return "input";
        }
		return "blank";
	}

    /**
     * 编辑联系人的action
     * @return
     * @throws Exception
     */
	public String editContact() throws Exception {
		contactDTO = new ContactDTO();
		contactDTO.setContactId(contactIdList.get(0));
		this.contactDTO = (ContactDTO)sendService("8008020006",contactDTO).getDetailvo();
        if (hasErrors()) {
            return "input";
        }
		return "editContact";
	}
	


    /**
     * 更新联系人的action
     * @return
     * @throws Exception
     */
	public String updateContact() throws Exception {
		sendService("8008020007",contactDTO);
        if (hasErrors()) {
            return "input";
        }
		return "blank";
	}

	 /**
     * 更新联系人的action  valildate
     * @throws Exception
     */
    public void validateUpdateContact(){   
        if (hasErrors()) {
        	actionName="shopManagement/updateContact";
        }
    }
	
    /**
     * 查看联系人的action
     * @return
     * @throws Exception
     */
	public String viewContact() throws Exception {
		this.contactDTO = (ContactDTO)sendService("2002030900",contactDTO).getDetailvo();
        if (hasErrors()) {
            return "input";
        }
		return "viewContact";
	}

    /**
     * 删除联系人的action
     * @return
     * @throws Exception
     */
    public String deleteContact() throws Exception {
        List<ContactDTO> contactDTOList = new ArrayList<ContactDTO>();
        for (String contactId : contactIdList) {
        	ContactDTO dto = new ContactDTO();
            dto.setContactId(contactId);
            contactDTOList.add(dto);
        }
        contactDTO = new ContactDTO();
        contactDTO.setContactDTOList(contactDTOList);
        sendService("8008020008", contactDTO);
        if (hasErrors()) {
            return "input";
        }
        return edit();
    }
 /**   public String addAuthorityCard() throws Exception{
    	authorityCardDTO=new AuthorityCardDTO();
    	authorityCardDTO.setShopId(shopDTO.getShopId());
    	if (hasErrors()) {
            return "input";
        }
		return "addAuthorityCard";
    }
    public String insertAuthorityCard() throws Exception {
		sendService("2002031301",authorityCardDTO);
        if (hasErrors()) {
            return "input";
        }
		return "blank";
	}
    public String deleteAuthorityCard() throws Exception{
    	List<AuthorityCardDTO> authorityCardList=new ArrayList<AuthorityCardDTO>();
    	for(Long id : authorityCardIdList){
    		AuthorityCardDTO dto=new AuthorityCardDTO();
    		dto.setId(id);
    		authorityCardList.add(dto);
    	}
    	authorityCardDTO=new AuthorityCardDTO();
    	authorityCardDTO.setAuthorityCardDTOList(authorityCardList);
    	sendService("2002031302", authorityCardDTO);
    	 if (hasErrors()) {
             return "input";
         }
         return "blank";
    }
 */   /**
     * 添加pos的action
     * @return
     * @throws Exception
     */
/**	public String addPos() throws Exception {
		shopPosDTO = new ShopPosDTO();
		shopPosDTO.setShopId(shopDTO.getShopId());
        if (hasErrors()) {
            return "input";
        }
		return "addPos";
	}
*/
    /**
     * 删除pos的action
     * @return
     * @throws Exception
     */
    /**	public String insertPos() throws Exception {
		sendService("2002031000",shopPosDTO);
        if (hasErrors()) {
            return "input";
        }
		return "blank";
	}
 */
    /**
     * 编辑pos的action
     * @return
     * @throws Exception
     */
    /**	public String editPos() throws Exception {
		shopPosDTO = new ShopPosDTO();
		shopPosDTO.setPosId(posIdList.get(0));
		this.shopPosDTO = (ShopPosDTO)sendService("2002031300",shopPosDTO).getDetailvo();
        if (hasErrors()) {
            return "input";
        }
		return "editPos";
	}
*/
    /**
     * 更新pos的action
     * @return
     * @throws Exception
     */
    /**	public String updatePos() throws Exception {
		sendService("2002031100",shopPosDTO);
        if (hasErrors()) {
            return "input";
        }
		return "blank";
	}
*/
    /**
     * 查看pos的action
     * @return
     * @throws Exception
     */
/**	public String viewPos() throws Exception {
		this.shopPosDTO = (ShopPosDTO)sendService("2002031300",shopPosDTO).getDetailvo();
        if (hasErrors()) {
            return "input";
        }
		return "viewPos";
	}
*/
    /**
     * 删除pos的action
     * @return
     * @throws Exception
    
    public String deletePos() throws Exception {
        List<ShopPosDTO> shopPosDTOList = new ArrayList<ShopPosDTO>();
        for (Long posId : posIdList) {
        	ShopPosDTO dto = new ShopPosDTO();
            dto.setPosId(posId);
            shopPosDTOList.add(dto);
        }
        shopPosDTO = new ShopPosDTO();
        shopPosDTO.setShopPosDTOList(shopPosDTOList);
        sendService("2002031200", shopPosDTO);
        if (hasErrors()) {
            return "input";
        }
        return "blank";
    }
*/
    /**
     * 添加验证,查询验证没有通过，取得门店城市，区域数据
     * @throws Exception
     */
    public void validateInsertShop() throws Exception {

        // 出错初始化数据
        if (hasErrors()) {
            //获取级联的门店城、区域列表
            getCityList();
            getCategoryList();
        }
    }

    /**
     * 验证
     * @throws Exception
     */
    public void validateUpdate() throws Exception {

        // 出错初始化数据
        if (hasErrors()) {
            //获取级联的门店城、区域列表
            getCityList();
            getCategoryList();
        }
    }

    /**
     * 验证
     * @throws Exception
     */
    public void validateInquiry() throws Exception {

        // 出错初始化数据
        if (hasErrors()) {
            //获取级联的门店城、区域列表
           getCityList();
            getCategoryList();
        }
    }
   
	/**
	 * 设置门店分类，子类级联列表
	 */
	public void getCategoryList() {
        Map<String, List<EntityDictInfoDTO>> shopCategory = new HashMap<String, List<EntityDictInfoDTO>>();
        List<EntityDictInfoDTO> dictInfoDTOList2 = SystemInfo.getDictList(getUser().getEntityId(),"107");
        for (EntityDictInfoDTO dictInfoDTO : dictInfoDTOList2) {
        	shopCategory.put(dictInfoDTO.getDictCode().toString(),
                    SystemInfo.getSubDictList(getUser().getEntityId(),"108", new Integer(dictInfoDTO.getDictCode())));
        }
	
        this.category = JSONObject.fromObject(shopCategory);
	}

	/**
	 * 设置门店城市，区域级联列表
	 */
	public void getCityList() {
//	    Map<String, List<DictInfoDTO>> salesRegions = new HashMap<String, List<DictInfoDTO>>();
//	    List<DictInfoDTO> dictInfoDTOList = SystemInfo.getDictList("405");
//	    for (DictInfoDTO dictInfoDTO : dictInfoDTOList) {
//	        salesRegions.put(dictInfoDTO.getDictCode().toString(),
//	                SystemInfo.getSubDictList("408", new Integer(dictInfoDTO.getDictCode())));
//	    }
	    Map<String, List<EntityDictInfoDTO>> salesRegions = new HashMap<String, List<EntityDictInfoDTO>>();
		List<EntityDictInfoDTO> dictInfoDTOList = SystemInfo.getDictList(getUser().getEntityId(),"405");
		if (dictInfoDTOList != null) {
			for (EntityDictInfoDTO dictInfoDTO : dictInfoDTOList) {
				salesRegions.put(dictInfoDTO.getDictCode(), SystemInfo
						.getSubDictList(getUser().getEntityId(),"408", new Integer(dictInfoDTO.getDictCode())));
			}
		}
	    this.city = JSONObject.fromObject(salesRegions);
	}

	  /**
	 * 该门店所属的商户已经被注销，不能编辑!
	 * 
	 * @throws Exception
	 */
	public void checkEdit() throws Exception {
		if(shopIdAll != null && shopIdAll.size() != 0) {
			int i = 0;
			String merchantId = "";
			String state = "";
			for (String shopId : shopIdAll) {
				if (shopId.equals(shopIdList.get(0))) {
					merchantId = entityIdList.get(i);
					state = stateList.get(i);
					break;
				}
				i++;
			}
			if(shopIdList.get(0).length() == 4){
	         	addActionError("内部门店不能编辑");
		    	inquiry();
	         }
			if ("有效".equals(state)) {
				return;
			}
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setEntityId(merchantId);
			merchantDTO.setFatherEntityId(getUser().getEntityId());
			merchantDTO = (MerchantDTO)sendService(ConstCode.SELECT_MERCHANT_BY_MERCHANT_KEY,merchantDTO).getDetailvo();
			if (merchantDTO != null && "0".equals(merchantDTO.getMerchantState())) {
	    		addActionError("该门店所属的商户已经被注销，不能编辑!");
	    		inquiry();
			}
		}
		
		
		
	}
    public String inquerySms() throws Exception {
        if (shopQueryDTO == null) {
            shopQueryDTO = new ShopQueryDTO();
        }
        ListPageInit(null,shopQueryDTO);
        this.pageDataDTO=(PageDataDTO)sendService("2002031304",shopQueryDTO).getDetailvo();
        if (hasErrors()) {
            return "input";
        }
        return "smslist";
    }

	public List<String> getEntityIdList() {
		return entityIdList;
	}

	public void setEntityIdList(List<String> entityIdList) {
		this.entityIdList = entityIdList;
	}
    
    
}