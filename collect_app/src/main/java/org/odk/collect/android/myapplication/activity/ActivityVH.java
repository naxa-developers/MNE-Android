package org.odk.collect.android.myapplication.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.common.DateUtils;

public class ActivityVH extends RecyclerView.ViewHolder {

    private TextView tvTitle, tvDesc, tvOutput, tvIconText;
    private TextView tvTargetNumber, tvDate, tvEndDate, tvTargetUnit;

    ActivityVH(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.primary_text);
        tvDesc = itemView.findViewById(R.id.sub_text);
        tvTargetNumber = itemView.findViewById(R.id.target);
        tvDate = itemView.findViewById(R.id.tv_date);

        tvTargetUnit = itemView.findViewById(R.id.target_unit);
    }

    public void bindView(Activity desc) {
        tvTitle.setText(desc.getName());
        tvDesc.setText(desc.getDescription());


        String formatedDate = "From" + " " + DateUtils.formatDate(desc.getStartDate()) + " " + "to" + " " + DateUtils.formatDate(desc.getEndDate());
        tvDate.setText(formatedDate);
        String formattedUnit = desc.getTargetNumber() + " " + desc.getTargetUnit();


        tvTargetNumber.setText(formattedUnit);

        itemView.setOnClickListener(v -> {
            viewItemClicked(desc);
        });

        if (desc.getBeneficiaryLevel()) {
            tvTargetNumber.setVisibility(View.GONE);
        } else {
            String formattedUnit = desc.getTargetNumber() + " " + desc.getTargetUnit();
            tvTargetNumber.setText(formattedUnit);
            tvTargetNumber.setVisibility(View.VISIBLE);
        }
    }


    void viewItemClicked(Activity activity) {

    }


}