package com.frba.abclandia.stringformatter;

public class StringWithoutdAllOccurrencesOfAnyLetter extends WordFormatter {
	
	@Override
	public String formatWord(String letter, String word) {
		
		return word.replace(letter, "_");
		

	}

}
