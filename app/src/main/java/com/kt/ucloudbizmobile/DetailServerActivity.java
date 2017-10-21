package com.kt.ucloudbizmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class DetailServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Slide slide = new Slide();
//        slide.setSlideEdge(Gravity.RIGHT);
//        getWindow().setEnterTransition(slide);
        setContentView(R.layout.activity_detail_server);

        Server data = getIntent().getParcelableExtra("data");

        ListView listDetail = (ListView) findViewById(R.id.list_detail);

        ListDetailAdapter adapter = new ListDetailAdapter();
        listDetail.setAdapter(adapter);

        // Sample Data
        adapter.addItem("서버명", data.displayname);
        adapter.addItem("UUID", data.serverid);
        adapter.addItem("IP", data.ipaddress);
        adapter.addItem("OS", data.os);
        String str = data.cpunumber + " vCore / " + (Integer.parseInt(data.memory) / 1024) + " GB";
        adapter.addItem("사양", str);
        adapter.addItem("호스트명", data.name);
        adapter.addItem("생성일시", data.created);
        adapter.addItem("상태", data.state);
    }
}
