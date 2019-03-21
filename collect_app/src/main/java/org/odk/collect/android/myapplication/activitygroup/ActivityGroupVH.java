package org.odk.collect.android.myapplication.activitygroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.activity.ActivityListActivity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.beneficary.BeneficiariesActivity;
import org.odk.collect.android.myapplication.utils.ActivityUtil;

import java.util.HashMap;

class ActivityGroupVH extends RecyclerView.ViewHolder {
    private final RelativeLayout rootLayout;
    private TextView tvTitle, tvDesc, tvIconText;

    ActivityGroupVH(View itemView) {
        super(itemView);
        rootLayout = itemView.findViewById(R.id.card_view_list_item_title_desc);
        tvTitle = itemView.findViewById(R.id.tv_list_item_title);
        tvDesc = itemView.findViewById(R.id.tv_list_item_desc);
        tvIconText = itemView.findViewById(R.id.title_desc_tv_icon_text);
    }

    void bindView(ActivityGroup desc) {
        tvTitle.setText(desc.getName());
        itemView.setOnClickListener(addClickListener(desc));
    }

    private View.OnClickListener addClickListener(ActivityGroup activityGroup) {
        return v -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("activity_group_id", activityGroup.getId());
            ActivityUtil.openActivity(ActivityListActivity.class, itemView.getContext(), hashMap, false);
        };
    }
}