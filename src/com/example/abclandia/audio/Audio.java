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
		
			mSoundMap.put("a",
					mSoundPool.load(mContext, R.raw.a, 1));
			mSoundMap.put("b",
					mSoundPool.load(mContext, R.raw.b, 1));
			mSoundMap.put("c",mSoundPool.load(mContext, R.raw.c, 1));
			mSoundMap.put("d",mSoundPool.load(mContext, R.raw.d, 1));
			mSoundMap.put("e",mSoundPool.load(mContext, R.raw.e, 1));
			mSoundMap.put("f",mSoundPool.load(mContext, R.raw.f, 1));
			mSoundMap.put("g",mSoundPool.load(mContext, R.raw.g, 1));
			mSoundMap.put("h",mSoundPool.load(mContext, R.raw.h, 1));
			mSoundMap.put("i",mSoundPool.load(mContext, R.raw.i, 1));
			mSoundMap.put("j",mSoundPool.load(mContext, R.raw.j, 1));
			mSoundMap.put("k",mSoundPool.load(mContext, R.raw.k, 1));
			mSoundMap.put("l",mSoundPool.load(mContext, R.raw.l, 1));
			mSoundMap.put("m",mSoundPool.load(mContext, R.raw.m, 1));
			mSoundMap.put("n",mSoundPool.load(mContext, R.raw.n, 1));
			mSoundMap.put("ñ",mSoundPool.load(mContext, R.raw.nn, 1));
			mSoundMap.put("o",mSoundPool.load(mContext, R.raw.o, 1));
			mSoundMap.put("p",mSoundPool.load(mContext, R.raw.p, 1));
			mSoundMap.put("q",mSoundPool.load(mContext, R.raw.q, 1));
			mSoundMap.put("r",mSoundPool.load(mContext, R.raw.r, 1));
			mSoundMap.put("s",mSoundPool.load(mContext, R.raw.s, 1));
			mSoundMap.put("u",mSoundPool.load(mContext, R.raw.u, 1));
			mSoundMap.put("v",mSoundPool.load(mContext, R.raw.v, 1));
			mSoundMap.put("w",mSoundPool.load(mContext, R.raw.w, 1));
			mSoundMap.put("x",mSoundPool.load(mContext, R.raw.x, 1));
			mSoundMap.put("y",mSoundPool.load(mContext, R.raw.y, 1));
			mSoundMap.put("z",mSoundPool.load(mContext, R.raw.z, 1));
		
	}



	public void playSoundWord(String soundId) {
		float streamVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume
				/ mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		Integer sound = mSoundMap.get(soundId + "0");
		if (sound != null)
			mSoundPool.play(mSoundMap.get(soundId + "0"), streamVolume,
				streamVolume, 1, 0, 1f);
	}

	public void playSoundLetter(String soundId) {
		float streamVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume
				/ mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		Integer sound = mSoundMap.get(soundId);
		if (sound != null)
			mSoundPool.play(sound, streamVolume,streamVolume, 1, 0, 1f);
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
