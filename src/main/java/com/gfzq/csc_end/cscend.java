package com.gfzq.csc_end;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yh.dao.CscDao;
import com.yh.dao.UserDao;
import com.yh.entity.Csc;
import com.yh.entity.User;

public class cscend {
	
	public static void exec(String str) {
   
    participle p = new participle(str);       
    List<Term> terms = p.partWord(); //分词

//    for(int i=0; i<terms.size(); i++) {
//        String word = terms.get(i).getName(); 
//        //System.out.println(word);
//    }
    
    SqlSession sqlSession = getSessionFactory().openSession();
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

	List<String> question = cscMapper.getQuesFromKey(matchKey);
	String answer = cscMapper.getAnswFromQues(question.get(0));//先假设取第一个问题
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
    exec("开放式基金账户开立");
}
}