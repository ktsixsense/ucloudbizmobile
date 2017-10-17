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

    String name;
    String zonename;
    String state;
    String serverid;
    String ipaddress;
    String os;
    String created;
    String cpunumber;
    String memory;

    public Server() {

    }

    private Server(Parcel in) {
        this.displayname = in.readString();
        this.name = in.readString();
        this.zonename = in.readString();
        this.state = in.readString();
        this.serverid = in.readString();
        this.ipaddress = in.readString();
        this.os = in.readString();
        this.created = in.readString();
        this.cpunumber = in.readString();
        this.memory = in.readString();
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
        dest.writeString(displayname);
        dest.writeString(name);
        dest.writeString(zonename);
        dest.writeString(state);
        dest.writeString(serverid);
        dest.writeString(ipaddress);
        dest.writeString(os);
        dest.writeString(created);
        dest.writeString(cpunumber);
        dest.writeString(memory);
    }
}

class Disk extends Objects implements Parcelable {
    String volumeid;

    String state; // vmstate라는 값이 있으면 연결 중이므로 true, vmstate값이 없으면 분리상태이므로 false 되도록
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

class Network extends Objects implements Parcelable {
    String addressid; // Network uuid
    String ipaddress;
    // displayname
    String zoneid;
    String zonename;
    String type; // Isolated or shared
    String usageplan;

    public Network() {

    }

    private Network(Parcel in) {
        this.addressid = in.readString();
        this.ipaddress = in.readString();
        // this.state = in.readString();
        this.zoneid = in.readString();
        this.zonename = in.readString();
        // this.vmid = in.readString();
        // this.vmname = in.readString();
        this.type = in.readString();
        // this.size = in.readString();
        this.usageplan = in.readString();
    }

    public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>() {
        public Network createFromParcel(Parcel in) {
            return new Network(in);
        }

        public Network[] newArray(int size) {
            return new Network[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressid);
        dest.writeString(ipaddress);
        dest.writeString(zoneid);
        dest.writeString(zonename);
        dest.writeString(type);
        dest.writeString(usageplan);
    }
}

class Alarm {

}

class Metric extends Objects implements Parcelable {
    public String metricname;
    public String namespace;
    public String unit;
    public String dimensions;

    public Metric() {

    }

    public Metric(Parcel in) {
        this.metricname = in.readString();
        this.namespace = in.readString();
        this.unit = in.readString();
        this.dimensions = in.readString();
    }

    public static final Parcelable.Creator<Metric> CREATOR = new Parcelable.Creator<Metric>() {
        public Metric createFromParcel(Parcel in) {
            return new Metric(in);
        }

        public Metric[] newArray(int size) {
            return new Metric[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(metricname);
        dest.writeString(namespace);
        dest.writeString(unit);
        dest.writeString(dimensions);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}


class metricStat extends Objects implements Parcelable {
    String timestamp;
    String unit;
    double Maximum = -1;
    double Minimum = -1;
    double Sum = -1;
    // int SampleCount = -1;
    double Average = -1;

    public metricStat() {

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