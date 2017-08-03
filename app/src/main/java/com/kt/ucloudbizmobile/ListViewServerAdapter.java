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

public class ListViewServerAdapter extends BaseAdapter  {

    private MyEventListener mListener;

    static class ViewHolder_List {
        TextView Servername;
        TextView Serverstatus;
        ImageView Colorstatus;
        Button buttonwatch;
        int position;
    }
    private ArrayList<ListViewServerItem> listViewServerItemList = new ArrayList<ListViewServerItem>();
    public ViewHolder_List holder=new ViewHolder_List();

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

            holder.Servername = (TextView) convertView.findViewById(R.id.textServerName);
            holder.Serverstatus = (TextView) convertView.findViewById(R.id.textServerStatus);
            holder.Colorstatus = (ImageView) convertView.findViewById(R.id.colorStatus);
            holder.buttonwatch = (Button) convertView.findViewById(R.id.btn_watch);
        }

        ImageView colorStatus = (ImageView) convertView.findViewById(R.id.colorStatus);
        TextView textServerName = (TextView) convertView.findViewById(R.id.textServerName);
        TextView textServerStatus = (TextView) convertView.findViewById(R.id.textServerStatus);

        ListViewServerItem item = listViewServerItemList.get(position);

        colorStatus.setImageDrawable(item.getImageStatus());
        textServerName.setText(item.getServerName());
        textServerStatus.setText(item.getServerStatus());

        holder.Servername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMyEvent(Action_Servername_Click,(int)pos);
                //Intent intent = new Intent(MainActivity.this, ListActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
            }
        });

        holder.buttonwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onMyEvent(Action_WatchButton_Click,(int)pos);
                //Intent intent = new Intent(MainActivity.this, ListActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
            }
        });
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

    public void onClickText1(View v) {
        Log.d("1", "onclick text12");
    }

    public void setMyEventListener(MyEventListener listener) { mListener = listener; }

}
