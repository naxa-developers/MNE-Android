package org.odk.collect.android.myapplication.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//
//import com.rillmark.royalworldcup.MainApplication;

import org.odk.collect.android.application.Collect;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<L, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    List<L> l;
    int layout;

    public BaseRecyclerViewAdapter(List<L> l, int layout) {
        this.l = l;
        this.layout = layout;
        Log.d("BaseRecyclerViewAdapter", "listSize = " + l.size());
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Collect.getInstance()).inflate(layout, parent, false);
        return attachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        viewBinded(holder, l.get(position));
    }

    public List<L> getData() {
        return this.l;
    }

    @Override
    public int getItemCount() {
        return l.size();
    }

    public abstract void viewBinded(VH vh, L l);

    public abstract VH attachViewHolder(View view);
}