package com.kt.ucloudbizmobile;

/**
 * Created by Administrator on 2017-08-03.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Spinner;

public class MetricActivity extends Activity implements MyEventListener {

    public MetricActivity() {
        Log.d("sangil", "died?1");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("sangil", "died?1");
        setContentView(R.layout.activity_metric);

        // #1
        Log.d("sangil", "died?2");
        Spinner locSpinner = (Spinner) findViewById(R.id.locSpinner_metric);
        ListView listView = (ListView) findViewById(R.id.listMetric);
        MetricViewAdapter adapter = new MetricViewAdapter();
        listView.setAdapter(adapter);
        adapter.setMyEventListener(this);
        Log.d("sangil", "died?3");
        // Data
        adapter.addItem("Cpu 점유율", "감시1", "group1", "30%");
        adapter.addItem("Mem 점유율", "감시1", "group1", "30%");
    }

    @Override
    public void onMyEvent(ActionType act, int test) {
        if (act == ActionType.Action_GraphButton_Click) {
            Intent intent = new Intent(MetricActivity.this, GraphActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
