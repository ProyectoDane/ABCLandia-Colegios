package com.example.abclandia;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;

public class Util {
	
	public static Bitmap getViewBitmap(View v) {
	    v.clearFocus();
	    v.setPressed(false);

	    boolean willNotCache = v.willNotCacheDrawing();
	    v.setWillNotCacheDrawing(false);

	    // Reset the drawing cache background color to fully transparent
	    // for the duration of this operation
	    int color = v.getDrawingCacheBackgroundColor();
	    v.setDrawingCacheBackgroundColor(0);

	    if (color != 0) {
	        v.destroyDrawingCache();
	    }
	    v.buildDrawingCache();
	    Bitmap cacheBitmap = v.getDrawingCache();
	    if (cacheBitmap == null) {
//	        Log.e(TAG, "failed getViewBitmap(" + v + ")", new RuntimeException());
	        return null;
	    }

	    Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

	    // Restore the view
	    v.destroyDrawingCache();
	    v.setWillNotCacheDrawing(willNotCache);
	    v.setDrawingCacheBackgroundColor(color);

	    return bitmap;
	}
	
	public static int[] getDragViewPosition(View v) {
		int[] loc = new int[2];
	     v.getLocationOnScreen(loc);
	     return loc;
	}
	
	public static void setOriginalPositionToImageWithAnimation(View v){
		  TranslateAnimation localTranslateAnimation = new TranslateAnimation(0, 500, 0, 500);
		  localTranslateAnimation.setDuration(400);
		  localTranslateAnimation.setFillAfter(false);
		//  localTranslateAnimation.setAnimationListener(new MyAnimationListener(this));
		  v.startAnimation(localTranslateAnimation);
	}
	
	public static int getTextSizeDensityDependent(Context context, int textSize){
		
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return  (int) (textSize * dm.scaledDensity); 
		
	}
	
//	public static void setFullScreen(Context context){
//		context.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		mWindowManager = (WindowManager) getSystemService("window");
//	}

}
