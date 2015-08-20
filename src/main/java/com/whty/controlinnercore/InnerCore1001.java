package com.whty.controlinnercore;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.http.NettyHttpRequest;
import com.whty.http.NettyHttpResponse;
import com.whty.http.NettyHttpServlet;
import com.whty.utils.file.Resource;
import com.whty.utils.soap.SoapUtil;
import com.whty.utils.tcpxml.TcpXmlUtil;
import com.whty.utils.xml.InitParam;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: InnerCore1001 
 *
 * Description:接收核心发送的交易1001
 * 
 * Copyright: Copyright (c) 2015年8月19日 下午3:19:07 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class InnerCore1001 extends NettyHttpServlet {
	private static Logger logger = LoggerFactory.getLogger(InnerCore1001.class);
	public void init(InitParam param) {
		String filePath = param.getValue();
		if (StringUtils.isNotBlank(filePath)) {
			System.setProperty("INNERCORE_HOME", filePath);
		}
		String INNERCORE_HOME = System.getProperty("INNERCORE_HOME");
		if (StringUtils.isBlank(INNERCORE_HOME)) {
			String ENV_INNERCORE_HOME = System.getenv("INNERCORE_HOME");
			if (StringUtils.isBlank(ENV_INNERCORE_HOME)) {
				logger.error("InnerCore1001启动失败, 没有设置INNERCORE_HOME");
			} else {
				System.setProperty("INNERCORE_HOME", ENV_INNERCORE_HOME);
			}
		}
		logger.info("InnerCore1001启动成功, INNERCORE_HOME=[{}]", System.getProperty("INNERCORE_HOME"));
	}

	@Override
	public String processTcp(String txnCode,int length,String xmlStr) {
		logger.info("[核心渠道发送InnerCore1001服务]处理请求开始......");
		long start = System.currentTimeMillis();

		String reqData = null;//请求的xml报文
		String rspData = null;//响应的xml报文
		try {
			reqData = xmlStr;
			rspData = doService(reqData);//xml解析+业务处理

		} catch (Exception e) {
			logger.error("InnerCore1001服务处理异常", e);
		}
		logger.info("[核心渠道发送InnerCore1001服务]请求处理结束！");
		
		//debug模式下计算请求处理耗时
		if(logger.isDebugEnabled()){
			double time = (System.currentTimeMillis()-start)/1000.00;
			logger.debug("[核心渠道发送InnerCore1001服务]请求处理耗时：{}秒", time);
		}
		return rspData;
	}
	
	/**
	 * 调用具体业务处理方法  
	 * @param reqxmlStr
	 * @return
	 */
	private String doService(String reqxmlStr) {
		//定义返回值
		String rspxmlStr = null;
		if(reqxmlStr == null){
			logger.info("请求的报文为空!业务无法处理");
		}
		//xml报文解析
		 Map<String,String> xmlMap = new HashMap<String,String>();
		try {
			 SoapUtil.parseSoapStr(reqxmlStr);
			 xmlMap = SoapUtil.map;
		} catch (Exception e) {
			logger.error("InnerCore1001服务:doService处理异常", e);
			e.printStackTrace();
		}

		//业务处理
		if(logger.isDebugEnabled()){
			Iterator<Entry<String, String>> iter = xmlMap.entrySet().iterator();
			while(iter.hasNext()){
				Entry<String, String> entry =  iter.next();
				System.out.println("key["+entry.getKey()+"],"+"value["+entry.getValue()+"]");
			}
			
			//业务逻辑处理
			 
			
			
			
			//返回xml报文
			//初始化模板配置的数组
			String placeHolder[][] =new String [3][2];
			//业务参数设置
			placeHolder[0][0] = "@value1@";placeHolder[0][1] = "张玉栋";
			placeHolder[1][0] = "@value2@";placeHolder[1][1] = "武汉天喻信息产业股份有限公司";
			placeHolder[2][0] = "@value3@";placeHolder[2][1] = "333333";
			
			//返回xml报文(返回数据模板文件读取+替换业务参数)
			String filePath = Resource.getRspToInnerCoreTplResourcePath("O2C01001");
			rspxmlStr = TcpXmlUtil.readFromFile(new File(filePath));
			try {
				rspxmlStr = TcpXmlUtil.wapperXmlStr(rspxmlStr, placeHolder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return  rspxmlStr;

	}

	@Override
	public void processHttp(NettyHttpRequest nettyRequest,
			NettyHttpResponse nettyHttpResponse) {
		// TODO Auto-generated method stub
		
	}
}
