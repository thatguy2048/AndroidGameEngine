package us.aaron_johnson.gameengine.GameEngine.Audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;

import us.aaron_johnson.gameengine.GameEngine.Base.Controller;
import us.aaron_johnson.gameengine.GameEngine.Base.GameView;

/**
 * Created by combu on 12/19/2017.
 */

public class AudioController extends Controller {
    public static MediaPlayer mediaPlayer = null;
    protected static Context context = null;
    protected static SoundPool soundPool = null;

    public static int MAX_SOUNDS = 10;

    public AudioController(GameView gameView) {
        super(gameView);
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        if(soundPool == null){
            soundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        }
    }

    public void setContext(Context context){
        this.context = context;
    }

    public static boolean loadMusic(String filename){
        boolean output = false;
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(filename);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(1,1);
            mediaPlayer.prepare();
            output = true;
        }catch (IOException e){
            Log.e("AudioController","Loading Music Exception "+e.toString());
        }
        return output;
    }

    public static int loadSound(String filename){
        int output = Integer.MIN_VALUE;
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(filename);
            output = soundPool.load(afd, 0);
        }catch (IOException e){
            Log.e("AudioController","Loading Sound Exception "+e.toString());
        }
        return output;
    }

    public static boolean playSound(int soundId){
        boolean output = false;
        if(soundId != Integer.MIN_VALUE) {
            output = soundPool.play(soundId, 1, 1, 0, 0, 1) != 0;
        }
        return output;
    }

    public static void startMusic(){
        mediaPlayer.start();
    }

    public static void pauseMusic(){
        mediaPlayer.pause();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void end() {
        super.end();
        mediaPlayer.release();
    }
}
