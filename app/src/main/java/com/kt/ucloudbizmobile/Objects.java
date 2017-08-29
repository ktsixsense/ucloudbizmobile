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

class Disk {

}

class Network {

}

class Alarm {

}

class Metric {

}