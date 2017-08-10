package com.kt.ucloudbizmobile;

/**
 * Created by Administrator on 2017-08-03.
 */
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

public class MetricActivity extends Activity implements MyEventListener{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metric);

        // #1

        Spinner locSpinner = (Spinner) findViewById(R.id.locSpinner);
        ListView listView = (ListView) findViewById(R.id.listServer);
        MetricViewAdapter adapter = new MetricViewAdapter();
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

    }

    @Override
    public void onMyEvent(ActionType act, int test) {
        if(act == ActionType.Action_WatchButton_Click)
        {
            Log.d("1", act + "event!" + test);
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
}
