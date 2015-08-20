package com.whty.service.basicData.dao;

import java.util.List;

import com.whty.service.BaseDao;
import com.whty.service.basicData.model.DataDict;
/**
 * 
 * Title:  前置系统对接
 * Description:数据字典dao接口
 * Company: 武汉天喻信息产业股份有限公司
 * @author zhangyudong
 * create date: 2015-07-06
 * @version 1.0
 */
public interface IDataInfoDao extends BaseDao<DataDict>{

	public List<DataDict> getDataDictInfo(DataDict bean) throws Exception;
}
