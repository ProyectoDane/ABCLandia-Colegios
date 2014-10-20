package com.example.abclandia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator.AnimatorListener;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.database.SQLException;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.ViewFlipper;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.CompleteCardRenderer;
import com.example.abclandia.graphics.JustLetterRenderer;
import com.example.abclandia.graphics.Renderer;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.Adapterprueba;
import com.frba.abclandia.adapters.CardViewAdapter;
import com.frba.abclandia.db.DataBaseHelper;

public class AbcPlayerActivity extends Activity implements View.OnTouchListener {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static final int FLIP_INTERVAL = 2000;

	private AdapterViewFlipper mAdapterViewFlipper;

	private WindowManager mWindowManager;
	private int  width;

	private List<Card> data;
	private ObjectAnimator mInAnimator, mOutAnimator;
	private Audio mAudio;
	AnimatorListener mAnimatorListener;
	DataBaseHelper myDbHelper;

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

		Configuration config = getResources().getConfiguration();
		if (config.smallestScreenWidthDp >= 720) {
			Renderer.TEXT_LETTER_SIZE = 30;
			Renderer.TEXT_WORD_SIZE = 25;
		} else if (config.smallestScreenWidthDp >= 600) {
			Renderer.TEXT_LETTER_SIZE = 20;
			Renderer.TEXT_WORD_SIZE = 18;
		}
		setContentView(R.layout.abc_player_activity);
	
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

				mAdapterViewFlipper.setAutoStart(true);
				mAdapterViewFlipper.setFlipInterval(FLIP_INTERVAL);
				
				mOutAnimator = new ObjectAnimator();
				mOutAnimator.setPropertyName("translationX");
				mOutAnimator.setFloatValues(0, -width);

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
	}

	private void loadDataCard() {

		data = new ArrayList<Card>();
		data = myDbHelper.getPalabrasFromCategoria(1);
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
			Log.d("POOCHIE", "No se pudo abrir la BD");
			throw sqle;
		}
		
	}

}
