package com.example.crescendo.classmodel;

import android.content.Context;
import android.media.MediaPlayer;

public class player{
    private static MediaPlayer mediaPlayer;
    public static void play(Context context, int musicId) {
        if (mediaPlayer!=null) {
            mediaPlayer.release();
            mediaPlayer=null;
        }
        mediaPlayer=MediaPlayer.create(context, musicId);
        mediaPlayer.start();
    }
    public static void pause() {
        if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    public static void resume() {
        if (mediaPlayer!=null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
    public static void stop() {
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
    public static boolean isPlaying() {
        return mediaPlayer!=null && mediaPlayer.isPlaying();
    }
    public static int duration(){
        if (mediaPlayer!=null){
            return mediaPlayer.getDuration();
        }
        return 0;
    }
    public static int getPos(){
        if (mediaPlayer!=null){
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public static void moveSong(int pos){
        if (mediaPlayer!=null){
            mediaPlayer.seekTo(pos);
        }
    }

    public static void onCompleteListener(MediaPlayer.OnCompletionListener listener){
        if (mediaPlayer!=null){
            mediaPlayer.setOnCompletionListener(listener);
        }
    }

    public static void toggleLoop(boolean loop){
        mediaPlayer.setLooping(loop);
    }
}
