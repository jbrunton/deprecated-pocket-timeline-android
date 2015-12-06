package com.jbrunton.pockettimeline.app.timelines;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.shared.BaseRecyclerAdapter;
import com.jbrunton.pockettimeline.entities.models.Timeline;

public class TimelinesAdapter extends BaseRecyclerAdapter<Timeline, TimelinesAdapter.ViewHolder> {
    protected TimelinesAdapter() {
        super(R.layout.item_timeline, new ViewHolderFactory());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.timeline_title);
        }
    }

    protected static class ViewHolderFactory implements BaseRecyclerAdapter.ViewHolderFactory<Timeline, ViewHolder> {
        @Override
        public ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        public void bindHolder(ViewHolder holder, Timeline item) {
            holder.titleView.setText(item.getTitle());
        }
    }
}

