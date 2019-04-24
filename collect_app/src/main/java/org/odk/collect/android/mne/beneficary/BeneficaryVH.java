package org.odk.collect.android.mne.beneficary;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.odk.collect.android.R;

import java.util.HashMap;

import timber.log.Timber;

public class BeneficaryVH extends RecyclerView.ViewHolder {
    private TextView tvTitle, tvDesc, tvWardNo, tvBeneType, tvGovermentTranch, tvConstructionPhase, tvTypeOfHouse, tvRemarks, tvTotalFilled;
    private View tvSupportingText;
    private HashMap<String, String> metadata = null;
    private Button btnOpenForm;
    private AppCompatImageButton btnExpand;


    BeneficaryVH(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.primary_text);
        tvDesc = itemView.findViewById(R.id.sub_text);
        btnOpenForm = itemView.findViewById(R.id.btn_open_form);
        btnExpand = itemView.findViewById(R.id.expand_button);
        tvSupportingText = itemView.findViewById(R.id.supporting_text_wrapper);
        tvWardNo = itemView.findViewById(R.id.tv_ward_no);
        tvBeneType = itemView.findViewById(R.id.tv_type);
        tvGovermentTranch = itemView.findViewById(R.id.tv_goverment_tranch);
        tvConstructionPhase = itemView.findViewById(R.id.tv_construction_phase);
        tvTypeOfHouse = itemView.findViewById(R.id.tv_type_of_house);
        tvRemarks = itemView.findViewById(R.id.tv_remarks);
        tvTotalFilled = itemView.findViewById(R.id.title_desc_tv_icon_text);


    }

    public void bindView(BeneficaryStats beneficaryStats, Context context) {


        try {
            tvTitle.setText(beneficaryStats.getName());
            tvDesc.setText(beneficaryStats.getAddress());

            tvWardNo.setText(context.getString(R.string.msg_ward_number, beneficaryStats.getWardNo()));
            tvBeneType.setText(context.getString(R.string.msg_bene_type, beneficaryStats.getType()));
            tvGovermentTranch.setText(context.getString(R.string.msg_gov_tranch, beneficaryStats.getGovernmentTranch()));
            tvTypeOfHouse.setText(context.getString(R.string.msg_type_of_house, beneficaryStats.getTypesofhouse()));
            tvRemarks.setText(context.getString(R.string.msg_remarks, beneficaryStats.getRemarks()));
            tvConstructionPhase.setText(context.getString(R.string.msg_construction_phase, beneficaryStats.getConstructionPhase()));
            tvTotalFilled.setText(beneficaryStats.getCount() == 0 ? "P" : String.valueOf(beneficaryStats.getCount()));

            itemView.setOnClickListener(v -> viewItemClicked(beneficaryStats));
            btnExpand.setOnClickListener(v -> toggleVisiblity());

        } catch (NullPointerException e) {
            Timber.e(e);
        }

    }

    private void toggleVisiblity() {
        if (tvSupportingText.getVisibility() == View.VISIBLE) {
            btnExpand.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            tvSupportingText.setVisibility(View.GONE);
        } else {
            btnExpand.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            tvSupportingText.setVisibility(View.VISIBLE);
        }
    }


    public void viewItemClicked(BeneficaryStats beneficaryResponse) {

    }

    public void setActivityAndBeneficiaryIds(HashMap<String, String> map) {
        this.metadata = map;
    }

}