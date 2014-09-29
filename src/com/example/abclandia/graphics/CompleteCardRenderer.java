package com.example.abclandia.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.example.abclandia.Card;


public class CompleteCardRenderer extends Renderer{
	
	@Override
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap) {
		   Rect rectangle = new Rect(2, 2, rectangleHeight, rectangleWidth);
//		   Rect rectangle2 = new Rect(rectangleHeight*0.3, rectangleHeight*0.3, rectangleHeight*0.7, rectangleHeight*0.7);
		   RectF rect = new RectF(rectangleHeight*0.25f, rectangleHeight*0.25f, rectangleHeight*0.75f, rectangleHeight*0.75f);
		   
		   
		   
		   canvas.drawRect(rectangle, borderRectanglePaint);
		   canvas.drawLine(2, (float) (rectangleHeight*0.2), rectangleWidth, (float) (rectangleHeight*0.2), borderRectanglePaint);
		   
		   canvas.drawText(card.getLetter(),rectangleWidth/2, (float) (rectangleHeight*0.18), textPaint);
//		   }
//		   if (isShowWord() && word != null){
		   canvas.drawText(card.getWord(), rectangleWidth/2, (float) (rectangleHeight*0.93), textPaint);
//		   }
//		   if (isShowImage()){
//			   canvas.drawBitmap(imageBitmap, null, rect, null);
		   
		   }
	
		
}
		
		
	
		
	


