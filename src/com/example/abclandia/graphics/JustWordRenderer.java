package com.example.abclandia.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.abclandia.Card;


public class JustWordRenderer extends Renderer {

	public JustWordRenderer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap) {

			
		   super.Render(canvas, rectangleWidth, rectangleHeight, borderRectanglePaint, textPaint, card, imageBitmap);
		   String word;
		   if (wordFormatter != null)
			   word = wordFormatter.formatWord(card.getLetter(), card.getWord());
		   else
			   word = card.getWord();
		   if (!card.isLowerUpperLetter())
				 drawWord(canvas, word, rectangleWidth, rectangleHeight);
			else 
				 drawWordLowerAndUpper(canvas, word, rectangleWidth, rectangleHeight);
			
			 
		 


		   
		   }
		
	

}
