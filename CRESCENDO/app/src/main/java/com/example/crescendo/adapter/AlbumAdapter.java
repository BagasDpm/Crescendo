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
import com.example.crescendo.classmodel.Artist;
import com.example.crescendo.classmodel.MusicFiles;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyHolder> {

    Context context;
    ArrayList<Artist> albumFiles;
    View view;
    private itemclick itemclick;

    public AlbumAdapter(Context context, ArrayList<Artist> albumFiles) {
        this.context = context;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public AlbumAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.list_artist, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.MyHolder holder, int position) {
        holder.image_album.setImageResource(albumFiles.get(position).getAlbumImage());
        holder.title_album.setText(albumFiles.get(position).getArtistName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemclick!=null){
                    itemclick.onitemclick(albumFiles.get(holder.getPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image_album;
        TextView title_album;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image_album = itemView.findViewById(R.id.image_album);
            title_album = itemView.findViewById(R.id.title_album);
        }
    }

    public interface itemclick{
        void onitemclick(Artist artist);
    }

    public void setonitemclick(itemclick listener){
        this.itemclick=listener;
    }

}
