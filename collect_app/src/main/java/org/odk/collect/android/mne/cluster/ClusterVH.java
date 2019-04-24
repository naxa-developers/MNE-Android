package org.odk.collect.android.mne.cluster;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.collect.android.R;
import org.odk.collect.android.mne.activitygroup.ActivityGroupListActivity;
import org.odk.collect.android.mne.utils.ActivityUtil;

import java.util.HashMap;

class ClusterVH extends RecyclerView.ViewHolder {
    private final RelativeLayout rootLayout;
    private TextView tvTitle, tvDesc, tvIconText;
    private ImageView cardIcon;


    ClusterVH(View itemView) {
        super(itemView);
        rootLayout = itemView.findViewById(R.id.card_view_list_item_title_desc);
        tvTitle = itemView.findViewById(R.id.tv_list_item_title);
        tvDesc = itemView.findViewById(R.id.tv_list_item_desc);
        cardIcon = itemView.findViewById(R.id.card_icon);
    }

    void bindView(Cluster desc) {
        tvTitle.setText(desc.getName());
        tvDesc.setText(desc.getDistrict());
        itemView.setOnClickListener(v -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("cluster_id", String.valueOf(desc.getId()));
            ActivityUtil.openActivity(ActivityGroupListActivity.class, itemView.getContext(), map, false);
        });
    }
}
