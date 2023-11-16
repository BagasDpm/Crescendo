package com.example.crescendo.fragment;

import static com.example.crescendo.PlayerActivity.shuffled;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crescendo.ListSongsArtistActivity;
import com.example.crescendo.PlayerActivity;
import com.example.crescendo.R;
import com.example.crescendo.classmodel.MusicFiles;
import com.example.crescendo.classmodel.player;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.SecureRandom;
import java.util.ArrayList;

public class PlayingFragment extends Fragment implements MediaPlayer.OnCompletionListener{
    ImageView albumImage, prevButton, nextButton;
    FloatingActionButton playButton;
    ArrayList<MusicFiles> musicFiles;
    public static int currentidx;
    TextView titleSong, artist;
    MediaPlayer mediaPlayer;
    RelativeLayout rl;

    public static PlayingFragment newfragment(MusicFiles musicFiles) {
        PlayingFragment fragment = new PlayingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("songTitle",musicFiles.getTitle());
        bundle.putString("artist",musicFiles.getArtist());
        bundle.putInt("image",musicFiles.getAlbumImageId());
        bundle.putInt("music",musicFiles.getMusicId());
        fragment.setArguments(bundle);
        return fragment;
    }

    private void getMediaPlayer(MediaPlayer mediaPlayer){
        this.mediaPlayer=mediaPlayer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_playing, container, false);
        player.onCompleteListener(this);
        albumImage=view.findViewById(R.id.image_playing);
        prevButton=view.findViewById(R.id.prev_playing);
        nextButton=view.findViewById(R.id.skip_playing);
        playButton=view.findViewById(R.id.playpause_playing);
        titleSong=view.findViewById(R.id.title_playing);
        artist=view.findViewById(R.id.artist_playing);
        rl=view.findViewById(R.id.card_playing);

        if (getArguments()!=null){
            currentidx=FragmentSongs.currentidx;
            musicFiles=FragmentSongs.musicFiles;
            if (ListSongsArtistActivity.filteredSongs){
                musicFiles=ListSongsArtistActivity.filteredmusicFiles;
                currentidx=PlayerActivity.currentidx;
            }
            String newartist= getArguments().getString("artist");
            String newtitle= getArguments().getString("songTitle");
            int newalbumImage = getArguments().getInt("image");
            int newmusic = getArguments().getInt("music");
            artist.setText(newartist);
            titleSong.setText(newtitle);
            albumImage.setImageResource(newalbumImage);
        }
        if (player.isPlaying()){
            playButton.setImageResource(R.drawable.baseline_pause_24);
        }else{
            playButton.setImageResource(R.drawable.ic_play);
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
                if (PlayerActivity.shuffled) {
                    SecureRandom random = new SecureRandom();
                    int randomnumber;
                    do {
                        randomnumber = random.nextInt(musicFiles.size()-1);
                    } while (randomnumber == currentidx);
                    currentidx=randomnumber;
                }
                player.play(getContext(),musicFiles.get(currentidx).getMusicId());
                player.onCompleteListener(PlayingFragment.this);
                artist.setText(musicFiles.get(currentidx).getArtist());
                titleSong.setText(musicFiles.get(currentidx).getTitle());
                albumImage.setImageResource(musicFiles.get(currentidx).getAlbumImageId());
                playButton.setImageResource(R.drawable.baseline_pause_24);
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
                if (PlayerActivity.shuffled) {
                    SecureRandom random = new SecureRandom();
                    int randomnumber;
                    do {
                        randomnumber = random.nextInt(musicFiles.size()-1);
                    } while (randomnumber == currentidx);
                    currentidx=randomnumber;
                }
                player.play(getContext(),musicFiles.get(currentidx).getMusicId());
                player.onCompleteListener(PlayingFragment.this);
                artist.setText(musicFiles.get(currentidx).getArtist());
                titleSong.setText(musicFiles.get(currentidx).getTitle());
                albumImage.setImageResource(musicFiles.get(currentidx).getAlbumImageId());
                playButton.setImageResource(R.drawable.baseline_pause_24);
            }
        });

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"Clicked!",Toast.LENGTH_SHORT).show();
                Intent movetoplay = new Intent(getContext(), PlayerActivity.class);
                movetoplay.putExtra("titleSong",titleSong.getText().toString());
                movetoplay.putExtra("artist",artist.getText().toString());
                movetoplay.putExtra("image",musicFiles.get(currentidx).getAlbumImageId());
                movetoplay.putExtra("song",musicFiles.get(currentidx).getMusicId());
                startActivity(movetoplay);
            }
        });
        return view;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        player.stop();
        if (currentidx>=musicFiles.size()-1){
            currentidx=musicFiles.size()-1;
        }else {
            currentidx++;
        }
        if (PlayerActivity.shuffled) {
            SecureRandom random = new SecureRandom();
            int randomnumber;
            do {
                randomnumber = random.nextInt(musicFiles.size()-1);
            } while (randomnumber == currentidx);
            currentidx=randomnumber;
        }
        player.play(getContext(),musicFiles.get(currentidx).getMusicId());
        player.onCompleteListener(PlayingFragment.this);
        artist.setText(musicFiles.get(currentidx).getArtist());
        titleSong.setText(musicFiles.get(currentidx).getTitle());
        albumImage.setImageResource(musicFiles.get(currentidx).getAlbumImageId());
    }
}