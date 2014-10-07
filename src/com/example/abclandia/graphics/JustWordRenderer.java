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
//		   Rect rectangle = new Rect(2, 2, rectangleHeight, rectangleWidth);
//
//		   RectF rect = new RectF(rectangleHeight*0.25f, rectangleHeight*0.25f, rectangleHeight*0.75f, rectangleHeight*0.75f);
//		   
//		   
//		   
//		   canvas.drawRect(rectangle, rectanglePaint);
//		 
			
		   super.Render(canvas, rectangleWidth, rectangleHeight, borderRectanglePaint, textPaint, card, imageBitmap);		   
		   canvas.drawText(card.getWord(), rectangleWidth/2, (float) (rectangleHeight*0.93), textPaint);


		   
		   }
		
	

}
