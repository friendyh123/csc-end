package com.yh.dao;
import java.util.List;

import com.yh.entity.Csc;
import com.yh.entity.User;
public interface CscDao { 
    
    public List<String> getAllKey();
    
    public List<String> getQuesFromKey(List<String> keyyyy);
    
    public String getAnswFromQues(String questi);



}
