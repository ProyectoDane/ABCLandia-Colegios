package com.example.abclandia;

public class Card {
	private String mWord;
	private String mLetter;
	private String mImagePath;
	private String mSoundName;
	
	public Card (){
		
	}
	
	public Card (String letter, String word, String imagePath, String soundName){
		mLetter = letter;
		mWord = word;
		mImagePath = imagePath;
		mSoundName = soundName; 
	}
	
	public String getLetter() {
		return mLetter;
	}


	public String getWord() {
		return mWord;
	}
	public String getImagePath(){
		return mImagePath;
	}
	public String getSoundName(){
		return mSoundName;
	}
	
	public boolean isEmptyCard(){
		if (mLetter == null && mWord == null){
			return true;
		}
		return false;
	}

	

}
