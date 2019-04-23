package org.odk.collect.android.mne.forms;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "practical_action_forms")
public class PraticalActionForm {
    private String beneficiaryId;
    private String activityId;
    private String instanceId;

    public PraticalActionForm(String beneficiaryId, String activityId, String instanceId) {
        this.beneficiaryId = beneficiaryId;
        this.activityId = activityId;
        this.instanceId = instanceId;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getInstanceId() {
        return instanceId;
    }
}
