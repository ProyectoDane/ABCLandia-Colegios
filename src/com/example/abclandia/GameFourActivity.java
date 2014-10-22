package com.example.abclandia;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.EOneMatchedRenderer;
import com.example.abclandia.graphics.JustImageRenderer;
import com.example.abclandia.graphics.JustWordRenderer;
import com.example.abclandia.graphics.Renderer;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.CardViewAdapter;

public class GameFourActivity extends GameActivity {
	
	
		public static final int TOTAL_JOINS = 3;
		private static final String CLASS_NAME = "com.example.abclandia.GameFourActivity";
		
		
		private DragLayer mDragLayer;
		private GridView mGridViewLeft;
		private GridView mGridViewRight;
		
		private List<Card> data;
		

		
		private int mCurrrentLevel = 0;
		private int mCurrentSecuence = 0;
		private int gadorcha = 0;
		
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
	        if (extras !=null) {
	        	mCurrrentLevel = extras.getInt("level");
	            gadorcha= extras.getInt("secuence");
	        }
	        
			// Recuperamos los valores de Maestro, Alumno y Categoria
			Intent i = getIntent();
			this.unMaestro = i.getIntExtra("unMaestro", 0);
			this.unAlumno = i.getIntExtra("unAlumno", 0);
			this.unaCategoria = i.getIntExtra("unaCategoria", 0);
	       
	        setFullScreen();
	        
	        mDragController = new DragController (this);
	        loadDataCard();
	        
	        
	       
	        setContentView(R.layout.game_four_five);
	        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
	        mDragLayer.setDragController (mDragController);
	        mDragController.setDragListener (mDragLayer);
	        mDroppedRenderer = new EOneMatchedRenderer(this);
	        
	        
	       mAudio = new Audio(this);
	       mAudio.loadWordSounds(data);
	        
	      
	        
	        
	       
	       
	  
	     
	        
	     
	        mDragController = new DragController (this);
	        
	        loadDataCard();
	        
	       
	        
	        mGridViewLeft = (GridView) findViewById(R.id.gridViewLeft);
	        mGridViewRight = (GridView) findViewById(R.id.gridViewRight);
	        
	        mGridViewRight.setAdapter(new CardViewAdapter(data, this, new JustWordRenderer(this), R.layout.grid_row));
	        mGridViewLeft.setAdapter(new CardViewAdapter(data, this, new JustImageRenderer(this), R.layout.grid_row));
	        
	        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
	        mDragLayer.setDragController (mDragController);
	        
	        mDragLayer.setGridViewLeft(mGridViewLeft);
	        mDragLayer.setGridViewRight(mGridViewRight);

	        mDragController.setDragListener (mDragLayer);
	        mDroppedRenderer = new EOneMatchedRenderer(this);
	        
	        
	       mAudio = new Audio(this);
	       mAudio.loadWordSounds(data);
	        
	        
	        //load fx
	        
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
			countHits++;
			if (countHits == TOTAL_JOINS) {
				Intent intent = new Intent(this, WinActivity.class);
				intent.putExtra(GameActivity.INTENT_LEVEL_KEY, mCurrrentLevel);
				intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, gadorcha);
				intent.putExtra(GameActivity.INTENT_CLASS_LAUNCHER_KEY, CLASS_NAME);
				startActivity(intent);
				finish();
				
			}
				
				
				
			

			}
		}

	}


