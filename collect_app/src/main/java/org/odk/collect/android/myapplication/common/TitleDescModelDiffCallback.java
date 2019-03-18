package org.odk.collect.android.myapplication.common;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

public class TitleDescModelDiffCallback extends DiffUtil.Callback {

    private List<TitleDesc> oldItems;
    private List<TitleDesc> newItems;

    public TitleDescModelDiffCallback(List<TitleDesc> newItems, List<TitleDesc> oldItems) {
        this.newItems = newItems;
        this.oldItems = oldItems;
    }
    
    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        TitleDesc old = oldItems.get(oldItemPosition);
        TitleDesc newItem = newItems.get(newItemPosition);

        return old.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        TitleDesc old = oldItems.get(oldItemPosition);
        TitleDesc newItem = newItems.get(newItemPosition);
        return newItem.equals(old);
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        TitleDesc old = oldItems.get(oldItemPosition);
        TitleDesc newItem = newItems.get(newItemPosition);

        return super.getChangePayload(oldItemPosition,newItemPosition);
    }
}
