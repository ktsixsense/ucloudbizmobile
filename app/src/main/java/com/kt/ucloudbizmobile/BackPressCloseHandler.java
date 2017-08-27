package com.kt.ucloudbizmobile;

import android.app.Activity;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * Created by Kpresent on 2017. 8. 21..
 */

public class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast.cancel();
            activity.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public void showGuide() {
        toast = makeText(activity, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

}