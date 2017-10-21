package com.kt.ucloudbizmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Document;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import static com.kt.ucloudbizmobile.R.id.spinner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyEventListener {

    // Push Server Connection
    String refreshedToken;
    String IP = "211.252.84.108";
    int PORT = 8088;

    SetSocket setSocket;

    private TabHost tabHost;
    private BackPressCloseHandler backPressCloseHandler;
    private ProgressBar progressBar;
    private Spinner zoneSpinner;

    ArrayList<Server> serverData;
    ArrayList<Disk> diskData;
    ArrayList<Network> networkData;
    ArrayList<Network> networkData2;

    ArrayList<Server> serverTempData;
    ArrayList<Disk> diskTempData;
    ArrayList<Network> networkTempData;
    ArrayList<Network> networkTempData2;

    private int totalServer;
    private int totalDisk;
    private int totalNetwork;
    private int totalNetwork2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        zoneSpinner = (Spinner) findViewById(spinner);
        progressBar = (ProgressBar) findViewById(R.id.progressList);

        serverTempData = new ArrayList<>();
        diskTempData = new ArrayList<>();
        networkTempData = new ArrayList<>();
        networkTempData2 = new ArrayList<>();

        final AQuery aq = new AQuery(this);
        final ApiParser parser = new ApiParser();

        // Firebase
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // AppUtility.showMsg(getApplicationContext(), refreshedToken);
        Log.d("push-server", "token id : " + refreshedToken);
        setSocket = new SetSocket(IP, PORT);
        setSocket.start();

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
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        // #1
        final TabHost.TabSpec ts1 = tabHost.newTabSpec("server");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("Server");
        tabHost.addTab(ts1);

        final ListView listView = (ListView) findViewById(R.id.listServer);
        final ListServerAdapter adapter = new ListServerAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        adapter.setMyEventListener(this);

        /** Data Set
         * 1. adapter.addItem(ListServerItem)
         * 2. adapter.addItemArray(ArrayList<ListServerItem>)
         **/
        final String apiKey = "kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg";
        final String secretKey = "NmczQzPOE-CoYLbKpvo3UHJSaZ_6e9SC3tJIYsMIoiTJYMWMn8x-DpzBRTzzSkk0xegYz7g2yrvt_8jRrScxHQ";

        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Server data = serverTempData.get(i);
                //
                Intent intent = new Intent(MainActivity.this, DetailServerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", data);

                startActivity(intent);
            }
        });

        // #2
        final TabHost.TabSpec ts2 = tabHost.newTabSpec("disk");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("Disk");
        tabHost.addTab(ts2);

        final ListView listView2 = (ListView) findViewById(R.id.listDisk);
        final ListDiskAdapter adapter2 = new ListDiskAdapter();
        listView2.setAdapter(adapter2);
        adapter2.setMyEventListener(this);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Disk data = diskTempData.get(i);
                //
                Intent intent = new Intent(MainActivity.this, DetailDiskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", data);

                startActivity(intent);
            }
        });

        // #3
        final TabHost.TabSpec ts3 = tabHost.newTabSpec("network");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("Network");
        tabHost.addTab(ts3);

        final ListView listView3 = (ListView) findViewById(R.id.listNetwork);
        final ListView listView4 = (ListView) findViewById(R.id.listNetwork2);
        final ListNetworkAdapter adapter3 = new ListNetworkAdapter();
        final ListNetworkAdapter2 adapter4 = new ListNetworkAdapter2();
        listView3.setAdapter(adapter3);
        listView4.setAdapter(adapter4);
        adapter3.setMyEventListener(this);
        adapter4.setMyEventListener(this);

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Network data = networkTempData.get(i);
                //
                Intent intent = new Intent(MainActivity.this, DetailNetworkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", data);

                startActivity(intent);
            }
        });

        listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Network data2 = networkTempData2.get(i);
                //
                Intent intent = new Intent(MainActivity.this, DetailNetworkActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data", data2);

                startActivity(intent);
            }
        });

        // APII ====================================================================================
        // Get data from API when tab changed.
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                setTabHostEnabled(false);

                if (s.equalsIgnoreCase("server")) {
                    getServerData(apiKey, secretKey, adapter, aq, parser);
                } else if (s.equalsIgnoreCase("disk")) {
                    getDiskData(apiKey, secretKey, adapter2, aq, parser);
                } else if (s.equalsIgnoreCase("network")) {
                    getNetworkData(apiKey, secretKey, adapter3, adapter4, aq, parser);
                }
            }
        });

        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String zonename = "";
                switch ("" + adapterView.getSelectedItem()) {
                    case "KOR-Seoul M2":
                        zonename = "kr-md2-1";
                        break;
                    case "KOR-Central A":
                        zonename = "kr-1";
                        break;
                    case "KOR-Central B":
                        zonename = "kr-2";
                        break;
                    case "KOR-Seoul M":
                        zonename = "kr-0";
                        break;
                    case "전체":
                        zonename = "all";
                }

                String curTab = tabHost.getCurrentTabTag();

                if (curTab.equalsIgnoreCase("server")) {
                    ArrayList<Server> temp = new ArrayList<>();
                    ArrayList<ListServerItem> tempSet = new ArrayList<>();
                    serverTempData = new ArrayList<>();

                    int count = 0;

                    if (zonename.equalsIgnoreCase("all")) {
                        temp = serverData;
                        count = totalServer;
                    } else {
                        for (Server server : serverData) {
                            if (server.zonename.equalsIgnoreCase(zonename)) {
                                temp.add(count, server);
                                count++;
                            }
                        }
                    }

                    for (int k = 0; k < count; k++) {
                        tempSet.add(k, new ListServerItem(temp.get(k).displayname, temp.get(k).os, temp.get(k).zonename, temp.get(k).state.equals("Running")));
                        serverTempData.add(k, temp.get(k));
                    }

                    adapter.removeAll();
                    adapter.addItemArray(tempSet);
                    adapter.notifyDataSetChanged();
                    listView.invalidate();
                } else if (curTab.equalsIgnoreCase("disk")) {
                    ArrayList<Disk> temp2 = new ArrayList<>();
                    ArrayList<ListDiskItem> tempSet2 = new ArrayList<>();
                    diskTempData = new ArrayList<>();

                    int count2 = 0;

                    if (zonename.equalsIgnoreCase("all")) {
                        temp2 = diskData;
                        count2 = totalDisk;
                    } else {
                        for (Disk disk : diskData) {
                            if (disk.zonename.equalsIgnoreCase(zonename)) {
                                temp2.add(count2, disk);
                                count2++;
                            }
                        }
                    }

                    for (int k = 0; k < count2; k++) {
                        tempSet2.add(k, new ListDiskItem(temp2.get(k).displayname, temp2.get(k).size, temp2.get(k).zonename, temp2.get(k).state != null ? true : false, temp2.get(k).vmname));
                        diskTempData.add(k, temp2.get(k));
                    }

                    adapter2.removeAll();
                    adapter2.addItemArray(tempSet2);
                    adapter2.notifyDataSetChanged();
                    listView2.invalidate();
                } else if (curTab.equalsIgnoreCase("network")) {

                    ArrayList<Network> temp3 = new ArrayList<>();
                    ArrayList<Network> temp4 = new ArrayList<>();

                    ArrayList<ListNetworkItem> tempSet3 = new ArrayList<>();
                    ArrayList<ListNetworkItem> tempSet4 = new ArrayList<>();

                    networkTempData = new ArrayList<>();
                    networkTempData2 = new ArrayList<>();

                    int count3 = 0;
                    int count4 = 0;

                    if (zonename.equalsIgnoreCase("all")) {
                        temp3 = networkData;
                        temp4 = networkData2;
                        count3 = totalNetwork;
                        count4 = totalNetwork2;
                    } else {
                        for (Network network : networkData) {
                            if (network.zonename.equalsIgnoreCase(zonename)) {
                                temp3.add(count3, network);
                                count3++;
                            }
                        }
                        for (Network network : networkData2) {
                            if (network.n_zonename.equalsIgnoreCase(zonename)) {
                                temp4.add(count4, network);
                                count4++;
                            }
                        }
                    }

                    for (int k = 0; k < count3; k++) {
                        tempSet3.add(k, new ListNetworkItem(temp3.get(k).ipaddress, temp3.get(k).addressid, temp3.get(k).zonename, temp3.get(k).usageplan.equals("") ? false : true));
                        networkTempData.add(k, temp3.get(k));
                    }
                    for (int k = 0; k < count4; k++) {
                        tempSet4.add(k, new ListNetworkItem(temp4.get(k).n_displayname, temp4.get(k).n_zonename, temp4.get(k).n_type, temp4.get(k).n_cidr));
                        networkTempData2.add(k, temp4.get(k));
                    }

                    adapter3.removeAll();
                    adapter4.removeAll();
                    adapter3.addItemArray(tempSet3);
                    adapter4.addItemArray(tempSet4);
                    adapter3.notifyDataSetChanged();
                    adapter4.notifyDataSetChanged();
                    listView3.invalidate();
                    listView4.invalidate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getServerData(apiKey, secretKey, adapter, aq, parser);
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

        if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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

    public class SetSocket extends Thread {

        Socket socket;
        OutputStream os;
        BufferedWriter bw;

        String IP;
        int PORT;

        public SetSocket(String IP, int PORT) {
            this.IP = IP;
            this.PORT = PORT;
        }

        public void run() {
            try {
                socket = new Socket(IP, PORT);
                os = new DataOutputStream(socket.getOutputStream());
                bw = new BufferedWriter(new OutputStreamWriter(os));

                bw.write(refreshedToken);
                bw.flush();
                bw.close();

                Log.d("push-server", "Push server connection succeed.");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getServerData(String apiKey, String secretKey, final ListServerAdapter adapter, final AQuery aq, final ApiParser parser) {

        serverData = new ArrayList<>();

        adapter.removeAll();
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);

        //String url = "https://api.ucloudbiz.olleh.com/server/v1/client/api?command=listVirtualMachines&apikey=kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg&signature=WgLcczEWC3Jf%2F4%2F7NJTqPuj1FrU%3D";
        final String cloudstack1 = ApiGenerator.apiGenerator(apiKey, secretKey, "listVirtualMachines", false, "all");
        final String cloudstack2 = ApiGenerator.apiGenerator(apiKey, secretKey, "listVirtualMachines", true, "all");
        final String cloudstack3 = ApiGenerator.apiGenerator(apiKey, secretKey, "listVirtualMachines", true, "6fa9ef2b-1824-4c7a-b288-9498738ca9b8");
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
                        dataSet.add(i, new ListServerItem(servers[i].displayname, servers[i].os, servers[i].zonename, servers[i].state.equals("Running")));
                        serverData.add(i, servers[i]);
                        dataCount++;
                    }

                    aq.ajax(cloudstack1, String.class, new AjaxCallback<String>() {

                        @Override
                        public void callback(String url, String json, AjaxStatus status) {
                            if (json != null) {
                                //successful ajax call, show status code and json content
                                Document doc = parser.getDocument(json);
                                int index = parser.getNumberOfResponse("server", doc);
                                totalServer = dataCount + index;
                                servers = new Server[index];
                                servers = parser.parseServerList(doc, index);

                                for (int i = 0; i < index; i++) {
                                    dataSet.add(i + dataCount, new ListServerItem(servers[i].displayname, servers[i].os, servers[i].zonename, servers[i].state.equals("Running")));
                                    serverData.add(i + dataCount, servers[i]);
                                }
                                serverTempData = serverData;
                                adapter.addItemArray(dataSet);
                                adapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.INVISIBLE);
                                zoneSpinner.setSelection(0);

                                setTabHostEnabled(true);
                            } else {
                                //ajax error, show error code
                                AppUtility.showMsg(getApplicationContext(), "Error : " + status.getError());
                            }
                        }
                    });

                } else {
                    //ajax error, show error code
                    AppUtility.showMsg(getApplicationContext(), "Error : " + status.getError());
                }
            }
        });
    }

    public void getDiskData(String apiKey, String secretKey, final ListDiskAdapter adapter2, final AQuery aq, final ApiParser parser) {

        diskData = new ArrayList<>();

        adapter2.removeAll();
        adapter2.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);

        final String diskStack1 = ApiGenerator.apiGenerator(apiKey, secretKey, "listVolumes", false, "all");
        final String diskStack2 = ApiGenerator.apiGenerator(apiKey, secretKey, "listVolumes", true, "all");

        // cloudstack2
        aq.ajax(diskStack2, String.class, new AjaxCallback<String>() {
            Disk[] disks = null;
            ArrayList<ListDiskItem> dataSet = new ArrayList<>();
            int dataCount = 0;

            @Override
            public void callback(String url, String json, AjaxStatus status) {
                if (json != null) {
                    //successful ajax call, show status code and json content
                    Document doc = parser.getDocument(json);
                    int index = parser.getNumberOfResponse("disk", doc);
                    disks = new Disk[index];
                    disks = parser.parseDiskList(doc, index);

                    for (int i = 0; i < index; i++) {
                        dataSet.add(i, new ListDiskItem(disks[i].displayname, disks[i].size, disks[i].zonename, disks[i].state != null ? true : false, disks[i].vmname));
                        diskData.add(i, disks[i]);
                        dataCount++;
                    }

                    aq.ajax(diskStack1, String.class, new AjaxCallback<String>() {

                        @Override
                        public void callback(String url, String json, AjaxStatus status) {
                            if (json != null) {
                                //successful ajax call, show status code and json content
                                Document doc = parser.getDocument(json);
                                int index = parser.getNumberOfResponse("disk", doc);
                                totalDisk = dataCount + index;
                                disks = new Disk[index];
                                disks = parser.parseDiskList(doc, index);

                                for (int i = 0; i < index; i++) {
                                    dataSet.add(i, new ListDiskItem(disks[i].displayname, disks[i].size, disks[i].zonename, disks[i].state != null ? true : false, disks[i].vmname));
                                    diskData.add(i, disks[i]);
                                }
                                diskTempData = diskData;
                                adapter2.addItemArray(dataSet);
                                adapter2.notifyDataSetChanged();

                                progressBar.setVisibility(View.INVISIBLE);
                                zoneSpinner.setSelection(0);

                                setTabHostEnabled(true);
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
    }

    public void getNetworkData(String apiKey, String secretKey, final ListNetworkAdapter adapter3, final ListNetworkAdapter2 adapter4, final AQuery aq, final ApiParser parser) {

        networkData = new ArrayList<>();
        networkData2 = new ArrayList<>();

        adapter3.removeAll();
        adapter4.removeAll();
        adapter3.notifyDataSetChanged();
        adapter4.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);

        final String nwStack1 = ApiGenerator.apiGenerator(apiKey, secretKey, "listPublicIpAddresses", false, "all");
        final String nwStack2 = ApiGenerator.apiGenerator(apiKey, secretKey, "listPublicIpAddresses", true, "all");

        final String n_nwStack1 = ApiGenerator.apiGenerator(apiKey, secretKey, "listNetworks", false, "all");
        final String n_nwStack2 = ApiGenerator.apiGenerator(apiKey, secretKey, "listNetworks", true, "all");

        // cloudstack2
        aq.ajax(nwStack2, String.class, new AjaxCallback<String>() {
            Network[] networks = null;
            ArrayList<ListNetworkItem> dataSet = new ArrayList<>();
            int dataCount = 0;

            @Override
            public void callback(String url, String json, AjaxStatus status) {
                if (json != null) {
                    //successful ajax call, show status code and json content
                    Document doc = parser.getDocument(json);
                    int index = parser.getNumberOfResponse("network", doc);
                    networks = new Network[index];
                    networks = parser.parseNetworkList(doc, index, false);

                    for (int i = 0; i < index; i++) {
                        dataSet.add(i, new ListNetworkItem(networks[i].ipaddress, networks[i].addressid, networks[i].zonename, networks[i].usageplan.equals("") ? false : true));
                        networkData.add(i, networks[i]);
                        dataCount++;
                    }

                    aq.ajax(nwStack1, String.class, new AjaxCallback<String>() {

                        @Override
                        public void callback(String url, String json, AjaxStatus status) {
                            if (json != null) {
                                //successful ajax call, show status code and json content
                                Document doc = parser.getDocument(json);
                                int index = parser.getNumberOfResponse("network", doc);
                                totalNetwork = dataCount + index;
                                networks = new Network[index];
                                networks = parser.parseNetworkList(doc, index, false);

                                for (int i = 0; i < index; i++) {
                                    dataSet.add(i, new ListNetworkItem(networks[i].ipaddress, networks[i].addressid, networks[i].zonename, networks[i].usageplan != null ? true : false));
                                    networkData.add(i, networks[i]);
                                    dataCount++;
                                }
                                networkTempData = networkData;
                                adapter3.addItemArray(dataSet);
                                adapter3.notifyDataSetChanged();
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

        aq.ajax(n_nwStack2, String.class, new AjaxCallback<String>() {
            Network[] networks = null;
            ArrayList<ListNetworkItem> dataSet = new ArrayList<>();
            int dataCount = 0;

            @Override
            public void callback(String url, String json, AjaxStatus status) {
                if (json != null) {
                    //successful ajax call, show status code and json content
                    Document doc = parser.getDocument(json);
                    int index = parser.getNumberOfResponse("n_network", doc);
                    networks = new Network[index];
                    networks = parser.parseNetworkList(doc, index, true);

                    for (int i = 0; i < index; i++) {
                        Log.d("11", networks[i].n_cidr);
                        dataSet.add(i, new ListNetworkItem(networks[i].n_displayname, networks[i].n_zonename, networks[i].n_type, networks[i].n_cidr));
                        networkData2.add(i, networks[i]);
                        dataCount++;
                    }

                    aq.ajax(n_nwStack1, String.class, new AjaxCallback<String>() {

                        @Override
                        public void callback(String url, String json, AjaxStatus status) {
                            if (json != null) {
                                //successful ajax call, show status code and json content
                                Document doc = parser.getDocument(json);
                                int index = parser.getNumberOfResponse("n_network", doc);
                                totalNetwork2 = dataCount + index;
                                networks = new Network[index];
                                networks = parser.parseNetworkList(doc, index, true);

                                for (int i = 0; i < index; i++) {
                                    dataSet.add(i, new ListNetworkItem(networks[i].n_displayname, networks[i].n_zonename, networks[i].n_type, networks[i].n_cidr));
                                    networkData2.add(i, networks[i]);
                                }
                                networkTempData2 = networkData2;
                                adapter4.addItemArray(dataSet);
                                adapter4.notifyDataSetChanged();

                                progressBar.setVisibility(View.INVISIBLE);
                                zoneSpinner.setSelection(0);

                                setTabHostEnabled(true);
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
    }

    public void setTabHostEnabled(boolean enabled) {
        for (int i = 0; i < tabHost.getChildCount(); i++) {
            tabHost.getTabWidget().getChildTabViewAt(i).setEnabled(enabled);
        }
    }
}

