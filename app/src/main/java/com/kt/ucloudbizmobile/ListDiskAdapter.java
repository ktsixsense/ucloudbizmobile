package com.kt.ucloudbizmobile;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
        ImageButton btnMonitor;
    }

    private ArrayList<ListDiskItem> listViewDiskItemList = new ArrayList<>();

    public ListDiskAdapter() {
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
            holder.btnMonitor = v.findViewById(R.id.btn_disk_monitor);

            v.setTag(holder);
        }

        // Get item
        ListDiskItem item = listViewDiskItemList.get(position);

        if (item != null) {
            final String test = item.getDiskName();
            ViewHolder_Disk holder = (ViewHolder_Disk) v.getTag();

            holder.txtDiskName.setText(item.getDiskName());
            holder.txtDiskSize.setText(item.getDiskSize());

            if (item.getDiskStatus()) {
                holder.imgStatus.setImageResource(R.drawable.ic_power_on);
            } else {
                holder.imgStatus.setImageResource(R.drawable.ic_power_off);
            }

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
        return listViewDiskItemList.get(position);
    }

    public void addItem(String diskName, String diskSize, boolean diskStatus) {
        listViewDiskItemList.add(new ListDiskItem(diskName, diskSize, diskStatus));
    }

    public void setMyEventListener(MyEventListener listener) {
        mListener = listener;
    }
}