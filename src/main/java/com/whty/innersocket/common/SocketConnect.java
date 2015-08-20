package com.whty.innersocket.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.utils.common.Constants;

public class SocketConnect {
	private String msg = null;
	private Socket socket = null;
	private String rsp;
	private OutputStream outputStream = null;
	private InputStream inputstream = null;
	public boolean isOver = false;
	protected static Logger log = LoggerFactory.getLogger(SocketConnect.class);
	public SocketConnect() {
		try {
			this.msg = "Hello World";
			// 发送地址IP
						String ip = Constants.SOCKET_IP;
						// 发送地址端口
						int port = Integer.parseInt(Constants.SOCKET_PORT);
						socket = new Socket(ip, port);
						// 设置超时
						socket.setSoTimeout(Integer.parseInt(Constants.SOCKET_TIMEOUT));
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		} catch (ConnectException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	/**
	 * 发送报文
	 * 
	 * @param txnCode
	 * @param mchntCd
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public SocketConnect(String msg) throws UnknownHostException, IOException {
		try {
			this.msg = msg;
			// 发送地址IP
			String ip = Constants.SOCKET_IP;
			// 发送地址端口
			int port = Integer.parseInt(Constants.SOCKET_PORT);
			socket = new Socket(ip, port);
			// 设置超时
			socket.setSoTimeout(Integer.parseInt(Constants.SOCKET_TIMEOUT));
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		} catch (ConnectException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			sendMessage(msg);
			this.rsp = getMessage();
			log.info(rsp);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void run(String encoding) {
		try {
			sendMessage(msg,encoding);
			this.rsp = getMessageByLen(encoding);
			log.info("原始返回报文:"+rsp);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * 发送报文
	 * 
	 * @param req
	 * @throws IOException
	 */
	private void sendMessage(String req) throws IOException {
		byte[] reqByte = req.getBytes();
		outputStream.write(reqByte);
		outputStream.flush();
	}

	
	/**
	 * 发送报文 指定编码
	 * 
	 * @param req
	 * @throws IOException
	 */
	private void sendMessage(String req,String encoding) throws IOException {
		byte[] reqByte = req.getBytes(encoding);
		outputStream.write(reqByte);
		outputStream.flush();
	}
	
	
	/**
	 * 获取报文
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getMessage() throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(
				inputstream));
		String str = null;
		while (!isOver) {
			str = bf.readLine();
			if (str != null)
				isOver = true;
		}
		return str;
	}

	private String getMessage(String encoding) throws IOException {
		byte[] rspBytes = new byte[8192];
		int offsize = 0;
		int len = 0;
		while ((len = inputstream.read()) != -1) {
			rspBytes[offsize] = (byte) len;
			inputstream.read(rspBytes, offsize + 1, len);
			offsize += len + 1;
		}
		
		return new String(rspBytes, encoding);
		// byte[] rspBytes = new byte[1024];
		// int len = 0;
		// if((len = inputstream.available()) > 0 && len<1023) {
		// inputstream.read(rspBytes, 2, len);
		// }
		// return new String(rspBytes,encoding).substring(2);
	}
	
	
	private String getMessageByLen(String encoding) throws IOException{
		byte[] lenBytes = new byte[4];
		byte[] rspBytes = new byte[8192];
		inputstream.read(lenBytes, 0, 4);
		String len = new String(lenBytes,encoding.trim());
		inputstream.read(rspBytes, 0, Integer.parseInt(len));
		return (len+new String(rspBytes,encoding.trim())).trim();
	}
	/**
	 * @return the rsp
	 */
	public String getRsp() {
		return rsp;
	}
	/**
	 * @param rsp
	 *            the rsp to set
	 */
	public void setRsp(String rsp) {
		this.rsp = rsp;
	}

	public void close() throws IOException {
		if (!socket.isClosed()) {
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		}
	}
}
