package com.frba.abclandia.stringformatter;

public class StringWithoutLastLetter extends WordFormatter {
	@Override
	public String formatWord(String letter, String word){
		return word.substring(0, word.length() - 1) + "_";
		
}

}
