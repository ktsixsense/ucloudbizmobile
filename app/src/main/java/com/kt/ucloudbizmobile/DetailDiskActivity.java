package com.kt.ucloudbizmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class DetailDiskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Slide slide = new Slide();
//        slide.setSlideEdge(Gravity.RIGHT);
//        getWindow().setEnterTransition(slide);
        setContentView(R.layout.activity_detail_disk);

        Intent intent = getIntent();
        Disk data = getIntent().getParcelableExtra("data");

        ListView listDetail = (ListView) findViewById(R.id.list_detail_disk);

        ListDetailAdapter adapter = new ListDetailAdapter();
        listDetail.setAdapter(adapter);

        // Sample Data
        adapter.addItem("볼륨명", data.displayname);
        adapter.addItem("UUID", data.volumeid);
        String zonename = "";
        switch (data.zonename) {
            case "kr-md2-1":
                zonename = "Seoul M2 zone";
                break;
            case "kr-1":
                zonename = "Central A zone";
                break;
            case "kr-2":
                zonename = "Central B zone";
                break;
            case "kr-0":
                zonename = "Seoul M zone";
                break;
            default:
                zonename = data.zonename;
        }
        adapter.addItem("Zone", zonename);
        if (data.vmname != null) adapter.addItem("연결 VM", data.vmname);
        else adapter.addItem("연결 VM", "분리");
        adapter.addItem("타입", data.type);
        String str = "" + (Long.parseLong(data.size) / 107374182);
        adapter.addItem("용량", str + " GB");
        adapter.addItem("생성일시", data.created);
    }
}
