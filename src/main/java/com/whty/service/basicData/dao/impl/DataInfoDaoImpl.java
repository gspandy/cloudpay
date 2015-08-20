package com.whty.service.basicData.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.service.BaseDaoImpl;
import com.whty.service.basicData.dao.IDataInfoDao;
import com.whty.service.basicData.model.DataDict;

/**
 * 
 * Title:  前置系统对接
 * Description:数据字典dao接口实现
 * Company: 武汉天喻信息产业股份有限公司
 * @author zhangyudong
 * create date: 2015-07-06
 * @version 1.0
 */
@Repository("dataInfoDao")
public class DataInfoDaoImpl extends BaseDaoImpl<DataDict> implements IDataInfoDao{

	@Override
	public List<DataDict> getDataDictInfo(DataDict bean) throws Exception {
		return this.getSqlSession().selectList("commonMapper.getDataDictInfos", bean);
	}
}
