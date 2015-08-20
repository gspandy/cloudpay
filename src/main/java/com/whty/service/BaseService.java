package com.whty.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: BaseService 
 *
 * Description:Service基类接口封装CRUD方法
 * 
 * Copyright: Copyright (c) 2015年8月11日 下午2:06:00 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public interface BaseService<T> {
	
	/**  
	 * 获取序列下一个值
	 * @param seqName
	 * @return
	 * @throws Exception  
	 */
	public Long getNextSeq(String seqName) throws Exception;
	
	/**  
	 * 获取序列当前值
	 * @param seqName
	 * @return
	 * @throws Exception  
	 */
	public Long getCurrentSeq(String seqName) throws Exception;
	
    /**
     * 增加
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean add(String classMethod, T entity) throws Exception;

    /**
     * 修改
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean edit(String classMethod, T entity) throws Exception;

    /**
     * 删除
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean remove(String classMethod, T entity) throws Exception;
    
    /**
     * 删除
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param sId      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean removeById(String classMethod, String sId) throws Exception;

    /**
     * 批量删除
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list        要删除的数据集合
     * @return 返回操作结果
     * @throws Exception
     */
    public boolean batchDelete(String classMethod, List<String> list) throws Exception;

    /**
     * 批量增加
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list        要增加的数据集合
     * @return 返回操作结果
     * @throws Exception
     */
    public boolean batchAdd(String classMethod, List<T> list) throws Exception;

    /**
     * 以id为条件查找对象
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public T get(String classMethod, T entity) throws Exception;

    /**
     * 查询
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity      封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getAllList(String classMethod, T entity) throws Exception;

    /**
     * 查询数量
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity           封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public int getCount(String classMethod, T entity) throws Exception;

    /**
     * 获取seq值
     *
     * @param queryClassMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @return Integer 生成的seq值
     * @throws Exception 抛出所有异常
     */
    public String getSeq(String queryClassMethod) throws Exception;

    /**
     * 批量删除
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list        要删除的数据集合
     * @return 返回操作结果
     * @throws Exception
     */
    public boolean batchDeleteT(String classMethod, List<T> list) throws Exception;

    /**
     * 查询 -单个String 查询所有 如in ids
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param params      封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getAllListByIds(String classMethod, List<String> params) throws Exception;

    /**
     * 查询 List
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param params      封装数据的map
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getAllListByMap(String classMethod, Map<String, Object> params) throws Exception;

    /**
     * 修改
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param params      封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean edit(String classMethod, Map<String, Object> params) throws Exception;

    /**
     * 获取getColumn值
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @return Integer 生成的seq值
     * @throws Exception 抛出所有异常
     */
    public String getColumn(String classMethod) throws Exception;

    /**
     * 获取单个值 如sum、count等
     *
     * @param queryClassMethod queryClassMethod	mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity           查询条件实体
     * @return
     * @throws Exception
     */
    public String getOneResult(String queryClassMethod, T entity) throws Exception;

    /**
     * 查询
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param params      查询条件
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getSearchList(String classMethod, Map<String, Object> params) throws Exception;

    /**
     * 批量修改
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list        要修改的对象数据集合
     * @return 返回操作结果
     * @throws Exception
     */
    public int batchUpdate(String classMethod, List<T> list) throws Exception;

    /**
     * 批量修改
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list        要修改的String数据集合
     * @return 返回操作结
     * @throws Exception
     */
    public int batchModify(String classMethod, List<String> list) throws Exception;
    
    /** 
     * 执行存储过程  
     * @param procName
     * @return
     * @throws Exception  
     */
    public Map<String, String> execProc(String procName, Map<String, Object> paraMap) throws Exception;
    
    /**
	 * 
	 * @param seqName
	 * @return 交易日期+seq
	 * @throws Exception
	 */
	public String getTxnNo(String seqName) throws Exception;
}
