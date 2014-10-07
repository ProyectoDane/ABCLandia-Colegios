package com.example.abclandia.graphics;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Align;

import com.example.abclandia.Card;
import com.example.abclandia.Util;

public abstract class Renderer {
	
	//Estas 3 medidas cambian dependiendo de si se trata de una tablet de 10' o 7'
	public static int TEXT_LETTER_SIZE;
	public static int TEXT_WORD_SIZE;
	public static int RADIUS_ROUNDED_RECTANGLE;
	
	protected Paint mBorderRectPaint, mFillRectPaint, mSuccessBorderRectPaint, mTextLetterPaint, mTextWordPaint;
	protected Context mContext;
	
	
	
	public Renderer(Context context){
		mContext = context;
		
		  mBorderRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		  mBorderRectPaint.setColor(Color.BLACK);
	      mBorderRectPaint.setStyle(Paint.Style.STROKE);
	      mBorderRectPaint.setStrokeWidth(3);
	        
	      mFillRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	      mFillRectPaint.setColor(Color.WHITE);
	      mFillRectPaint.setStyle(Paint.Style.FILL);
	      
	      mTextLetterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		  mTextLetterPaint.setColor(Color.BLACK);
		  mTextLetterPaint.setTextAlign(Align.CENTER);
		  mTextLetterPaint.setTextSize(Util.getTextSizeDensityDependent(mContext,TEXT_LETTER_SIZE ));
		  
		 mTextWordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		 mTextWordPaint.setColor(Color.BLACK);
		 mTextWordPaint.setTextAlign(Align.CENTER);
		 mTextWordPaint.setTextSize(Util.getTextSizeDensityDependent(mContext,TEXT_WORD_SIZE));
		  
		  
		  
	 }
	
	
	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card, Bitmap imageBitmap){
		
		Paint rectangleBorderPaint = (borderRectanglePaint !=null) ? borderRectanglePaint : mBorderRectPaint;
		
		 RectF rectangle = new RectF(3, 3, rectangleWidth - 3,rectangleHeight - 3 );
		 
		 
//		 canvas.drawRoundRect ( rectangle, 20.0f, 20.0f, mFillRectPaint);
//		 canvas.drawRect(rectangle, mFillRectPaint);
		 canvas.drawRoundRect(rectangle, 18.0f, 18f, mFillRectPaint);
		 canvas.drawRoundRect(rectangle, 18.0f, 18f, rectangleBorderPaint);
//		 canvas.drawLine(2, (float) (rectangleHeight*0.2), rectangleWidth, (float) (rectangleHeight*0.2), rectangleBorderPaint);
//		 canvas.drawText(card.getLetter(),rectangleWidth/2, (float) (rectangleHeight*0.18), textPaint);
		
	}
	
	
	protected void drawImage(Canvas canvas,Card card, int rectangleWidth, int rectangleHeight, Bitmap imageBitmap){
		
	  
		float sideImageLong = (float) (rectangleWidth * 0.8 - rectangleWidth * 0.2);
		RectF rect = new RectF(rectangleWidth * 0.2f, rectangleHeight * 0.35f,
				rectangleWidth * 0.2f + sideImageLong, rectangleHeight * 0.35f
						+ sideImageLong);

		if (imageBitmap != null) {
			canvas.drawBitmap(imageBitmap, null, rect, null);
		}
		  
		
	}
	protected void drawLetter(Canvas canvas,Card card, int rectangleWidth, int rectangleHeight){
		 canvas.drawText(card.getLetter(),(float) (rectangleWidth * 0.5),
				 (float) (rectangleHeight*0.2), mTextLetterPaint);
		
	}
	protected void drawWord(Canvas canvas,Card card, int rectangleWidth, int rectangleHeight){
		 canvas.drawText(card.getWord(),(float) (rectangleWidth * 0.5), (float) (rectangleHeight*0.9), mTextWordPaint);
	}
	protected void drawWordWithoutLastCharacter(Canvas canvas,Card card, int rectangleWidth, int rectangleHeight){
		String originalWord = card.getWord();
		String word = card.getWord().substring(0, originalWord.length()-1) + "_";
		 canvas.drawText(word,(float) (rectangleWidth * 0.5), (float) (rectangleHeight*0.9), mTextWordPaint);
	}
	
	protected void drawWordWithoutFirstLetter(Canvas canvas,Card card, 
					int rectangleWidth, int rectangleHeight){
		String originalWord = card.getWord();
		String word = "_" + card.getWord().substring(1, originalWord.length()) ;
		 canvas.drawText(word,(float) (rectangleWidth * 0.5), (float) (rectangleHeight*0.9), mTextWordPaint);
	}
	
	protected void dragWordWordWithoutdAllOccurrencesOfFirstLetter(Canvas canvas,Card card, 
					int rectangleWidth, int rectangleHeight){
		String firstLetter = card.getLetter().toLowerCase();
		String word = card.getWord().replace(firstLetter, "_");
		canvas.drawText(word,(float) (rectangleWidth * 0.5), (float) (rectangleHeight*0.9), mTextWordPaint);
		
	}
	
	
	protected void successRender(){
		
	}
		   

		
}


