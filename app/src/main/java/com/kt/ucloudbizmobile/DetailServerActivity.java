package com.kt.ucloudbizmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class DetailServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.RIGHT);
        getWindow().setEnterTransition(slide);
        setContentView(R.layout.activity_detail_server);

        Server data = getIntent().getParcelableExtra("data");

        ListView listDetail = (ListView) findViewById(R.id.list_detail);
        TextView txtData = (TextView) findViewById(R.id.txt_detail_server_info);
        txtData.setText("TEST 중");

        ListDetailAdapter adapter = new ListDetailAdapter();
        listDetail.setAdapter(adapter);

        // Sample Data
        adapter.addItem("AAA", data.os);
        adapter.addItem("BBB", data.starttime);
        adapter.addItem("CCC", data.state);
        adapter.addItem("DDD", data.zonename);
    }
}
