package com.kt.ucloudbizmobile;

/**
 * Created by Kpresent on 2017. 8. 18..
 */

public class ListNetworkItem {

    String networkIP;
    String networkID;
    String networkZone;
    boolean networkBasicIP;

    public ListNetworkItem(String networkIP, String networkID, String networkZone, boolean networkBasicIP) {
        this.networkIP = networkIP;
        this.networkID = networkID;
        this.networkZone = networkZone;
        this.networkBasicIP = networkBasicIP;
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
}
