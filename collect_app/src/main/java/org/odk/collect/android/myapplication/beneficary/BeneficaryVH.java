package org.odk.collect.android.myapplication.beneficary;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.activity.ActivityListActivity;
import org.odk.collect.android.myapplication.beneficary.BeneficiariesActivity;
import org.odk.collect.android.myapplication.utils.ActivityUtil;

import java.util.HashMap;

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

    public void bindView(BeneficaryResponse desc,String formId) {
        tvTitle.setText(desc.getName());
        tvDesc.setText(desc.getAddress());
        itemView.setOnClickListener(addClickListener(desc,formId));
    }

    public void setActivityAndBeneficiaryIds(HashMap<String, String> map) {
        this.metadata = map;
    }

    View.OnClickListener addClickListener(BeneficaryResponse desc, String formId) {
        return v -> {
            String activityId = metadata.get(ActivityUtil.KEYS.ACTIVITY_ID);
            String beneficiaryId = String.valueOf(desc.getId());
            ActivityUtil.openFormEntryActivity(itemView.getContext(), formId, activityId, beneficiaryId);
        };
    }
}