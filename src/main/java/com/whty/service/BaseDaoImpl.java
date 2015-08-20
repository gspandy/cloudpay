package com.whty.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.whty.utils.common.Constants;
import com.whty.utils.common.ObjectUtils;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: BaseDaoImpl 
 *
 * Description:Dao基类接口封装CRUD方法 具体实现
 * 
 * Copyright: Copyright (c) 2015年8月11日 下午2:05:16 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
@Repository("baseDao")
public class BaseDaoImpl<T extends BaseModel> extends SqlSessionDaoSupport implements BaseDao<T> {
    protected Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    private static String DEV_MODEL = "1";//开发测试模式
    
    @Override
    public Long getNextSeq(String seqName) throws Exception {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("seqName", seqName);
    	return this.getSqlSession().selectOne("commonMapper.getNextSequence", paramMap);
    }
    
    @Override
    public Long getCurrentSeq(String seqName) throws Exception {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("seqName", seqName);
    	return this.getSqlSession().selectOne("commonMapper.getCurrentSequence", paramMap);
    }
    
    /**
     * 增加
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity 封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean add(String classMethod, T entity) throws Exception {
        boolean flag = false;
        try {
            printObjectValue(entity);
            int result  = this.getSqlSession().insert(classMethod, entity);
            logger.debug("dao add!{} result={}", classMethod, result);
            flag = result > 0 ? true : false;
        } catch (Exception e) {
            logger.error("dao add:" + classMethod, e);
            throw e;
        }
        return flag;
    }

    /**
     * 修改
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity 封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean edit(String classMethod, T entity) throws Exception {
        boolean flag = false;
        try {
            printObjectValue(entity);
            int result = this.getSqlSession().update(classMethod, entity);
            flag = result > 0 ? true : false;
            logger.debug("dao edit!{} result={}", classMethod, result);
        } catch (Exception e) {
            logger.error("dao edit:" + classMethod, e);
            throw e;
        }
        return flag;
    }

    /**
     * 以id为条件查找对象
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity 封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public T get(String classMethod, T entity) throws Exception {
        T result = null;
        try {
        	printObjectValue(entity);
            List list = this.getSqlSession().selectList(classMethod, entity);
            result = (T) ((list != null && list.size() > 0) ? list.get(0) : null);
            logger.debug("dao get!"+classMethod + " result=" + (result == null ? 0 : 1));
        } catch (Exception e) {
            logger.error("dao get:" + classMethod, e);
            throw e;
        }
        return result;
    }

    /**
     * 删除
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity 封装数据的实体
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean remove(String classMethod, T entity) throws Exception {
        boolean flag = false;
        try {
            printObjectValue(entity);
            int result = this.getSqlSession().delete(classMethod, entity);
            flag = result > 0 ? true : false;
            logger.debug("dao remvoe!"+classMethod + " result=" + result);
        } catch (Exception e) {
            logger.error("dao remvoe:" + classMethod, e);
            throw e;
        }
        return flag;
    }
    /**
     * 删除
     *
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param sIdName   
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean removeById(String classMethod, String sIdName) throws Exception{
        boolean flag = false;
        try {
            ObjectUtils.printObjectValue(sIdName);
            int result = this.getSqlSession().delete(classMethod, sIdName);
            logger.info("dao remvoe!"+classMethod + " result=" + result);
            flag = result > 0 ? true : false;
        } catch (Exception e) {
            flag = false;
            logger.error("dao remvoe:" + classMethod, e);
            throw e;
        }
        return flag;
    }
    /**
     * 批量删除
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list 要删除的数据集合
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean batchDelete(String classMethod,List<String>list) throws Exception{
        boolean flag = false;
        try {
            printObjectValue(list);
            int result = this.getSqlSession().delete(classMethod,list);
            flag = result > 0 ? true : false;
            logger.debug("dao batchDelete!"+classMethod + " result=" + result);
        } catch (Exception e) {
            logger.error("dao batchDelete:" + classMethod, e);
            throw e;
        }
        logger.debug("flag:" + flag);
        return flag;
    }


    /**
     * 批量增加
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param list 要增加的数据集合
     * @return 返回操作结果
     * @throws Exception  抛出所有异常
     */
    public boolean batchAdd(String classMethod,List<T>list) throws Exception{
        boolean flag = false;
        try {
            for (T t : list) {
                printObjectValue(t);
                int result = this.getSqlSession().insert(classMethod,t);
                flag = result > 0 ? true : false;
                logger.debug("dao batchAdd!"+classMethod + " result=" + result);
            }
        } catch (Exception e) {
            flag = false;
            logger.error("dao batchAdd:" + classMethod,e);
            throw e;
        }
        logger.debug("flag:" + flag);
        return flag;
    }

