package com.example.abclandia.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.abclandia.Card;


public class JustLetterRenderer extends Renderer {
	Paint mRectanglePaint;
	
	public JustLetterRenderer(){
		super();
	}
	
	
	{
	     mRectanglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectanglePaint.setColor(Color.WHITE);
        mRectanglePaint.setStyle(Paint.Style.FILL);
//        mRectanglePaint.setStrokeWidth(2);
	}

	@Override
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap) {
		
		 { super.Render(canvas, rectangleWidth, rectangleHeight, borderRectanglePaint, textPaint, card, imageBitmap);
	   

			   
			  
			   canvas.drawText(card.getLetter(),rectangleWidth/2, (float) (rectangleHeight*0.18), textPaint);

			   
			   }
		
	}

}
 