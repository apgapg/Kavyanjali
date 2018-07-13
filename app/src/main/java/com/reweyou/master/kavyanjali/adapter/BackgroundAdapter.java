package com.reweyou.master.kavyanjali.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.reweyou.master.kavyanjali.GlideApp;
import com.reweyou.master.kavyanjali.R;
import com.reweyou.master.kavyanjali.ui.image.ItemBackgroundClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by master on 13/7/18.
 */
public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.BackgroundViewHolder> {
    private static final String TAG = BackgroundAdapter.class.getName();
    private final Context mContext;
    private final ItemBackgroundClickListener listener;
    private List<String> dataList = new ArrayList<>();

    public BackgroundAdapter(Context context, ItemBackgroundClickListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BackgroundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BackgroundViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BackgroundViewHolder holder, int position) {
        GlideApp.with(mContext).load(dataList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void add(List<String> strings) {
        Log.d(TAG, "add: " + strings.size());
        dataList.clear();
        dataList.addAll(strings);
        notifyDataSetChanged();
    }


    class BackgroundViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public BackgroundViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemBackgoundClick(dataList.get(getAdapterPosition()));
                }
            });
        }
    }
}
