package com.example.abclandia.graphics;

import com.example.abclandia.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class JustLetterFiveRenderer extends Renderer {

	public JustLetterFiveRenderer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap) {
		
		 super.Render(canvas, rectangleWidth, rectangleHeight, borderRectanglePaint, textPaint, card, imageBitmap);
		 String word = card.getWord();
		 String letter =word.substring(word.length()-1, word.length());
	   

		 if (!card.isLowerUpperLetter())
			 drawLetter(canvas,letter, rectangleWidth, rectangleHeight);
//		else 
//			 drawLetterLowerAndUpper(canvas, card, rectangleWidth, rectangleHeight);
		
		 

			   
			   
		
	}

}
