package com.example.crescendo.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.crescendo.ListSongsArtistActivity;
import com.example.crescendo.R;
import com.example.crescendo.adapter.MusicAdapter;
import com.example.crescendo.classmodel.MusicFiles;
import com.example.crescendo.classmodel.player;
import com.example.crescendo.classmodel.player;
import com.example.crescendo.fragment.PlayingFragment;

import java.util.ArrayList;

public class FragmentSongs extends Fragment implements MusicAdapter.itemclick{
    RecyclerView recyclerView;
    public static ArrayList<MusicFiles> musicFiles = new ArrayList<>();
    static int currentidx;
    FrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        frameLayout=getActivity().findViewById(R.id.bottomplayer);
        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        if (musicFiles.isEmpty()){
            musicFiles.add(new MusicFiles("Piccolo Jr.","Dokkan",R.raw.piccolo,R.drawable.db));
            musicFiles.add(new MusicFiles("Giovanni Battle Theme","Pokemon",R.raw.gio,R.drawable.pokemon));
            musicFiles.add(new MusicFiles("Power Plant","Pokemon",R.raw.pp,R.drawable.pokemon));
            musicFiles.add(new MusicFiles("\"beast\"","Dokkan",R.raw.beast,R.drawable.db));
            musicFiles.add(new MusicFiles("Opelucid City","Pokemon",R.raw.op_city,R.drawable.pokemon));
            musicFiles.add(new MusicFiles("Metal Cooler","Dokkan",R.raw.phymc,R.drawable.db));
            musicFiles.add(new MusicFiles("Paradise","ikson",R.raw.waduh,R.drawable.waduh));
            musicFiles.add(new MusicFiles("Reversal Mountain","Pokemon",R.raw.rm_w2,R.drawable.pokemon));
            musicFiles.add(new MusicFiles("Turnabout Jazz Soul","Godot",R.raw.godot,R.drawable.godot));
            musicFiles.add(new MusicFiles("Revive SSJ Goku","Dokkan",R.raw.bird,R.drawable.db));
            musicFiles.add(new MusicFiles("Sinjoh","Pokemon",R.raw.sinjoh,R.drawable.pokemon));
            musicFiles.add(new MusicFiles("Town","Harvest Moon",R.raw.town,R.drawable.hmbtn));
            musicFiles.add(new MusicFiles("Ultimate Hearts","Dokkan",R.raw.hearts,R.drawable.db));
            musicFiles.add(new MusicFiles("Iris Battle Theme","Pokemon",R.raw.iris,R.drawable.pokemon));

        }
        MusicAdapter musicAdapter = new MusicAdapter(getContext(),musicFiles);
        musicAdapter.setitemclick(this);
        recyclerView.setAdapter(musicAdapter);
        return view;
    }

    @Override
    public void onItemclick(MusicFiles musicFiles, int position) {
        currentidx=position;
        player.stop();
        player.play(getContext(),musicFiles.getMusicId());
        ListSongsArtistActivity.filteredSongs=false;
        if (frameLayout.getVisibility()==View.GONE){
            frameLayout.setVisibility(View.VISIBLE);
        }

        PlayingFragment fragment = PlayingFragment.newfragment(musicFiles);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.bottomplayer,fragment).commit();

    }
}