package com.halfopen.h.cislsign.bean;

/**
 * Created by h on 2017/7/20.
 */

public class User {

    /**
     * userid : 6
     * username : pengzhang
     * zh_name : 张鹏
     * roleid : 1
     */

    private String userid;
    private String username;
    private String zh_name;
    private String roleid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getZh_name() {
        return zh_name;
    }

    public void setZh_name(String zh_name) {
        this.zh_name = zh_name;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
}
