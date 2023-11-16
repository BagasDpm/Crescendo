package com.example.crescendo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.crescendo.adapter.ArtistAdapter;
import com.example.crescendo.classmodel.MusicFiles;
import com.example.crescendo.classmodel.player;
import com.example.crescendo.fragment.FragmentSongs;

import java.util.ArrayList;

public class ListSongsArtistActivity extends AppCompatActivity implements ArtistAdapter.itemclick{
    public static boolean filteredSongs=false;
    public static int currentidx;
    String currentartistname;
    ArrayList<MusicFiles> musicFiles;
    public static ArrayList<MusicFiles> filteredmusicFiles = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_songs_artist);
        filteredmusicFiles.clear();
        recyclerView=findViewById(R.id.rvSongArtist);
        Intent intent= getIntent();
        currentartistname=intent.getStringExtra("currentartistname");
        musicFiles= FragmentSongs.musicFiles;
//        Log.i("tes",currentartistname);
        for (MusicFiles musicFiles1:musicFiles){
            if (musicFiles1.getArtist().equals(currentartistname)){
                filteredmusicFiles.add(musicFiles1);
            }
        }

        ArtistAdapter artistAdapter = new ArtistAdapter(this,filteredmusicFiles);
        artistAdapter.setonitemclick(this);
        recyclerView.setAdapter(artistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onclick(MusicFiles musicFiles, int position) {
        currentidx=position;
        filteredSongs=true;
        player.play(getBaseContext(),musicFiles.getMusicId());
        Intent intent = new Intent(this,PlayerActivity.class);
        intent.putExtra("titleSong",musicFiles.getTitle());
        intent.putExtra("artist",musicFiles.getArtist());
        intent.putExtra("image",musicFiles.getAlbumImageId());
        startActivity(intent);
    }
}