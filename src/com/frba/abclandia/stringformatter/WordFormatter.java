package com.frba.abclandia.stringformatter;


public abstract class WordFormatter {
	public abstract String formatWord(String letter, String word);
	
	
	public class StringWithoutFirstLetter extends WordFormatter{
		@Override
		public String formatWord(String letter, String word){
			return "_" + word.substring(1, word.length());
			
		}
		
	}
	
	
	
	public class StringWithoutLastLetter extends WordFormatter{
		@Override
		public String formatWord(String letter, String word){
			return word.substring(0, word.length() - 1) + "_";
			
	}
		
			
		}
	public class Gadorcha extends WordFormatter{

		@Override
		public String formatWord(String letter, String word) {
			
			return word.replace(letter, "_");
			

		}

		
	}

}
