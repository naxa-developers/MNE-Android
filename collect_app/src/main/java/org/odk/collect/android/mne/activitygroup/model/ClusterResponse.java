
package org.odk.collect.android.mne.activitygroup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClusterResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("project")
    @Expose
    private Integer project;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("municipality")
    @Expose
    private String municipality;
    @SerializedName("ward")
    @Expose
    private String ward;
    @SerializedName("activitygroup")
    @Expose
    private List<ActivityGroup> activitygroup = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
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

    public List<ActivityGroup> getActivitygroup() {
        return activitygroup;
    }

    public void setActivitygroup(List<ActivityGroup> activitygroup) {
        this.activitygroup = activitygroup;
    }

}
