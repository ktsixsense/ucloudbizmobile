package com.kt.ucloudbizmobile;

import android.content.Intent;
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

        Intent intent = getIntent();
        String test = intent.getStringExtra("data");

        ListView listDetail = (ListView) findViewById(R.id.list_detail);
        TextView txtData = (TextView) findViewById(R.id.txt_detail_server_info);
        txtData.setText(test);

        ListDetailAdapter adapter = new ListDetailAdapter();
        listDetail.setAdapter(adapter);

        // Sample Data
        adapter.addItem("AAA", "121213");
        adapter.addItem("BBB", "221232");
        adapter.addItem("CCC", "123333");
        adapter.addItem("DDD", "12142423");
    }
}
