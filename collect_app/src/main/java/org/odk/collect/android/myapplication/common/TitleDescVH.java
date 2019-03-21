package org.odk.collect.android.myapplication.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.beneficary.BeneficiariesActivity;
import org.odk.collect.android.myapplication.utils.ActivityUtil;

import java.util.HashMap;

public class TitleDescVH extends RecyclerView.ViewHolder {
    final RelativeLayout rootLayout;
    TextView tvTitle, tvDesc, tvIconText;

    public TitleDescVH(View itemView) {
        super(itemView);
        rootLayout = itemView.findViewById(R.id.card_view_list_item_title_desc);
        tvTitle = itemView.findViewById(R.id.tv_list_item_title);
        tvDesc = itemView.findViewById(R.id.tv_list_item_desc);
        tvIconText = itemView.findViewById(R.id.title_desc_tv_icon_text);
    }

    public void bindView(TitleDesc desc) {
        tvTitle.setText(desc.getName());
        itemView.setOnClickListener(addClickListener(desc));
    }

    View.OnClickListener addClickListener(TitleDesc desc) {
        return v -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("cluster_id", desc.getSecondaryId());
            ActivityUtil.openActivity(BeneficiariesActivity.class, itemView.getContext(), hashMap, false);
        };
    }
}