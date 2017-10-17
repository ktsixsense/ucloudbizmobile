package com.kt.ucloudbizmobile;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Kpresent on 2017. 10. 11..
 */

public class AppUtility {

    public static void showMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
