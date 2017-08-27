package com.kt.ucloudbizmobile;

/**
 * Created by Kpresent on 2017. 8. 1..
 */

public class ListServerItem {

    String serverName;
    String serverOS;
    boolean status;

    public ListServerItem(String serverName, String serverOS, boolean status) {
        this.serverName = serverName;
        this.serverOS = serverOS;
        this.status = status;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerOS() {
        return serverOS;
    }

    public void setServerOS(String serverOS) {
        this.serverOS = serverOS;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
