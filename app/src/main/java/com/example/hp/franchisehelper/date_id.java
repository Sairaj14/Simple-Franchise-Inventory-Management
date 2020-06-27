package com.example.hp.franchisehelper;

import android.os.Parcel;
import android.os.Parcelable;

public class date_id implements Parcelable {

    String id,date;
    date_id()
    {

    }

    public date_id(String id, String date) {
        this.id = id;
        this.date = date;
    }

    protected date_id(Parcel in) {
        id = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<date_id> CREATOR = new Creator<date_id>() {
        @Override
        public date_id createFromParcel(Parcel in) {
            return new date_id(in);
        }

        @Override
        public date_id[] newArray(int size) {
            return new date_id[size];
        }
    };
}
