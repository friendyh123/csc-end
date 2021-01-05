package com.gfzq.csc_end;

import java.util.ArrayList;
import java.util.List;

import org.ansj.domain.Term;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.yh.dao.CscDao;

/**
 * 收到客户端连接之后开启的线程
 */
public class newTask implements Runnable {

	private String str;
	SqlSessionFactory factory;

	public newTask(String str, SqlSessionFactory factory) {
		this.str = str;
		this.factory = factory;
	}

	@Override
	public void run() {
		//System.out.println("正在执行task " + str);
		exec(str, factory);
		//System.out.println("task " + str + "执行完毕");
	}

	// 主要执行逻辑
	private void exec(String str, SqlSessionFactory factory) {
		participle p = new participle(str);
		List<Term> terms = p.partWord(); // 分词
		SqlSession sqlSession = factory.openSession();
		try {
			CscDao cscMapper = sqlSession.getMapper(CscDao.class);
			ArrayList<String> matchKey = new ArrayList<>();
			List<String> key = cscMapper.getAllKey();// 匹配关键字
			for (int i = 0; i < key.size(); i++) {
				for (int j = 0; j < terms.size(); j++) {
					if (key.get(i).trim().contains(terms.get(j).getName().trim())) {
						matchKey.add(key.get(i));
					}
				}
			}
			List<String> question = cscMapper.getQuesFromKey(matchKey);// 通过关键字获取到问题
			String answer = cscMapper.getAnswFromQues(question.get(0));// 先假设取第一个问题，得到答案
			cscMapper.updaCountFromQues(question.get(0));
			System.out.println(answer);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();// 出现问题回退
		} finally {
			sqlSession.close();// 关闭连接
		}
	}

}
