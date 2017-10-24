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

public class ListDiskAdapter extends BaseAdapter {

    private MyEventListener mListener;

    static class ViewHolder_Disk {
        TextView txtDiskName;
        TextView txtDiskSize;
        ImageView imgStatus;
    }

    private ArrayList<ListDiskItem> listViewDiskItemList = new ArrayList<>();

    public ListDiskAdapter() {
    }

    public void removeAll() {
        listViewDiskItemList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listViewDiskItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        View v = convertView;

        // ViewHolder makes the usage of system resources reducing.
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.list_item_disk, null);

            ViewHolder_Disk holder = new ViewHolder_Disk();

            holder.txtDiskName = v.findViewById(R.id.txt_disk_name);
            holder.txtDiskSize = v.findViewById(R.id.txt_disk_size);
            holder.imgStatus = v.findViewById(R.id.img_disk_status);

            v.setTag(holder);
        }

        // Get item
        ListDiskItem item = listViewDiskItemList.get(position);

        if (item != null) {
            final String test = item.getDiskName();
            ViewHolder_Disk holder = (ViewHolder_Disk) v.getTag();

            holder.txtDiskName.setText(item.getDiskName());
            holder.txtDiskSize.setText(Long.parseLong(item.getDiskSize()) / 1073741824 + "GB / " + item.getDiskZone());

            if (item.getDiskStatus()) {
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
        return listViewDiskItemList.get(position);
    }

    public void addItem(String diskName, String diskSize, String diskZone, boolean diskStatus, String connServer) {
        listViewDiskItemList.add(new ListDiskItem(diskName, diskSize, diskZone, diskStatus, connServer));
    }

    public void addItemArray(ArrayList<ListDiskItem> itemArray) {
        for (ListDiskItem item : itemArray) {
            listViewDiskItemList.add(item);
        }
    }

    public void setMyEventListener(MyEventListener listener) {
        mListener = listener;
    }
}