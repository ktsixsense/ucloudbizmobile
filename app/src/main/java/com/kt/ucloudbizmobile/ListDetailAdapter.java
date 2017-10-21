package com.kt.ucloudbizmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kpresent on 2017. 8. 25..
 */

public class ListDetailAdapter extends BaseAdapter {

    private MyEventListener mListener;

    static class ViewHolder_Detail {
        TextView txtTitle;
        TextView txtContent;
    }

    private ArrayList<ListDetailItem> listViewDetailItemList = new ArrayList<>();

    public ListDetailAdapter() {
    }

    @Override
    public int getCount() {
        return listViewDetailItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        View v = convertView;

        // ViewHolder makes the usage of system resources reducing.
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.list_item_detail, null);

            ViewHolder_Detail holder = new ViewHolder_Detail();

            holder.txtTitle = v.findViewById(R.id.txt_detail_title);
            holder.txtContent = v.findViewById(R.id.txt_detail_content);

            v.setTag(holder);
        }

        // Get item
        ListDetailItem item = listViewDetailItemList.get(position);

        if (item != null) {
            ViewHolder_Detail holder = (ViewHolder_Detail) v.getTag();

            holder.txtTitle.setText(item.getTitle());
            holder.txtContent.setText(item.getContent());

            if (holder.txtTitle.getText().toString().equalsIgnoreCase("UUID")) {
                holder.txtContent.setTextSize(13);
            } else {
                holder.txtContent.setTextSize(16);
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
        return listViewDetailItemList.get(position);
    }

    public void addItem(String title, String content) {
        listViewDetailItemList.add(new ListDetailItem(title, content));
    }

    public void setMyEventListener(MyEventListener listener) {
        mListener = listener;
    }
}
