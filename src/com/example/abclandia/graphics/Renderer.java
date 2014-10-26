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

	protected Paint mBorderRectPaint, mFillRectPaint, mSuccessBorderRectPaint,
			mTextLetterPaint, mTextWordPaint;
	protected Context mContext;
	protected WordFormatter wordFormatter;

	public Renderer(Context context) {
		mContext = context;

		mBorderRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		mBorderRectPaint.setColor(Color.BLACK);
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
				rectangleWidth * 0.28f + sideImageLong, rectangleHeight * 0.30f
						+ sideImageLong);

		if (imageBitmap != null) {
			canvas.drawBitmap(imageBitmap, null, rect, null);
		}

	}

	protected void drawLetter(Canvas canvas, Card card, int rectangleWidth,
			int rectangleHeight) {
		canvas.drawText(card.getLetter(), (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * 0.2), mTextLetterPaint);

	}

	protected void drawWord(Canvas canvas, Card card, int rectangleWidth,
			int rectangleHeight) {
		canvas.drawText(card.getWord(), (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * 0.9), mTextWordPaint);
	}
	
	protected void drawWord(Canvas canvas, String word, int rectangleWidth,
			int rectangleHeight) {
		canvas.drawText(word, (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * 0.9), mTextWordPaint);
	}

	protected void drawWordWithoutLastCharacter(Canvas canvas, Card card,
			int rectangleWidth, int rectangleHeight) {
		String originalWord = card.getWord();
		String word = card.getWord().substring(0, originalWord.length() - 1)
				+ "_";
		canvas.drawText(word, (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * 0.9), mTextWordPaint);
	}

	protected void drawWordWithoutFirstLetter(Canvas canvas, Card card,
			int rectangleWidth, int rectangleHeight) {
		String originalWord = card.getWord();
		String word = "_" + card.getWord().substring(1, originalWord.length());
		canvas.drawText(word, (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * 0.9), mTextWordPaint);
	}

	protected void dragWordWordWithoutdAllOccurrencesOfFirstLetter(
			Canvas canvas, Card card, int rectangleWidth, int rectangleHeight) {
		String firstLetter = card.getLetter().toLowerCase();
		String word = card.getWord().replace(firstLetter, "_");
		canvas.drawText(word, (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * 0.9), mTextWordPaint);

	}
	
	protected void drawLetterLowerAndUpper(Canvas canvas, Card card, int rectangleWidth,
			int rectangleHeight) {
		String letter = card.getLetter();
		canvas.drawText(letter.toUpperCase(), (float) (rectangleWidth * 0.35),
				(float) (rectangleHeight * 0.16), mTextLetterPaint);
		canvas.drawText(letter.toLowerCase(), (float) (rectangleWidth * 0.65),
				(float) (rectangleHeight * 0.16), mTextLetterPaint);
		

	}
	
	protected void drawWordLowerAndUpper(Canvas canvas, Card card, int rectangleWidth,
			int rectangleHeight) {
		String word = card.getWord();
		canvas.drawText(word.toUpperCase(), (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * 0.86), mTextWordPaint);
		canvas.drawText(word.toLowerCase(), (float) (rectangleWidth * 0.5),
				(float) (rectangleHeight * 0.97), mTextWordPaint);
	}

	protected void successRender() {

	}

}
