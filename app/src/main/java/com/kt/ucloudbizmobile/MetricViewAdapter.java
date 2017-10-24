package com.kt.ucloudbizmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kpresent on 2017. 8. 1..
 */

public class MetricViewAdapter extends BaseAdapter {

    public ViewHolder_Metric holder = new ViewHolder_Metric();
    private ArrayList<MetricViewItem> listViewMetricItemList = new ArrayList<MetricViewItem>();

    public MetricViewAdapter() {
    }

    @Override
    public int getCount() {
        return listViewMetricItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.list_item_metric, null);
            ViewHolder_Metric holder = new ViewHolder_Metric();

            holder.Metrictype = v.findViewById(R.id.textmetric);
            holder.Metricname = v.findViewById(R.id.textmetricname);
            holder.Metricgroup = v.findViewById(R.id.textgroup);
            holder.Metricvalue = v.findViewById(R.id.textvalue);

            v.setTag(holder);
        }

        // Get Item
        MetricViewItem item = listViewMetricItemList.get(position);
        ViewHolder_Metric holder = (ViewHolder_Metric) v.getTag();
        if (item != null) {
            holder.Metrictype.setText(item.getMetricType());
            holder.Metricname.setText(item.getMetricName());
            holder.Metricgroup.setText(item.getMetricGroup());
            holder.Metricvalue.setText(item.getMetricValue());
        }

        return v;
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

    static class ViewHolder_Metric {
        TextView Metrictype;
        TextView Metricname;
        TextView Metricgroup;
        TextView Metricvalue;
    }

}