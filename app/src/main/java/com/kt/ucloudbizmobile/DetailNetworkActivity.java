package com.kt.ucloudbizmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class DetailNetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Slide slide = new Slide();
//        slide.setSlideEdge(Gravity.RIGHT);
//        getWindow().setEnterTransition(slide);
        setContentView(R.layout.activity_detail_network);

        Intent intent = getIntent();
        Network data = getIntent().getParcelableExtra("data");

        ListView listDetail = (ListView) findViewById(R.id.list_detail_network);

        ListDetailAdapter adapter = new ListDetailAdapter();
        listDetail.setAdapter(adapter);

        if(data.ipaddress != null){
            adapter.addItem("공인 IP", data.ipaddress);
            adapter.addItem("UUID", data.addressid);
            String zonename = "";
            switch(data.zonename) {
                case "kr-md2-1": zonename = "Seoul M2 zone"; break;
                case "kr-1": zonename = "Central A zone"; break;
                case "kr-2": zonename = "Central B zone"; break;
                case "kr-0": zonename = "Seoul M zone"; break;
                default: zonename = data.zonename;
            }
            adapter.addItem("Zone", zonename);
            adapter.addItem("상태", data.state);
        }
        else {
            adapter.addItem("Network명", data.n_displayname);
            adapter.addItem("UUID", data.n_networkid);
            adapter.addItem("종류", data.n_type);
            String zonename = "";
            switch(data.n_zonename) {
                case "kr-md2-1": zonename = "Seoul M2 zone"; break;
                case "kr-1": zonename = "Central A zone"; break;
                case "kr-2": zonename = "Central B zone"; break;
                case "kr-0": zonename = "Seoul M zone"; break;
                default: zonename = data.n_zonename;
            }
            adapter.addItem("Zone", zonename);
            adapter.addItem("CIDR", data.n_cidr);
            adapter.addItem("Subnet Mask", data.n_subnet);
            adapter.addItem("Gateway", data.n_gateway);
            adapter.addItem("상태", data.n_state);
        }



    }
}
