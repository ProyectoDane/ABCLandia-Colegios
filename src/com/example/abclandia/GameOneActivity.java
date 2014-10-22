/* Copyright (C) 2012 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.example.abclandia;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.EOneMatchedRenderer;
import com.example.abclandia.graphics.JustImageRenderer;
import com.example.abclandia.graphics.JustLetterRenderer;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.CardViewAdapter;
import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.dtos.Alumno;
import com.frba.abclandia.dtos.Maestro;
import com.frba.abclandia.dtos.Palabra;





public class GameOneActivity extends GameActivity 
	
{
	public static final int TOTAL_JOINS = 1;
	
	
	private DragLayer mDragLayer;
	private GridView mGridViewLeft;
	private GridView mGridViewRight;
	
	
	
	private static String CLASS_NAME = "com.example.abclandia.GameOneActivity";
	private static int GAME_NUMBER = 1;
	

	// Definimos las variables para saber que Maestro, Alumno y Categoria estan involucrados. 
	private int unMaestro = 0;
	private int unAlumno = 0;
	private int unaCategoria = 0;
	

    /**
     * Called when the activity is first created.
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		mGameNumber = GAME_NUMBER;
		if (extras != null) {
			mCurrrentLevel = extras.getInt(GameActivity.INTENT_LEVEL_KEY);

			secuence = extras.getInt(INTENT_SECUENCE_KEY);
		}

		// Recuperamos los valores de Maestro, Alumno y Categoria
		Intent i = getIntent();
		this.unMaestro = i.getIntExtra("unMaestro", 0);
		this.unAlumno = i.getIntExtra("unAlumno", 0);
		this.unaCategoria = i.getIntExtra("unaCategoria", 0);
		
		mDragController = new DragController(this);

		loadDataCard();

		setContentView(R.layout.game_one_activity);
		mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
		mDragLayer.setDragController(mDragController);
		mDragController.setDragListener(mDragLayer);
		mDroppedRenderer = new EOneMatchedRenderer(this);

		mAudio = new Audio(this);
		mAudio.loadWordSounds(data);
		mAudio.loadLetterSoungs(data);
		mAudio.loadDefaultSounds();

		mGridViewLeft = (GridView) findViewById(R.id.gridViewLeft);
		mGridViewRight = (GridView) findViewById(R.id.gridViewRight);

		mGridViewRight.setAdapter(new CardViewAdapter(data, this,
				new JustImageRenderer(this), R.layout.game_one_card_view));
		mGridViewLeft.setAdapter(new CardViewAdapter(data, this,
				new JustLetterRenderer(this), R.layout.game_one_card_view));

		mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
		mDragLayer.setDragController(mDragController);

		mDragLayer.setGridViewLeft(mGridViewLeft);
		mDragLayer.setGridViewRight(mGridViewRight);

		mDragController.setDragListener(mDragLayer);
		mDroppedRenderer = new EOneMatchedRenderer(this);

	}



	

		
		

	
   

	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	    return super.onTouch(v, event);
	}

	public boolean startDrag (View v)
	{
		super.startDrag(v);
	    

	    return true;
	}
	
	


	@Override
	public void onDragEnd(boolean success) {
		if (success) {
			mAudio.playCorrectSound();
		countHits++;
		if (countHits == TOTAL_JOINS) {
			 Handler handler = new Handler();
		     handler.postDelayed(new Runnable() {
		    	 public void run() {
		    		 int nextLevel = mCurrrentLevel + 1;
		    		 if (GameDataStructure.isExcersiseComplete(GAME_NUMBER, mCurrrentLevel, secuence)){
		    			 Intent intent = new Intent(GameOneActivity.this, GameWinActivity.class);
		    			 startActivity(intent);
		    			 
		    		 } else {
		    			
			            	Intent intent = new Intent(GameOneActivity.this, WinActivity.class);
			            	
			    			intent.putExtra(GameActivity.INTENT_LEVEL_KEY, mCurrrentLevel);
			    			intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, secuence);
			    			intent.putExtra(GameActivity.INTENT_CLASS_LAUNCHER_KEY, CLASS_NAME);
			    			startActivity(intent);
		    			 
		    		 }
		               
		            }
		        }, 500);
		    }
			
//			finish();
			   
			
		}
		}
	

	

		
	}

	
	
	
   
   


