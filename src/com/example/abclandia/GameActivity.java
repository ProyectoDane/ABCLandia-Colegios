package com.example.abclandia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.CardView;
import com.example.abclandia.graphics.EOneMatchedRenderer;
import com.example.abclandia.graphics.JustLetterRenderer;
import com.example.abclandia.graphics.Renderer;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.CardViewAdapter;

import android.app.Activity;
import android.content.res.Configuration;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity implements View.OnTouchListener,
		DragController.DragListener {

	public static final int TOTAL_JOINS = 5;
	public static final String PACKAGE_NAME = "com.example.abclandia";
	public static final String INTENT_LEVEL_KEY = "level";
	public static final String INTENT_SECUENCE_KEY = "secuence";
	public static final String INTENT_CLASS_LAUNCHER_KEY = "class_launcher";

	protected int countHits = 0;

	protected DragController mDragController;
	protected DragLayer mDragLayer;

	protected Renderer mDroppedRenderer;
	protected List<Card> data;

	private WindowManager.LayoutParams mWindowParams;
	private WindowManager mWindowManager;
	protected Audio mAudio;

	protected int mCurrrentLevel = 0;
	protected int mCurrentSecuence = 0;
	protected int gadorcha = 0;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
			Renderer.TEXT_WORD_SIZE = 25;
		} else if (config.smallestScreenWidthDp >= 600) {
			Renderer.TEXT_LETTER_SIZE = 20;
			Renderer.TEXT_WORD_SIZE = 18;
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
				if (cardView.getRenderer().getClass() == JustLetterRenderer.class){
//				mAudio.playSoundLetter(cardView.getCardId());
				} else 
					mAudio.playSoundWord(cardView.getCardId());

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
	public void onDragEnd(boolean success) {

	}
	
	

}
