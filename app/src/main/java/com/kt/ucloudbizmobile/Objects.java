package com.kt.ucloudbizmobile;

/**
 * Created by aicel on 2017-08-10.
 */

public class Objects {
    String displayname;

}

class Server extends Objects {
    String state;
    String zoneid;
    String os;
    String spec;
    String externalip;
    String internalip;
    String starttime;
}

class Disk {

}

class Network {

}

class Alarm {

}

class Metric {

}

class metricStatistic {
    String timestamp;
    String unit;
    double Maximum = -1;
    double Minimum = -1;
    double Sum = -1;
    int SampleCount = -1;
    double Average = -1;

}