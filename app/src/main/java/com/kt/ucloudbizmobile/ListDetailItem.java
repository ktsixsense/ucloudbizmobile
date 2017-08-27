package com.kt.ucloudbizmobile;

/**
 * Created by Kpresent on 2017. 8. 25..
 */

public class ListDetailItem {

    String title;
    String content;

    public ListDetailItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
