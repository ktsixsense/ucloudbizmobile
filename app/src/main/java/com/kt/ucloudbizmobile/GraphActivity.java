package com.kt.ucloudbizmobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.androidquery.AQuery;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by LTR on 2017-08-10.
 */

public class GraphActivity extends Activity {


    private Calendar calendar;
    private Date datenow;
    private int m_data[];
    private int m_count;
    private int m_gap;

    public GraphActivity() {
        m_count = 0;
        m_gap = 30;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        calendar = Calendar.getInstance();
        datenow = calendar.getTime();

        // API로 값 얻어오기

        String apiKey = "kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg";
        String secretKey = "NmczQzPOE-CoYLbKpvo3UHJSaZ_6e9SC3tJIYsMIoiTJYMWMn8x-DpzBRTzzSkk0xegYz7g2yrvt_8jRrScxHQ";

        AQuery aq = new AQuery(this);
        final ApiParser parser = new ApiParser();

        String watch_url_tmp = ApiGenerator.apiGeneratorWatch(apiKey, secretKey, "getMetricStatistics", false);

        GraphView graph = (GraphView) findViewById(R.id.graph_metric1);

        Button button30 = (Button) findViewById(R.id.btn_grpah_30);
        Button button120 = (Button) findViewById(R.id.btn_grpah_120);
        Button button600 = (Button) findViewById(R.id.btn_grpah_600);

        int[] test_data = {2, 3, 2, 2, 2, 3, 5, 10, 12, 3, 2, 4, 5, 2, 6, 1, 2, 7, 2, 5};
        setdata(15, test_data);

        button30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgap(30);
                DrawGraph();
            }
        });

        button120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgap(120);
                DrawGraph();
            }
        });

        button600.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgap(600);
                DrawGraph();
            }
        });

    }

    public void setdata(int nCount, int ndata[]) {
        int i;
        if (m_count == 0) {
            m_data = new int[nCount];
        }
        m_count = nCount;
        for (i = 0; i < nCount; i++)
            m_data[i] = ndata[i];
    }

    public void setdata(int nCount, int ndata[], int ngap) {
        int i;
        m_count = nCount;
        for (i = 0; i < nCount; i++)
            m_data[i] = ndata[i];
        m_gap = ngap;
    }

    public void setgap(int ngap) {
        m_gap = ngap;
    }

    public boolean DrawGraph() {
        DataPoint dp[];

        dp = new DataPoint[m_count];
        if (m_gap == 30) {
            Calendar c = calendar;
            c.add(Calendar.MINUTE, -30);
            int gap = 30 / m_count;
            for (int i = 0; i < m_count; i++) {
                Date d = c.getTime();
                dp[i] = new DataPoint(d, m_data[i]);
                c.add(Calendar.MINUTE, gap);
            }
        }
        if (m_gap == 120) {
            Calendar c = calendar;
            c.add(Calendar.MINUTE, -120);
            int gap = 120 / m_count;
            for (int i = 0; i < m_count; i++) {
                Date d = c.getTime();
                dp[i] = new DataPoint(d, m_data[i]);
                c.add(Calendar.MINUTE, gap);
            }
        }
        if (m_gap == 600) {
            Calendar c = calendar;
            c.add(Calendar.MINUTE, -600);
            int gap = 600 / m_count;
            for (int i = 0; i < m_count; i++) {
                Date d = c.getTime();
                dp[i] = new DataPoint(d, m_data[i]);
                c.add(Calendar.MINUTE, gap);
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dp);
        GraphView graph = (GraphView) findViewById(R.id.graph_metric1);
        graph.removeAllSeries();
        ;
        graph.addSeries(series);
        //graph.
        // set date label formatter

        //   graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        //   graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(dp[0].getX());
        graph.getViewport().setMaxX(dp[m_count - 1].getX());
        //   graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        // graph.getGridLabelRenderer().setHumanRounding(false);

        return true;
    }
}
