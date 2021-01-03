package com.gfzq.csc_end;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.ansj.domain.Term;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.java_websocket.WebSocketImpl;

import com.yh.dao.CscDao;
import com.yh.websocket.WsServer;
/** 
 * 
 * 主程序*/
public class cscend {
	
	public static void main(String[] args) {
		SqlSessionFactory factory = sessionFactory.getSessionFactory();
		ExecutorService executor = Executors.newFixedThreadPool(4);	//线程数量为4
		WebSocketImpl.DEBUG = true;
	    int port = 8887; // 端口
	    WsServer s = new WsServer(port,factory,executor);
	    s.start();
		

	    
//	    for(int i=0;i<2;i++){
//	    	newTask task = new newTask("开放式基金账户开立",factory);
//	        executor.execute(task);
//	    }    
	    
	    //executor.shutdown();//关闭线程池

	}
	
//	public static void exec(String str,SqlSessionFactory factory) {
//    participle p = new participle(str);       
//    List<Term> terms = p.partWord(); //分词
//    SqlSession sqlSession = factory.openSession();
//    try {    	
//        CscDao cscMapper = sqlSession.getMapper(CscDao.class);    
//        ArrayList<String> matchKey = new ArrayList<String>();
//    	List<String> key = cscMapper.getAllKey();//匹配关键字
//    	for(int i = 0;i<key.size();i++) {
//    		for(int j =0;j<terms.size(); j++) {
//    			if(key.get(i).trim().contains(terms.get(j).getName().trim())) {
//    				matchKey.add(key.get(i));
//    			}
//    		}
//    	}
//    	List<String> question = cscMapper.getQuesFromKey(matchKey);//通过关键字获取到问题
//    	String answer = cscMapper.getAnswFromQues(question.get(0));//先假设取第一个问题，得到答案
//    	cscMapper.updaCountFromQues(question.get(0));
//    	System.out.println(answer);
//    	sqlSession.commit();
//    }catch (Exception e) {
//        sqlSession.rollback();//出现问题回退
//    }finally {    	
//        sqlSession.close();//关闭连接
//    }
//}
	

//static class newTask implements Runnable {
//	   //private int taskNum;
//	   private String str; 
//	   SqlSessionFactory factory;
//	   public newTask(String str,SqlSessionFactory factory) {
//	       this.str = str;
//	       this.factory = factory;
//	   }
//	    
//	   @Override
//	   public void run() {
//	       System.out.println("正在执行task "+str);
//	       exec(str,factory);
//	       System.out.println("task "+str+"执行完毕");
//	   }
//	}

}