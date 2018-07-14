package com.reweyou.master.kavyanjali.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.reweyou.master.kavyanjali.R;
import com.reweyou.master.kavyanjali.data.model.PoemModel;
import com.reweyou.master.kavyanjali.databinding.ItemPoemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by master on 13/7/18.
 */
public class PoemAdapter extends RecyclerView.Adapter<PoemAdapter.BackgroundViewHolder> {
    private static final String TAG = PoemAdapter.class.getName();
    private final Context mContext;
    private List<PoemModel> dataList = new ArrayList<>();

    public PoemAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public BackgroundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPoemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_poem, parent, false);
        return new BackgroundViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BackgroundViewHolder holder, int position) {
        holder.binding.setModel(dataList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void add(List<PoemModel> poemModels) {
        Log.d(TAG, "add: " + poemModels.size());
        dataList.clear();
        dataList.addAll(poemModels);
        notifyDataSetChanged();
    }


    class BackgroundViewHolder extends RecyclerView.ViewHolder {

        private ItemPoemBinding binding;


        public BackgroundViewHolder(ItemPoemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
