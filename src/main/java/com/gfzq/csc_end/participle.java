package com.gfzq.csc_end;

import java.util.List;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

/**
 * 分词
 */
public class participle {
	String str;// 分词字符串

	public participle(String str) {
		super();
		this.str = str;
	}

	public List<Term> partWord() {

		Result result = ToAnalysis.parse(str); // 分词结果的一个封装，主要是一个List<Term>的terms
		// System.out.println(result.getTerms());

		List<Term> terms = result.getTerms(); // 拿到terms
		return terms;

		// System.out.println(terms.size());

//  for(int i=0; i<terms.size(); i++) {
//      String word = terms.get(i).getName(); //拿到词
//      System.out.println(word);
////      String natureStr = terms.get(i).getNatureStr(); //拿到词性
////      if(expectedNature.contains(natureStr)) {
////          System.out.println(word + ":" + natureStr);
////      }
//  }

	}
}
