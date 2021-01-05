package com.gfzq.csc_end;

import org.apache.ibatis.session.SqlSessionFactory;
import org.java_websocket.WebSocketImpl;
import com.yh.websocket.WsServer;
/** 
 * 
 * 主程序*/
public class cscend {
	
	public static void main(String[] args) {
		SqlSessionFactory factory = sessionFactory.getSessionFactory();		
		WebSocketImpl.DEBUG = true;
	    int port = 8887; // 端口
	    WsServer s = new WsServer(port,factory);
	    s.start();

	}


}