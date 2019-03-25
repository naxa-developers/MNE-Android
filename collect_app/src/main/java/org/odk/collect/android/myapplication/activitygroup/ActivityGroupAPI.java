package org.odk.collect.android.myapplication.activitygroup;

import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ActivityGroupAPI {


    @GET(Constant.URLs.GET_ACT_GROUP)
    Observable<List<ActivityGroup>> getActivityGroup();
}
