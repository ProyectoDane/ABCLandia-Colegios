package com.example.abclandia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
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
import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.utils.Util;

public class CardLetterPlayerActivity extends Activity
implements View.OnTouchListener {
	
	private List<Card> data;
	private Audio mAudio;
	private GridView mGridView;
	private WindowManager mWindowManager;
	private int mCenterScreenX, mCenterScreenY;
	private LetterPlayerAnimator mDragShadowAnimator;
	private DataBaseHelper myDbHelper;
	
	// Definimos las variables para saber que Maestro, Alumno y Categoria estan involucrados. 
	private int unMaestro = 0;
	private int unAlumno = 0;
	private int unaCategoria = 11;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        mWindowManager = (WindowManager)getSystemService("window");
	    
	        
			Intent i = getIntent();
			this.unMaestro = i.getIntExtra("unMaestro", 0);
			this.unAlumno = i.getIntExtra("unAlumno", 0);
			this.unaCategoria = i.getIntExtra("unaCategoria", 0);
			
			
	        setScreenPointSenter();
		
		
		
		

	
			Configuration config = getResources().getConfiguration();
			if (config.smallestScreenWidthDp >= 720) {
				Renderer.TEXT_LETTER_SIZE = 30;
				Renderer.TEXT_WORD_SIZE = 25;
			} else if (config.smallestScreenWidthDp >= 600) {
				Renderer.TEXT_LETTER_SIZE = 20;
				Renderer.TEXT_WORD_SIZE = 18;
			}

		

		iniciarDB();
		loadDataCard();
		
//		mAudio = new Audio(this);
//		mAudio.loadWordSounds(data);

		setContentView(R.layout.card_letter_player_activity);
		

	
		mGridView = (GridView) findViewById(R.id.gridView);
		
		

		mGridView.setAdapter(new CardViewAdapter(data, this,
				new CompleteCardRenderer(this),R.layout.card_letter_player_card_view));
		
		mDragShadowAnimator = new LetterPlayerAnimator(this);
		
		

	

		
		


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
//		mAudio.playSoundWord(cardView.getCardId());
		
		
	}
	
	private void setScreenPointSenter(){
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		mCenterScreenX = size.x / 2;
		mCenterScreenY = size.y / 2;
		
	}
	
	private void iniciarDB() {
		// Inicializar servicios
		myDbHelper = new DataBaseHelper(this);
		try {
			myDbHelper.createDatabase();
		} catch (IOException ioe) {
			throw new Error("No se pudo crear la base de datos");
			
		}
		
		try {
			myDbHelper.openDatabase();
		}catch (SQLException sqle){
			Log.d("POOCHIE", "No se pudo abrir la BD");
			throw sqle;
		}
		
	}

	private void loadDataCard() {

		data = new ArrayList<Card>();
		data = myDbHelper.getPalabrasFromCategoria(unaCategoria);
//		Card card1 = new Card(1, "A",
//				"Auto", "/storage/emulated/0/Images/Auto.jpg", "Auto.ogg", "");
//		Card card2 = new Card(1, "B",
//				"Botella", "/storage/emulated/0/Images/Botella.jpg", "Botella.ogg", "");
//		Card card3 = new Card(1, "C",
//				"Conejo", "storage/emulated/0/Images/Conejo.jpg", "Conejo.ogg", "");
//
//		data.add(card1);
//		data.add(card2);
//		data.add(card3);

	}
}
