package com.example.abclandia.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.abclandia.Card;

public class LetterWordRenderer extends Renderer {
	
	
	public LetterWordRenderer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap) {
		
		
		super.Render(canvas, rectangleWidth, rectangleHeight, borderRectanglePaint, textPaint, card, imageBitmap);
		
		
			drawLetter(canvas, card.getLetter(), rectangleWidth, rectangleHeight);
			drawWord(canvas, card.getLetter(), rectangleWidth, rectangleHeight);
		   
		
		   
		  

		   
	}

	
}
