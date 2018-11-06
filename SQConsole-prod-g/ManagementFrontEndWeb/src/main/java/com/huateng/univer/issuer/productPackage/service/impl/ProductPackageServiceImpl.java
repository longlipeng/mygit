package com.huateng.univer.issuer.productPackage.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.PackageDAO;
import com.huateng.framework.ibatis.dao.ProductPackageDAO;
import com.huateng.framework.ibatis.model.Package;
import com.huateng.framework.ibatis.model.ProductPackage;
import com.huateng.framework.ibatis.model.ProductPackageExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.productPackage.service.ProductPackageService;

public class ProductPackageServiceImpl implements ProductPackageService {

	Logger logger = Logger.getLogger(ProductPackageServiceImpl.class);
	// 包装信息DAO
	private PackageDAO packageDAO;
	// 查询分页DAO
	private PageQueryDAO pageQueryDAO;
	private CommonsDAO commonsDAO;
	private ProductPackageDAO prodPackageDAO;

	public ProductPackageDAO getProdPackageDAO() {
		return prodPackageDAO;
	}

	public void setProdPackageDAO(ProductPackageDAO prodPackageDAO) {
		this.prodPackageDAO = prodPackageDAO;
	}

	public PackageDAO getPackageDAO() {
		return packageDAO;
	}

	public void setPackageDAO(PackageDAO packageDAO) {
		this.packageDAO = packageDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public void deletePackage(PackageDTO packageDTO) throws BizServiceException {
		try {
			List<String> cardLayoutIdList = new ArrayList<String>();
			for (PackageDTO target : packageDTO.getPackageDTOs()) {
				cardLayoutIdList.add(target.getPackageId());
			}
			ProductPackageExample prodPackageExample = new ProductPackageExample();
			prodPackageExample.createCriteria()
					.andPackageIdIn(cardLayoutIdList);
			List<ProductPackage> prodPackageList = prodPackageDAO
					.selectByExample(prodPackageExample);
			if (null != prodPackageList && prodPackageList.size() > 0) {
				throw new BizServiceException("包装已关联产品,不能删除");
			}
			List<Package> packages = new ArrayList<Package>();
			for (PackageDTO packageDto : packageDTO.getPackageDTOs()) {
				Package packag = packageDAO.selectByPrimaryKey(packageDto
						.getPackageId());
				packag.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				packages.add(packag);
			}
			commonsDAO.batchUpdate(
					"TB_PACKAGE.abatorgenerated_updateByPrimaryKeySelective",
					packages);
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除包装信息失败~！");
		}

	}

	public PackageDTO initPackage() throws BizServiceException {
		
		return null;
	}

	public PageDataDTO inqueryPackAge(PackageQueryDTO packAgeQueryDTO)
			throws BizServiceException {
		try {
			if(packAgeQueryDTO.getSort()==null||packAgeQueryDTO.getSort().trim().equals("")){
				packAgeQueryDTO.setSort("desc");
				packAgeQueryDTO.setSortFieldName("packageId");
			}
			PageDataDTO pdd = pageQueryDAO.query("PACKAGE.selectPackage",
					packAgeQueryDTO);
			return pdd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询信息失败！");
		}
	}

	public void insertPackage(PackageDTO packageDTO) throws BizServiceException {
		try {/*
			 * PackageExample packageExample = new PackageExample();
			 * packageExample.createCriteria().andDataStateEqualTo(
			 * DataBaseConstant.DATA_STATE_NORMAL).andPackageNameEqualTo(
			 * packageDTO.getPackageName()); List<Package> packages =
			 * packageDAO.selectByExample(packageExample); if (packages.size() >
			 * 0) { throw new BizServiceException(packageDTO.getPackageName() +
			 * " 包装名称已存在!"); }
			 */
			Package packag = new Package();
			ReflectionUtil.copyProperties(packageDTO, packag);
			packag.setPackageFee((new BigDecimal(packageDTO.getPackageFee())
					.multiply(new BigDecimal("100"))).toString());
			packag
					.setPackageId(commonsDAO
							.getNextValueOfSequence("TB_PACKAGE"));
			packag.setEntityId(packageDTO.getDefaultEntityId());
			packag.setCreateTime(DateUtil.getCurrentTime());
			packag.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			packag.setModifyTime(DateUtil.getCurrentTime());
			packag.setModifyUser(packageDTO.getLoginUserId());
			packageDAO.insert(packag);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加信息失败~！");
		}

	}

	public void modifyStatePackage(PackageDTO packageDTO)
			throws BizServiceException {
		

	}

	public PageDataDTO selectPackage(PackageQueryDTO PackAgeQueryDTO)
			throws BizServiceException {
		
		return null;
	}

	public void updatePackage(PackageDTO packageDTO) throws BizServiceException {
		try {
			Package packag = new Package();
			ReflectionUtil.copyProperties(packageDTO, packag);
			packag.setModifyTime(DateUtil.getCurrentTime());
			/*
			 * // 检查新编辑的名称在数据库是否存在 PackageExample packageExample = new
			 * PackageExample();
			 * packageExample.createCriteria().andDataStateEqualTo(
			 * DataBaseConstant.DATA_STATE_NORMAL).andPackageNameEqualTo(
			 * packageDTO.getPackageName()).andPackageIdNotEqualTo(
			 * packageDTO.getPackageId()); List<Package> packages =
			 * packageDAO.selectByExample(packageExample); if (packages.size() >
			 * 0) { throw new BizServiceException(packageDTO.getPackageName() +
			 * " 包装名称已存在!"); }
			 */
			packag.setPackageFee((new BigDecimal(packageDTO.getPackageFee())
					.multiply(new BigDecimal("100"))).toString());
			packageDAO.updateByPrimaryKeySelective(packag);
		} // catch (BizServiceException bse) {
		// throw bse;
		// }
		catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改包装信息失败~！");
		}

	}

	public PackageDTO viewPackage(PackageDTO packageDTO)
			throws BizServiceException {
		try {
			Package packag = packageDAO.selectByPrimaryKey(packageDTO
					.getPackageId());
			PackageDTO pkd = new PackageDTO();
			ReflectionUtil.copyProperties(packag, pkd);
			return pkd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取包装信息失败~！");
		}
	}

	public PackageDTO EditViewPackage(PackageDTO packageDTO)
			throws BizServiceException {
		try {

			ProductPackageExample prodPackageExample = new ProductPackageExample();
			prodPackageExample.createCriteria().andPackageIdEqualTo(
					packageDTO.getPackageId());
			List<ProductPackage> prodPackageList = prodPackageDAO
					.selectByExample(prodPackageExample);
			if (null != prodPackageList && prodPackageList.size() > 0) {
				throw new BizServiceException("包装已关联产品,不能被编辑");
			}
			Package packag = packageDAO.selectByPrimaryKey(packageDTO
					.getPackageId());
			PackageDTO pkd = new PackageDTO();
			ReflectionUtil.copyProperties(packag, pkd);
			return pkd;
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取包装信息失败~！");
		}
	}

}
