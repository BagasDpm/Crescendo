package com.example.crescendo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendo.R;
import com.example.crescendo.classmodel.MusicFiles;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.viewHolder> {
    Context ctx;
    ArrayList<MusicFiles> musicFiles;
    private itemclick itemclick;

    public MusicAdapter(Context ctx, ArrayList<MusicFiles> musicFiles) {
        this.ctx = ctx;
        this.musicFiles = musicFiles;
    }

    @NonNull
    @Override
    public MusicAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.list_songs,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.viewHolder holder, int position) {
        holder.albumImage.setImageResource(musicFiles.get(position).getAlbumImageId());
        holder.songTitle.setText(musicFiles.get(position).getTitle());
        holder.artistName.setText(musicFiles.get(position).getArtist());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemclick!=null){
                    itemclick.onItemclick(musicFiles.get(holder.getPosition()), holder.getPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView albumImage;
        TextView songTitle, artistName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            albumImage=itemView.findViewById(R.id.music_image);
            songTitle=itemView.findViewById(R.id.music_title);
            artistName=itemView.findViewById(R.id.music_des);
        }
    }

    public interface itemclick{
        void onItemclick(MusicFiles musicFiles, int position);
    }

    public void setitemclick(itemclick listener) {
        this.itemclick = listener;
    }
}


