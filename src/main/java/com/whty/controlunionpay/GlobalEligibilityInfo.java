package com.whty.controlunionpay;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.http.NettyHttpRequest;
import com.whty.http.NettyHttpResponse;
import com.whty.http.NettyHttpServlet;
import com.whty.service.basicData.model.DataDict;
import com.whty.service.basicData.service.IDataInfoService;
import com.whty.utils.file.Resource;
import com.whty.utils.soap.SoapUtil;
import com.whty.utils.spring.SpringHelper;
import com.whty.utils.xml.InitParam;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: GlobalEligibilityInfo 
 *
 * Description:根据web.xml配置文件中的urlpattern匹配业务处理,进行具体的业务处理
 * 
 * Copyright: Copyright (c) 2015年8月8日 下午3:43:36 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class GlobalEligibilityInfo extends NettyHttpServlet {
	private static Logger logger = LoggerFactory.getLogger(GlobalEligibilityInfo.class);
	public void init(InitParam param) {
		String filePath = param.getValue();
		if (StringUtils.isNotBlank(filePath)) {
			System.setProperty("UNIONPAY_HOME", filePath);
		}
		String UNIONPAY_HOME = System.getProperty("UNIONPAY_HOME");
		if (StringUtils.isBlank(UNIONPAY_HOME)) {
			String ENV_UNIONPAY_HOME = System.getenv("UNIONPAY_HOME");
			if (StringUtils.isBlank(ENV_UNIONPAY_HOME)) {
				logger.error("GlobalEligibilityInfo启动失败, 没有设置UNIONPAY_HOME");
			} else {
				System.setProperty("UNIONPAY_HOME", ENV_UNIONPAY_HOME);
			}
		}
		logger.info("GlobalEligibilityInfo启动成功, UNIONPAY_HOME=[{}]", System.getProperty("UNIONPAY_HOME"));
	}

	@Override
	public void processHttp(NettyHttpRequest nettyRequest, NettyHttpResponse nettyHttpResponse) {
		logger.info("[银联云支付GlobalEligibilityInfo服务]处理请求开始......");
		long start = System.currentTimeMillis();

		String reqData = null;//请求的soap报文
		String rspData = null;//响应的soap报文
		try {
			reqData = nettyRequest.getContent();
			rspData = doService(reqData);//soap解析+业务处理
			//修改返回的nettyResponse
			nettyHttpResponse.addHeader("state", "S");
			nettyHttpResponse.setContent(new String(rspData));

		} catch (Exception e) {
			logger.error("GlobalEligibilityInfo服务处理异常", e);
			nettyHttpResponse.addHeader("state", "F");
			nettyHttpResponse.setContent(new String(rspData));
		}
		logger.info("[银联云支付GlobalEligibilityInfo服务]请求处理结束！");
		
		//debug模式下计算请求处理耗时
		if(logger.isDebugEnabled()){
			double time = (System.currentTimeMillis()-start)/1000.00;
			logger.debug("[银联云支付GlobalEligibilityInfo服务]请求处理耗时：{}秒", time);
		}
	}
	
	/**
	 * 调用具体业务处理方法  
	 * @param reqSoapStr
	 * @return
	 */
	private String doService(String reqSoapStr) {
		
		//定义返回值
		String rspSoapStr = null;
		if(reqSoapStr == null){
			logger.info("请求的soap为空!业务无法处理");
		}
		//soap报文解析
		 HashMap<String,String> soapMap = new HashMap<String,String>();
		try {
			SoapUtil.parseSoapStr(reqSoapStr);
			soapMap = SoapUtil.map;
		} catch (DocumentException e) {
			logger.error("GlobalEligibilityInfo服务:doService处理异常", e);
			e.printStackTrace();
		}

		//调试用
		if(logger.isDebugEnabled()){
			Iterator<Entry<String, String>> iter = soapMap.entrySet().iterator();
			while(iter.hasNext()){
				Entry<String, String> entry =  iter.next();
				System.out.println("key["+entry.getKey()+"],"+"value["+entry.getValue()+"]");
			}
			
			/*****************************************业务逻辑处理Start*************************************/
			
			//数据库操作
			IDataInfoService dataInfoService = SpringHelper.getBean("dataInfoService");
			//查找
			Map<String, DataDict> dataDictMap = new HashMap<String, DataDict>();
			try {
	 			//数据字典加载
				List<DataDict> dataDictList = dataInfoService.getAllList("dataDictMapper.selectList", null);
	 			for(DataDict dataDict : dataDictList){
	 				dataDictMap.put(dataDict.getId(), dataDict);
	 			}
	 			logger.info("{}配置共计加载{}项", "数据字典加载初始化", dataDictMap.size());
	 			
	 			
			
			
			   //获取流水号
        	String  txnNo = dataInfoService.getTxnNo("SEQ_COMMON");
        	logger.info("申请到的当前seq为:"+txnNo);

			//数据库操作
        	String id = String.valueOf((int)(Math.random()*10000));
			DataDict dataDict = new DataDict();
			dataDict.setId(id);
			dataDict.setItemName("测试11111");
			dataDict.setColNameCn("测试11111");
			dataDict.setColName("ceshi11111");
			dataDict.setItemVal("测试11111");
			dataDict.setItemDesc("测试11111");
			dataDict.setItemType("00");
			dataDict.setStatus("00");
			dataDict.setHandleStatus("00");
			dataDict.setCreateUserName("zyd");
	        //插入一条字典数据
			if(dataInfoService.add("dataDictMapper.insertModel", dataDict)){
				logger.info("****************插入字典记录成功******************");
			}
			
			
	        //修改一条字典数据
			dataDict.setId(id);
			dataDict.setItemName("修改00");
			dataDict.setColNameCn("修改00");
			dataDict.setColName("ceshi0");
			dataDict.setItemVal("修改00");
			dataDict.setItemDesc("修改00");
			dataDict.setItemType("00");
			dataDict.setStatus("00");
			dataDict.setHandleStatus("00");
			dataDict.setCreateUserName("mdy");
			if(dataInfoService.edit("dataDictMapper.updateById", dataDict)){
				logger.info("****************修改字典记录成功******************");
			}
	        //删除一条字典数据
			if(dataInfoService.removeById("dataDictMapper.deleteById", id)){
				logger.info("***************删除入字典记录成功******************id:"+id);
			}
			
			//初始化模板配置的数组
			String placeHolder[][] =new String [4][2];
			//业务参数设置
			placeHolder[0][0] = "@value1@";placeHolder[0][1] = "1232434五六七八";
			placeHolder[1][0] = "@value2@";placeHolder[1][1] = "22222五六七八";
			placeHolder[2][0] = "@value3@";placeHolder[2][1] = "333333";
			placeHolder[3][0] = "@value4@";placeHolder[3][1] = id;
			
			//返回soap报文(返回数据模板文件读取+替换业务参数)
			String filePath = Resource.getRspToUnionPayTplResourcePath("002-iss2hce-yunkashengqin.txt");
			rspSoapStr = SoapUtil.readFromFile(new File(filePath));
			try {
				rspSoapStr = SoapUtil.wapperSoapStr(rspSoapStr, placeHolder);
				logger.info("替换业务参数后响应的soap报文[{}]:",rspSoapStr);
			} catch (DocumentException e) {
				e.printStackTrace();
				logger.error("soap报文解析有误{}", e.getMessage());
			}
			
		 } catch (Exception e) {
				logger.error("业务处理异常 错误信息{}", e.getMessage());
			}

		}
			
		return  rspSoapStr;
		

	}

	@Override
	public String processTcp(String txnCode, int length, String xmlStr) {
		// TODO Auto-generated method stub
		return null;
	}
}
