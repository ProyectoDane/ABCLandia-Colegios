package com.example.abclandia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterViewFlipper;
import android.widget.TextView;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.CompleteCardRenderer;
import com.example.abclandia.graphics.Renderer;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.Adapterprueba;
import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.dtos.Categoria;

public class AbcPlayerActivity extends Activity implements View.OnTouchListener {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 180;
	private static final int FLIP_INTERVAL = 2500;
	
	// Definimos las variables para saber que Maestro, Alumno y Categoria estan involucrados. 
	private int unMaestro = 0;
	private int unAlumno = 0;
	private int unaCategoria = 0;
	

	private AdapterViewFlipper mAdapterViewFlipper;

	private WindowManager mWindowManager;
	private int  width;

	private List<Card> data;
	private ObjectAnimator mInAnimator, mOutAnimator;
	private Audio mAudio;
	AnimatorListener mAnimatorListener;
	DataBaseHelper myDbHelper;
	TextView lblWord;
	private int mLastIndexView;

	@SuppressWarnings("deprecation")
	private final GestureDetector detector = new GestureDetector(
			new SwipeGestureDetector());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mWindowManager = (WindowManager) getSystemService("window");
		
		Intent i = getIntent();
		this.unMaestro = i.getIntExtra("unMaestro", 0);
		this.unAlumno = i.getIntExtra("unAlumno", 0);
		this.unaCategoria = i.getIntExtra("unaCategoria", 0);
		

		Configuration config = getResources().getConfiguration();
		if (config.smallestScreenWidthDp >= 720) {
			Renderer.TEXT_LETTER_SIZE = 48;
			Renderer.TEXT_WORD_SIZE = 48;
		} else if (config.smallestScreenWidthDp >= 600) {
			Renderer.TEXT_LETTER_SIZE = 39;
			Renderer.TEXT_WORD_SIZE = 39;
		}
		setContentView(R.layout.abc_player_activity);
		 lblWord = (TextView) findViewById(R.id.lblWord);
	
		mAdapterViewFlipper = (AdapterViewFlipper) this
				.findViewById(R.id.view_flipper);
		iniciarDB();
		loadDataCard();
		mAudio = new Audio(this);
		mAudio.loadWordSounds(data);

		mAdapterViewFlipper.setAdapter(new Adapterprueba(data, this,
				new CompleteCardRenderer(this), R.layout.abc_player_card_view));

		mAdapterViewFlipper.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				detector.onTouchEvent(event);
				return true;
			}
		});

		findViewById(R.id.play).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mAudio.playSoundWord(data.get(mAdapterViewFlipper.getDisplayedChild()).getLetter());
				mAdapterViewFlipper.setAutoStart(true);
				mAdapterViewFlipper.setFlipInterval(FLIP_INTERVAL);
				mOutAnimator = new ObjectAnimator();
				mOutAnimator.setPropertyName("translationX");
				mOutAnimator.setFloatValues(0, -width);
//				mInAnimator.addListener(mAnimatorListener);

				mInAnimator = new ObjectAnimator();
				mInAnimator.setPropertyName("translationX");
				mInAnimator.setFloatValues(width, 0);
				mInAnimator.addListener(mAnimatorListener);

				mAdapterViewFlipper.setOutAnimation(mOutAnimator);
				mAdapterViewFlipper.setInAnimation(mInAnimator);
				mAdapterViewFlipper.startFlipping();
				
			}
		});

		findViewById(R.id.stop).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// stop auto flipping
				mAdapterViewFlipper.stopFlipping();
			}
		});

		mAnimatorListener = new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				int indexView = mAdapterViewFlipper.getDisplayedChild();
				
				lblWord.setText(data.get(indexView).getWord());
				mAudio.playSoundWord(data.get(indexView).getLetter());
				
				

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		};
	}

	@Override
	public void onWindowFocusChanged(boolean focus) {
		super.onWindowFocusChanged(focus);
		width = mAdapterViewFlipper.getWidth();
//		mAudio.playSoundWord(data.get(0).getLetter());
		lblWord.setText(data.get(0).getWord());
	}

	private void loadDataCard() {

		data = new ArrayList<Card>();
		Categoria miCategoria = myDbHelper.getCagetoriaFromAlumno(unAlumno);
		this.unaCategoria = miCategoria.getCategoriaID();
		data = myDbHelper.getPalabrasFromCategoria(unaCategoria);

	}

	class SwipeGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				mAdapterViewFlipper.getDisplayedChild();
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

					mOutAnimator = new ObjectAnimator();
					mOutAnimator.setPropertyName("translationX");
					mOutAnimator.setFloatValues(0, -width);

					mInAnimator = new ObjectAnimator();
					mInAnimator.setPropertyName("translationX");
					mInAnimator.setFloatValues(width, 0);
					mInAnimator.addListener(mAnimatorListener);

					mAdapterViewFlipper.setOutAnimation(mOutAnimator);
					mAdapterViewFlipper.setInAnimation(mInAnimator);

					mAdapterViewFlipper.showNext();
					return true;
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

					mOutAnimator = new ObjectAnimator();
					mOutAnimator.setPropertyName("translationX");
					mOutAnimator.setFloatValues(0, width);

					mInAnimator = new ObjectAnimator();
					mInAnimator.setPropertyName("translationX");
					mInAnimator.setFloatValues(-width, 0);
					mInAnimator.addListener(mAnimatorListener);

					mAdapterViewFlipper.setOutAnimation(mOutAnimator);
					mAdapterViewFlipper.setInAnimation(mInAnimator);

					mAdapterViewFlipper.showPrevious();
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
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
			Log.d("ABCLandia", "No se pudo abrir la BD");
			throw sqle;
		}	
	}
	

}
