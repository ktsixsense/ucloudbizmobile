package com.kt.ucloudbizmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {
    ArrayList<Alarm> alarmData;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        setContentView(R.layout.activity_alarm);

        progressBar = (ProgressBar) findViewById(R.id.progressList);

        String apiKey = "kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg";
        String secretKey = "NmczQzPOE-CoYLbKpvo3UHJSaZ_6e9SC3tJIYsMIoiTJYMWMn8x-DpzBRTzzSkk0xegYz7g2yrvt_8jRrScxHQ";

        alarmData = new ArrayList<>();

        final AQuery aq = new AQuery(this);
        final ApiParser parser = new ApiParser();

        final ListView listView = (ListView) findViewById(R.id.listAlarm);
        final ListAlarmAdapter adapter = new ListAlarmAdapter();
        listView.setAdapter(adapter);
        adapter.setMyEventListener(adapter.mListener);
        //
        //String url = "https://api.ucloudbiz.olleh.com/server/v1/client/api?command=listVirtualMachines&apikey=kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg&signature=WgLcczEWC3Jf%2F4%2F7NJTqPuj1FrU%3D";
        final String cloudstack1 = ApiGenerator.apiGenerator(apiKey, secretKey, "listAlarms", false, "all");

        aq.ajax(cloudstack1, String.class, new AjaxCallback<String>() {
            Alarm[] alarms = null;
            ArrayList<ListAlarmItem> dataSet = new ArrayList<>();
            int dataCount = 0;

            @Override
            public void callback(String url, String json, AjaxStatus status) {
                if (json != null) {
                    //successful ajax call, show status code and json content
                    Document doc = parser.getDocument(json);
                    int index = parser.getNumberOfResponse("alarm", doc);
                    alarms = new Alarm[index];
                    alarms = parser.parseAlarmList(doc, index);
                    Log.d("alarm 개수 ", ""+index);

                    for (int i = 0; i < index; i++) {
                        dataSet.add(i, new ListAlarmItem(alarms[i].alarmname, alarms[i].statevalue, alarms[i].metricname, alarms[i].compOperator, alarms[i].period, alarms[i].evalPeriod, alarms[i].threshold, alarms[i].statistics, alarms[i].unit, alarms[i].alarmSenabled));
                        alarmData.add(i, alarms[i]);
                        dataCount++;
                    }

                    adapter.addItemArray(dataSet);
                    adapter.notifyDataSetChanged();

                    //progressBar.setVisibility(View.INVISIBLE);

                } else {
                    //ajax error, show error code
                    AppUtility.showMsg(getApplicationContext(), "Error : " + status.getError());
                }
            }
        });

    }
}

class ListAlarmItem {
    public String alarmname;
    public String statevalue; // OK - 안정, INSUFFICIENT_DATA - 데이터 부족, ALARM - 알람
    public String metricname; // CPUUtilization 등등
    public String compOperator; // GreaterThanOrEqualToThreshold(이상), GreaterThanThreshold(초과), LessThanThreshold(미만), LessThanOrEqualToThreshold(이하)
    // 출력할 주기는 period * evalPeriod
    public String period;
    public String evalPeriod;
    public String threshold;
    public String statistics; // SampleCount, Average, Sum, Minimum, Maximum
    public String unit; // Percent, Bytes 등등
    public String alarmSenabled;

    public ListAlarmItem(String alarmname, String statevalue, String metricname, String compOperator, String period, String evalPeriod, String threshold, String statistics, String unit, String alarmSenabled) {
        this.alarmname = alarmname;
        this.statevalue = statevalue;
        this.metricname = metricname;
        this.compOperator = compOperator;
        this.period = period;
        this.evalPeriod = evalPeriod;
        this.threshold = threshold;
        this.statistics = statistics;
        this.unit = unit;
        this.alarmSenabled = alarmSenabled;
    }

    public String getAlarmname() {
        return alarmname;
    }

    public void setAlarmname(String alarmname) {
        this.alarmname = alarmname;
    }

    public String getStatevalue() {
        return statevalue;
    }

    public void setStatevalue(String statevalue) {
        this.statevalue = statevalue;
    }

    public String getMetricname() {
        return metricname;
    }

    public void setMetricname(String metricname) {
        this.metricname = metricname;
    }

    public String getCompOperator() {
        return compOperator;
    }

