package com.huateng.univer.issuer.issuer.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.issuer.dto.cardserialnumber.CardSerialNumberDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface IssuerService {

    /**
     * 查询发行机构信息
     * 
     * @param issuerDTO
     * @return PageDataDTO
     * @throws BizServiceException
     * */
    public PageDataDTO inqueryIssuer(IssuerQueryDTO issuerQueryDTO) throws BizServiceException;

    /**
     * 增加发行机构信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public IssuerDTO insertIssuer(IssuerDTO issuerDTO) throws BizServiceException;

    /**
     * 删除发行机构信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public void deleteIssuerInfo(IssuerDTO issuerDTO) throws BizServiceException;

    /**
     * 加载发行机构信息
     * 
     * @return IssuerDTO
     * @throws BizServiceException
     * */
    public IssuerDTO viewIssuer(IssuerDTO issuerDTO) throws BizServiceException;

    /**
     * 增加实体发票公司的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public void insertCompanyInfo(InvoiceCompanyDTO invoiceCompanyDTO) throws BizServiceException;

    /**
     * 删除实体发票公司的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */

    public void deleteCompanyInfo(InvoiceCompanyDTO invoiceCompanyDTO) throws BizServiceException;

    /**
     * 查看实体发票公司的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public InvoiceCompanyDTO viewCompanyInfo(InvoiceCompanyDTO invoiceCompanyDTO) throws BizServiceException;

    /**
     * 更新实体发票公司的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */

    public void updateCompanyInfo(InvoiceCompanyDTO invoiceCompanyDTO) throws BizServiceException;

    /**
     * 增加实体发票地址的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public void insertInvoiceAddress(InvoiceAddressDTO dto) throws BizServiceException;

    /**
     * 删除实体发票公司地址的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */

    public void deleteAdressInfo(InvoiceAddressDTO dto) throws BizServiceException;

    /**
     * 查看实体发票地址的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public InvoiceAddressDTO viewInvoiceAddress(InvoiceAddressDTO dto) throws BizServiceException;

    /**
     * 更新实体发票地址的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */

    public void updateInvoiceAddress(InvoiceAddressDTO dto) throws BizServiceException;

    /**
     * 增加实体联系人的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public void insertContactInfo(ContactDTO dto) throws BizServiceException;

    /**
     * 查看实体联系人的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public ContactDTO viewContactInfo(ContactDTO dto) throws BizServiceException;

    /**
     * 更新实体联系人的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */

    public void updateContactInfo(ContactDTO dto) throws BizServiceException;

    /**
     * 删除实体联系人的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */

    public void deleteContactInfo(ContactDTO dto) throws BizServiceException;

    /**
     * 增加实体部门的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public void insertDepartMentInfo(DepartmentDTO dto) throws BizServiceException;

    /**
     * 删除实体部门的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */

    public void deleteDepartMentInfo(DepartmentDTO dto) throws BizServiceException;

    /**
     * 查看实体部门的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public DepartmentDTO viewDepartMentInfo(DepartmentDTO dto) throws BizServiceException;

    /**
     * 更新实体部门的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */

    public void updateDepartMentInfo(DepartmentDTO dto) throws BizServiceException;

    /**
     * 增加实体投递点的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public void insertDeliveryInfo(DeliveryPointDTO dto) throws BizServiceException;

    /**
     * 删除实体投递点的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */

    public void deleteDeliveryInfo(DeliveryPointDTO dto) throws BizServiceException;

    /**
     * 增加实体投递点联系人的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public DeliveryPointDTO insertDeliveryContractInfo(DeliveryPointDTO dto) throws BizServiceException;

    /**
     * 增加实体发货人的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public void insertDeliveryReceptInfo(DeliveryRecipientDTO dto) throws BizServiceException;

    /**
     * 查询实体发货人的信息
     * 
     * @param issuerDTO
     * 
     * @throws BizServiceException
     * */
    public DeliveryPointDTO queryDeliveryReceptInfo(DeliveryRecipientDTO dto) throws BizServiceException;

    /**
     * 更新实体发货人的信息
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */

    public void updateDeliveryReceptInfo(DeliveryPointDTO dto) throws BizServiceException;

    /**
     * 删除实体发货人的信息
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    public void deleteReceptInfo(DeliveryRecipientDTO dto) throws BizServiceException;

    /**
     * 更新发行机构的信息
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    public void updateIssuerInfo(IssuerDTO dto) throws BizServiceException;

    /**
     * insert CardBin相关的信息
     * 
     * @param cardSerialNumberDTO
     * @throws Exception
     */
    public void insertCardBin(CardSerialNumberDTO cardSerialNumberDTO) throws Exception;

    public PageDataDTO queryEntityId(IssuerQueryDTO issuerQueryDTO) throws BizServiceException;

    public IssuerDTO configEntityId(IssuerQueryDTO issuerQueryDTO) throws BizServiceException;

    public IssuerDTO getCardSerialNumberDTOs(IssuerDTO issuerDTO) throws BizServiceException;

    public IssuerDTO checkIssuerIdPin(IssuerDTO issuerDTO) throws BizServiceException;

    public PageDataDTO listIssuer(IssuerQueryDTO issuerQueryDTO) throws BizServiceException;

    public PageDataDTO inquerySelfIssuer(IssuerQueryDTO issuerQueryDTO) throws BizServiceException;
}
