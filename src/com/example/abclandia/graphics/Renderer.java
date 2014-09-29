package com.example.abclandia.graphics;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.example.abclandia.Card;

public abstract class Renderer {
	protected Paint mBorderRectPaint, mFillRectPaint, mSuccessBorderRectPaint;
	
	{
		  mBorderRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		  mBorderRectPaint.setColor(Color.BLACK);
	      mBorderRectPaint.setStyle(Paint.Style.STROKE);
	      mBorderRectPaint.setStrokeWidth(3);
	        
	      mFillRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      mFillRectPaint.setColor(Color.WHITE);
	      mFillRectPaint.setStyle(Paint.Style.FILL);
	}
	
	
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap){
		
		Paint rectangleBorderPaint = (borderRectanglePaint !=null) ? borderRectanglePaint : mBorderRectPaint;
		
		 Rect rectangle = new Rect(3, 3, rectangleHeight -3 , rectangleWidth - 3 );
		 RectF rect = new RectF(rectangleHeight*0.25f, rectangleHeight*0.25f, rectangleHeight*0.75f, rectangleHeight*0.75f);
		   
		 canvas.drawRect(rectangle, mFillRectPaint);
		 canvas.drawRect(rectangle, rectangleBorderPaint);
		 canvas.drawLine(2, (float) (rectangleHeight*0.2), rectangleWidth, (float) (rectangleHeight*0.2), rectangleBorderPaint);
//		 canvas.drawText(card.getLetter(),rectangleWidth/2, (float) (rectangleHeight*0.18), textPaint);
		
	}
	
	protected void successRender(){
		
	}
		   

		
}


