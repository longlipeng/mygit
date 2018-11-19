package com.suning.svc.ibatis.dao;

import java.util.List;

import com.suning.svc.ibatis.model.InvoiceRequirement;

public interface InvoiceRequirementServiceDAO {

    List<InvoiceRequirement> paginationDisplay();

}
