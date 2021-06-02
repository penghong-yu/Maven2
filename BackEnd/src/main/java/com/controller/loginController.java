package com.controller;

import com.bean.User;
import com.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yu
 * @title: LoginController
 * @projectName demo
 * @description: TODO
 * @date 2021/5/31/22:35
 */

@RestController

public class loginController {

    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping("/login")
    public String login(@RequestBody User user){
        System.out.println("User : " + user);
        String str = "error";
        int count = userDao.getUserByMessage(user.getUsername(), user.getPassword());
        if (count > 0) {
            str = "ok";
        }
        return str;
    }
}
