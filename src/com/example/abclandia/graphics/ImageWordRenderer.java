package com.example.abclandia.graphics;

import com.example.abclandia.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ImageWordRenderer extends Renderer{

	public ImageWordRenderer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card,
			Bitmap imageBitmap) {

		super.Render(canvas, rectangleWidth, rectangleHeight,
				borderRectanglePaint, textPaint, card, imageBitmap);

		if (!card.isLowerUpperLetter()) {

			drawImage(canvas, card, rectangleWidth, rectangleHeight,
					imageBitmap);
			drawWord(canvas, card.getWord(), rectangleWidth, rectangleHeight);
		
		} else {
			drawImageLowerAndUpper(canvas, card, rectangleWidth, rectangleHeight, imageBitmap);
					

			drawWordLowerAndUpper(canvas, card.getWord(), rectangleWidth,
					rectangleHeight);
		}

		//

	}
}