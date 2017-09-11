package com.kt.ucloudbizmobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by LTR on 2017-08-10.
 */

public class GraphActivity extends Activity {


    private Calendar calendar;
    private Date datenow;
    private double m_data[];
    private int m_count;
    private int m_gap;

    public GraphActivity() {
        m_count = 0;
        m_gap = 30;
        m_data = null;
    }

    private String MakeCommand_Statics(String mainCommand, String metricName, String namespace, String dimensions, String unit, String period, String statistics, String starttime, String endtime)
    {
        String fullcommand;
        mainCommand = mainCommand.length() != 0?mainCommand : "command=getMetricStatistics";
        metricName = metricName.length() != 0?metricName : "&metricname=CPUUtilization";
        namespace = namespace.length() != 0?namespace : "&namespace=ucloud/server";
        //dimensions = dimensions.length() != 0?dimensions : "&dimensions.member.1.name=name";
        unit = unit.length() != 0?unit : "&unit=Percent";
        period = period.length() != 0?period : "&period=10";
        statistics = statistics.length() != 0?statistics : "&statistics.member.1=Average";
        starttime = starttime.length() != 0?starttime : "&starttime=2017-09-09T12:00:00.000";
        endtime = endtime.length() != 0?endtime : "&endtime=2017-09-09T18:13:00.000";
        fullcommand = mainCommand + metricName + namespace +dimensions + unit + period + statistics + starttime + endtime;
        return fullcommand;
    }
    public void refresh_data(Calendar req_calendar, final int req_gap)
    {
        /*
${API_URL}command=getMetricStatistics
&metricname=NetworkIn
&namespace=ucloud/server
&dimensions.member.1.name=name
&dimensions.member.1.value=i-2901-32012-VM
&unit=Bytes
&period=10
&statistics.member.1=Maximum
&statistics.member.2=Minimum
&statistics.member.3=Average
&statistics.member.4=Sum
&statistics.member.5=SampleCount
&starttime=2012-08-01T12:00:00.000
&endtime=2012-08-01T18:13:00.000
&response=xml&apiKey=${APIKey}&signature=${Signature}
         */
        // API로 값 얻어오기
        String apiKey = "kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg";
        String secretKey = "NmczQzPOE-CoYLbKpvo3UHJSaZ_6e9SC3tJIYsMIoiTJYMWMn8x-DpzBRTzzSkk0xegYz7g2yrvt_8jRrScxHQ";

        AQuery aq = new AQuery(this);
        final ApiParser parser = new ApiParser();

        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        calendar = Calendar.getInstance();
        datenow = calendar.getTime();
        //String fullcmd = MakeCommand_Statics("","","","","","","","","");
       // String watch_url_tmp = ApiGenerator.apiGeneratorWatch_command(apiKey, secretKey, fullcmd , false);
       // setgap(req_gap);
        String watch_url_tmp = ApiGenerator.apiGeneratorWatch(apiKey, secretKey, "getMetricStatistics" , false, req_calendar,req_gap);

        aq.ajax(watch_url_tmp, String.class, new AjaxCallback<String>() {
            metricStat[] mStat =null;
            ArrayList<ListServerItem> dataSet = new ArrayList<>();
            int dataCount = 0;
        //@Override
           public void callback(String url, String json, AjaxStatus status) {
                if (json != null) {
                    //successful ajax call, show status code and json content
                    Document doc = parser.getDocument(json);
                    int index = parser.getNumberOfResponse("getmetricstatisticsresponse", doc);
                    mStat = new metricStat[index];
                    mStat = parser.parseMetricStatistics(doc);

                    setdata(mStat);
                    setgap(req_gap);
                    DrawGraph();
                } else {
                    //ajax error, show error code
                    Toast.makeText(getApplicationContext(), "Error : " + status.getError(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        calendar = Calendar.getInstance(seoul);
        datenow = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph_metric1);

        Button button30 = (Button) findViewById(R.id.btn_grpah_30);
        Button button120 = (Button) findViewById(R.id.btn_grpah_120);
        Button button600 = (Button) findViewById(R.id.btn_grpah_600);

        refresh_data(calendar,30);
       // int[] test_data = {2, 3, 2, 2, 2, 3, 5, 10, 12, 3, 2, 4, 5, 2, 6, 1, 2, 7, 2, 5};
     //   setdata(15, test_data);

        button30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgap(30);
                refresh_data(calendar,30);
              //  DrawGraph();
            }
        });

        button120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgap(120);
                refresh_data(calendar,120);
             //   DrawGraph();
            }
        });

