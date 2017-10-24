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

    public static final Parcelable.Creator<Server> CREATOR = new Parcelable.Creator<Server>() {
        public Server createFromParcel(Parcel in) {
            return new Server(in);
        }

        public Server[] newArray(int size) {
            return new Server[size];
        }
    };
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
    public static final Parcelable.Creator<Disk> CREATOR = new Parcelable.Creator<Disk>() {
        public Disk createFromParcel(Parcel in) {
            return new Disk(in);
        }

        public Disk[] newArray(int size) {
            return new Disk[size];
        }
    };
    String volumeid;
    String state; // vmstate라는 값이 있으면 연결 중이므로 true, vmstate값이 없으면 분리상태이므로 false 되도록
    String zoneid;
    String zonename;
    String vmid;
    String vmname; // attach되어 있는 vm 관련 정보
    String type; // Root disk인지 Data disk인지
    String size; // 2^30인 1073741824로 나누어서 GB로 바꿔야함
    String usageplan; // hourly, monthly - API 응답값이 없으면 hourly
    String created; // 생성일시

    public Disk() {

    }

    private Disk(Parcel in) {
        this.displayname = in.readString();
        this.volumeid = in.readString();
        this.state = in.readString();
        this.zoneid = in.readString();
        this.zonename = in.readString();
        this.vmid = in.readString();
        this.vmname = in.readString();
        this.type = in.readString();
        this.size = in.readString();
        this.usageplan = in.readString();
        this.created = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayname);
        dest.writeString(volumeid);
        dest.writeString(state);
        dest.writeString(zoneid);
        dest.writeString(zonename);
        dest.writeString(vmid);
        dest.writeString(vmname);
        dest.writeString(type);
        dest.writeString(size);
        dest.writeString(usageplan);
        dest.writeString(created);

    }
}

class Network extends Objects implements Parcelable {
    // Public IP Address 변수

    public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>() {
        public Network createFromParcel(Parcel in) {
            return new Network(in);
        }

        public Network[] newArray(int size) {
            return new Network[size];
        }
    };
    String addressid;
    String ipaddress;
    String zoneid;
    String zonename;
    String usageplan;
    String state;
    // Network 변수
    String n_displayname;
    String n_networkid;
    String n_cidr;
    String n_subnet;
    String n_gateway;
    String n_zonename;
    String n_state;
    String n_type; // Isolated or shared

    public Network() {

    }

    private Network(Parcel in) {
        this.addressid = in.readString();
        this.ipaddress = in.readString();
        this.zoneid = in.readString();
        this.zonename = in.readString();
        this.usageplan = in.readString();
        this.state = in.readString();

        this.n_displayname = in.readString();
        this.n_networkid = in.readString();
        this.n_cidr = in.readString();
        this.n_subnet = in.readString();
        this.n_gateway = in.readString();
        this.n_zonename = in.readString();
        this.n_state = in.readString();
        this.n_type = in.readString();
    }

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
        dest.writeString(usageplan);
        dest.writeString(state);

        dest.writeString(n_displayname);
        dest.writeString(n_networkid);
        dest.writeString(n_cidr);
        dest.writeString(n_subnet);
        dest.writeString(n_gateway);
        dest.writeString(n_zonename);
        dest.writeString(n_state);
        dest.writeString(n_type);

        dest.writeString(n_type);

    }
}

class Alarm extends Objects implements Parcelable {
    public static final Parcelable.Creator<Alarm> CREATOR = new Parcelable.Creator<Alarm>() {
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
    public String alarmname;
    public String statevalue; // OK - 안정, INSUFFICIENT_DATA - 데이터 부족, ALARM - 알람
    public String metricname; // CPUUtilization 등등
    public String compOperator; // GreaterThanOrEqualToThreshold(이상), GreaterThanThreshold(초과), LessThanThreshold(미만), LessThanOrEqualToThreshold(이하)
    // 출력할 주기는 period * evalPeriod
    public String period;
    public String evalPeriod;
    public String threshold;
    public String statistics; // SampleCount, Average, Sum, Minimum, Maximum
    public String unit; // Percent, Bytes 등등
    public String alarmSenabled;

    public Alarm() {

    }

    public Alarm(Parcel in) {
        this.alarmname = in.readString();
        this.statevalue = in.readString();
        this.metricname = in.readString();
        this.compOperator = in.readString();
        this.period = in.readString();
        this.evalPeriod = in.readString();
        this.threshold = in.readString();
        this.statistics = in.readString();
        this.unit = in.readString();
        this.alarmSenabled = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(metricname);
        dest.writeString(statevalue);
        dest.writeString(metricname);
        dest.writeString(compOperator);
        dest.writeString(period);
        dest.writeString(evalPeriod);
        dest.writeString(threshold);
        dest.writeString(statistics);
        dest.writeString(unit);
        dest.writeString(alarmSenabled);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

class Metric extends Objects implements Parcelable {
    public static final Parcelable.Creator<Metric> CREATOR = new Parcelable.Creator<Metric>() {
        public Metric createFromParcel(Parcel in) {
            return new Metric(in);
        }

        public Metric[] newArray(int size) {
            return new Metric[size];
        }
    };
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
    public static final Parcelable.Creator<metricStat> CREATOR = new Parcelable.Creator<metricStat>() {
        public metricStat createFromParcel(Parcel in) {
            return new metricStat(in);
        }

        public metricStat[] newArray(int size) {
            return new metricStat[size];
        }
    };
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