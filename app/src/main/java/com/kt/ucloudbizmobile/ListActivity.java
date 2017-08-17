package com.kt.ucloudbizmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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

import org.w3c.dom.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ListActivity extends Activity implements MyEventListener{

    private CustomDialog mCustomDialog;
    ListView listView = null;
    ListView listView2 = null;
    ListView listView3 = null;
    ListViewServerAdapter adapter = null;
    getHttpResponse m_response=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        // #1
        TabHost.TabSpec ts1 = tabHost.newTabSpec("server");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("Server");
        tabHost.addTab(ts1);

        Spinner locSpinner = (Spinner) findViewById(R.id.locSpinner);
        listView = (ListView) findViewById(R.id.listServer);

        // Key
        String apiKey = "kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg";
        String secretKey = "NmczQzPOE-CoYLbKpvo3UHJSaZ_6e9SC3tJIYsMIoiTJYMWMn8x-DpzBRTzzSkk0xegYz7g2yrvt_8jRrScxHQ";

        String finalURL = ApiGenerator.apiGenerator(apiKey, secretKey, "listVirtualMachines", false, "all");
        m_response = new getHttpResponse();
        m_response.setActivity(this);
        try {
            m_response.execute(new URL(finalURL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        // #2
        TabHost.TabSpec ts2 = tabHost.newTabSpec("disk");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("Disk");
        tabHost.addTab(ts2);

        // #3
        TabHost.TabSpec ts3 = tabHost.newTabSpec("network");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("Network");
        tabHost.addTab(ts3);

      }

    @Override
    public void onMyEvent(ActionType act, int test) {
        if(act == ActionType.Action_WatchButton_Click)
        {
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

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "왼쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();
            mCustomDialog.dismiss();
        }
    };


    class getHttpResponse extends AsyncTask<URL, Server[], Server[]>
    {
        private MyEventListener m_parentlistener;
        protected Server[] doInBackground(URL... urls) {
        URLConnection connection = null;
        try {
            connection = urls[0].openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try{
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
            doc = objDocumentBuilder.parse(connection.getInputStream());
            //Log.d("doc: ", doc.getNodeValue());
        } catch(Exception ex){
            Log.d("ParseXML Error", "ParseXML Error");
            ex.printStackTrace();
        }
        ApiParser apiPar = new ApiParser();
        int arrayLength = apiPar.getNumberOfResponse(doc);

        Server[] servers = new Server[arrayLength];
        servers = apiPar.parseServerList(doc, arrayLength);

        return servers;
    }

    void setActivity(MyEventListener lisnter)
    {
        m_parentlistener = lisnter;
    }
        @Override
        protected void onPostExecute(Server[] servers) {
            adapter = new ListViewServerAdapter();
            adapter.setMyEventListener(m_parentlistener);
            listView.setAdapter(adapter);


            for(int i=0; i<servers.length; i++){
                adapter.addItem(servers[i].displayname, servers[i].state);
            }
        }
    }
}
