package com.kt.ucloudbizmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.w3c.dom.Document;

public class DetailServerActivity extends AppCompatActivity {

    Server data;
    AQuery aq = new AQuery(this);
    ApiParser parser = new ApiParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Slide slide = new Slide();
//        slide.setSlideEdge(Gravity.RIGHT);
//        getWindow().setEnterTransition(slide);
        setContentView(R.layout.activity_detail_server);

        data = getIntent().getParcelableExtra("data");

        ListView listDetail = (ListView) findViewById(R.id.list_detail);
        final Button startBtn = (Button) findViewById(R.id.btn_server_start);
        ListDetailAdapter adapter = new ListDetailAdapter();
        listDetail.setAdapter(adapter);

        // Sample Data
        adapter.addItem("서버명", data.displayname);
        adapter.addItem("UUID", data.serverid);
        adapter.addItem("Zone", data.zonename);
        adapter.addItem("IP", data.ipaddress);
        adapter.addItem("OS", data.os);
        String str = data.cpunumber + " vCore / " + (Integer.parseInt(data.memory) / 1024) + " GB";
        adapter.addItem("사양", str);
        adapter.addItem("호스트명", data.name);
        adapter.addItem("생성일시", data.created);
        adapter.addItem("상태", data.state);

        if (data.state.equals("Stopped")) {
            startBtn.setVisibility(View.VISIBLE);
        }

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uuid = data.serverid;
                String zone = data.zonename;
                String url = ApiGenerator.apiStartServer(MainActivity.apiKey, MainActivity.secretKey, uuid, zone.equals("kr-md2-1"));

                aq.ajax(url, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String json, AjaxStatus status) {
                        if (json != null) {
                            //successful ajax call, show status code and json content
                            Document doc = parser.getDocument(json);
                            int index = parser.getNumberOfResponse("jobid", doc);
                            if (index > 0) {
                                Toast.makeText(getApplicationContext(), "서버시작 요청이 완료되었습니다. \n5분 이상의 시간이 소요될 수 있습니다. \n서버리스트에서 화면 새로고침을 하시기 바랍니다.", Toast.LENGTH_LONG).show();
                                startBtn.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getApplicationContext(), "서버 시작을 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //ajax error, show error code
                            AppUtility.showMsg(getApplicationContext(), "Error : " + status.getError());
                        }
                    }
                });
            }
        });

    }
}
