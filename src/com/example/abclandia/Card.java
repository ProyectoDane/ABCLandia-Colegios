package com.example.abclandia;

public class Card {
	private String mWord;
	private String mLetter;
	private String mImagePath;
	private String mSoundWordPath;
	private String mSoundLetterPath;
	
	public Card (){
		
	}
	
	public Card (String letter, String word, String imagePath, String soundWordPath, String soundLetterPath){
		mLetter = letter;
		mWord = word;
		mImagePath = imagePath;
		mSoundWordPath = soundWordPath; 
		mSoundLetterPath = soundLetterPath;
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
	public String getSoundWordPath(){
		return mSoundWordPath;
	}
	
	public String getSoundLetterPath(){
		return mSoundLetterPath;
	}
	
	public boolean isEmptyCard(){
		if (mLetter == null && mWord == null){
			return true;
		}
		return false;
	}

	

}