        button600.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgap(600);
                refresh_data(calendar,600);
              //  DrawGraph();
            }
        });

    }

    public void setdata(int nCount, int ndata[]) {
        int i;
        if (m_count == 0) {
            m_data = new double[nCount];
        }
        m_count = nCount;
        for (i = 0; i < nCount; i++)
            m_data[i] = ndata[i];
    }

    public void setdata(metricStat[] mstat) {
        int i;
        int nCount = mstat.length;
        if (m_data != null)
            m_data = null;
        m_data = new double[nCount];

        m_count = nCount;
        for (i = 0; i < nCount; i++)
            m_data[i] = mstat[i].Average;
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

        dp = new DataPoint[m_gap];
        if (m_gap == 30) {
            Calendar c = calendar;
            c.add(Calendar.MINUTE, -30);
            int realxgap = 30 / m_count;
            realxgap = 1;
            for(int i = 0;i<m_gap - m_count;i++)
            {
                //데이타 부족시 빈데이타 삽입
                Date d = c.getTime();
                dp[i] = new DataPoint(i, 0);
                c.add(Calendar.MINUTE, realxgap);
            }
            for (int i = m_gap - m_count; i < m_gap; i++) {
                Date d = c.getTime();
                if(m_data.length <= i-m_gap+m_count )
                    break;
                dp[i] = new DataPoint(i, m_data[i-m_gap+m_count]);
                c.add(Calendar.MINUTE, realxgap);
            }
        }
        if (m_gap == 120) {
            Calendar c = calendar;
            c.add(Calendar.MINUTE, -120);
            int realxgap = 120 / m_count;
            realxgap = 1;
            for(int i = 0;i<m_gap - m_count;i++)
            {
                //데이타 부족시 빈데이타 삽입
                Date d = c.getTime();
                dp[i] = new DataPoint(i, 0);
                c.add(Calendar.MINUTE, realxgap);
            }
            for (int i = m_gap - m_count; i < m_gap; i++) {
                Date d = c.getTime();
                if(m_data.length <= i-m_gap+m_count )
                    break;
                dp[i] = new DataPoint(i, m_data[i-m_gap+m_count]);
                c.add(Calendar.MINUTE, realxgap);
            }
        }
        if (m_gap == 600) {
            Calendar c = calendar;
            c.add(Calendar.MINUTE, -600);
            int gap = 600 / m_count;
            gap = 1;
            int realxgap = 600 / m_count;
            realxgap = 1;
            for(int i = 0;i<m_gap - m_count;i++)
            {
                //데이타 부족시 빈데이타 삽입
                Date d = c.getTime();
                dp[i] = new DataPoint(i, 0);
                c.add(Calendar.MINUTE, realxgap);
            }
            for (int i = m_gap - m_count; i < m_gap; i++) {
                Date d = c.getTime();
                if(m_data.length <= i-m_gap+m_count )
                    break;
                dp[i] = new DataPoint(i, m_data[i-m_gap+m_count]);
                c.add(Calendar.MINUTE, realxgap);
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

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(m_gap);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        if(m_gap == 30)
        staticLabelsFormatter.setHorizontalLabels(new String[] {"30분전","20분전", "10분전", "현재"});
        if(m_gap == 120)
            staticLabelsFormatter.setHorizontalLabels(new String[] {"120분전","80분전", "40분전", "현재"});
        if(m_gap == 600)
            staticLabelsFormatter.setHorizontalLabels(new String[] {"10시간전","6시간40분전", "3시간20분전", "현재"});
      //  staticLabelsFormatter.setVerticalLabels(new String[] {"low", "middle", "high"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

// set manual x bounds to have nice steps

        //   graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        // graph.getGridLabelRenderer().setHumanRounding(false);

        return true;
    }
}
