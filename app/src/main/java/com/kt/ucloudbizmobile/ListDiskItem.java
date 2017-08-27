package com.kt.ucloudbizmobile;

/**
 * Created by Kpresent on 2017. 8. 18..
 */

public class ListDiskItem {

    String diskName;
    String diskSize;
    boolean diskStatus;

    public ListDiskItem(String diskName, String diskSize, boolean diskStatus) {
        this.diskName = diskName;
        this.diskSize = diskSize;
        this.diskStatus = diskStatus;
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
}
