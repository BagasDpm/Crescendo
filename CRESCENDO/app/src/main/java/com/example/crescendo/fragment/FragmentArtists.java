package com.example.crescendo.fragment;

import static com.example.crescendo.fragment.FragmentSongs.musicFiles;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crescendo.ListSongsArtistActivity;
import com.example.crescendo.R;
import com.example.crescendo.adapter.AlbumAdapter;
import com.example.crescendo.adapter.MusicAdapter;
import com.example.crescendo.classmodel.Artist;
import com.example.crescendo.classmodel.MusicFiles;

import java.util.ArrayList;


public class FragmentArtists extends Fragment implements AlbumAdapter.itemclick{

    RecyclerView recyclerViewAlbums;
    AlbumAdapter albumAdapter;
    public static ArrayList<Artist> artists = new ArrayList<>();
    ArrayList<MusicFiles> musicFiles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        musicFiles=FragmentSongs.musicFiles;
        recyclerViewAlbums = view.findViewById(R.id.rvAlbum);
        recyclerViewAlbums.setHasFixedSize(true);
        if (artists.isEmpty()){
            for (MusicFiles musicFiles1:musicFiles){
                String artistname=musicFiles1.getArtist();
                int albumimageid=musicFiles1.getAlbumImageId();
                boolean dupe=false;
                for (Artist artist:artists){
                    if (artist.getArtistName().equals(artistname)){
                        dupe=true;
                        break;
                    }
                }
                if (!dupe){
                    artists.add(new Artist(artistname,albumimageid));
                }
            }
        }
        albumAdapter = new AlbumAdapter(getContext(), artists);
        recyclerViewAlbums.setAdapter(albumAdapter);
        recyclerViewAlbums.setLayoutManager(new GridLayoutManager(getContext(), 2));
        albumAdapter.setonitemclick(this);
        return view;
    }

    @Override
    public void onitemclick(Artist artist) {
        Intent gotoartistlist = new Intent(getContext(), ListSongsArtistActivity.class);
        gotoartistlist.putExtra("currentartistname",artist.getArtistName());
        startActivity(gotoartistlist);
    }
}