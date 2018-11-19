/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: Details.java
 * Author:   12073942
 * Date:     2013-10-18 下午2:47:19
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.xmlbean.trans.detail;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 明细
 * 
 * @author LEO
 */
public class Details {

    /** item 行项 */
    @XStreamImplicit(itemFieldName = "item")
    private ArrayList<Item> itemList;

    /**
     * @return the itemList
     */
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

}
