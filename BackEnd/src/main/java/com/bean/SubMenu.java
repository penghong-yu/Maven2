package com.bean;

/**
 * @author Yu
 * @title: SubMenu
 * @projectName demo
 * @description: TODO
 * @date 2021/6/1/1:08
 */
public class SubMenu {
    private int id;
    private String title;
    private String path;

    public SubMenu() {
    }

    public SubMenu(int id, String title, String path) {
        this.id = id;
        this.title = title;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "SubMenu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
