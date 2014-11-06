package com.example.abclandia.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.abclandia.Card;

public class LetterImageRenderer extends Renderer{
	
	
	public LetterImageRenderer(Context context) {
		super(context);
		
	}

	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap) {
		
		super.Render(canvas, rectangleWidth, rectangleHeight, borderRectanglePaint, textPaint, card, imageBitmap);
		  
			if (!card.isLowerUpperLetter()) {
			drawLetter(canvas, card.getLetter(), rectangleWidth,
					rectangleHeight);
			drawImage(canvas, card, rectangleWidth, rectangleHeight,
					imageBitmap);

		} else {
			drawLetterLowerAndUpper(canvas, card.getLetter(), rectangleWidth,
					rectangleHeight);
			drawImage(canvas, card, rectangleWidth, rectangleHeight,
					imageBitmap);

		}
		   
		   
		   }

}
