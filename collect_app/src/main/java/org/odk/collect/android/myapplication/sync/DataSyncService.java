package org.odk.collect.android.myapplication.sync;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.activitygroup.ActivityGroupRemoteSource;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.api.RetrofitException;
import org.odk.collect.android.myapplication.beneficary.BeneficaryResponse;
import org.odk.collect.android.myapplication.beneficary.BeneficiariesActivity;
import org.odk.collect.android.myapplication.beneficary.BeneficiaryRemoteSource;
import org.odk.collect.android.myapplication.cluster.ClusterRemoteSource;
import org.odk.collect.android.myapplication.common.Constant;
import org.odk.collect.android.utilities.ToastUtils;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static org.odk.collect.android.utilities.NotificationUtils.CHANNEL_ID;
import static org.odk.collect.android.utilities.NotificationUtils.showNotification;

public class DataSyncService extends Service {
    boolean isAlreadyUptoDate = false;
    private DisposableObserver<String> dis;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String sentAction = intent.getAction();
        if (TextUtils.equals(sentAction, Constant.SERVICE.STARTFOREGROUND_SYNC)) {
            startIfNetworkAvailable();
        } else if (TextUtils.equals(sentAction, Constant.SERVICE.STOPFOREGROUND_SYNC)) {
            disposeTasks();
            stopSafely();
        }
        return START_STICKY;
    }

    private void startIfNetworkAvailable() {
        String notificationContentText = getString(R.string.downloading_data);
        startForeground(Constant.NOTIFICATION_ID.FOREGROUND_DATA_SYNC_SERVICE, buildNotification(notificationContentText));
        if (!InternetUtil.checkConnectedToNetwork()) {
            stopSafely();
            ToastUtils.showLongToast(R.string.no_connection);
        } else {
            if (isAlreadyUptoDate) {
                showNotification(null, Constant.NOTIFICATION_ID.DATA_UPTO_DATE, R.string.noti_title_already_upto_date, getString(R.string.noti_desc_already_upto_date));
                stopSafely();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("OnCreate()");
        if (InternetUtil.checkConnectedToNetwork()) {

            Observable<List<Activity>> actObservable = ClusterRemoteSource.getInstance()
                    .getAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .flatMapIterable((Function<List<ActivityGroup>, Iterable<ActivityGroup>>) activityGroups -> activityGroups)
                    .flatMap((Function<ActivityGroup, ObservableSource<List<Activity>>>) activityGroup -> {
                        String actGroupId = activityGroup.getId();
                        return ActivityGroupRemoteSource.getInstance().getActivityGroup(actGroupId);
                    });

            Observable<List<BeneficaryResponse>> beneficiaryObservable = BeneficiaryRemoteSource.getInstance().getAll();

            dis = Observable.zip(actObservable, beneficiaryObservable, (activityList, o) -> String.format(Locale.getDefault(), " %d and %d", activityList.size(), o.size()))
                    .subscribeWith(new DisposableObserver<String>() {
                        @Override
                        public void onNext(String message) {
                        }

                        @Override
                        public void onError(Throwable e) {
                            String message = e.getMessage();
                            if (e instanceof RetrofitException) {
                                message = ((RetrofitException) e).getKind().getMessage();
                            }
                            showNotification(null, Constant.NOTIFICATION_ID.DATA_SYNC_ERROR, R.string.error_occured, message);
                            stopSafely();
                            Timber.e(e);
                        }

                        @Override
                        public void onComplete() {
                            showNotification(null, Constant.NOTIFICATION_ID.DATA_UPTO_DATE, R.string.msg_download_sucess, "Synced successfully just now");
                            cancelNotification(DataSyncService.this, Constant.NOTIFICATION_ID.FOREGROUND_DATA_SYNC_SERVICE);
                            stopSafely();
                        }
                    });
        }
    }

    private void updateNotificationText(String text) {

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(Constant.NOTIFICATION_ID.FOREGROUND_DATA_SYNC_SERVICE, buildNotification(text));
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancel(notifyId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.i("OnDestroy()");
        disposeTasks();
    }

    private void disposeTasks() {
        if (dis != null) {
            dis.dispose();
        }
    }

    private void stopSafely() {

        stopForeground(true);
        stopSelf();
    }

    private Notification buildNotification(String contentText) {


        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.msg_download_new_data))
                .setTicker(getString(R.string.msg_download_new_data))
                .setContentText(contentText)
                .setSmallIcon(android.R.drawable.ic_popup_sync)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_play, getString(R.string.action_stop_download),
                        buildStopDownloadIntent()).build();

    }

    private PendingIntent buildStopDownloadIntent() {
        Intent nextIntent = new Intent(this, DataSyncService.class);
        nextIntent.setAction(Constant.SERVICE.STOPFOREGROUND_SYNC);
        return PendingIntent.getService(this, 0,
                nextIntent, 0);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
