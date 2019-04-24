
package org.odk.collect.android.mne.cluster;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.odk.collect.android.mne.activitygroup.model.ActivityGroup;

import java.util.List;


@Entity(tableName = "clusters")
public class Cluster {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("district")
    @Expose
    private String district;

    @SerializedName("clusterag")
    @Expose
    @Ignore
    private List<ActivityGroup> clusterag = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    public List<ActivityGroup> getClusterag() {
        return clusterag;
    }

    public void setClusterag(List<ActivityGroup> clusterag) {
        this.clusterag = clusterag;
    }

}
