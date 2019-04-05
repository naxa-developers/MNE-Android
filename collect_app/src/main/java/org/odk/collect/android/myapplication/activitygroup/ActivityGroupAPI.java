package org.odk.collect.android.myapplication.activitygroup;

import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ActivityGroupAPI {


    @GET(Constant.URLs.GET_ACT_GROUP)
    Observable<List<Activity>> getActivityGroup(@Path("id") String actGroupId);
}
