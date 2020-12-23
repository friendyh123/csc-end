package com.gfzq.scs_end;
import java.util.HashSet;
import java.util.List;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

public class scsend {
	
	public static void test() {
    //只关注这些词性的词
    participle p = new participle("开放式基金账户开立");
    
    
    List<Term> terms = p.partWord(); //拿到terms
    //System.out.println(terms.size());
    
    

    for(int i=0; i<terms.size(); i++) {
        String word = terms.get(i).getName(); //拿到词
        
        System.out.println(word);
//        String natureStr = terms.get(i).getNatureStr(); //拿到词性
//        if(expectedNature.contains(natureStr)) {
//            System.out.println(word + ":" + natureStr);
//        }
    }
}

public static void main(String[] args) {
    test();
}
}