package org.odk.collect.android.myapplication.database.dao;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;

import java.util.List;

public class ActivityGroupAndActivity {

    @Embedded
    private ActivityGroup group;

    @Relation(parentColumn = "id", entityColumn = "activity_group_id")
    private List<Activity> activities;

    public ActivityGroup getGroup() {
        return group;
    }

    public void setGroup(ActivityGroup group) {
        this.group = group;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
