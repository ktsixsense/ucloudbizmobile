package com.kt.ucloudbizmobile;

import android.content.Context;
import android.content.Intent;
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
 * Created by Kpresent on 2017. 8. 1..
 */

public class ListServerAdapter extends BaseAdapter {

    private MyEventListener mListener;
    private Context context;
    private ArrayList<ListServerItem> listViewServerItemList = new ArrayList<>();

    public ListServerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listViewServerItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        View v = convertView;

        // ViewHolder makes the usage of system resources reducing.
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.list_item_server, null);

            ViewHolder_Server holder = new ViewHolder_Server();

            holder.txtServerName = v.findViewById(R.id.txt_server_name);
            holder.txtServerOS = v.findViewById(R.id.txt_server_os);
            holder.imgStatus = v.findViewById(R.id.img_server_status);
            holder.btnMonitor = v.findViewById(R.id.btn_server_monitor);

            v.setTag(holder);
        }

        // Get item
        final ListServerItem item = listViewServerItemList.get(position);

        if (item != null) {
            final String test = item.getServerName();
            ViewHolder_Server holder = (ViewHolder_Server) v.getTag();

            holder.txtServerName.setText(item.getServerName());
            holder.txtServerOS.setText(item.getServerOS() + " / " + item.getServerZone());
            if (item.getStatus()) {
                holder.imgStatus.setImageResource(R.drawable.ic_power_on);
            } else {
                holder.imgStatus.setImageResource(R.drawable.ic_power_off);
            }

            // [TO FIX] Move to monitoring Activity...
            holder.btnMonitor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!item.getStatus()) {
                        Snackbar.make(view, "해당 서버가 꺼져있어 메트릭을 조회할 수 없습니다. 서버를 재가동 하시기 바랍니다.", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    String data = item.getServerName();
                    //
                    Intent intent = new Intent(context, MetricActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("data", data);

                    context.startActivity(intent);
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
        return listViewServerItemList.get(position);
    }

    public void addItem(ListServerItem item) {
        listViewServerItemList.add(item);
    }

    public void addItemArray(ArrayList<ListServerItem> itemArray) {
        for (ListServerItem item : itemArray) {
            listViewServerItemList.add(item);
        }
    }

    public void removeAll() {
        listViewServerItemList = new ArrayList<>();
    }

    public void setMyEventListener(MyEventListener listener) {
        mListener = listener;
    }

    static class ViewHolder_Server {
        TextView txtServerName;
        TextView txtServerOS;
        ImageView imgStatus;
        ImageButton btnMonitor;
    }
}