    /**
     * 查询
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getAllList(String classMethod) throws Exception {
        List<T> result = null;
        try {
            result = this.getSqlSession().selectList(classMethod);
            logger.debug("dao getAllList!"+classMethod + " size=" + result.size());
        } catch (Exception e) {
            logger.error("dao getAllList:" + classMethod,e);
            throw e;
        }
        return result;
    }

    /**
     * 查询
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity 封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getAllList(String classMethod, T entity) throws Exception {
        List<T> result = null;
        try {
            printObjectValue(entity);
            result = this.getSqlSession().selectList(classMethod,entity);
            logger.debug("dao getAllList!"+classMethod + " size=" + result.size());
        } catch (Exception e) {
            logger.error("dao getAllList:" + classMethod,e);
            throw e;
        }
        return result;
    }

    /**
     * 查询 -单个String 查询所有 如in ids
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param params 封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getAllListByIds(String classMethod, List<String> params) throws Exception {
        List<T> result=null;
        try {
            printObjectValue(params);
            result = this.getSqlSession().selectList(classMethod,params);
            logger.debug("dao getAllList!"+classMethod + " size=" + result.size());
        } catch (Exception e) {
            logger.error("dao getAllList:" + classMethod,e);
            throw e;
        }
        return result;
    }

    /**
     * 查询 List
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param params 封装数据的map
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getAllListByMap(String classMethod, Map<String,Object> params) throws Exception{
        List<T> result=null;
        try {
            printObjectValue(params);
            result = this.getSqlSession().selectList(classMethod,params);
            logger.debug("dao getAllListByMap!"+classMethod + " size=" + result.size());
        } catch (Exception e) {
            logger.error("dao getAllList:" + classMethod,e);
            throw e;
        }
        return result;
    }

    /**
     * 查询数量
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity 封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public Integer getCount(String classMethod, T entity) throws Exception {
        int result = 0;
        try {
            printObjectValue(entity);
            result = (Integer) this.getSqlSession().selectOne(classMethod,entity);
            logger.debug("dao getCount!" + classMethod + " result:" + result);
        } catch (Exception e) {
            logger.error("dao getCount:" + classMethod,e);
            throw e;
        }
        return result;
    }
    /**
     * 查询数量
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity 封装数据的实体
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    @SuppressWarnings("unchecked")
    public T getCountObject(String classMethod, T entity) throws Exception {
        Object result;
        try {
            logger.debug("dao getCount!"+classMethod);
            printObjectValue(entity);
            result = this.getSqlSession().selectOne(classMethod,entity);
            if(result instanceof Integer){
                entity.setTotalNumber(String.valueOf(result));
            }else{
                entity.setTotalAmount(((T)result).getTotalAmount());
                entity.setTotalNumber(((T)result).getTotalNumber());
                logger.debug("dao getCount result=" + entity.getTotalAmount());
            }
            logger.debug("dao getCount result=" + entity.getTotalNumber());
        } catch (Exception e) {
            logger.error("dao getCount:" + classMethod,e);
            throw e;
        }
        return entity;
    }

    
    public String getStringResult(String classMethod) throws Exception {
        logger.debug("dao getStringResult!"+classMethod);
        String result = null;
        try {
            result = (String)this.getSqlSession().selectOne(classMethod);
        } catch (Exception e) {
            logger.error("dao getStringResult:" + classMethod,e);
            throw e;
        }finally{
            this.getSqlSession().clearCache();
        }
        return result;
    }

    
    public Integer getIntResult(String classMethod) throws Exception {
        logger.debug("dao getStringResult!"+classMethod);
        Integer result = null;
        try {
            result = (Integer)this.getSqlSession().selectOne(classMethod);
        } catch (Exception e) {
            logger.error("dao getStringResult:" + classMethod,e);
            throw e;
        }finally{
            this.getSqlSession().clearCache();
        }
        return result;
    }

    
    public Integer getIntResult(String classMethod, Object obj) throws Exception {
        logger.debug("dao getStringResult!"+classMethod);
        Integer result = null;
        try {
            result = (Integer)this.getSqlSession().selectOne(classMethod, obj);
        } catch (Exception e) {
            logger.error("dao getStringResult:" + classMethod,e);
            throw e;
        }finally{
            this.getSqlSession().clearCache();
        }
        return result;
    }

    
    public String getColumn(String classMethod) throws Exception {
        logger.debug("dao getColumn!"+classMethod);
        String result = null;
        try {
            result = (String)this.getSqlSession().selectOne(classMethod);
        } catch (Exception e) {
            logger.error("dao getColumn:" + classMethod,e);
            throw e;
        } finally{
            this.getSqlSession().clearCache();
        }
        return result;
    }

    
    public boolean batchDeleteT(String classMethod, List<T> list)
            throws Exception {
        boolean flag = false;
        try {
            int result = this.getSqlSession().delete(classMethod,list);
            logger.debug("dao batchDeleteT!"+classMethod + " result=" + result);
            flag = result > 0 ? true : false;
        } catch (Exception e) {
            flag = false;
            logger.error("dao batchDeleteT:" + classMethod,e);
            throw e;
        }
        logger.debug("flag:" + flag);
        return flag;
    }
    /**
     * 获取单个值 如sum、count等
     * @param classMethod  classMethod	mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity 查询条件实体
     * @return
     * @throws Exception
     */
    
