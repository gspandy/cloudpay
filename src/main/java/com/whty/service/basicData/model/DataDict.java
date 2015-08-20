package com.whty.service.basicData.model;

import java.util.Date;

import com.whty.service.BaseModel;
import com.whty.utils.common.annotation.DescAnnotation;

/**
 * @author zhangyudong create date: 2015-07-06
 * @version 1.0s
 * @Title: 前置系统对接
 * @Description:数据字典表实体
 * @Company: 武汉天喻信息产业股份有限公司
 */

public class DataDict extends BaseModel {
    private static final long serialVersionUID = 1L;
    @DescAnnotation(description = "编号")
    private String id;
    @DescAnnotation(description = "字典项名")
    private String itemName;
    @DescAnnotation(description = "列中文名")
    private String colNameCn;
    @DescAnnotation(description = "列英文名")
    private String colName;
    @DescAnnotation(description = "列值项")
    private String itemVal;
    @DescAnnotation(description = "描述")
    private String itemDesc;
    @DescAnnotation(description = "预留字段1")
    private String spare1;
    @DescAnnotation(description = "记录生成日期")
    private Date createTime;
    @DescAnnotation(description = "记录最后更新日期")
    private Date lastUpdTime;
    @DescAnnotation(description = "列类型(暂时TRANS_CODE使用)")   
    private String itemType;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getColNameCn() {
        return colNameCn;
    }

    public void setColNameCn(String colNameCn) {
        this.colNameCn = colNameCn == null ? null : colNameCn.trim();
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName == null ? null : colName.trim();
    }

    public String getItemVal() {
        return itemVal;
    }

    public void setItemVal(String itemVal) {
        this.itemVal = itemVal == null ? null : itemVal.trim();
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc == null ? null : itemDesc.trim();
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1 == null ? null : spare1.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdTime() {
        return lastUpdTime;
    }

    public void setLastUpdTime(Date lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }
}