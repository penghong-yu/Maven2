package com.dao;

import com.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Yu
 * @title: UserDao
 * @projectName demo
 * @description: TODO
 * @date 2021/5/31/23:07
 */
@Mapper
public interface UserDao {
     public int getUserByMessage(@Param("username") String username,@Param("password") String password);
    public List<User> getAllUser(@Param("username") String username, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
    public int getUserCounts(@Param("username") String username);
    public int updateState(Integer id, Boolean state);
    public int addUser(User user);
    public User getUpdateUser(int id);
    public int editUser(User user);
    public int deleteUser(int id);

}
