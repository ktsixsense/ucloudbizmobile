package com.kt.ucloudbizmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;

import static com.kt.ucloudbizmobile.ActionType.Action_Servername_Click;
import static com.kt.ucloudbizmobile.ActionType.Action_WatchButton_Click;

/**
 * Created by Kpresent on 2017. 8. 1..
 */

public class MetricViewAdapter extends BaseAdapter  {

    private MyEventListener mListener;

    static class ViewHolder_Metric {
        TextView Metrictype;
        TextView Metricname;
        TextView Metricgroup;
        TextView Metricvalue;

        int position;
    }
    private ArrayList<MetricViewItem> listViewMetricItemList = new ArrayList<MetricViewItem>();
    public ViewHolder_Metric holder=new ViewHolder_Metric();

    public MetricViewAdapter() {   }

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

        holder.Metrictype.setText(item.getMetricType());
        holder.Metricname.setText(item.getMetricName());
        holder.Metricgroup.setText(item.getMetricGroup());
        holder.Metricvalue.setText(item.getMetricValue());

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

    public void addItem(String type, String name,String group, String value ) {
        MetricViewItem item = new MetricViewItem();

        item.setMetricType(type);
        item.setMetricName(name);
        item.setMetricGroup(group);
        item.setMetricValue(value);

        listViewMetricItemList.add(item);
    }

    public void setMyEventListener(MyEventListener listener) { mListener = listener; }

}