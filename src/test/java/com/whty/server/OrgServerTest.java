package com.whty.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class OrgServerTest {

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            conn.setRequestProperty("Pragma:", "no-cache");  
            conn.setRequestProperty("Cache-Control", "no-cache");  
            conn.setRequestProperty("Content-Type", "text/xml");  
            
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }   

    
    private static String getXmlInfo() {  
        StringBuilder sb = new StringBuilder();  
//        sb.append("<?xml version='1.0' encoding='UTF-8'?>");  
        sb.append("<Finance>");  
        sb.append("<Message>");  
        sb.append(" <CSReq>");  
        sb.append("     <version>是地方数据恢复</version>");  
//        sb.append("     <appType>A</appType>");  //A 备付金系统
//        sb.append("     <srcReq>P</srcReq>"); //交易发起方 (P:支付机构)
//        sb.append("     <signatureType>0</signatureType>"); //安全校验类型(0-不校验 1-签名)
//        sb.append("     <transCode>6004</transCode>");  //交易代码
//        sb.append("     <transDate>20150623</transDate>");  //交易日期
//        sb.append("     <transTime>075959</transTime>"); //交易时间
//        sb.append("     <paySysSeq>20150623075959000000000001</paySysSeq>"); 
//        sb.append("     <senderInstId>100000000000001</senderInstId>"); 
//        sb.append("     <recverInstId>100000000000001</recverInstId>"); 
//        sb.append("     <corpNDate>20150623</corpNDate>"); 
//        sb.append("     <fileType>ZF</fileType>"); 
//        ZF：5.3中提到的机构端发送的文件: PF：5.5中提到的人行检验机制文件 ALL：所有文件
//        sb.append("     <misc1>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc1>"); 
//        sb.append("     <misc2>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc2>"); 
//        sb.append("     <misc3>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc3>"); 
//        sb.append("     <misc4>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc4>"); 
//        sb.append("     <misc5>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc5>"); 
//        sb.append("     <misc6>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc6>"); 
//        sb.append("     <misc7>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc7>"); 
//        sb.append("     <misc8>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc8>"); 
//        sb.append("     <misc9>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc9>"); 
//        sb.append("     <misc10>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc10>"); 
//        sb.append("     <misc11>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc11>"); 
//        sb.append("     <misc12>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc12>"); 
//        sb.append("     <misc13>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc13>"); 
//        sb.append("     <misc14>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc14>"); 
//        sb.append("     <misc15>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc15>"); 
//        sb.append("     <misc16>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc16>"); 
//        sb.append("     <misc17>10000000000000111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111</misc17>"); 

        sb.append(" </CSReq>");  
        sb.append(" <Signature>");  
        sb.append("");  
        sb.append(" </Signature>");  
        sb.append("</Message>");
        sb.append("<Finance>");  
          
        return sb.toString();  
    }  
    
	public static void main(String[] args) {
        //发送 GET 请求
//        String s=OrgServerTest.sendGet("http://127.0.0.1/DPESB0001", "flag=123&uid=456");
//        System.out.println(s);
        //发送 POST 请求
//        String sr=OrgServerTest.sendPost("http://127.0.0.1/DPESB0001", "flag=123&uid=456");
		  String sr=OrgServerTest.sendPost("http://127.0.0.1/DPESB0001", String.format("%04d", getXmlInfo().length())+getXmlInfo());//xml
        System.out.println(sr);

	}

}