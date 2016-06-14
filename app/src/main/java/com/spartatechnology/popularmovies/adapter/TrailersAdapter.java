package com.spartatechnology.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.spartatechnology.moviedbapi.entity.Video;
import com.spartatechnology.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kadu on 5/10/16.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Video video;
        private ImageButton playButton;
        private TextView nameView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.trailer_name);
            playButton = (ImageButton) itemView.findViewById(R.id.trailer_play_button);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoSelected(video);
                }
            });
        }

    }

    public interface TrailerSelectedListener {
        void onTrailerSelected(Video video);
    }

    private List<Video> mVideoList;
    private TrailerSelectedListener mTrailerSelectedListener;

    public TrailersAdapter(TrailerSelectedListener trailerSelectedListener) {
        this(trailerSelectedListener, new ArrayList<Video>());
    }

    public TrailersAdapter(TrailerSelectedListener trailerSelectedListener, List<Video> videoList) {
        this.mTrailerSelectedListener = trailerSelectedListener;
        this.mVideoList = videoList;
    }

    public void videoSelected(Video video) {
        mTrailerSelectedListener.onTrailerSelected(video);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Video video = mVideoList.get(position);

        holder.nameView.setText(video.getName());
        holder.video = video;
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
}
