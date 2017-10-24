package com.kt.ucloudbizmobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
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
    private Metric m_metric;
    public TextClock clk;
    public EditText m_EditText_date, m_EditText_time;
    public static int m_year, m_month, m_day, m_hour, m_min;

    public GraphActivity() {
        m_count = 0;
        m_gap = 30;
        m_data = null;
        m_metric = new Metric();
        m_metric.dimensions = "";
        m_metric.namespace = "ucloud/server";
        m_metric.metricname = "CPUUtilization";
        m_metric.metricname = "Percent";
    }

    private String MakeCommand_Statics(String mainCommand, String metricName, String namespace, String dimensions, String unit, String period, String statistics, String starttime, String endtime) {
        String fullcommand;
        mainCommand = mainCommand.length() != 0 ? mainCommand : "command=getMetricStatistics";
        metricName = metricName.length() != 0 ? metricName : "&metricname=CPUUtilization";
        namespace = namespace.length() != 0 ? namespace : "&namespace=ucloud/server";
        //dimensions = dimensions.length() != 0?dimensions : "&dimensions.member.1.name=name";
        unit = unit.length() != 0 ? unit : "&unit=Percent";
        period = period.length() != 0 ? period : "&period=10";
        statistics = statistics.length() != 0 ? statistics : "&statistics.member.1=Average";
        starttime = starttime.length() != 0 ? starttime : "&starttime=2017-09-09T12:00:00.000";
        endtime = endtime.length() != 0 ? endtime : "&endtime=2017-09-09T18:13:00.000";
        fullcommand = mainCommand + metricName + namespace + dimensions + unit + period + statistics + starttime + endtime;
        return fullcommand;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        calendar = Calendar.getInstance(seoul);
        datenow = calendar.getTime();
        TextView title = (TextView)  findViewById(R.id.textView_Title);
        GraphView graph = (GraphView) findViewById(R.id.graph_metric1);

        Button button30 = (Button) findViewById(R.id.btn_graph_30);
        Button button120 = (Button) findViewById(R.id.btn_grpah_120);
        Button button600 = (Button) findViewById(R.id.btn_grpah_600);
        button30.setVisibility(View.GONE);
        button120.setVisibility(View.GONE);
        button600.setVisibility(View.GONE);

        Button buttonmagnify = (Button) findViewById(R.id.button_magnify);
        Button buttonreduce = (Button) findViewById(R.id.button_reducion);
        Button buttonrefresh = (Button) findViewById(R.id.button_refresh);


        clk = (TextClock) findViewById(R.id.textClock_graph);
        m_EditText_date = (EditText) findViewById(R.id.editTextdate_graph);
        m_EditText_time = (EditText) findViewById(R.id.editTexttime_graph);

        int year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH) + 1, day = calendar.get(Calendar.DAY_OF_MONTH);
        m_EditText_date.setText(year + "/" + month + "/" + day);
        clk.setTimeZone("Asia/Seoul");
        m_year = year;
        m_month = month - 1;
        m_day = day;
        m_hour = calendar.get(Calendar.HOUR);
        m_min = calendar.get(Calendar.MINUTE);
        m_EditText_time.setText(m_hour + "시 : " + m_min + "분");

        Bundle b = getIntent().getExtras();
        if (b != null) {
            m_metric.dimensions = b.getString("dimensions");
            m_metric.metricname = b.getString("metricname");
            m_metric.namespace = b.getString("namespace");
            m_metric.unit = b.getString("unit");
            title.setText("☞"+m_metric.namespace +" / " + m_metric.metricname + " (" +m_metric.unit+")");
        }
        refresh_data(calendar, 30);
        // int[] test_data = {2, 3, 2, 2, 2, 3, 5, 10, 12, 3, 2, 4, 5, 2, 6, 1, 2, 7, 2, 5};
        //   setdata(15, test_data);

        button30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgap(30);
                refresh_data(calendar, 30);
                //  DrawGraph();
            }
        });

        button120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgap(120);
                refresh_data(calendar, 120);
                //   DrawGraph();
            }
        });

        button600.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgap(600);
                refresh_data(calendar, 600);
                //  DrawGraph();
            }
        });

        buttonmagnify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (m_gap)
                {
                    case 30:
                        m_gap = 120;
                        refresh_data(calendar, 120);
                        break;
                    case 120:
                        m_gap = 600;
                        refresh_data(calendar, 600);
                        break;
                    case 600:
                        m_gap = 600;
                        //refresh_data(calendar, 120);
                        break;
                    default:
                        m_gap = 30;
                        break;
                }
            }
        });

        buttonreduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (m_gap)
                {
                    case 30:
                        m_gap = 30;
                      //  refresh_data(calendar, 120);
                        break;
                    case 120:
                        m_gap = 30;
                        refresh_data(calendar, 30);
                        break;
                    case 600:
                        m_gap = 120;
                        refresh_data(calendar, 120);
                        break;
                    default:
                        m_gap = 30;
                        break;
                }
            }
        });

        buttonrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Refresh 인 경우
                    TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
                    calendar = Calendar.getInstance(seoul);
                    datenow = calendar.getTime();

                     m_hour = calendar.get(Calendar.HOUR);
                    m_min = calendar.get(Calendar.MINUTE);
                   m_EditText_time.setText(m_hour + "시 : " + m_min + "분");
                  refresh_data(calendar, m_gap);
            }
        });
        m_EditText_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });

        m_EditText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

    }

    public void refresh_data(Calendar req_calendar, final int req_gap) {
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

        String watch_url_tmp = ApiGenerator.apiGeneratorWatch(apiKey, secretKey, "getMetricStatistics", false, req_calendar, req_gap, 1, m_metric.metricname, m_metric.namespace, m_metric.unit);


        aq.ajax(watch_url_tmp, String.class, new AjaxCallback<String>() {
            metricStat[] mStat = null;
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
        for (i = 0; i < nCount; i++) {
            if(mstat[i].Average == 0 && i != 0)
                m_data[i] = m_data[i-1];
            else
                m_data[i] = mstat[i].Average;
        }
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
            for (int i = 0; i < m_gap - m_count; i++) {
                //데이타 부족시 빈데이타 삽입
                Date d = c.getTime();
                dp[i] = new DataPoint(i, 0);
                c.add(Calendar.MINUTE, realxgap);
            }
            for (int i = m_gap - m_count; i < m_gap; i++) {
                Date d = c.getTime();
                if (m_data.length <= i - m_gap + m_count)
                    break;
                dp[i] = new DataPoint(i, m_data[i - m_gap + m_count]);
                c.add(Calendar.MINUTE, realxgap);
            }
        }
        if (m_gap == 120) {
            Calendar c = calendar;
            c.add(Calendar.MINUTE, -120);
            int realxgap = 120 / m_count;
            realxgap = 1;
            for (int i = 0; i < m_gap - m_count; i++) {
                //데이타 부족시 빈데이타 삽입
                Date d = c.getTime();
                dp[i] = new DataPoint(i, 0);
                c.add(Calendar.MINUTE, realxgap);
            }
            for (int i = m_gap - m_count; i < m_gap; i++) {
                Date d = c.getTime();
                if (m_data.length <= i - m_gap + m_count)
                    break;
                dp[i] = new DataPoint(i, m_data[i - m_gap + m_count]);
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
            for (int i = 0; i < m_gap - m_count; i++) {
                //데이타 부족시 빈데이타 삽입
                Date d = c.getTime();
                dp[i] = new DataPoint(i, 0);
                c.add(Calendar.MINUTE, realxgap);
            }
            for (int i = m_gap - m_count; i < m_gap; i++) {
                Date d = c.getTime();
                if (m_data.length <= i - m_gap + m_count)
                    break;
                dp[i] = new DataPoint(i, m_data[i - m_gap + m_count]);
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
        if (m_gap == 30)
            staticLabelsFormatter.setHorizontalLabels(new String[]{"30분전", "20분전", "10분전", "현재"});
        if (m_gap == 120)
            staticLabelsFormatter.setHorizontalLabels(new String[]{"120분전", "80분전", "40분전", "현재"});
        if (m_gap == 600)
            staticLabelsFormatter.setHorizontalLabels(new String[]{"10시간전", "6시간40분전", "3시간20분전", "현재"});
        //  staticLabelsFormatter.setVerticalLabels(new String[] {"low", "middle", "high"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

// set manual x bounds to have nice steps

        //   graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        // graph.getGridLabelRenderer().setHumanRounding(false);

        return true;
    }

    public Calendar MakenewCalendar() {
        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        Calendar c = Calendar.getInstance(seoul);
        c.set(m_year, m_month, m_day, m_hour, m_min);
        return c;
    }

    public static class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        // Thanks to -> http://javapapers.com/android/android-datepicker/
        EditText mtime;
        GraphActivity mgraph;

        public SelectTimeFragment(EditText time, GraphActivity graph) {
            this.mtime = time;
            this.mgraph = graph;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();
            int hh = calendar.get(Calendar.HOUR);
            int mm = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hh, mm, false);
        }

        @Override
        public void onTimeSet(TimePicker timePickerw, int hh, int mm) {
            populateSetTime(mtime, hh, mm);
            m_hour = hh;
            m_min = mm;
            mgraph.refresh_data(mgraph.MakenewCalendar(), mgraph.m_gap);
        }
    }

    public void selectTime() {
            DialogFragment newFragment = new SelectTimeFragment(m_EditText_time, this);
            newFragment.show(getFragmentManager(), "DatePicker");

    }

    public static void populateSetTime(EditText time_, int hour, int minute) {
        time_.setText(hour + "시 : " + minute + "분");
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        // Thanks to -> http://javapapers.com/android/android-datepicker/
        EditText mEditText;
        GraphActivity mgraph;

        public SelectDateFragment(EditText mEditText, GraphActivity graph) {
            this.mEditText = mEditText;
            mgraph = graph;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(mEditText, yy, mm + 1, dd);
            m_year = yy;
            m_month = mm;
            m_day = dd;
            mgraph.refresh_data(mgraph.MakenewCalendar(), mgraph.m_gap);
        }
    }

    public void selectDate() {
        DialogFragment newFragment = new SelectDateFragment(m_EditText_date, this);
        newFragment.show(getFragmentManager(), "DatePicker");
    }

    public static void populateSetDate(EditText mEditText, int year, int month, int day) {
        mEditText.setText(year + "/" + month + "/" + day);
    }

}
