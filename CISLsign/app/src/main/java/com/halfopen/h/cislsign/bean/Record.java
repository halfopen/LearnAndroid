package com.halfopen.h.cislsign.bean;

/**
 * Created by h on 2017/7/20.
 */

public class Record {


    /**
     * signflag : 签入
     * timestamp : 2017-07-20 09:01:58
     * username : zhilingong
     * zh_name : 龚志霖
     */

    private String signflag;
    private String timestamp;
    private String username;
    private String zh_name;

    public String getSignflag() {
        return signflag;
    }

    public void setSignflag(String signflag) {
        this.signflag = signflag;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

}
