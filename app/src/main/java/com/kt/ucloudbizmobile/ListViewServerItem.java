package com.kt.ucloudbizmobile;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

/**
 * Created by Kpresent on 2017. 8. 1..
 */

public class ListViewServerItem {

    boolean isChecked;
    String serverName;
    Drawable colorStatus;
    String serverStatus;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Drawable getImageStatus() {
        return colorStatus;
    }

    public void setImageStatus(Drawable colorStatus) {
        this.colorStatus = colorStatus;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public void onClickText1(View v) {
        Log.d("1", "onclick text13");
    }
}
