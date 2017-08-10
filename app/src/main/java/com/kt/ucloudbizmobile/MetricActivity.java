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

    public MetricActivity() {
        Log.d("sangil","died?1");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("sangil","died?1");
        setContentView(R.layout.activity_metric);

        // #1
        Log.d("sangil","died?2");
        Spinner locSpinner = (Spinner) findViewById(R.id.locSpinner_metric);
        ListView listView = (ListView) findViewById(R.id.listMetric);
        MetricViewAdapter adapter = new MetricViewAdapter();
        listView.setAdapter(adapter);
        adapter.setMyEventListener(this);
        Log.d("sangil","died?3");
        // Data
        adapter.addItem("Cpu 점유율","감시1","group1","30%");
        adapter.addItem("Mem 점유율","감시1","group1","30%");
    }

    @Override
    public void onMyEvent(ActionType act, int test) {
         /*  if(act == ActionType.Action_WatchButton_Click)
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
*/
    }
}
