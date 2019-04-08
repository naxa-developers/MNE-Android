package org.odk.collect.android.myapplication.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.beneficary.BeneficaryResponse;
import org.odk.collect.android.myapplication.beneficary.BeneficiariesActivity;
import org.odk.collect.android.myapplication.utils.ActivityUtil;

import java.util.HashMap;

public class ActivityVH extends RecyclerView.ViewHolder {

    private TextView tvTitle, tvDesc, tvOutput, tvIconText;
    private TextView tvTargetNumber, tvStartDate, tvEndDate, tvTargetUnit;

    ActivityVH(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.primary_text);
        tvDesc = itemView.findViewById(R.id.sub_text);
        tvTargetNumber = itemView.findViewById(R.id.target);
        tvStartDate = itemView.findViewById(R.id.start_date);
        tvEndDate = itemView.findViewById(R.id.end_date);
        tvTargetUnit = itemView.findViewById(R.id.target_unit);


    }

    public void bindView(Activity desc) {
        tvTitle.setText(desc.getName());
        tvDesc.setText(desc.getDescription());
        tvStartDate.setText(desc.getStartDate());
        tvEndDate.setText(desc.getEndDate());
        String formattedUnit = desc.getTargetNumber() + " " + desc.getTargetUnit();
        tvTargetNumber.setText(formattedUnit);
        itemView.setOnClickListener(v -> {
           viewItemClicked(desc);
        });
    }

    void viewItemClicked(Activity activity) {

    }


}