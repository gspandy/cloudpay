package com.whty.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.utils.common.CommonFunction;
import com.whty.utils.common.Constants;
import com.whty.utils.common.DateUtils;



/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: BaseServiceImpl 
 *
 * Description:Service基类接口封装CRUD方法具体实现
 * 
 * Copyright: Copyright (c) 2015年8月11日 下午2:06:12 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
@Service("baseService")
public class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {
    protected static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
    
    @Autowired
    BaseDao<T> baseDao;

    /**
     * 取得记录的sql语句id
     */
    private String recordSql;
    /**
     * 取得所有记录数量的sql语句id
     */
    private String countSql;
    

	public Long getNextSeq(String seqName) throws Exception{
		return baseDao.getNextSeq(seqName);
	};
	
	public Long getCurrentSeq(String seqName) throws Exception{
		return baseDao.getCurrentSeq(seqName);
	};
	
	

    /**
     * 增加
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean add(String classMethod, T entity) throws Exception {
        return baseDao.add(classMethod, entity);
    }

    /**
     * 修改
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean edit(String classMethod, T entity) throws Exception {
        return baseDao.edit(classMethod, entity);
    }

    /**
     * 删除
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean remove(String classMethod, T entity) throws Exception {
        return baseDao.remove(classMethod, entity);
    }
    /**
     * 删除
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean removeById(String classMethod, String sId) throws Exception {
        return baseDao.removeById(classMethod, sId);
    }
    /**
     * 批量删除
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list        要删除的数据集合
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean batchDelete(String classMethod, List<String> list) throws Exception {
        return baseDao.batchDelete(classMethod, list);
    }

    /**
     * 批量增加
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list        要增加的数据集合
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean batchAdd(String classMethod, List<T> list) throws Exception {
        return baseDao.batchAdd(classMethod, list);
    }

    /**
     * 以id为条件查找对象
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public T get(String classMethod, T entity) throws Exception {
        return baseDao.get(classMethod, entity);
    }

    /**
     * 查询
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getAllList(String classMethod, T entity) throws Exception {
        return baseDao.getAllList(classMethod, entity);
    }

    /**
     * 查询数量
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public int getCount(String classMethod, T entity) throws Exception {
        return baseDao.getCount(classMethod, entity);
    }

    
    public String getSeq(String queryClassMethod) throws Exception {
        return baseDao.getStringResult(queryClassMethod);
    }

    
    public boolean batchDeleteT(String classMethod, List<T> list)
            throws Exception {
        return baseDao.batchDeleteT(classMethod, list);
    }

    
    public List<T> getAllListByIds(String classMethod, List<String> params) throws Exception {
        return baseDao.getAllListByIds(classMethod, params);
    }

    
    public List<T> getAllListByMap(String classMethod, Map<String, Object> params) throws Exception {
        return baseDao.getAllListByMap(classMethod, params);
    }

    /**
     * 修改
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param params      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    
    public boolean edit(String classMethod, Map<String, Object> params) throws Exception {
        return baseDao.bantchEdit(classMethod, params);
    }

    /**
     * 获取getColumn值
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @return Integer 生成的seq值
     * @throws Exception 抛出所有异常
     */
    
    public String getColumn(String classMethod) throws Exception {
        return baseDao.getColumn(classMethod);
    }

    /**
     * 获取单个值 如sum、count等
     *
     * @param queryClassMethod queryClassMethod	mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity           查询条件实体
     * @return
     * @throws Exception
     */
    
    public String getOneResult(String queryClassMethod, T entity) throws Exception {
        return baseDao.getOneResult(queryClassMethod, entity);
    }


    

    public List<T> getSearchList(String classMethod, Map<String, Object> params) throws Exception {
        return baseDao.getSearchList(classMethod, params);
    }

    /**
     * 批量修改
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list        要修改的对象数据集合
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public int batchUpdate(String classMethod, List<T> list) throws Exception {
        return baseDao.batchUpdate(classMethod, list);
    }

    /**
     * 批量修改
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list        要修改的String数据集合
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public int batchModify(String classMethod, List<String> list) throws Exception {
        return baseDao.batchModify(classMethod, list);
    }

    /**
     * 预处理参数 1、将值为单撇号的转移成
     *
     * @param parameter 参数
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void prepareParameter(Map parameter) {
//		Map<String, Object> newMap = new HashMap<String, Object>();
        try {
            if (parameter != null) {
//				newMap.putAll(parameter);
                Set set = parameter.keySet();
                for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                    String key = (String) iter.next();
                    Object obj = parameter.get(key);
                    if (obj instanceof String) { // 如果值对象是字符串型
                        if (obj.equals("")) {
                            iter.remove(); // 注意：不要用newMap.remove(key)，会导致ConcurrentModificationException
                        } else {
                            String value = (String) obj;
                            //使用oracle中regexp_like 替换特殊符号
                            String[] ss = {"^", "$", ".", "?", "+", "*", "|", "(", "),", "[", "]", "{", "}"};
                            for (String s : ss) {
                                value = StringUtils.replace(value, s, "\\" + s);
                            }
                            parameter.put(key, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("", e);
        }

//		return newMap;
    }

    public String getRecordSql() {
        return recordSql;
    }

    public void setRecordSql(String recordSql) {
        this.recordSql = recordSql;
    }

    public String getCountSql() {
        return countSql;
    }

    public void setCountSql(String countSql) {
        this.countSql = countSql;
    }
    
    @Override
    public Map<String, String> execProc(String procName, Map<String, Object> paraMap) throws Exception {
    	return baseDao.execProc(procName, paraMap);
    }
    
	@Override
	public String getTxnNo(String seqName) throws Exception {
		String dbCurSeq = Long.toString(baseDao.getNextSeq(seqName));
		String curSeq  = CommonFunction.fillString(dbCurSeq, '0', Constants.TXN_SEQNO_LEN, false);
		String curDate = DateUtils.getTxnDateStr(new Date());
		return curDate+curSeq;
	} 
}
