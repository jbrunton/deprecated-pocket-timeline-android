package com.jbrunton.pockettimeline.app.shared;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jbrunton.pockettimeline.models.Event;

public class TextViewRecyclerAdapter<D> extends BaseRecyclerAdapter<D, TextViewRecyclerAdapter.ViewHolder> {
    public TextViewRecyclerAdapter() {
        super(android.R.layout.simple_list_item_1, new TextViewRecyclerAdapter.ViewHolderFactory<D>());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.textView = (TextView) view;
        }
    }

    public static class ViewHolderFactory<D> implements BaseRecyclerAdapter.ViewHolderFactory<D, ViewHolder> {
        @Override
        public ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        public void bindHolder(ViewHolder holder, D item) {
            holder.textView.setText(item.toString());
        }
    }
}
