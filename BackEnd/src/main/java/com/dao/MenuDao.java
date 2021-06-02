package com.dao;

import com.bean.MainMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yu
 * @title: MenuDao
 * @projectName demo
 * @description: TODO
 * @date 2021/6/1/1:04
 */
@Mapper
public interface MenuDao {
    public List<MainMenu> getMainMenus();
}
