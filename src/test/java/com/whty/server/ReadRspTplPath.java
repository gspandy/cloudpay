package com.whty.server;

import com.whty.utils.file.Resource;

public class ReadRspTplPath {
public static void main(String[] args) {
	String filePath = Resource.getRspToUnionPayTplResourcePath("002-iss2hce-yunkashengqin.txt");
	System.out.println("filePath:"+filePath);
}
}
