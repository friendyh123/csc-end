package com.yh.dao;
import java.util.List;

import com.yh.entity.Scs;
import com.yh.entity.User;
public interface ScsDao {
	//public void insert(User user);

    public Scs findScsByKey (String key);

    //public List<User> findAllUsers();

}
