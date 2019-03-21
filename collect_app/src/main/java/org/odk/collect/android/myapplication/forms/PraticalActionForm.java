package org.odk.collect.android.myapplication.forms;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "practical_action_forms")
public class PraticalActionForm {
    private String beneficiaryId;
    private String activityId;
    private String idString;

    public PraticalActionForm(String beneficiaryId, String activityId, String idString) {
        this.beneficiaryId = beneficiaryId;
        this.activityId = activityId;
        this.idString = idString;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getIdString() {
        return idString;
    }

}
