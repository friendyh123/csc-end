package com.yh.dao;
import java.util.List;

import com.yh.entity.Csc;
/** 
 * 数据库操作接口
 * */
public interface CscDao { 
    
    public List<String> getAllKey();
    
    public List<String> getQuesFromKey(List<String> keyyyy);
    
    public String getAnswFromQues(String questi);
    
    public void updaCountFromQues(String questi);



}
