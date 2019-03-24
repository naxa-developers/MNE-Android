package org.odk.collect.android.myapplication;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.CollectAbstractActivity;
import org.odk.collect.android.activities.FormEntryActivity;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.provider.FormsProviderAPI;
import org.odk.collect.android.utilities.ThemeUtils;

import timber.log.Timber;


public class BaseActivity extends CollectAbstractActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils = new ThemeUtils(this);
        setTheme(themeUtils.getPraticalActionTheme());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setupToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    /**
     * Only works from an activity, using getActivity() does not work
     *
     * @param activity
     */
    public void hideKeyboardInActivity(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showProgress() {
        try {
            findViewById(R.id.fl_toolbar_progress_wrapper).setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            //unused
            e.printStackTrace();
        }
    }


    protected void hideProgress() {
        try {
            findViewById(R.id.fl_toolbar_progress_wrapper).setVisibility(View.GONE);
        } catch (NullPointerException e) {
            //unused
            e.printStackTrace();
        }
    }




}
