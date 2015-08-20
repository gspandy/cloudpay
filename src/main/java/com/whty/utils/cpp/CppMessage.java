package com.whty.utils.cpp;

public final class CppMessage {
private Header header;//消息头
private Object body;//消息体
public Header getHeader() {
	return header;
}
public void setHeader(Header header) {
	this.header = header;
}
public Object getBody() {
	return body;
}
public void setBody(Object body) {
	this.body = body;
}
@Override
public String toString() {
	return "CppMessage [header=" + header + "]";
}

}
