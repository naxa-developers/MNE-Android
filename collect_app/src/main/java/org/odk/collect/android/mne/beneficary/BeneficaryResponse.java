package org.odk.collect.android.mne.beneficary;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "beneficiaries",
        primaryKeys = {"id"})
public class BeneficaryResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("ward_no")
    @Expose
    private Integer wardNo;
    @SerializedName("cluster")
    @Expose
    private Integer cluster;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("GovernmentTranch")
    @Expose
    private String governmentTranch;
    @SerializedName("ConstructionPhase")
    @Expose
    private String constructionPhase;
    @SerializedName("Typesofhouse")
    @Expose
    private String typesofhouse;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("progress")
    @Expose
    private Integer progress;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getWardNo() {
        return wardNo;
    }

    public void setWardNo(Integer wardNo) {
        this.wardNo = wardNo;
    }

    public Integer getCluster() {
        return cluster;
    }

    public void setCluster(Integer cluster) {
        this.cluster = cluster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGovernmentTranch() {
        return governmentTranch;
    }

    public void setGovernmentTranch(String governmentTranch) {
        this.governmentTranch = governmentTranch;
    }

    public String getConstructionPhase() {
        return constructionPhase;
    }

    public void setConstructionPhase(String constructionPhase) {
        this.constructionPhase = constructionPhase;
    }

    public String getTypesofhouse() {
        return typesofhouse;
    }

    public void setTypesofhouse(String typesofhouse) {
        this.typesofhouse = typesofhouse;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

}