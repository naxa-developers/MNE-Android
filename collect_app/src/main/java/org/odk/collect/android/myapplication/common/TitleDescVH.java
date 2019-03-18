package org.odk.collect.android.myapplication.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.collect.android.R;

public class TitleDescVH extends RecyclerView.ViewHolder {
        public final RelativeLayout rootLayout;
        public TextView tvTitle, tvDesc, tvIconText;

        public TitleDescVH(View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.card_view_list_item_title_desc);
            tvTitle = itemView.findViewById(R.id.tv_list_item_title);
            tvDesc = itemView.findViewById(R.id.tv_list_item_desc);
            tvIconText = itemView.findViewById(R.id.title_desc_tv_icon_text);
        }

        public void bindView(TitleDesc desc) {

        }
    }