package com.yh.websocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

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
	public WsServer(int port,SqlSessionFactory factory) {
        super(new InetSocketAddress(port));
        this.factory = factory;
        //this.executor = executor;
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
        //userLeave(conn);
        System.out.println(reason);
    }    
 

    @Override
    public void onMessage(WebSocket conn, String message) {
        if(null != message&&(!message.trim().equals(""))){//空字符串不处理
            //userJoin(conn,conn.getRemoteSocketAddress().toString());//用户加入
            SqlSession sqlSession = factory.openSession();
            try {            	
                CscDao cscMapper = sqlSession.getMapper(CscDao.class);
                List<String> allQues = cscMapper.getAllQuesti();//二级缓存有效
                if(allQues.contains(message.trim())) {//如果直接是问题，不需要分词，直接返回答案
                	String answer = cscMapper.getAnswFromQues(message.trim());//取问题，得到答案
    	  	      	cscMapper.updaCountFromQues(message.trim());
    	  	        conn.send(answer);
    	  	        sqlSession.commit();
                }else {//如果不是问题，先分词，再返回问题
                	participle p = new participle(message.trim());       
                    List<Term> terms = p.partWord(); //分词
                	ArrayList<String> matchKey = new ArrayList<String>();
        	      	List<String> key = cscMapper.getAllKey();//匹配关键字
        	      	for(int i = 0;i<key.size();i++) {
        	      		for(int j =0;j<terms.size(); j++) {
        	      			String term = terms.get(j).getName().trim();
        	      			if((!term.equals(""))&&key.get(i).trim().contains(term)) {
        	      				matchKey.add(key.get(i));
        	      			}
        	      		}
        	      	}
        	      	List<String> question = cscMapper.getQuesFromKey(matchKey);//通过关键字获取到问题
        	      	conn.send(question.toString());
        	      	sqlSession.commit();
                }
            }catch (Exception e) {
	          sqlSession.rollback();//出现问题回退
	      }finally {    	
	          sqlSession.close();//关闭连接
	      }
            
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
//    private void userLeave(WebSocket conn){
//        WsPool.removeUser(conn);
//    }
    /**
     * 将websocket加入用户池
     * @param conn
     * @param userName
     */
//    private void userJoin(WebSocket conn,String userName){
//        WsPool.addUser(userName, conn);
//    }



}
