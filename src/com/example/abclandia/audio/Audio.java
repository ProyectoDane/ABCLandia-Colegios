package com.example.abclandia.audio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.example.abclandia.Card;
import com.frba.abclandia.R;

public class Audio {
	private static final String SOUND_FX_CORRECT = "correct";
	private static final String SOUND_FX_COMPLETE = "complete";
	private static final String SOUND_FX_WIN = "win";
	private static final String PLUS_ID_WORD = "0";
	private static final String PLUS_ID_LETTER = "1";
	private SoundPool mSoundPool;;
	private Map<String, Integer> mSoundMap = new HashMap<String, Integer>();
	private Context mContext;

	private AudioManager mAudioManager;


	public Audio(Context context) {
		mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mContext = context;

	}

	public void loadWordSounds(List<Card> cards) {
		for (Card card : cards) {
			String soundPath = card.getSoundWordPath();
			mSoundMap.put(card.getLetter() + "0",
					mSoundPool.load(soundPath, 1));

		}
	}

	public void loadLetterSoungs(List<Card> cards) {
		
			mSoundMap.put("a1",
					mSoundPool.load(mContext, R.raw.a, 1));
			mSoundMap.put("b1",
					mSoundPool.load(mContext, R.raw.c, 1));
			mSoundMap.put("c1",mSoundPool.load(mContext, R.raw.c, 1));
			mSoundMap.put("d1",mSoundPool.load(mContext, R.raw.d, 1));
			mSoundMap.put("e1",mSoundPool.load(mContext, R.raw.e, 1));
			mSoundMap.put("f1",mSoundPool.load(mContext, R.raw.f, 1));
			mSoundMap.put("g1",mSoundPool.load(mContext, R.raw.g, 1));
			mSoundMap.put("h1",mSoundPool.load(mContext, R.raw.h, 1));
		
	}



	public void playSoundWord(String soundId) {
		float streamVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume
				/ mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(mSoundMap.get(soundId + "0"), streamVolume,
				streamVolume, 1, 0, 1f);
	}

	public void playSoundLetter(String soundId) {
		float streamVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume
				/ mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		Integer sound = mSoundMap.get(soundId + "1");
		if (sound != null){
		
		mSoundPool.play(sound, streamVolume,streamVolume, 1, 0, 1f);
		}
	}

	public void loadDefaultSounds() {
		

		mSoundMap.put(SOUND_FX_CORRECT,
				mSoundPool.load(mContext, R.raw.correct, 1));
		mSoundMap.put(SOUND_FX_COMPLETE,
				mSoundPool.load(mContext, R.raw.complete, 1));
		

	}

	public void playCorrectSound() {
		float streamVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(mSoundMap.get(SOUND_FX_CORRECT), streamVolume,
				streamVolume, 1, 0, 1f);
	}
	
	public void playCompleteSound() {
		float streamVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(mSoundMap.get(SOUND_FX_COMPLETE), streamVolume,
				streamVolume, 1, 0, 1f);
	}

}
