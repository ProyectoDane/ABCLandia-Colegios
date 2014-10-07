package com.example.abclandia.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.abclandia.Card;


public class JustLetterRenderer extends Renderer {
	public JustLetterRenderer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap) {
		
		 super.Render(canvas, rectangleWidth, rectangleHeight, borderRectanglePaint, textPaint, card, imageBitmap);
	   

			   
			  
			   canvas.drawText(card.getLetter(),(float) (rectangleWidth * 0.19), (float) (rectangleHeight*0.2), textPaint);

			   
			   
		
	}

}
 