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
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.viewholder> {
    Context ctx;
    ArrayList<MusicFiles> artistMusic;
    View view;
    private itemclick itemclick;

    public ArtistAdapter(Context ctx, ArrayList<MusicFiles> artistMusic) {
        this.ctx = ctx;
        this.artistMusic = artistMusic;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(ctx).inflate(R.layout.list_artistsongs, parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.artistName.setText(artistMusic.get(position).getArtist());
        holder.songName.setText(artistMusic.get(position).getTitle());
        holder.imageView.setImageResource(artistMusic.get(position).getAlbumImageId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclick.onclick(artistMusic.get(holder.getPosition()),holder.getPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistMusic.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView songName, artistName;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.music_image);
            songName=itemView.findViewById(R.id.music_title);
            artistName=itemView.findViewById(R.id.music_des);
        }
    }

    public interface itemclick{
        void onclick(MusicFiles musicFiles, int position);
    }

    public void setonitemclick(itemclick itemclick){
        this.itemclick=itemclick;
    }
}
