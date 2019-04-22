package org.odk.collect.android.myapplication.beneficary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeneficaryStats {

    private Integer count;

    private Integer id;

    private String name;

    private String address;

    private String wardNo;

    private String cluster;

    private String type;

    private String governmentTranch;

    private String constructionPhase;

    private String typesofhouse;

    private String remarks;

    private Integer progress;



    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

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

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
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