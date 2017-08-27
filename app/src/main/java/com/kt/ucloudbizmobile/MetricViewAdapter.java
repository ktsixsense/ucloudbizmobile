package com.kt.ucloudbizmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.kt.ucloudbizmobile.ActionType.Action_GraphButton_Click;

/**
 * Created by Kpresent on 2017. 8. 1..
 */

public class MetricViewAdapter extends BaseAdapter {

    private MyEventListener mListener;

    static class ViewHolder_Metric {
        TextView Metrictype;
        TextView Metricname;
        TextView Metricgroup;
        TextView Metricvalue;
        Button ButtonGraph;

        int position;
    }

    private ArrayList<MetricViewItem> listViewMetricItemList = new ArrayList<MetricViewItem>();
    public ViewHolder_Metric holder = new ViewHolder_Metric();

    public MetricViewAdapter() {
    }

    @Override
    public int getCount() {
        return listViewMetricItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_metric, parent, false);
        }
        MetricViewItem item = listViewMetricItemList.get(position);

        holder.Metrictype = (TextView) convertView.findViewById(R.id.textmetric);
        holder.Metricname = (TextView) convertView.findViewById(R.id.textmetricname);
        holder.Metricgroup = (TextView) convertView.findViewById(R.id.textgroup);
        holder.Metricvalue = (TextView) convertView.findViewById(R.id.textvalue);
        holder.ButtonGraph = (Button) convertView.findViewById(R.id.btn_grpah);

        holder.Metrictype.setText(item.getMetricType());
        holder.Metricname.setText(item.getMetricName());
        holder.Metricgroup.setText(item.getMetricGroup());
        holder.Metricvalue.setText(item.getMetricValue());

        holder.ButtonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMyEvent(Action_GraphButton_Click, (int) pos);
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewMetricItemList.get(position);
    }

    public void addItem(String type, String name, String group, String value) {
        MetricViewItem item = new MetricViewItem();

        item.setMetricType(type);
        item.setMetricName(name);
        item.setMetricGroup(group);
        item.setMetricValue(value);

        listViewMetricItemList.add(item);
    }

    public void setMyEventListener(MyEventListener listener) {
        mListener = listener;
    }

}