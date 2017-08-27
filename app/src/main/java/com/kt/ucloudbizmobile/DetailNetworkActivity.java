package com.kt.ucloudbizmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

public class DetailNetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.RIGHT);
        getWindow().setEnterTransition(slide);
        setContentView(R.layout.activity_detail_network);

        Intent intent = getIntent();
        String test = intent.getStringExtra("data");

        TextView txtData = (TextView) findViewById(R.id.txt_detail_network_info);
        txtData.setText(test);
    }
}
