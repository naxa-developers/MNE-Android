package org.odk.collect.android.myapplication.cluster;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.collect.android.R;

class ClusterVH extends RecyclerView.ViewHolder {
    private final RelativeLayout rootLayout;
    private TextView tvTitle, tvDesc, tvIconText;

    ClusterVH(View itemView) {
        super(itemView);
        rootLayout = itemView.findViewById(R.id.card_view_list_item_title_desc);
        tvTitle = itemView.findViewById(R.id.tv_list_item_title);
        tvDesc = itemView.findViewById(R.id.tv_list_item_desc);
        tvIconText = itemView.findViewById(R.id.title_desc_tv_icon_text);
    }

    void bindView(Cluster desc) {
        tvTitle.setText(desc.getName());

    }


}
