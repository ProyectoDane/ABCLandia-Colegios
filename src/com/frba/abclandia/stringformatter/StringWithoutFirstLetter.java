package com.frba.abclandia.stringformatter;


public class StringWithoutFirstLetter extends WordFormatter {
	
	@Override
	public String formatWord(String letter, String word){
		return "_" + word.substring(1, word.length());
		
	}

}
