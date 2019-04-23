package org.odk.collect.android.mne.database.base;

import java.util.List;

import io.reactivex.Completable;

public interface BaseLocalDataSourceRX<T> {

    Completable saveCompletable(T... items);

    Completable saveCompletable(List<T> items);


}
