package com.example.crescendo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.crescendo.classmodel.MusicFiles;
import com.example.crescendo.classmodel.player;
import com.example.crescendo.fragment.FragmentSongs;
import com.example.crescendo.fragment.PlayingFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    ImageView albumImage, prevButton, nextButton, backButton, loopButton, shuffleButton;
    TextView artist, songTitle, songStart, songEnd;
    public static ArrayList<MusicFiles> musicFiles;
    ArrayList<MusicFiles> shuffledSong=new ArrayList<>();
    SeekBar seekBar;
    Handler handler;
    Runnable runnable;
    FloatingActionButton playButton;
    public static int currentidx;
    public static boolean shuffled=false;
    boolean loop=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        player.onCompleteListener(this);
        albumImage=findViewById(R.id.img_player);
        prevButton=findViewById(R.id.previous_song);
        nextButton=findViewById(R.id.next_song);
        artist=findViewById(R.id.artist_song);
        songTitle=findViewById(R.id.title_song);
        songStart=findViewById(R.id.duration_song);
        songEnd=findViewById(R.id.totalDuration_song);
        seekBar=findViewById(R.id.seekbar_song);
        playButton=findViewById(R.id.play);
        backButton=findViewById(R.id.btn_back);
        loopButton=findViewById(R.id.repeat_song);
        shuffleButton=findViewById(R.id.shuffle_song);
        handler = new Handler();
        currentidx= PlayingFragment.currentidx;
        musicFiles= FragmentSongs.musicFiles;

        if (ListSongsArtistActivity.filteredSongs){
            musicFiles=ListSongsArtistActivity.filteredmusicFiles;
            currentidx=ListSongsArtistActivity.currentidx;
        }

        shuffledSong=musicFiles;

        Intent intent = getIntent();
        String title = intent.getStringExtra("titleSong");
        String artists = intent.getStringExtra("artist");
        int imageID = intent.getIntExtra("image",-1);
        int songID = intent.getIntExtra("song",-1);

        songTitle.setText(title);
        artist.setText(artists);
        albumImage.setImageResource(imageID);

        int duration = player.duration();
        seekBar.setMax(duration);
        songEnd.setText(convert(duration));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                songStart.setText(convert(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                player.pause();
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int position=seekBar.getProgress();
                player.moveSong(position);
                player.resume();
                handler.postDelayed(runnable,1000);
            }
        });

        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (player.isPlaying()){
                    int pos = player.getPos();
                    seekBar.setProgress(pos);
                    songStart.setText(convert(pos));
                }
                handler.postDelayed(this,1000);
            }
//            handler.postDelayed(this,1000);
        });

        if (player.isPlaying()){
            playButton.setImageResource(R.drawable.baseline_pause_24);
        }else{
            playButton.setImageResource(R.drawable.ic_play);
        }

        if (shuffled){
            shuffleButton.setImageResource(R.drawable.baseline_shuffle_on_24);
        }else{
            shuffleButton.setImageResource(R.drawable.ic_shuffle);
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()){
                    player.pause();
                    playButton.setImageResource(R.drawable.ic_play);
                }else{
                    player.resume();
                    playButton.setImageResource(R.drawable.baseline_pause_24);
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                if (currentidx<=0){
                    currentidx=0;
                }else {
                    currentidx--;
                }
                if (shuffled) {
                    SecureRandom random = new SecureRandom();
                    int randomnumber;
                    do {
                        randomnumber = random.nextInt(musicFiles.size()-1);
                    } while (randomnumber == currentidx);
                    currentidx=randomnumber;
                }
                player.play(getBaseContext(),musicFiles.get(currentidx).getMusicId());
                player.onCompleteListener(PlayerActivity.this);
                int duration = player.duration();
                seekBar.setMax(duration);
                songEnd.setText(convert(duration));
                artist.setText(musicFiles.get(currentidx).getArtist());
                songTitle.setText(musicFiles.get(currentidx).getTitle());
                albumImage.setImageResource(musicFiles.get(currentidx).getAlbumImageId());
                playButton.setImageResource(R.drawable.baseline_pause_24);
                int pos = player.getPos();
                seekBar.setProgress(pos);
                songStart.setText(convert(pos));

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                if (currentidx>=musicFiles.size()-1){
                    currentidx=musicFiles.size()-1;
                }else {
                    currentidx++;
                }
                if (shuffled) {
                    SecureRandom random = new SecureRandom();
                    int randomnumber;
                    do {
                        randomnumber = random.nextInt(musicFiles.size()-1);
                    } while (randomnumber==currentidx);
                    currentidx=randomnumber;
                }
                player.play(getBaseContext(),musicFiles.get(currentidx).getMusicId());
                player.onCompleteListener(PlayerActivity.this);
                int duration = player.duration();
                seekBar.setMax(duration);
                songEnd.setText(convert(duration));
                artist.setText(musicFiles.get(currentidx).getArtist());
                songTitle.setText(musicFiles.get(currentidx).getTitle());
                albumImage.setImageResource(musicFiles.get(currentidx).getAlbumImageId());
                playButton.setImageResource(R.drawable.baseline_pause_24);
                int pos = player.getPos();
                seekBar.setProgress(pos);
                songStart.setText(convert(pos));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getBaseContext(),HomeActivity.class);
                intent1.putExtra("currentidx",currentidx);
                startActivity(intent1);
                finish();
            }
        });

        loopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop=!loop;
                if (loop){
                    loopButton.setImageResource(R.drawable.baseline_repeat_on_24);
                }else{
                    loopButton.setImageResource(R.drawable.ic_repeat);
                }
                if (player.isPlaying()){
                    player.toggleLoop(loop);
                }
            }
        });

        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shuffled){
                    shuffleButton.setImageResource(R.drawable.baseline_shuffle_on_24);
//                    Collections.shuffle(shuffledSong);
                    shuffled=true;
                }else{
                    shuffleButton.setImageResource(R.drawable.ic_shuffle);
//                    shuffledSong.clear();
//                    shuffledSong=musicFiles;
                    shuffled=false;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
    private String convert(int time){
        int second=(time/1000)%60;
        int minute=(time/(1000*60))%60;
        String timetoString=String.format("%02d:%02d",minute,second);
        return timetoString;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        player.stop();
        if (currentidx>=musicFiles.size()-1){
            currentidx=musicFiles.size()-1;
        }else {
            currentidx++;
        }
        if (shuffled) {
            SecureRandom random = new SecureRandom();
            int randomnumber;
            do {
                randomnumber = random.nextInt(musicFiles.size()-1);
            } while (randomnumber == currentidx);
            currentidx=randomnumber;
        }
        player.play(getBaseContext(),musicFiles.get(currentidx).getMusicId());
        player.onCompleteListener(PlayerActivity.this);
        int duration = player.duration();
        seekBar.setMax(duration);
        songEnd.setText(convert(duration));
        artist.setText(musicFiles.get(currentidx).getArtist());
        songTitle.setText(musicFiles.get(currentidx).getTitle());
        albumImage.setImageResource(musicFiles.get(currentidx).getAlbumImageId());
        int pos = player.getPos();
        seekBar.setProgress(pos);
        songStart.setText(convert(pos));
    }
}