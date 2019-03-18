package org.odk.collect.android.myapplication.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created on 11/29/17
 * by nishon.tan@gmail.com
 */

public class TitleDesc implements Parcelable {

    private String name;
    private String desc;
    private String id;
    private String secondaryId;
    private String pictureUrl;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public TitleDesc(String name, String desc, String id, String secondaryId) {
        this.name = name;
        this.desc = desc;
        this.id = id;
        this.secondaryId = secondaryId;
    }

    public String getSecondaryId() {
        return secondaryId;
    }


    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<TitleDesc> getDummyList(int totalItems) {
        ArrayList<TitleDesc> viewModels = new ArrayList<>();

        for (int i = 0; i <= totalItems; i++) {
            viewModels.add(new TitleDesc("name " + i, "Description " + i, String.valueOf(i), String.valueOf(i)));
        }

        return viewModels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleDesc titleDesc = (TitleDesc) o;

        if (name != null ? !name.equals(titleDesc.name) : titleDesc.name != null) return false;
        if (desc != null ? !desc.equals(titleDesc.desc) : titleDesc.desc != null) return false;
        if (id != null ? !id.equals(titleDesc.id) : titleDesc.id != null) return false;
        if (secondaryId != null ? !secondaryId.equals(titleDesc.secondaryId) : titleDesc.secondaryId != null)
            return false;
        return pictureUrl != null ? pictureUrl.equals(titleDesc.pictureUrl) : titleDesc.pictureUrl == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (secondaryId != null ? secondaryId.hashCode() : 0);
        result = 31 * result + (pictureUrl != null ? pictureUrl.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.desc);
        dest.writeString(this.id);
        dest.writeString(this.secondaryId);
        dest.writeString(this.pictureUrl);
    }

    protected TitleDesc(Parcel in) {
        this.name = in.readString();
        this.desc = in.readString();
        this.id = in.readString();
        this.secondaryId = in.readString();
        this.pictureUrl = in.readString();
    }

    public static final Creator<TitleDesc> CREATOR = new Creator<TitleDesc>() {
        @Override
        public TitleDesc createFromParcel(Parcel source) {
            return new TitleDesc(source);
        }

        @Override
        public TitleDesc[] newArray(int size) {
            return new TitleDesc[size];
        }
    };
}
