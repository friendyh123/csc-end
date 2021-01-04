package com.yh.dao;
import java.util.List;

import com.yh.entity.Csc;
/** 
 * 数据库操作接口
 * */
public interface CscDao { 
    
    public List<String> getAllKey();//获取所有关键字
    
    public List<String> getQuesFromKey(List<String> keyyyy);//通过关键字获取问题
    
    public String getAnswFromQues(String questi);//获取答案
    
    public void updaCountFromQues(String questi);//问题计数值

    public List<String> getAllQuesti();//获取所有问题getAllQuesti

}
