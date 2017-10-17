package com.kt.ucloudbizmobile;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aicel on 2017-08-10.
 */

public class Objects {
    String displayname;

}

class Server extends Objects implements Parcelable {
    String state;
    String zoneid;
    String zonename;
    String os;
    String spec;
    String externalip;
    String internalip;
    String starttime;

    public Server() {

    }

    private Server(Parcel in) {
        this.state = in.readString();
        this.zoneid = in.readString();
        this.zonename = in.readString();
        this.os = in.readString();
        this.spec = in.readString();
        this.externalip = in.readString();
        this.internalip = in.readString();
        this.starttime = in.readString();
    }

    public static final Parcelable.Creator<Server> CREATOR = new Parcelable.Creator<Server>() {
        public Server createFromParcel(Parcel in) {
            return new Server(in);
        }

        public Server[] newArray(int size) {
            return new Server[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(state);
        dest.writeString(zoneid);
        dest.writeString(zonename);
        dest.writeString(os);
        dest.writeString(spec);
        dest.writeString(externalip);
        dest.writeString(internalip);
        dest.writeString(starttime);
    }

}

class Disk extends Objects implements Parcelable {
    String volumeid;

    String state; // Ready(연결이 안되어 있음 vm에), Running
    String zoneid;
    String zonename;
    String vmid;
    String vmname; // attach되어 있는 vm 관련 정보
    String type; // Root disk인지 Data disk인지

    String size; // 2^30인 1073741824로 나누어서 GB로 바꿔야함
    String usageplan; // hourly, monthly - API 응답값이 없으면 hourly

    public Disk() {

    }

    private Disk(Parcel in) {
        this.volumeid = in.readString();
        this.state = in.readString();
        this.zoneid = in.readString();
        this.zonename = in.readString();
        this.vmid = in.readString();
        this.vmname = in.readString();
        this.type = in.readString();
        this.size = in.readString();
        this.usageplan = in.readString();
    }

    public static final Parcelable.Creator<Disk> CREATOR = new Parcelable.Creator<Disk>() {
        public Disk createFromParcel(Parcel in) {
            return new Disk(in);
        }

        public Disk[] newArray(int size) {
            return new Disk[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(volumeid);
        dest.writeString(state);
        dest.writeString(zoneid);
        dest.writeString(zonename);
        dest.writeString(vmid);
        dest.writeString(vmname);
        dest.writeString(type);
        dest.writeString(size);
        dest.writeString(usageplan);
    }
}

class Network {

}

class Alarm {

}

class Metric {

}

class metricStat extends Objects implements Parcelable {
    String timestamp;
    String unit;
    double Maximum = -1;
    double Minimum = -1;
    double Sum = -1;
   // int SampleCount = -1;
    double Average = -1;

    public metricStat()
    {

    }
public metricStat(Parcel in) {
        this.timestamp = in.readString();
        this.unit = in.readString();
        this.Maximum = in.readDouble();
        this.Minimum = in.readDouble();
        this.Sum = in.readDouble();
        this.Average = in.readDouble();
     }

    public static final Parcelable.Creator<metricStat> CREATOR = new Parcelable.Creator<metricStat>() {
        public metricStat createFromParcel(Parcel in) {
            return new metricStat(in);
        }

        public metricStat[] newArray(int size) {
            return new metricStat[size];
        }
    };
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timestamp);
        dest.writeString(unit);
        dest.writeDouble(Maximum);
        dest.writeDouble(Minimum);
        dest.writeDouble(Sum);
        dest.writeDouble(Average);

    }
    @Override
    public int describeContents() {
        return 0;
    }
}

class metricStatistic {
    String timestamp;
    String unit;
    double Maximum = -1;
    double Minimum = -1;
    double Sum = -1;
    int SampleCount = -1;
    double Average = -1;

    public int describeContents() {
        return 0;
    }

}