package com.kt.ucloudbizmobile;

/**
 * Created by Kpresent on 2017. 8. 1..
 */

public class ListServerItem {

    String serverName;
    String serverOS;
    String serverZone;
    boolean status;

    public ListServerItem(String serverName, String serverOS, String serverZone, boolean status) {
        this.serverName = serverName;
        this.serverOS = serverOS;
        this.serverZone = serverZone;
        this.status = status;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerZone() {
        String zonename = "";
        switch(serverZone) {
            case "kr-md2-1": zonename = "Seoul M2 zone"; break;
            case "kr-1": zonename = "Central A zone"; break;
            case "kr-2": zonename = "Central B zone"; break;
            case "kr-0": zonename = "Seoul M zone"; break;
            default: zonename = serverZone;

        }
        return zonename;
    }

    public void setServerZone(String serverZone) {
        this.serverZone = serverZone;
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
