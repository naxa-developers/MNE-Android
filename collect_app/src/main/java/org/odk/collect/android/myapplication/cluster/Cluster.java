
package org.odk.collect.android.myapplication.cluster;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;

import java.util.List;

public class Cluster {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("municipality")
    @Expose
    private String municipality;
    @SerializedName("ward")
    @Expose
    private String ward;
    @SerializedName("clusterag")
    @Expose
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

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public List<ActivityGroup> getClusterag() {
        return clusterag;
    }

    public void setClusterag(List<ActivityGroup> clusterag) {
        this.clusterag = clusterag;
    }

}
