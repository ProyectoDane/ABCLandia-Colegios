package com.example.abclandia.audio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.example.abclandia.Card;



public class Audio {
	private static String PLUS_ID_WORD = "0";
	private static String PLUS_ID_LETTER = "1";
	private SoundPool mSoundPool;
	private Map<String,Integer> mSoundMap = new HashMap<String, Integer>();
	private Map<String, Integer> mSoundLetterMap = new HashMap<String, Integer>();
	private AudioManager mAudioManager;
	private String mdirSoundds = "/storage/emulated/0/Sounds/";
	
	
	
	public Audio(Activity activity) { 
		 mAudioManager = (AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);
		  mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	}
	
	public void loadWordSounds(List<Card> cards){
		for (Card card : cards){
			String soundName = card.getSoundName();
			mSoundMap.put(card.getLetter()+"0", mSoundPool.load(mdirSoundds + soundName, 1));
			
		}
	}
	public void loadLetterSoungs(List<Card> cards){
		for (Card card : cards){
			String soundName = card.getSoundName();
			mSoundMap.put(card.getLetter()+"1", mSoundPool.load(mdirSoundds + soundName, 1));
			
		}
		
	}
	
	public void playSound(String soundId){
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(mSoundMap.get(soundId), streamVolume, streamVolume, 1, 0, 1f);
		
		
	}
	public void playSoundWord(String soundId){
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(mSoundMap.get(soundId+"0"), streamVolume, streamVolume, 1, 0, 1f);
	}
	
	public void playSoundLetter(String soundId){
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(mSoundMap.get(soundId+"1"), streamVolume, streamVolume, 1, 0, 1f);
	}
	
	
  

}
