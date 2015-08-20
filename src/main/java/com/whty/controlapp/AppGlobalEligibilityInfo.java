package com.whty.controlapp;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.http.NettyHttpRequest;
import com.whty.http.NettyHttpResponse;
import com.whty.http.NettyHttpServlet;
import com.whty.innersocket.sendTo.S_DemoMessage;
import com.whty.innersocket.sendTo.S_DemoPublic;
import com.whty.utils.common.CommonFunction;
import com.whty.utils.common.Constants;
import com.whty.utils.file.Resource;
import com.whty.utils.json.JsonUtil;
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
public class AppGlobalEligibilityInfo extends NettyHttpServlet {
	private static Logger logger = LoggerFactory.getLogger(AppGlobalEligibilityInfo.class);
	public void init(InitParam param) {
		String filePath = param.getValue();
		if (StringUtils.isNotBlank(filePath)) {
			System.setProperty("PHONEAPP_HOME", filePath);
		}
		String PHONEAPP_HOME = System.getProperty("PHONEAPP_HOME");
		if (StringUtils.isBlank(PHONEAPP_HOME)) {
			String ENV_PHONEAPP_HOME = System.getenv("PHONEAPP_HOME");
			if (StringUtils.isBlank(ENV_PHONEAPP_HOME)) {
				logger.error("GlobalEligibilityInfo启动失败, 没有设置PHONEAPP_HOME");
			} else {
				System.setProperty("PHONEAPP_HOME", ENV_PHONEAPP_HOME);
			}
		}
		logger.info("GlobalEligibilityInfo启动成功, PHONEAPP_HOME=[{}]", System.getProperty("PHONEAPP_HOME"));
	}

	@Override
	public void processHttp(NettyHttpRequest nettyRequest, NettyHttpResponse nettyHttpResponse) {
		logger.info("[手机渠道发送GlobalEligibilityInfo服务]处理请求开始......");
		long start = System.currentTimeMillis();

		String reqData = null;//请求的json报文
		String rspData = null;//响应的json报文
		try {
			reqData = nettyRequest.getContent();
			rspData = doService(reqData);//json解析+业务处理
			//修改返回的nettyResponse
			nettyHttpResponse.addHeader("state", "S");
			nettyHttpResponse.setContent(new String(rspData));

		} catch (Exception e) {
			logger.error("GlobalEligibilityInfo服务处理异常", e);
			nettyHttpResponse.addHeader("state", "F");
			nettyHttpResponse.setContent(new String(rspData));
		}
		logger.info("[手机渠道发送GlobalEligibilityInfo服务]请求处理结束！");
		
		//debug模式下计算请求处理耗时
		if(logger.isDebugEnabled()){
			double time = (System.currentTimeMillis()-start)/1000.00;
			logger.debug("[手机渠道发送GlobalEligibilityInfo服务]请求处理耗时：{}秒", time);
		}
	}
	
	/**
	 * 调用具体业务处理方法  
	 * @param reqjsonStr
	 * @return
	 */
	private String doService(String reqjsonStr) {
		//定义返回值
		String rspjsonStr = null;
		if(reqjsonStr == null){
			logger.info("请求的json为空!业务无法处理");
		}
		//json报文解析
		 Map<String,Object> jsonMap = new HashMap<String,Object>();
		try {
			jsonMap = JsonUtil.parseJSON2Map(reqjsonStr);
		} catch (Exception e) {
			logger.error("GlobalEligibilityInfo服务:doService处理异常", e);
			e.printStackTrace();
		}

		//业务处理
		if(logger.isDebugEnabled()){
			Iterator<Entry<String, Object>> iter = jsonMap.entrySet().iterator();
			while(iter.hasNext()){
				Entry<String, Object> entry =  iter.next();
				System.out.println("key["+entry.getKey()+"],"+"value["+entry.getValue()+"]");
			}
			
			//业务逻辑处理
			 //socket通信(发送报文:4位长度+xml报文, 返回报文:4为长度+xml报文)
			 S_DemoPublic pubic = new S_DemoPublic();
			 pubic.setsCode("9000");
			 pubic.setTermCode("8888");
			 
			 S_DemoMessage entity = new S_DemoMessage();
			 entity.setPubic(pubic);
			 
			 JAXBContext context1;
//			 JAXBContext context2;
				try {
					context1 = JAXBContext.newInstance(new Class[] {S_DemoMessage.class});
					Marshaller mar = context1.createMarshaller();
					// mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					mar.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
					mar.setProperty(Marshaller.JAXB_FRAGMENT, true);
					StringWriter writer = new StringWriter();
					mar.marshal(entity, writer);
					String sendXml = Constants.XMLBAOWENHEADER+writer.toString();
					
					String sendXmlByteLength = CommonFunction.fillString(Integer.toHexString(sendXml.getBytes("UTF-8").length), '0', 4, false);//10进制转换为16进制
					String sendContent = sendXmlByteLength + sendXml;
					 logger.info("长度:" + sendXmlByteLength + ",发送测试报文:"+ sendContent);
					 
					// socket通信发送报文
//					SocketConnect s = new SocketConnect(sendContent);
//					s.run("GB18030");
//					String acceptXml = s.getRsp().trim();	
//					String acceptXmlLength = String.valueOf(Integer.parseInt(acceptXml.substring(0, 4),16));//16进制转换为10进制
//					String acceptContent = acceptXml.substring(4);							
//					logger.info("长度:" + acceptXmlLength + ",接收测试报文:"+ acceptContent);
//					s.close();
//	
					//String acceptXml ="0078<?xml version=\"1.0\" encoding=\"GB18030\"?><Message><Public><SCode>9000</SCode><TermCode>8888</TermCode></Public></Message>";
					//String acceptXmlLength = String.valueOf(Integer.parseInt(acceptXml.substring(0, 4),16));
					//String acceptContent = acceptXml.substring(4);
//					context2 = JAXBContext.newInstance(new Class[] {A_DemoMessage.class});
//					Unmarshaller um = context2.createUnmarshaller();
//					A_DemoMessage accept = (A_DemoMessage) um.unmarshal(new StringReader(acceptContent));
//					logger.info("接收报文解析sCode:" + accept.getPubic().getsCode());
//					logger.info("接收报文解析TermCode:" + accept.getPubic().getTermCode());
				}catch(Exception e){
					logger.info("通讯报文解析失败"+e.getMessage());
					e.printStackTrace();
				}
			
			
			
			//返回json报文
			//初始化模板配置的数组
			String placeHolder[][] =new String [3][2];
			//业务参数设置
			placeHolder[0][0] = "@value1@";placeHolder[0][1] = "张玉栋";
			placeHolder[1][0] = "@value2@";placeHolder[1][1] = "武汉天喻信息产业股份有限公司";
			placeHolder[2][0] = "@value3@";placeHolder[2][1] = "333333";
			
			//返回json报文(返回数据模板文件读取+替换业务参数)
			String filePath = Resource.getRspToPhoneAppTplResourcePath("001-app2hce-yunkashengqin.txt");
			rspjsonStr = JsonUtil.readFromFile(new File(filePath));
			try {
				rspjsonStr = JsonUtil.wapperJsonStr(rspjsonStr, placeHolder);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return  rspjsonStr;

	}

	@Override
	public String processTcp(String txnCode, int length, String xmlStr) {
		// TODO Auto-generated method stub
		return null;
	}
}
