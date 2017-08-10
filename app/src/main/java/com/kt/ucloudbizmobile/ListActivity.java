package com.kt.ucloudbizmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class ListActivity extends Activity implements MyEventListener{

    private CustomDialog mCustomDialog;

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
        adapter.setMyEventListener(this);

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

    @Override
    public void onMyEvent(ActionType act, int test) {
        if(act == ActionType.Action_WatchButton_Click)
        {
            Intent intent = new Intent(ListActivity.this, MetricActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if(act == ActionType.Action_Servername_Click)
        {
            Log.d("1", act + "event!" + test);
            mCustomDialog = new CustomDialog(this,
                    "[다이얼로그 제목]", // 제목
                    "다이얼로그 내용 표시하기", // 내용
                    leftListener// 왼쪽 버튼 이벤트
                    ); // 오른쪽 버튼 이벤트
            mCustomDialog.show();
        }

    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "왼쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();
            mCustomDialog.dismiss();
        }
    };

}
