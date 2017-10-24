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
 * Created by Kpresent on 2017. 8. 18..
 */

public class ListNetworkAdapter extends BaseAdapter {

    private MyEventListener mListener;

    static class ViewHolder_Network {
        TextView txtNetworkIP;
        TextView txtNetworkID;
        ImageView imgStatus;
    }

    private ArrayList<ListNetworkItem> listViewNetworkItemList = new ArrayList<>();

    public ListNetworkAdapter() {
    }

    public void removeAll() {
        listViewNetworkItemList = new ArrayList<>();
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

            holder.txtNetworkIP = v.findViewById(R.id.txt_network_ip);
            holder.txtNetworkID = v.findViewById(R.id.txt_network_id);
            holder.imgStatus = v.findViewById(R.id.img_network_status);
            v.setTag(holder);
        }


        // Get item
        ListNetworkItem item = listViewNetworkItemList.get(position);
        ViewHolder_Network holder = (ViewHolder_Network) v.getTag();
        if (item != null) {
            final String test = item.getNetworkIP();

            holder.txtNetworkIP.setText(item.getNetworkIP());
            holder.txtNetworkID.setText(/*item.getNetworkID() + "\n" + */item.getNetworkZone());

            if (item.getNetworkBasicIP()) {
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
        return listViewNetworkItemList.get(position);
    }

    public void addItem(String networkIP, String networkID, String networkZone, boolean networkBasicIP) {
        listViewNetworkItemList.add(new ListNetworkItem(networkIP, networkID, networkZone, networkBasicIP));
    }

    public void addItem(String n_displayname, String n_zonename, String n_type, String n_cidr) {
        listViewNetworkItemList.add(new ListNetworkItem(n_displayname, n_zonename, n_type, n_cidr));
    }


    public void addItemArray(ArrayList<ListNetworkItem> itemArray) {
        for (ListNetworkItem item : itemArray) {
            listViewNetworkItemList.add(item);
        }
    }

    public void setMyEventListener(MyEventListener listener) {
        mListener = listener;
    }
}