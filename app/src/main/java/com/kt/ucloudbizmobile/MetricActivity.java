package com.kt.ucloudbizmobile;

/**
 * Created by Administrator on 2017-08-03.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class MetricActivity extends AppCompatActivity implements MyEventListener {

    private Metric[] m_metricArray;
    private int m_count;
    private String serverName;
    private ProgressBar progressBar;

    public MetricActivity() {
        m_metricArray = null;
        m_count = 0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metric);
        serverName = getIntent().getStringExtra("data");
        progressBar = (ProgressBar) findViewById(R.id.progressMetric);
        // #1
        ListView listView = (ListView) findViewById(R.id.listMetric);
        MetricViewAdapter adapter = new MetricViewAdapter();
        listView.setAdapter(adapter);
        adapter.setMyEventListener(this);

        refreshData();

        if (m_count != 0)
            Setitems();
    }

    public void refreshData() {
        AQuery aq = new AQuery(this);
        final ApiParser parser = new ApiParser();

        String apiKey = "kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg";
        String secretKey = "NmczQzPOE-CoYLbKpvo3UHJSaZ_6e9SC3tJIYsMIoiTJYMWMn8x-DpzBRTzzSkk0xegYz7g2yrvt_8jRrScxHQ";

        String watch_url_tmp = ApiGenerator.apiGeneratorMetrics(apiKey, secretKey, "listMetrics", false);

        aq.ajax(watch_url_tmp, String.class, new AjaxCallback<String>() {
            Metric[] metrics_array = null;
            ArrayList<ListServerItem> dataSet = new ArrayList<>();
            int dataCount = 0;

            //@Override
            public void callback(String url, String json, AjaxStatus status) {
                if (json != null) {
                    //successful ajax call, show status code and json content
                    Document doc = parser.getDocument(json);
                    int index = parser.getNumberOfResponse("listmetricsresponse", doc);
                    metrics_array = new Metric[500];
                    metrics_array = parser.parseMetric(doc);
                    MakeupFinalmetrics(metrics_array, metrics_array.length);
                    Setitems();
                } else {
                    //ajax error, show error code
                    Toast.makeText(getApplicationContext(), "Error : " + status.getError(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onMyEvent(ActionType act, int test) {
        if (act == ActionType.Action_GraphButton_Click) {
            Log.d("sangil", "onMyEvent:  " + test);
            if (test >= m_count)
                return;
            Intent intent = new Intent(MetricActivity.this, GraphActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle b = new Bundle();
            b.putString("metricname", m_metricArray[test].metricname);
            b.putString("namespace", m_metricArray[test].namespace);
            b.putString("unit", m_metricArray[test].unit);
            b.putString("dimensions", m_metricArray[test].dimensions);
            intent.putExtras(b);
            startActivity(intent);
        }
    }

    public void MakeupFinalmetrics(Metric[] metric_array, int ncnt) {
        m_metricArray = metric_array.clone();

        //중복 체크
        int i, j, k;
        for (i = 0; i < ncnt; i++) {
            for (j = i + 1; j < ncnt; j++) {
                if (m_metricArray[i].metricname.compareTo(m_metricArray[j].metricname) != 0)
                    continue;
                if (m_metricArray[i].namespace.compareTo(m_metricArray[j].namespace) != 0)
                    continue;
                if (m_metricArray[i].dimensions.compareTo(m_metricArray[j].dimensions) != 0 && m_metricArray[j].dimensions.length() != 0 && m_metricArray[i].dimensions.length() != 0)
                    continue;
                // dimension 에대해서는 같거나 + 한쪽의 dimesion 의 길이가 0인 경우삭제
                // 두개가 같을 경우에는 중복 필드 메트릭 제거 조치
                // 지워질때 dimesion 은 있는쪽으로 수정
                if (m_metricArray[j].dimensions.length() != 0 && m_metricArray[i].dimensions.length() == 0)
                    m_metricArray[i].dimensions = m_metricArray[j].dimensions;
                for (k = j + 1; k < ncnt; k++)
                    m_metricArray[k - 1] = m_metricArray[k];
                j--;
                ncnt--;//다시 그 항목 부터 비교
            }
        }
        m_count = ncnt;
    }


    public void Setitems() {
        if (m_count == 0) return;
        ListView listView = (ListView) findViewById(R.id.listMetric);
        MetricViewAdapter adapter = (MetricViewAdapter) listView.getAdapter();
        int i;
        for (i = 0; i < m_count; i++) {
            // todo : average 값 과  실제 서버 name 은 추가 파싱 + 추가 정보얻어내야함(getstatics 를 통해))
            if (serverName.equalsIgnoreCase(DimesionstoName(m_metricArray[i].dimensions))) {
                adapter.addItem(m_metricArray[i].metricname, DimesionstoName(m_metricArray[i].dimensions), m_metricArray[i].namespace, m_metricArray[i].unit);
            }
        }

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
    }

    public String DimesionstoName(String str) {
        int starti = -1;
        int endi = -1;
        starti = str.indexOf("name");
        endi = str.indexOf("(");
        if (endi > starti && starti >= 0 && endi >= 4)
            return str.substring(starti + 4, endi);
        return " ";
    }
}
