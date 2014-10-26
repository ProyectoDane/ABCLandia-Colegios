package com.example.abclandia.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.abclandia.Card;

public class JustWordRendererSix extends Renderer {

	public JustWordRendererSix(Context context) {
		super(context);
		
	}

	@Override
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card,
			Bitmap imageBitmap) {

		String firstLetter = card.getLetter();
		String word = card.getWord().replace(firstLetter, "_");

		super.Render(canvas, rectangleWidth, rectangleHeight,
				borderRectanglePaint, textPaint, card, imageBitmap);
		super.drawWord(canvas, word, rectangleWidth, rectangleHeight);

	}

}
