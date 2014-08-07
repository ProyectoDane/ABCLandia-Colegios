package com.example.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.abclandia.R;


public class Rectangle extends View {
	//circle and text colors
	private int circleCol, labelCol;
	//label text
	private String circleText;
	//paint for drawing custom view
	private Paint circlePaint;

	public Rectangle(Context context, AttributeSet attrs) {
		super(context, attrs);
		//paint object for drawing in onDraw
		circlePaint = new Paint();
		

		
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
		    R.styleable.Rectangle, 0, 0);
	}
	

}
