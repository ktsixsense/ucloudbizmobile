package com.kt.ucloudbizmobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;

public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        // #1
        TabHost.TabSpec ts1 = tabHost.newTabSpec("server");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("Server");
        tabHost.addTab(ts1);

        Spinner locSpinner = (Spinner) findViewById(R.id.locSpinner);
        ListView listView = (ListView) findViewById(R.id.listServer);
        ListViewServerAdapter adapter = new ListViewServerAdapter();
        listView.setAdapter(adapter);

        // Data
        adapter.addItem("Test 1", "사용");
        adapter.addItem("Test 2", "사용");
        adapter.addItem("Test 3", "사용");
        adapter.addItem("Test 4", "정지");
        adapter.addItem("Test 5", "사용");
        adapter.addItem("Test 6", "정지");
        adapter.addItem("Test 7", "정지");
        adapter.addItem("Test 8", "정지");

        // #2
        TabHost.TabSpec ts2 = tabHost.newTabSpec("disk");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("Disk");
        tabHost.addTab(ts2);

        // #3
        TabHost.TabSpec ts3 = tabHost.newTabSpec("network");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("Network");
        tabHost.addTab(ts3);
    }
}
