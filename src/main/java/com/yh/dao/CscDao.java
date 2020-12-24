package com.yh.dao;
import java.util.List;

import com.yh.entity.Csc;
import com.yh.entity.User;
public interface CscDao {
	//public void insert(User user);

    public Csc findCscByKey (String key);  
    
    public List<String> queryAllKey();

    //public List<User> findAllUsers();

}
