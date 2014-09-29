package com.example.abclandia.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.abclandia.Card;

public class EOneMatchedRenderer extends Renderer{
	
	
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap) {
		
		super.Render(canvas, rectangleWidth, rectangleHeight, borderRectanglePaint, textPaint, card, imageBitmap);
		  
		   RectF rect = new RectF(rectangleHeight*0.25f, rectangleHeight*0.25f, rectangleHeight*0.75f, rectangleHeight*0.75f);
		   canvas.drawText(card.getLetter(),rectangleWidth/2, (float) (rectangleHeight*0.18), textPaint);

		   if (imageBitmap != null){
			   canvas.drawBitmap(imageBitmap, null, rect, null);
			   
		   }
		   
		   
		   }

}
