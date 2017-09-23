package com.kt.ucloudbizmobile;

/**
 * Created by Kpresent on 2017. 8. 18..
 */

public class ListDiskItem {

    String diskName;
    String diskSize;
    String diskZone;
    boolean diskStatus;
    String connServer;

    public ListDiskItem(String diskName, String diskSize, String diskZone, boolean diskStatus, String connServer) {
        this.diskName = diskName;
        this.diskSize = diskSize;
        this.diskZone = diskZone;
        this.diskStatus = diskStatus;
        this.connServer = connServer;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    public boolean getDiskStatus() {
        return diskStatus;
    }

    public void setDiskStatus(boolean diskStatus) {
        this.diskStatus = diskStatus;
    }

    public String getDiskZone() {
        String zonename = "";
        switch(diskZone) {
            case "kr-md2-1": zonename = "Seoul M2 zone"; break;
            case "kr-1": zonename = "Central A zone"; break;
            case "kr-2": zonename = "Central B zone"; break;
            case "kr-0": zonename = "Seoul M zone"; break;
            default: zonename = diskZone;
        }
        return zonename;
    }
    public String getDiskConnServer() {
        return connServer;
    }

}