    @SuppressWarnings("rawtypes")
	public String getOneResult(String classMethod,T entity) throws Exception{
        String result = null;
        try {
            logger.debug("dao getOneResult!"+classMethod);
            printObjectValue(entity);
            List list = this.getSqlSession().selectList(classMethod, entity);
            result = (String) ((list != null && list.size() > 0) ? list.get(0) : null);
        } catch (Exception e) {
            logger.error("dao getOneResult:" + classMethod,e);
            throw e;
        }
        logger.debug("result:" + result);
        return result;
    }

    /**
     * 获取单个值 如sum、count等
     * @param classMethod  classMethod	mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @param entity 查询条件实体
     * @return
     * @throws Exception
     */
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public T getOneObject(String classMethod,T entity) throws Exception{
        Object result;
        try {
            logger.debug("dao getOneResult!"+classMethod);
            printObjectValue(entity);
            List list = this.getSqlSession().selectList(classMethod, entity);
            result = ((list != null && list.size() > 0) ? list.get(0) : null);
        } catch (Exception e) {
            logger.error("dao getOneResult:" + classMethod,e);
            throw e;
        }
        return (T) result;
    }

    /**
     * 获取map集合
     * @param classMethod  classMethod	mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @return
     * @throws Exception
     */
    
    public Map<String, Object> getMap(String classMethod,Map<String, Object> map) throws Exception {
        try {
        	logger.debug("dao getMap!"+classMethod);
            printObjectValue(map);
            this.getSqlSession().selectList(classMethod, map);
        } catch (Exception e) {
            logger.error("dao getMap:" + classMethod,e);
            throw e;
        }
        return map;
    }
    /**
     * 获取对象
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public T get(String classMethod,String param) throws Exception{
        logger.debug("dao get!"+classMethod);
        T result = null;
        try {
            printObjectValue(param);
			List list = this.getSqlSession().selectList(classMethod, param);
            result = (T)((list != null && list.size() > 0) ? list.get(0) : null);
        } catch (Exception e) {
            logger.error("dao get:" + classMethod,e);
            throw e;
        }
        return result;
    }
    /**
     * 批量更新
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     */
    public boolean bantchEdit(String classMethod,Map<String,Object> maps) throws Exception{
        boolean flag = false;
        try {
            printObjectValue(maps);
            int result = this.getSqlSession().update(classMethod, maps);
            logger.debug("dao edit!"+classMethod + " result=" + result);
            flag = result > 0 ? true : false;
        } catch (Exception e) {
            flag = false;
            logger.error("dao edit:" + classMethod,e);
            throw e;
        }
        return flag;
    }

    /**
     * 查询
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @return 返回查询结果
     * @throws Exception 抛出所有异常
     */
    public List<T> getSearchList(String classMethod, Map<String, Object> params) throws Exception {
        List<T> result=null;
        try {
            printObjectValue(params);
            result = this.getSqlSession().selectList(classMethod,params);
            logger.debug("dao getSearchList!"+classMethod + " size=" + result.size());
        } catch (Exception e) {
            logger.error("dao getSearchList:" + classMethod,e);
            throw e;
        }
        return result;
    }
    /**
     * 批量修改
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @throws Exception 抛出所有异常
     */
    
    public int batchUpdate(String classMethod, List<T> list) throws Exception {
        int flag = 0;
        try {
            if(list == null || list.size() == 0) {
                return 0;
            }
            flag = this.getSqlSession().update(classMethod,list);
            logger.debug("dao batchUpdate!"+classMethod + " result=" + flag);
        } catch (Exception e) {
            logger.error("dao batchUpdate:" + classMethod,e);
            throw e;
        }
        return flag;
    }
    /**
     * 批量修改
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @throws Exception 抛出所有异常
     */
    
