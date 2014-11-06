package com.example.abclandia;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.CardView;
import com.example.abclandia.graphics.JustLetterRenderer;
import com.example.abclandia.graphics.Renderer;
import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.dragdrop.DragController;
import com.frba.abclandia.dragdrop.DragLayer;
import com.frba.abclandia.dragdrop.DragSource;
import com.frba.abclandia.dtos.Categoria;


public class GameActivity extends Activity implements View.OnTouchListener,
		DragController.DragListener {

	public static final int TOTAL_JOINS = 1;

	public static final String PACKAGE_NAME = "com.example.abclandia";
	public static final String INTENT_LEVEL_KEY = "level";
	public static final String INTENT_SECUENCE_KEY = "secuence";
	public static final String INTENT_CLASS_LAUNCHER_KEY = "class_launcher";
	public static final String INTENT_EXERCISE_NUMBER = "exercise";

	protected int countHits = 0;

	protected DragController mDragController;
	protected DragLayer mDragLayer;

	protected Renderer mDroppedRenderer;
	protected List<Card> data;

	private WindowManager.LayoutParams mWindowParams;
	private WindowManager mWindowManager;
	protected Audio mAudio;
	protected DataBaseHelper myDbHelper;

	protected int mCurrrentLevel = 0;
	protected int mCurrentSecuence = 0;
	protected int mGameNumber = 0;
	protected int secuence = 0;
	protected String mGameClassName;
	
	// Definimos las variables para saber que Maestro, Alumno y Categoria estan involucrados. 
	protected int unMaestro = 0;
	protected int unAlumno = 0;
	protected int unaCategoria = 0;
	
	protected int mTotalJoins;
	
	protected GameStatistics mGameStatistics;



	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setFullScreen();
		setSizes();
		iniciarDB();
		getExtraData();
		loadDataCard();
		setSounds();
		mGameStatistics = new GameStatistics(this);
	}

	protected void setSounds() {
		mAudio = new Audio(this);
		mAudio.loadWordSounds(data);
	
		mAudio.loadDefaultSounds();
	}

	protected void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mWindowManager = (WindowManager) getSystemService("window");
	}

	protected void setSizes() {
		Configuration config = getResources().getConfiguration();
		if (config.smallestScreenWidthDp >= 720) {
			Renderer.TEXT_LETTER_SIZE = 30;
			Renderer.TEXT_WORD_SIZE = 30;
		} else if (config.smallestScreenWidthDp >= 600) {
			Renderer.TEXT_LETTER_SIZE = 24;
			Renderer.TEXT_WORD_SIZE = 24;
		}

	}

	protected void loadDataCard() {
		data = new ArrayList<Card>();
		char[] secuences = GameDataStructure.getSecuence(mGameNumber,
				mCurrrentLevel, secuence);
		Categoria estaCategoria = myDbHelper.getCagetoriaFromAlumno(unAlumno);
		for (int i = 0; i < secuences.length; i++) {
			String letter = String.valueOf(secuences[i]);
			Card card = myDbHelper.getPalabraFromLetraAndCategoria(letter, estaCategoria.getCategoriaID());
			card.setLetterType(estaCategoria.getCategoriaTipoLetra());
			data.add(card);
		}
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
		
	protected void getExtraData() {
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			mCurrrentLevel = extras.getInt(GameActivity.INTENT_LEVEL_KEY, 1);
			secuence = extras.getInt(INTENT_SECUENCE_KEY, 1);
			unMaestro = extras.getInt("unMaestro", 0);
			unAlumno = extras.getInt("unAlumno", 0);
			unaCategoria = extras.getInt("unaCategoria", 0);

		}

	}



	@Override
	public boolean onTouch(View v, MotionEvent event) {

		boolean handledHere = false;
		CardView cardView = (CardView) v;
		final int action = event.getAction();

		// In the situation where a long click is not needed to initiate a drag,
		// simply start on the down event.
		if (action == MotionEvent.ACTION_DOWN) {

			if (!cardView.isEmptyCard() && cardView.allowDrag()) {
				Class<?> rendererClass = cardView.getRenderer().getClass();
				if (rendererClass == JustLetterRenderer.class){
				mAudio.playSoundLetter(cardView.getCardLetter().toLowerCase());
				} else 
					mAudio.playSoundWord(cardView.getCardLetter());

			}

			handledHere = startDrag(v);
		}

		return handledHere;
	}

	public boolean startDrag(View v) {
		DragSource dragSource = (DragSource) v;

		// We are starting a drag. Let the DragController handle it.
		mDragController.startDrag(v, dragSource, dragSource,
				DragController.DRAG_ACTION_MOVE);

		return true;
	}

	public Renderer getMatchedRenderer() {
		return mDroppedRenderer;
	}

	@Override
	public void onDragStart(DragSource source, Object info, int dragAction) {
		// TODO Auto-generated method stub

	}


	


	@Override
	public void onDragEnd(boolean success, boolean isClick) {
		if (success) {
			mAudio.playCorrectSound();
			countHits++;
			mGameStatistics.countHit();
			if (countHits == mTotalJoins) {
				mGameStatistics.saveStatistics();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					public void run() {

						if (GameDataStructure.isExcersiseComplete(mGameNumber,
								mCurrrentLevel, secuence)) {
							Intent intent = new Intent(GameActivity.this,
									GameWinActivity.class);
							intent.putExtra(
									GameActivity.INTENT_CLASS_LAUNCHER_KEY,
									mGameClassName);
							intent.putExtra("unMaestro", unMaestro);
							intent.putExtra("unAlumno", unAlumno);
							intent.putExtra("unaCategoria", unaCategoria);
							startActivity(intent);

						} else {
							mAudio.playCompleteSound();

							Intent intent = new Intent(GameActivity.this,
									WinActivity.class);

							intent.putExtra(GameActivity.INTENT_LEVEL_KEY,
									mCurrrentLevel);
							intent.putExtra(GameActivity.INTENT_SECUENCE_KEY,
									secuence);
							intent.putExtra(
									GameActivity.INTENT_CLASS_LAUNCHER_KEY,
									mGameClassName);
							intent.putExtra("unMaestro", unMaestro);
							intent.putExtra("unAlumno", unAlumno);
							intent.putExtra("unaCategoria", unaCategoria);
							startActivity(intent);

						}

					}
				}, 500);
			}

		} else if (!isClick)
			mGameStatistics.countFail();
		
	}

	public int getmGameNumber() {
		return mGameNumber;
	}

	public int getSecuence() {
		return secuence;
	}

	public int getUnMaestro() {
		return unMaestro;
	}

	public int getUnAlumno() {
		return unAlumno;
	}

	public int getUnaCategoria() {
		return unaCategoria;
	}
	
	public int getNivel(){
		return mCurrrentLevel;
	}

	@Override
	public void onDragEnd(boolean success) {
		// TODO Auto-generated method stub
		
	}
	

}
