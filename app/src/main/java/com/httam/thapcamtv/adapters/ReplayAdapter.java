package com.httam.thapcamtv.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.httam.thapcamtv.R;
import com.httam.thapcamtv.models.Replay;

import java.util.List;

public class ReplayAdapter extends RecyclerView.Adapter<ReplayAdapter.HighlightViewHolder> {
    private final List<Replay> replays;
    private final OnHighlightClickListener listener;

    public ReplayAdapter(List<Replay> replays, OnHighlightClickListener listener) {
        this.replays = replays;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HighlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_replay, parent, false);
        return new HighlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightViewHolder holder, int position) {
        Replay replay = replays.get(position);
        holder.title.setText(replay.getName());
        Glide.with(holder.image.getContext())
                .load(replay.getFeatureImage())
                .into(holder.image);

        // Improve focus handling for Android TV
        holder.itemView.setFocusable(true);
        holder.itemView.setFocusableInTouchMode(true);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHighlightClick(replay.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return replays.size();
    }

    public interface OnHighlightClickListener {
        void onHighlightClick(String id);
    }

    static class HighlightViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        HighlightViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.highlight_title);
            image = itemView.findViewById(R.id.highlight_image);
        }
    }
}