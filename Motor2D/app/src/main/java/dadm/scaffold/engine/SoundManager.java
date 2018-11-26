package dadm.scaffold.engine;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;
import java.util.HashMap;

public class SoundManager {

    public enum FXSounds{
        Explosion,
        Fire,
        GetPowerUp
    }
    private final int MAX_STREAMS = 50;
    private HashMap<FXSounds,Integer> soundsID;
    private final float DEFAULT_MUSIC_VOLUME = 0.25f;
    Context actualContext;
    SoundPool soundPool;
    MediaPlayer backgroundPlayer;
    public SoundManager(Context context){
        actualContext = context;
        createSoundsPool(context);
        loadFX(context);
        loadMusic();
    }

    private void createSoundsPool(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(MAX_STREAMS,
                    AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes audioAttributes = new
                    AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(MAX_STREAMS)
                    .build();
        }
    }

    private void loadFX(Context context){
        soundsID = new HashMap<>();
        loadFromType(context,FXSounds.Fire,"fire.wav");
        loadFromType(context,FXSounds.Explosion,"explosion.wav");
        loadFromType(context,FXSounds.GetPowerUp,"powerup.wav");
    }

    private void loadFromType (Context context,FXSounds type,String fileName){
        try {
            AssetFileDescriptor descriptor =
                    context.getAssets().openFd("sfx/" + fileName);
            int soundId = soundPool.load(descriptor, 1);
            soundsID.put(type, soundId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PlayFX(FXSounds type){
        soundPool.play(soundsID.get(type),1.0f,1.0f,0,0,1.0f);
    }

    private void loadMusic() {
        try {
            backgroundPlayer = new MediaPlayer();
            AssetFileDescriptor afd = actualContext.getAssets()
                    .openFd("sfx/main.mp3");
            backgroundPlayer.setDataSource(
                    afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getLength());
            backgroundPlayer.setLooping(true);
            backgroundPlayer.setVolume(DEFAULT_MUSIC_VOLUME,
                    DEFAULT_MUSIC_VOLUME);
            backgroundPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void unloadMusic() {
        backgroundPlayer.stop();
        backgroundPlayer.release();
    }
    public void pauseBgMusic() {
        backgroundPlayer.pause();
    }
    public void resumeBgMusic() {
        backgroundPlayer.start();
    }
    private void unloadSounds() {
        soundPool.release();
        soundPool = null;
        soundsID.clear();
    }
    public void Close(){
        unloadMusic();
        unloadSounds();
    }

}
