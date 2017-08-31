package com.kt.ucloudbizmobile;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyEventListener {

    private BackPressCloseHandler backPressCloseHandler;

    ArrayList<Server> serverData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        serverData = new ArrayList<>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        backPressCloseHandler = new BackPressCloseHandler(this);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Creating bottom tab menu
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        // #1
        TabHost.TabSpec ts1 = tabHost.newTabSpec("server");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("Server");
        tabHost.addTab(ts1);

        ListView listView = (ListView) findViewById(R.id.listServer);
        final ListServerAdapter adapter = new ListServerAdapter();
        listView.setAdapter(adapter);
        adapter.setMyEventListener(this);


        /** Data Set
         * 1. adapter.addItem(ListServerItem)
         * 2. adapter.addItemArray(ArrayList<ListServerItem>)
         **/
        // Sample data
//        for (int i = 0; i < 10; i++) {
//            adapter.addItem(new ListServerItem("Server Name " + (i + 1), "Server OS " + (i + 1), i % 3 == 0));
//        }

        String apiKey = "kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg";
        String secretKey = "NmczQzPOE-CoYLbKpvo3UHJSaZ_6e9SC3tJIYsMIoiTJYMWMn8x-DpzBRTzzSkk0xegYz7g2yrvt_8jRrScxHQ";

        final AQuery aq = new AQuery(this);
        final ApiParser parser = new ApiParser();

        //String url = "https://api.ucloudbiz.olleh.com/server/v1/client/api?command=listVirtualMachines&apikey=kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg&signature=WgLcczEWC3Jf%2F4%2F7NJTqPuj1FrU%3D";
        final String cloudstack1 = ApiGenerator.apiGenerator(apiKey, secretKey, "listVirtualMachines", false, "all");
        final String cloudstack2 = ApiGenerator.apiGenerator(apiKey, secretKey, "listVirtualMachines", true, "all");

        String watch_url_tmp = ApiGenerator.apiGeneratorWatch(apiKey, secretKey, "getMetricStatistics", false);
        // cloudstack2
        aq.ajax(cloudstack2, String.class, new AjaxCallback<String>() {
            Server[] servers = null;
            ArrayList<ListServerItem> dataSet = new ArrayList<>();
            int dataCount = 0;

            @Override
            public void callback(String url, String json, AjaxStatus status) {
                if (json != null) {
                    //successful ajax call, show status code and json content
                    Document doc = parser.getDocument(json);
                    int index = parser.getNumberOfResponse("server", doc);
                    servers = new Server[index];
                    servers = parser.parseServerList(doc, index);

                    for (int i = 0; i < index; i++) {
                        dataSet.add(i, new ListServerItem(servers[i].displayname, servers[i].os, servers[i].state.equals("Running")));
                        serverData.add(i, servers[i]);
                        dataCount++;
                    }
//                    adapter.addItemArray(dataSet);
//                    adapter.notifyDataSetChanged();

                    aq.ajax(cloudstack1, String.class, new AjaxCallback<String>() {

                        @Override
                        public void callback(String url, String json, AjaxStatus status) {
                            if (json != null) {
                                //successful ajax call, show status code and json content
                                Document doc = parser.getDocument(json);
                                int index = parser.getNumberOfResponse("server", doc);
                                servers = new Server[index];
                                servers = parser.parseServerList(doc, index);

                                for (int i = 0; i < index; i++) {
                                    dataSet.add(i + dataCount, new ListServerItem(servers[i].displayname, servers[i].os, servers[i].state.equals("Running")));
                                    serverData.add(i + dataCount, servers[i]);
                                }
                                adapter.addItemArray(dataSet);
                                adapter.notifyDataSetChanged();

                            } else {
                                //ajax error, show error code
                                Toast.makeText(getApplicationContext(), "Error : " + status.getError(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    //ajax error, show error code
                    Toast.makeText(getApplicationContext(), "Error : " + status.getError(), Toast.LENGTH_LONG).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Server data = serverData.get(i);

                //
                Intent intent = new Intent(MainActivity.this, DetailServerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", data);

                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.LEFT);
                getWindow().setExitTransition(slide);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });

        // #2
        TabHost.TabSpec ts2 = tabHost.newTabSpec("disk");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("Disk");
        tabHost.addTab(ts2);

        ListView listView2 = (ListView) findViewById(R.id.listDisk);
        ListDiskAdapter adapter2 = new ListDiskAdapter();
        listView2.setAdapter(adapter2);
        adapter2.setMyEventListener(this);

        // Sample data
        adapter2.addItem("Test A", "500GB", true);
        adapter2.addItem("Test B", "250GB", false);
        adapter2.addItem("Test C", "200GB", false);
        adapter2.addItem("Test D", "128GB", true);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String test = ((ListDiskItem) adapterView.getItemAtPosition(i)).getDiskName();
                //
                Intent intent = new Intent(MainActivity.this, DetailDiskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", test);

                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.LEFT);
                getWindow().setExitTransition(slide);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });

        // #3
        TabHost.TabSpec ts3 = tabHost.newTabSpec("network");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("Network");
        tabHost.addTab(ts3);

        ListView listView3 = (ListView) findViewById(R.id.listNetwork);
        ListNetworkAdapter adapter3 = new ListNetworkAdapter();
        listView3.setAdapter(adapter3);
        adapter3.setMyEventListener(this);

        // Sample data
        adapter3.addItem("211.252.84.108", "ID 1");
        adapter3.addItem("14.63.222.251", "ID 2");
        adapter3.addItem("14.63.163.15", "ID 3");

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String test = ((ListNetworkItem) adapterView.getItemAtPosition(i)).getNetworkName();
                //
                Intent intent = new Intent(MainActivity.this, DetailNetworkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", test);

                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.LEFT);
                getWindow().setExitTransition(slide);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backPressCloseHandler.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            getWindow().setExitTransition(new Explode());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, GraphActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            getWindow().setExitTransition(new Explode());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            getWindow().setExitTransition(new Explode());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else if (id == R.id.nav_share) {
            Toast.makeText(getApplicationContext(), "444", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(getApplicationContext(), "555", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMyEvent(ActionType act, int test) {
        if (act == ActionType.Action_WatchButton_Click) {

        }
        if (act == ActionType.Action_Servername_Click) {

        }

    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };
}
