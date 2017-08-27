package com.kt.ucloudbizmobile;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kpresent on 2017. 8. 18..
 */

public class ListNetworkAdapter extends BaseAdapter {

    private MyEventListener mListener;

    static class ViewHolder_Network {
        TextView txtNetworkName;
        TextView txtNetworkID;
        ImageButton btnMonitor;
    }

    private ArrayList<ListNetworkItem> listViewNetworkItemList = new ArrayList<>();

    public ListNetworkAdapter() {
    }

    @Override
    public int getCount() {
        return listViewNetworkItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        View v = convertView;

        // ViewHolder makes the usage of system resources reducing.
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.list_item_network, null);

            ViewHolder_Network holder = new ViewHolder_Network();

            holder.txtNetworkName = v.findViewById(R.id.txt_network_name);
            holder.txtNetworkID = v.findViewById(R.id.txt_network_id);
            holder.btnMonitor = v.findViewById(R.id.btn_network_monitor);
            v.setTag(holder);
        }

        // Get item
        ListNetworkItem item = listViewNetworkItemList.get(position);

        if (item != null) {
            final String test = item.getNetworkName();
            ViewHolder_Network holder = (ViewHolder_Network) v.getTag();

            holder.txtNetworkName.setText(item.getNetworkName());
            holder.txtNetworkID.setText(item.getNetworkID());

            // [TO FIX] Move to monitoring Activity...
            holder.btnMonitor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, test + "의 모니터링 화면", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            // Allow touch event to be delivered to the listview.
            holder.btnMonitor.setFocusable(false);
        }

        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewNetworkItemList.get(position);
    }

    public void addItem(String networkName, String networkID) {
        listViewNetworkItemList.add(new ListNetworkItem(networkName, networkID));
    }

    public void setMyEventListener(MyEventListener listener) {
        mListener = listener;
    }
}