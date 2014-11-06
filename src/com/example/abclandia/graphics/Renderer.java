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
import com.frba.abclandia.stringformatter.WordFormatter;
import com.frba.abclandia.utils.Util;

public abstract class Renderer {

	// Estas 3 medidas cambian dependiendo de si se trata de una tablet de 10' o
	// 7'
	public static int TEXT_LETTER_SIZE;
	public static int TEXT_WORD_SIZE;
	public static int RADIUS_ROUNDED_RECTANGLE;
	public  float X_LETTER_MULTIPLIER = 0.5f;
	public float X_FIRST_LETTER_MULTIPLIER = 0.4f; 
	public float SPACE_BETWEEN_LETTERS = 0.2f;
	public  float Y_LETTER_MULTIPLIER = 0.2f;
	public float Y_LETTER_MULTIPLIER_UL = 0.18f;
	public  float X_WORD_MULTIPLIER = 0.5f;
	public  float Y_WORD_MULTIPLIER = 0.9f;
	public float Y_FIRST_WORD_MULTIPLIER = 0.8f;
	public float SPACE_BETWEEN_WORDS = 0.13f;
	public  int SPACEBLANCK_LETTER_MULTIPLIER;
	public  int BOTTOM_WORD_MULTIPLIER;


	protected Paint mBorderRectPaint, mFillRectPaint, mSuccessBorderRectPaint,
			mTextLetterPaint, mTextWordPaint;
	protected Context mContext;
	protected WordFormatter wordFormatter;

	public Renderer(Context context) {
		mContext = context;

		mBorderRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		mBorderRectPaint.setColor(Color.parseColor("#454954"));
		mBorderRectPaint.setStyle(Paint.Style.STROKE);
		mBorderRectPaint.setStrokeWidth(Util.getTextSizeDensityDependent(
				mContext, 2));

		mFillRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mFillRectPaint.setColor(Color.WHITE);
		mFillRectPaint.setStyle(Paint.Style.FILL);

		mTextLetterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextLetterPaint.setColor(Color.BLACK);
		mTextLetterPaint.setTextAlign(Align.CENTER);
		mTextLetterPaint.setTextSize(Util.getTextSizeDensityDependent(mContext,
				TEXT_LETTER_SIZE));

		mTextWordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextWordPaint.setColor(Color.BLACK);
		mTextWordPaint.setTextAlign(Align.CENTER);
		mTextWordPaint.setTextSize(Util.getTextSizeDensityDependent(mContext,
				TEXT_WORD_SIZE));

	}
	public void setWordFormatter(WordFormatter formatter){
		wordFormatter = formatter;
	}

	public void Render(Canvas canvas, int rectangleWidth, int rectangleHeight,
			Paint borderRectanglePaint, Paint textPaint, Card card,
			Bitmap imageBitmap) {

		Paint rectangleBorderPaint = (borderRectanglePaint != null) ? borderRectanglePaint
				: mBorderRectPaint;

		RectF rectangle = new RectF(3, 3, rectangleWidth - 3,
				rectangleHeight - 3);

		float radiusX = (float) (rectangleWidth * 0.1);
		float radiusY = (float) (rectangleHeight * 0.1);

		canvas.drawRoundRect(rectangle, radiusX, radiusY, mFillRectPaint);
		canvas.drawRoundRect(rectangle, radiusX, radiusY, rectangleBorderPaint);

	}
	
	public void setRectangleColorBorder(int color){
		mBorderRectPaint.setColor(color);
	}

	protected void drawImage(Canvas canvas, Card card, int rectangleWidth,
			int rectangleHeight, Bitmap imageBitmap) {

		float sideImageLong = (float) (rectangleWidth * 0.8 - rectangleWidth * 0.2);
		RectF rect = new RectF(rectangleWidth * 0.2f, rectangleHeight * 0.28f,
				rectangleWidth * 0.2f + sideImageLong, rectangleHeight * 0.28f
						+ sideImageLong);

		if (imageBitmap != null) {
			canvas.drawBitmap(imageBitmap, null, rect, null);
		}

	}

	protected void drawLetter(Canvas canvas, String letter, int rectangleWidth,
			int rectangleHeight) {
		canvas.drawText(letter, (float) (rectangleWidth * X_LETTER_MULTIPLIER),
				(float) (rectangleHeight * Y_LETTER_MULTIPLIER), mTextLetterPaint);

	}


	
	protected void drawWord(Canvas canvas, String word, int rectangleWidth,
			int rectangleHeight) {
		canvas.drawText(word, (float) (rectangleWidth * X_WORD_MULTIPLIER),
				(float) (rectangleHeight * Y_WORD_MULTIPLIER), mTextWordPaint);
	}



	


	
	
	protected void drawLetterLowerAndUpper(Canvas canvas, String letter, int rectangleWidth,
			int rectangleHeight) {
		
		canvas.drawText(letter.toUpperCase(), (float) (rectangleWidth * X_FIRST_LETTER_MULTIPLIER),
				(float) (rectangleHeight * Y_LETTER_MULTIPLIER_UL), mTextLetterPaint);
		canvas.drawText(letter.toLowerCase(), (float) (rectangleWidth * (X_FIRST_LETTER_MULTIPLIER + SPACE_BETWEEN_LETTERS)),
				(float) (rectangleHeight * Y_LETTER_MULTIPLIER_UL), mTextLetterPaint);
		

	}
	
	protected void drawWordLowerAndUpper(Canvas canvas, String word, int rectangleWidth,
			int rectangleHeight) {
		
		canvas.drawText(word.toUpperCase(), (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * Y_FIRST_WORD_MULTIPLIER), mTextWordPaint);
		canvas.drawText(word.toLowerCase(), (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * (Y_FIRST_WORD_MULTIPLIER + SPACE_BETWEEN_WORDS)), mTextWordPaint);
	}
	protected void drawImageLowerAndUpper(Canvas canvas, Card card, int rectangleWidth,
			int rectangleHeight, Bitmap imageBitmap){
		float sideImageLong = (float) (rectangleWidth * 0.8 - rectangleWidth * 0.2);
		RectF rect = new RectF(rectangleWidth * 0.2f, rectangleHeight * 0.22f,
				rectangleWidth * 0.2f + sideImageLong, rectangleHeight * 0.22f
						+ sideImageLong);

		if (imageBitmap != null) {
			canvas.drawBitmap(imageBitmap, null, rect, null);
		}
		
	}

	protected void successRender() {

	}

}
