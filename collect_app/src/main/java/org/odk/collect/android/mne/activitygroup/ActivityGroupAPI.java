package org.odk.collect.android.mne.activitygroup;

import org.odk.collect.android.mne.activitygroup.model.Activity;
import org.odk.collect.android.mne.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ActivityGroupAPI {


    @GET(Constant.URLs.GET_ACT_GROUP)
    Observable<List<Activity>> getActivityGroup(@Path("id") String actGroupId);
}
