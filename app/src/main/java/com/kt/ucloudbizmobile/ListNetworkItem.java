package com.kt.ucloudbizmobile;

/**
 * Created by Kpresent on 2017. 8. 18..
 */

public class ListNetworkItem {

    String networkName;
    String networkID;

    public ListNetworkItem(String networkName, String networkID) {
        this.networkName = networkName;
        this.networkID = networkID;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkID() {
        return networkID;
    }

    public void setNetworkID(String networkID) {
        this.networkID = networkID;
    }
}
