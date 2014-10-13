package com.example.abclandia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.CardView;
import com.example.abclandia.graphics.CompleteCardRenderer;
import com.example.abclandia.graphics.Renderer;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.CardViewAdapter;

public class CardLetterPlayerActivity extends Activity
implements View.OnTouchListener {
	
	private List<Card> data;
	private Audio mAudio;
	private GridView mGridView;
	private WindowManager mWindowManager;
	private int mCenterScreenX, mCenterScreenY;
	private LetterPlayerAnimator mDragShadowAnimator;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        mWindowManager = (WindowManager)getSystemService("window");
	        
	        setScreenPointSenter();
		
		
		
		

	
			Configuration config = getResources().getConfiguration();
			if (config.smallestScreenWidthDp >= 720) {
				Renderer.TEXT_LETTER_SIZE = 30;
				Renderer.TEXT_WORD_SIZE = 25;
			} else if (config.smallestScreenWidthDp >= 600) {
				Renderer.TEXT_LETTER_SIZE = 20;
				Renderer.TEXT_WORD_SIZE = 18;
			}

		

		
		loadDataCard();
		
		mAudio = new Audio(this);
		mAudio.loadWordSounds(data);

		setContentView(R.layout.card_letter_player_activity);
		

	
		mGridView = (GridView) findViewById(R.id.gridView);
		
		

		mGridView.setAdapter(new CardViewAdapter(data, this,
				new CompleteCardRenderer(this),R.layout.card_letter_player_card_view));
		
		mDragShadowAnimator = new LetterPlayerAnimator(this);
		
		

	

		
		


	}
	
	
	private void loadDataCard() {
//		GameDataStructure.getSecuence(1,1, 1);
//		GameDataStructure.getSecuence(1,1,2);
//		GameDataStructure.isFinalSecuence(1, 1, 10);
//		GameDataStructure.isFinalSecuence(1,1,9);
//		GameDataStructure.isFinalLevel(1,6);
		
		
		data = new ArrayList<Card>();
        Card card1 = new Card("A","AUT_","/storage/emulated/0/Images/Auto.jpg", "Auto.ogg", null);
        Card card2 = new Card("B","Botella", "/storage/emulated/0/Images/Botella.jpg","Botella.ogg", null);
        Card card3 = new Card("C","Conejo","storage/emulated/0/Images/Conejo.jpg", "Conejo.ogg", null);
        Card card4 = new Card("D","Dado","storage/emulated/0/Images/Dado.jpg","Dado.ogg", null);
        Card card5 = new Card("E","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null);
        data.add(card1);
        data.add(card2);
        data.add(card3);
        data.add(card4);
        data.add(card5);
        data.add(new Card("F","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("G","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("H","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("I","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("J","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("L","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("M","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("N","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("Ñ","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("O","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("P","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("Q","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("R","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("S","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("T","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("U","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("V","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("W","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("X","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("Y","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        data.add(new Card("Z","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", null));
        
        
        
        
        
        
        
        
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {

		final int action = event.getAction();

		
		//

		//
		// In the situation where a long click is not needed to initiate a drag,
		// simply start on the down event.
		if (action == MotionEvent.ACTION_DOWN) {
//			mGridView.setAlpha(0.1f);
			int motionDownX = (int) event.getRawX();
			int motionDownY = (int) event.getRawY();
			//
			Bitmap bitmapCard = Util.getViewBitmap(v);
			int[] loc = new int[2];
			v.getLocationOnScreen(loc);

			int registrationX = motionDownX - loc[0];
			int registrationY = motionDownY - loc[1];
			
			int bitmapSizeX = bitmapCard.getWidth();
			int bitmapSizeY = bitmapCard.getHeight();
			//
			//
//			DragShadow dragShadow = new DragShadow(this, bitmapCard,
//					registrationX, registrationY, 0, 0, bitmapSizeX,
//					bitmapSizeY);
//			dragShadow.show(null, motionDownX, motionDownY);
			
			
//			WindowManager.LayoutParams lp = (WindowManager.LayoutParams) dragShadow
//					.getLayoutParams();
//
//			
//				lp.width = 10;
//			
//				lp.height = 20;
//			mWindowManager.updateViewLayout(dragShadow, lp);
			ImageView iv = new ImageView(this);
			iv.setImageBitmap(bitmapCard);
			
			 
			        WindowManager.LayoutParams lp;
			        int pixelFormat;

			        pixelFormat = PixelFormat.TRANSLUCENT;

			        lp = new WindowManager.LayoutParams(
			        		ViewGroup.LayoutParams.WRAP_CONTENT,
			                ViewGroup.LayoutParams.WRAP_CONTENT,
			                loc[0] , loc[1] ,
			                WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL,
			                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
			                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
			                    /*| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM*/,
			                pixelFormat);
//			        lp.token = mStatusBarView.getWindowToken();
			        lp.gravity = Gravity.LEFT | Gravity.TOP;
			        lp.token = null;
			        lp.setTitle("DragView");
			       

			        mWindowManager.addView(iv, lp);
			
			
			CardView view = (CardView) v;
			mDragShadowAnimator.animate(iv,mGridView, view,loc[0], mCenterScreenX - bitmapSizeX/2,
					loc[1], mCenterScreenY - bitmapSizeY/2, bitmapSizeX, bitmapSizeX);
			// //
			//
			//
			//
//			bitmapCard.recycle();

		}

		return false;

	}
	
	public void reproduceSoundCard(CardView cardView){
		mAudio.playSoundWord(cardView.getCardId());
		
		
	}
	
	private void setScreenPointSenter(){
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		mCenterScreenX = size.x / 2;
		mCenterScreenY = size.y / 2;
		
	}
}
