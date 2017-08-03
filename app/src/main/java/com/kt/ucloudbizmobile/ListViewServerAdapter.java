package com.kt.ucloudbizmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kpresent on 2017. 8. 1..
 */

public class ListViewServerAdapter extends BaseAdapter {

    private ArrayList<ListViewServerItem> listViewServerItemList = new ArrayList<ListViewServerItem>();

    public ListViewServerAdapter() {
    }

    @Override
    public int getCount() {
        return listViewServerItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_server, parent, false);
        }

        ImageView colorStatus = (ImageView) convertView.findViewById(R.id.colorStatus);
        TextView textServerName = (TextView) convertView.findViewById(R.id.textServerName);
        TextView textServerStatus = (TextView) convertView.findViewById(R.id.textServerStatus);

        ListViewServerItem item = listViewServerItemList.get(position);

        colorStatus.setImageDrawable(item.getImageStatus());
        textServerName.setText(item.getServerName());
        textServerStatus.setText(item.getServerStatus());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewServerItemList.get(position);
    }

    public void addItem(String name, String status) {
        ListViewServerItem item = new ListViewServerItem();

        //item.setImageStatus(icon);
        item.setServerName(name);
        item.setServerStatus(status);

        listViewServerItemList.add(item);
    }
}
