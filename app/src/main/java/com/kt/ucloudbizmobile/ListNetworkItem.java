package com.kt.ucloudbizmobile;

/**
 * Created by Kpresent on 2017. 8. 18..
 */

public class ListNetworkItem {

    String networkIP;
    String networkID;
    String networkZone;
    boolean networkBasicIP;

    String n_displayname;
    String n_zonename;
    String n_type;
    String n_cidr;



    public ListNetworkItem(String networkIP, String networkID, String networkZone, boolean networkBasicIP) {
        this.networkIP = networkIP;
        this.networkID = networkID;
        this.networkZone = networkZone;
        this.networkBasicIP = networkBasicIP;
    }

    public ListNetworkItem(String n_displayname, String n_zonename, String n_type, String n_cidr) {
        this.n_displayname = n_displayname;
        this.n_zonename = n_zonename;
        this.n_type = n_type;
        this.n_cidr = n_cidr;
    }

    public String getNetworkIP() {
        return networkIP;
    }

    public void setNetworkIP(String networkIP) {
        this.networkIP = networkIP;
    }

    public String getNetworkID() {
        return networkID;
    }

    public void setNetworkID(String networkID) {
        this.networkID = networkID;
    }

    public String getNetworkZone() {
        String zonename = "";
        switch(networkZone) {
            case "kr-md2-1": zonename = "Seoul M2 zone"; break;
            case "kr-1": zonename = "Central A zone"; break;
            case "kr-2": zonename = "Central B zone"; break;
            case "kr-0": zonename = "Seoul M zone"; break;
            default: zonename = networkZone;
        }
        return zonename;
    }

    public void setNetworkZone(String networkZone) {
        this.networkZone = networkZone;
    }

    public boolean getNetworkBasicIP() {
        return networkBasicIP;
    }

    public void setNetworkBasicIP(boolean networkBasicIP) {
        this.networkBasicIP = networkBasicIP;
    }

    public String getN_displayname() {
        return n_displayname;
    }

    public void setN_displayname(String n_displayname) {
        this.n_displayname = n_displayname;
    }

    public String getN_zonename() {
        String zonename = "";
        switch(n_zonename) {
            case "kr-md2-1": zonename = "Seoul M2 zone"; break;
            case "kr-1": zonename = "Central A zone"; break;
            case "kr-2": zonename = "Central B zone"; break;
            case "kr-0": zonename = "Seoul M zone"; break;
            default: zonename = n_zonename;
        }
        return zonename;
    }

    public void setN_zonename(String n_zonename) {
        this.n_zonename = n_zonename;
    }

    public String getN_type() {
        return n_type;
    }

    public void setN_type(String n_type) {
        this.n_type = n_type;
    }

    public String getN_cidr() {
        return n_cidr;
    }

    public void setN_cidr(String n_cidr) {
        this.n_cidr = n_cidr;
    }
}
