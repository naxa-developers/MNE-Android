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
    TextView tvTitle, tvDesc, tvIconText;
    HashMap<String, String> metadata = null;



    public BeneficaryVH(View itemView) {
        super(itemView);
        rootLayout = itemView.findViewById(R.id.card_view_list_item_title_desc);
        tvTitle = itemView.findViewById(R.id.tv_list_item_title);
        tvDesc = itemView.findViewById(R.id.tv_list_item_desc);
        tvIconText = itemView.findViewById(R.id.title_desc_tv_icon_text);


    }

    public void bindView(BeneficaryResponse beneficaryResponse, String formId) {
        try {
            tvTitle.setText(beneficaryResponse.getName());
            tvDesc.setText(beneficaryResponse.getAddress());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewItemClicked(beneficaryResponse);
                }
            });
            tvIconText.setText(beneficaryResponse.getName().substring(0, 1));
        } catch (NullPointerException e) {
            Timber.e(e);
        }

    }

    public void viewItemClicked(BeneficaryResponse beneficaryResponse) {

    }

    public void setActivityAndBeneficiaryIds(HashMap<String, String> map) {
        this.metadata = map;
    }



}