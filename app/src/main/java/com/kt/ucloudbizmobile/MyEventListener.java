package com.kt.ucloudbizmobile;

enum ActionType {
    Action_Servername_Click,
    Action_WatchButton_Click,
    Action_GraphButton_Click
}

public interface MyEventListener {
    void onMyEvent(ActionType act, int test);
}
