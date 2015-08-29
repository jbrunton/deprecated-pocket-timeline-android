package com.jbrunton.pockettimeline.app.timelines;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.shared.BaseRecyclerAdapter;
import com.jbrunton.pockettimeline.models.Event;

public class EventsAdapter extends BaseRecyclerAdapter<Event, EventsAdapter.ViewHolder> {
    protected EventsAdapter() {
        super(R.layout.item_event, new ViewHolderFactory());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateView;
        private TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateView = (TextView) itemView.findViewById(R.id.event_date);
            titleView = (TextView) itemView.findViewById(R.id.event_title);
        }
    }

    protected static class ViewHolderFactory implements BaseRecyclerAdapter.ViewHolderFactory<Event, ViewHolder> {
        @Override
        public ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        public void bindHolder(ViewHolder holder, Event item) {
            holder.dateView.setText(item.getDate().toString());
            holder.titleView.setText(item.getTitle());
        }
    }
}
