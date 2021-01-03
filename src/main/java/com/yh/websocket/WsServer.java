package com.yh.websocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.ansj.domain.Term;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.gfzq.csc_end.participle;
import com.yh.dao.CscDao;
public class WsServer extends WebSocketServer{
	SqlSessionFactory factory;
	ExecutorService executor;
	public WsServer(int port,SqlSessionFactory factory,ExecutorService executor) {
        super(new InetSocketAddress(port));
        this.factory = factory;
        this.executor = executor;
    }

    public WsServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // ws连接的时候触发的代码，onOpen中我们不做任何操作

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        //断开连接时候触发代码
        userLeave(conn);
        System.out.println(reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(message);
        //conn.send("收到");
        System.out.println(conn.getRemoteSocketAddress().toString());
        conn.getRemoteSocketAddress().toString();
        conn.getLocalSocketAddress();
        if(null != message &&message.startsWith("1")){//先分词
            //String userName=message.replaceFirst("online", message);//用户名
            userJoin(conn,conn.getRemoteSocketAddress().toString());//用户加入
            participle p = new participle(message.trim());       
            List<Term> terms = p.partWord(); //分词
            SqlSession sqlSession = factory.openSession();
	          try {    	
	          CscDao cscMapper = sqlSession.getMapper(CscDao.class);    
	          ArrayList<String> matchKey = new ArrayList<String>();
	      	List<String> key = cscMapper.getAllKey();//匹配关键字
	      	for(int i = 0;i<key.size();i++) {
	      		for(int j =0;j<terms.size(); j++) {
	      			if(key.get(i).trim().contains(terms.get(j).getName().trim())) {
	      				matchKey.add(key.get(i));
	      			}
	      		}
	      	}
	      	List<String> question = cscMapper.getQuesFromKey(matchKey);//通过关键字获取到问题
	      	conn.send(question.toString());
//	      	String answer = cscMapper.getAnswFromQues(question.get(0));//先假设取第一个问题，得到答案
//	      	cscMapper.updaCountFromQues(question.get(0));
//	      	System.out.println(answer);
//	      	sqlSession.commit();
	      }catch (Exception e) {
	          sqlSession.rollback();//出现问题回退
	      }finally {    	
	          sqlSession.close();//关闭连接
	      }
            
        }else if(null != message &&message.startsWith("2")){
        	SqlSession sqlSession = factory.openSession();
        	try {    	
  	          CscDao cscMapper = sqlSession.getMapper(CscDao.class);
  	        message = message.trim().replaceFirst("\\d", "");
	  	      	String answer = cscMapper.getAnswFromQues(message.trim());//先假设取第一个问题，得到答案
	  	      	cscMapper.updaCountFromQues(message.trim());
	  	        conn.send(answer);
	  	      	System.out.println(answer);
	  	      	sqlSession.commit();
  	      }catch (Exception e) {
  	          sqlSession.rollback();//出现问题回退
  	      }finally {    	
  	          sqlSession.close();//关闭连接
  	      }
        }
        else if(null != message && message.startsWith("offline")){
            userLeave(conn);
        }

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //错误时候触发的代码
        System.out.println("on error");
        ex.printStackTrace();
    }
    /**
     * 去除掉失效的websocket链接
     * @param conn
     */
    private void userLeave(WebSocket conn){
        WsPool.removeUser(conn);
    }
    /**
     * 将websocket加入用户池
     * @param conn
     * @param userName
     */
    private void userJoin(WebSocket conn,String userName){
        WsPool.addUser(userName, conn);
    }

}
