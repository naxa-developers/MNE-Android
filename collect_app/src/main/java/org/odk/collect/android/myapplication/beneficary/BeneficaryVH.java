package org.odk.collect.android.myapplication.beneficary;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.collect.android.R;

import java.util.HashMap;

import timber.log.Timber;

public class BeneficaryVH extends RecyclerView.ViewHolder {
    final RelativeLayout rootLayout;
    TextView tvTitle, tvDesc, tvIconText, tvTotalFilled;
    HashMap<String, String> metadata = null;


    public BeneficaryVH(View itemView) {
        super(itemView);
        rootLayout = itemView.findViewById(R.id.card_view_list_item_title_desc);
        tvTitle = itemView.findViewById(R.id.tv_list_item_title);
        tvDesc = itemView.findViewById(R.id.tv_list_item_desc);
        tvTotalFilled = itemView.findViewById(R.id.tv_total_filled);
        tvIconText = itemView.findViewById(R.id.title_desc_tv_icon_text);


    }

    public void bindView(BeneficaryStats beneficaryStats, String formId) {
        try {
            tvTitle.setText(beneficaryStats.getName());
            tvDesc.setText(beneficaryStats.getAddress());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewItemClicked(beneficaryStats);
                }
            });
            tvIconText.setText(beneficaryStats.getName().substring(0, 1));
            tvTotalFilled.setText(getFormStatus(beneficaryStats.getCluster()));
            tvTotalFilled.setText(getFormStatus(beneficaryStats.getCount()));

        } catch (NullPointerException e) {
            Timber.e(e);
        }

    }

    private String getFormStatus(Integer totalFilled) {
        switch (totalFilled) {
            case 0:
                return "Pending";
            case 1:
                return "Filled once";
            default:
                return "Filled " + totalFilled + " times";
        }
    }

    public void viewItemClicked(BeneficaryStats beneficaryResponse) {

    }

    public void setActivityAndBeneficiaryIds(HashMap<String, String> map) {
        this.metadata = map;
    }


}