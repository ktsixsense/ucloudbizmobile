package com.kt.ucloudbizmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aicel on 2017-10-19.
 */

public class ListNetworkAdapter2 extends BaseAdapter {
    private MyEventListener mListener;
    private ArrayList<ListNetworkItem> listViewNetworkItemList = new ArrayList<>();

    public ListNetworkAdapter2() {

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
            v = inflater.inflate(R.layout.list_item_network2, null);
            ViewHolder_Network2 holder = new ViewHolder_Network2();

            holder.txtNname = v.findViewById(R.id.txt_network_nname);
            holder.txtNzoneNtype = v.findViewById(R.id.txt_network_nzone_ntype);
            holder.txtNcidr = v.findViewById(R.id.txt_network_ncidr);

            v.setTag(holder);
        }
        ListNetworkItem item = listViewNetworkItemList.get(position);
        ViewHolder_Network2 holder = (ViewHolder_Network2) v.getTag();
        if (item != null) {
            holder.txtNname.setText(item.getN_displayname());
            holder.txtNzoneNtype.setText(item.getN_zonename() + " / " + item.getN_type());
            holder.txtNcidr.setText(item.getN_cidr());

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

    static class ViewHolder_Network2 {
        TextView txtNname;
        TextView txtNzoneNtype;
        TextView txtNcidr;
    }

}