    public void setCompOperator(String compOperator) {
        this.compOperator = compOperator;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getEvalPeriod() {
        return evalPeriod;
    }

    public void setEvalPeriod(String evalPeriod) {
        this.evalPeriod = evalPeriod;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAlarmSenabled() {
        return alarmSenabled;
    }

    public void setAlarmSenabled(String alarmSenabled) {
        this.alarmSenabled = alarmSenabled;
    }
}

class ListAlarmAdapter extends BaseAdapter {
    public MyEventListener mListener;

    static class ViewHolder_Alarm {
        TextView txtAlarmName;
        TextView txtAlarmStateAndPeriod;
        TextView txtAlarmStatAndMetric;
        TextView txtAlarmThresholdAndComp;
        ImageView imgStatus;
    }

    private ArrayList<ListAlarmItem> listViewAlarmItemList = new ArrayList<>();

    public ListAlarmAdapter() {

    }

    public void removeAll() {
        listViewAlarmItemList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listViewAlarmItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        View v = convertView;

        // ViewHolder makes the usage of system resources reducing.
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.list_item_alarm, null);

            ListAlarmAdapter.ViewHolder_Alarm holder = new ListAlarmAdapter.ViewHolder_Alarm();

            holder.txtAlarmName = v.findViewById(R.id.txt_alarm_name);
            holder.txtAlarmStateAndPeriod = v.findViewById(R.id.txt_alarm_state_and_period);
            holder.txtAlarmStatAndMetric = v.findViewById(R.id.txt_alarm_stat_and_metric);
            holder.txtAlarmThresholdAndComp = v.findViewById(R.id.txt_alarm_threshold_comp);
            holder.imgStatus = v.findViewById(R.id.img_alarmsenabled);
            v.setTag(holder);
        }

        // Get item
        ListAlarmItem item = listViewAlarmItemList.get(position);

        if (item != null) {
            final String test = item.getAlarmname();
            ListAlarmAdapter.ViewHolder_Alarm holder = (ListAlarmAdapter.ViewHolder_Alarm) v.getTag();

            // 데이터 파싱은 여기 출력부에서
            holder.txtAlarmName.setText(nameParser(item.getAlarmname()));
            holder.txtAlarmStateAndPeriod.setText("상태: " + stateParser(item.getStatevalue()) + " / 주기 " + Integer.parseInt(item.getPeriod()) * Integer.parseInt(item.getEvalPeriod()) + "분");
            holder.txtAlarmStatAndMetric.setText(item.getStatistics() + " " + item.getMetricname() + "이(가)");
            holder.txtAlarmThresholdAndComp.setText(item.getThreshold() + " " + item.getUnit() + compParser(item.getCompOperator()));
            if (item.getAlarmSenabled().equals("true")) {
                holder.imgStatus.setImageResource(R.drawable.ic_power_on);
            } else {
                holder.imgStatus.setImageResource(R.drawable.ic_power_off);
            }
        }

        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewAlarmItemList.get(position);
    }

    public void addItem(String alarmname, String statevalue, String metricname, String compOperator, String period, String evalPeriod, String threshold, String statistics, String unit, String alarmSenabled) {
        listViewAlarmItemList.add(new ListAlarmItem(alarmname, statevalue, metricname, compOperator, period, evalPeriod, threshold, statistics, unit, alarmSenabled));
    }

    public void addItemArray(ArrayList<ListAlarmItem> itemArray) {
        for (ListAlarmItem item : itemArray) {
            listViewAlarmItemList.add(item);
        }
    }

    public void setMyEventListener(MyEventListener listener) {
        mListener = listener;
    }

    public String nameParser(String alarmName) {
        String temp = "";
        int index =  alarmName.indexOf("_");
        temp = alarmName.substring(index+1);
        return temp;
    }
    public String stateParser(String alarmState) {
        if(alarmState.equals("OK")) {
            return "안정";
        } else if(alarmState.equals("INSUFFICIENT_DATA")) {
            return "데이터부족";
        } else if(alarmState.equals("ALARM")) {
            return "알람";
        }
        return "안정";
    }

    public String statParser(String alarmStat) {
        if(alarmStat.equals("Average")) {
            return "평균";
        } else if (alarmStat.equals("Maximum")) {
            return " 최대";
        } else if (alarmStat.equals("Minimum")) {
            return " 최소";
        } else if (alarmStat.equals("Sum")) {
            return " 합계";
        } else {
            return " 샘플카운트";
        }
    }

    public String compParser(String alarmComp) {
        if(alarmComp.equals("GreaterThanThreshold")) {
            return " 보다 클 때";
        } else if (alarmComp.equals("GreaterThanOrEqualToThreshold")) {
            return " 보다 크거나 같을 때";
        } else if (alarmComp.equals("LessThanOrEqualToThreshold")) {
            return " 보다 작거나 같을 때";
        } else {
            return " 보다 작을 때";
        }
    }
}
