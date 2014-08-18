package com.example.abclandia;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class Rectangle extends View {
	//circle and text colors
	private int circleCol, labelCol;
	//label text
	private String circleText;
	//paint for drawing custom view
	private Paint circlePaint;
	private  boolean mShowText;
	private Paint mTextPaint;
	private Paint mPiePaint;
	private Paint mShadowPaint;
	private int mTextHeight;
	private int mTextColor;
	
	
	public Rectangle(Context context, AttributeSet attrs) {
		super(context, attrs);
		//paint object for drawing in onDraw
		circlePaint = new Paint();
		 init();
		
		TypedArray a = context.getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.Rectangle,
		        0, 0);

		   try {
		       mShowText = a.getBoolean(R.styleable.Rectangle_showText, false);
		       
		   } finally {
		       a.recycle();
		   }
	
	}
	
	private void init(){
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);	
		mTextPaint.setColor(mTextColor);
		if (mTextHeight == 0) {
			mTextHeight = (int) mTextPaint.getTextSize();
		} else {
			mTextPaint.setTextSize(mTextHeight);
		}

			   mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			   mPiePaint.setStyle(Paint.Style.FILL);
			   mPiePaint.setTextSize(mTextHeight);

			   mShadowPaint = new Paint(0);
			   mShadowPaint.setColor(0xff101010);
			   mShadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));

	}
	
	public boolean isShowText() {
		   return mShowText;
		}

		public void setShowText(boolean showText) {
		   mShowText = showText;
		   invalidate();
		   requestLayout();
		}
	

}