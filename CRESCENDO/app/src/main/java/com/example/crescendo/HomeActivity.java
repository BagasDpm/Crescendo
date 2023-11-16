package com.example.crescendo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.crescendo.classmodel.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.crescendo.classmodel.MusicFiles;
import com.example.crescendo.fragment.FragmentArtists;
import com.example.crescendo.fragment.FragmentSongs;
import com.example.crescendo.fragment.PlayingFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{
    FrameLayout bottomplayer;
    ArrayList<MusicFiles> musicFiles;
    int currentidx;
    ImageView logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomplayer=findViewById(R.id.bottomplayer);
        logoutButton=findViewById(R.id.logoutButton);
        if (player.isPlaying()){
            Intent intent = getIntent();
            if (intent.hasExtra("currentidx")){
                currentidx=intent.getIntExtra("currentidx",-1);
            }
            musicFiles=FragmentSongs.musicFiles;
            if (ListSongsArtistActivity.filteredSongs){
                musicFiles=ListSongsArtistActivity.filteredmusicFiles;
            }
            MusicFiles musicFiles1 = musicFiles.get(currentidx);
            PlayingFragment fragment = PlayingFragment.newfragment(musicFiles1);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.bottomplayer,fragment).commit();
            bottomplayer.setVisibility(View.VISIBLE);
        }else{
            bottomplayer.setVisibility(View.GONE);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()){
                    player.stop();
                }
                Intent backtoLogin = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(backtoLogin);
                finish();
            }
        });

        initViewPager();
    }

    private void initViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.lyTab);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new FragmentSongs(), "Songs");
        viewPagerAdapter.addFragments(new FragmentArtists(), "Artists");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter   {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        void addFragments(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        @Override
        public int getCount () {
            return fragments.size();
        }

        @NonNull
        @Override
        public CharSequence getPageTitle ( int position){
            return titles.get(position);
        }
    }
}

