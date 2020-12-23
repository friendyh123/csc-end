package com.yh.dao;
import java.util.List;

import com.yh.entity.User;
public interface UserDao {
	public void insert(User user);

    public User findUserById (int userId);

    public List<User> findAllUsers();

}
