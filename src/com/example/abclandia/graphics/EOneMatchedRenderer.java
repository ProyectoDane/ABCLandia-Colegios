package com.example.abclandia.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.abclandia.Card;

public class EOneMatchedRenderer extends Renderer{
	
	
	public EOneMatchedRenderer(Context context) {
		super(context);
		mBorderRectPaint.setColor(Color.GREEN);
	}

	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap) {
		
		super.Render(canvas, rectangleWidth, rectangleHeight, borderRectanglePaint, textPaint, card, imageBitmap);
		  
		drawImage(canvas, card, rectangleWidth, rectangleHeight, imageBitmap);
		drawLetter(canvas, card, rectangleWidth, rectangleHeight);
		   
		   
		   }

}
