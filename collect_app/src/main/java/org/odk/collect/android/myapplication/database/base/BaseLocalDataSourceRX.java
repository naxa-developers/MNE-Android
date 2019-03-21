package org.odk.collect.android.myapplication.database.base;

import java.util.ArrayList;


import io.reactivex.Completable;

public interface BaseLocalDataSourceRX<T> {

    Completable save(T... items);

    Completable save(ArrayList<T> items);


}
