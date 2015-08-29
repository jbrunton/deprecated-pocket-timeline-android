package com.jbrunton.pockettimeline.app.shared;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jbrunton.pockettimeline.models.Timeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRecyclerAdapter<D, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {
    private List<D> dataSource = new ArrayList<>();
    private final @LayoutRes int layout;
    private final ViewHolderFactory<D, H> viewHolderFactory;

    protected BaseRecyclerAdapter(int layout, ViewHolderFactory<D, H> viewHolderFactory) {
        this.layout = layout;
        this.viewHolderFactory = viewHolderFactory;
    }

    public void setDataSource(Collection<D> items) {
        this.dataSource = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int position) {
        View view = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        view.setOnClickListener(clickListener);

        return viewHolderFactory.createViewHolder(view);
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        D item = dataSource.get(position);
        holder.itemView.setTag(item);
        viewHolderFactory.bindHolder(holder, item);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    protected void onItemClicked(D item) {}

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            D item = (D) v.getTag();
            onItemClicked(item);
        }
    };

    public interface ViewHolderFactory<D, H extends RecyclerView.ViewHolder> {
        H createViewHolder(View view);
        void bindHolder(H holder, D item);
    }
}