    public int batchModify(String classMethod, List<String> list) throws Exception {
        int flag = 0;
        try {
            if(list == null || list.size() == 0) {
                return 0;
            }
            flag = this.getSqlSession().update(classMethod,list);
            logger.debug("dao batchModify!"+classMethod + " result:" + flag);
        } catch (Exception e) {
            logger.error("dao batchModify:" + classMethod,e);
            throw e;
        }
        return flag;
    }

    /**
     * 批量修改
     * @param classMethod mybatis配置文件里面对应的命名空间+要执行的sql语句id
     * @return 返回操作结果
     * @throws Exception 抛出所有异常
     *
     */
    public boolean batchUpdateByMap(String classMethod,
                                    Map<String, Object> params) throws Exception {
        logger.debug("dao batchUpdate!"+classMethod);
        boolean flag = false;
        int result = 0;
        try {
            result = this.getSqlSession().update(classMethod,params);
            flag = result > 0 ? true : false;
        } catch (Exception e) {
            flag = false;
            logger.error("dao batchUpdate:" + classMethod,e);
            throw e;
        }
        logger.debug("result:" + result);
        return flag;
    }

    /**
     * 查询记录数
     *
     * @param sqlID sqlmap文件sql语句对应的id
     * @param params 查询条件
     * @param useConfig 是否使用sqlmap中配置的count sql来查询记录数，如果不是，则该方法自动包装select
     *            count语句
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     * @throws Exception
     */
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Integer count(String sqlID, Map params, boolean useConfig) throws Exception {
        // 如果使用sqlmap中配置的sql查询记录数，则直接查询
        Object startRow = params.get("startRow");
        Object pageSize = params.get("pageSize");
        params.put("startRow", 0);
        params.put("pageSize", 999999999);
        if (useConfig) {
            return getSqlSession().selectOne(sqlID, params);
        } else { // 否则包装一个查询记录SQL语句
            // 拼接统计记录数的SQL
            MappedStatement statement = getSqlSession().getConfiguration()
                    .getMappedStatement(sqlID);
            BoundSql boundSql = statement.getBoundSql(params);
            String countSql = boundSql.getSql().trim();
            Object parameterObject = boundSql.getParameterObject();
            countSql = countSql == null ? countSql : countSql.trim();
            countSql = "select count(*) from (" + countSql + ") t";
            logger.debug("countSql ==》" + countSql.replaceAll("\n", " "));
            PreparedStatement countStmt = getSqlSession().getConnection()
                    .prepareStatement(countSql.toString());
            BoundSql countBS = new BoundSql(statement.getConfiguration(),
                    countSql.toString(), boundSql.getParameterMappings(),
                    parameterObject);
            setParameters(countStmt, statement, countBS, parameterObject);
            ResultSet result = countStmt.executeQuery();
            int count = 0;
            try {
                while (result.next()) {
                    count = result.getInt(1);
                }

            } catch (SQLException e) {
                throw e;
            } finally {
                params.put("startRow", startRow);
                params.put("pageSize", pageSize);
                try {
                    if (result != null) {
                        result.close();
                    }
                } catch (SQLException e) {
                    throw e;
                }
                try {
                    if (countStmt != null) {
                        countStmt.close();
                    }
                } catch (SQLException e) {
                    throw e;
                }
            }
            return count;
        }
    }

    /**
     * 查询记录数
     *
     * @param sqlID sqlmap文件sql语句对应的id
     * @param params 查询条件
     * @return int 记录数
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public Integer count(String sqlID, Map params) throws Exception {
//		Map parameter = prepareParameter(params);
        return count(sqlID, params, true);
    }


    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.
     * DefaultParameterHandler
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setParameters(PreparedStatement ps,
                               MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters")
                .object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql
                .getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration
                    .getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null
                    : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry
                            .hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName
                            .startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value)
                                    .getValue(
                                            propertyName.substring(prop
                                                    .getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject
                                .getValue(propertyName);
                    }
                    
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException(
                                "There was no TypeHandler found for parameter "
                                        + propertyName + " of statement "
                                        + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value,
                            parameterMapping.getJdbcType());
                    logger.debug("参数：" + propertyName + "=>参数值：" + value);
                }
            }
        }
    }
    
    /**
     * 开发和测试模式下 打印参数值  
     * @param obj  
     */
    private void printObjectValue(Object obj){
    	 if(DEV_MODEL.equals(Constants.SYSTEM_RUN_MODEL)) {
             ObjectUtils.printObjectValue(obj);
         }
    }
    
    @Override
    public Map<String, String> execProc(String procName, Map<String, Object> paraMap) throws Exception {
    	getSqlSession().update("procMapper." + procName, paraMap);
    	return null;
    }
    
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
    	super.setSqlSessionFactory(sqlSessionFactory);
    }
}
