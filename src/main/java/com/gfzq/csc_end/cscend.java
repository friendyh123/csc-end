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

import com.yh.dao.CscDao;

public class cscend {
	
	public static void exec(String str,SqlSessionFactory factory) {
    participle p = new participle(str);       
    List<Term> terms = p.partWord(); //分词

//    for(int i=0; i<terms.size(); i++) {
//        String word = terms.get(i).getName(); 
//        //System.out.println(word);
//    }
    
    SqlSession sqlSession = factory.openSession();
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
	String answer = cscMapper.getAnswFromQues(question.get(0));//先假设取第一个问题，得到答案
	System.out.println(answer);
	sqlSession.commit();
    sqlSession.close();//关闭连接
   
    
}
	
private static SqlSessionFactory getSessionFactory() {
    SqlSessionFactory sessionFactory = null;
    String resource = "configuration.xml";
    try {
        sessionFactory = new SqlSessionFactoryBuilder().build(Resources
                .getResourceAsReader(resource));
    } catch (IOException e) {
        e.printStackTrace();
    }
    return sessionFactory;
}

public static void main(String[] args) {
	SqlSessionFactory factory = getSessionFactory();
	ExecutorService executor = Executors.newFixedThreadPool(5);
	
//	exec("开放式基金账户开立",factory);
//	exec("开放式基金账户开立",factory);
//	exec("开放式基金账户开立",factory);
    
//    for(int i=0;i<2;i++){
//        MyTask myTask = new MyTask("开放式基金账户开立",factory);
//        executor.execute(myTask);
//    }
    //Thread.sleep(millis);
    MyTask myTask1 = new MyTask("开放式基金账户开立",factory);
    executor.execute(myTask1);
    try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    MyTask myTask2 = new MyTask("开放式基金账户开立",factory);
    executor.execute(myTask2);
    
    
    //executor.shutdown();//关闭线程池
   // exec("开放式基金账户开立");
}

static class MyTask implements Runnable {
	   //private int taskNum;
	   private String str; 
	   SqlSessionFactory factory;
	   public MyTask(String str,SqlSessionFactory factory) {
	       this.str = str;
	       this.factory = factory;
	   }
	    
	   @Override
	   public void run() {
	       System.out.println("正在执行task "+str);
	       exec(str,factory);
	       System.out.println("task "+str+"执行完毕");
	   }
	}

}