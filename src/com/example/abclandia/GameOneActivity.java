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


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.EOneMatchedRenderer;
import com.example.abclandia.graphics.JustImageRenderer;
import com.example.abclandia.graphics.JustLetterRenderer;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.CardViewAdapter;





public class GameOneActivity extends GameActivity 
	
{
	public static final int TOTAL_JOINS = 5;
	
	
	private DragLayer mDragLayer;
	private GridView mGridViewLeft;
	private GridView mGridViewRight;
	
	private List<Card> data;
	
	private static String CLASS_NAME = "com.example.abclandia.GameOneActivity";
	private static int GAME_NUMBER = 1;

	

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle extras = getIntent().getExtras();
        if (extras !=null) {
        	mCurrrentLevel = extras.getInt(GameActivity.INTENT_LEVEL_KEY);
            gadorcha= extras.getInt(INTENT_SECUENCE_KEY);
        }
       
        setFullScreen();
        setSizes();
        
        
        mDragController = new DragController (this);
        loadDataCard();
        int a = 1;
        
        
       
        setContentView(R.layout.game_one_activity);
        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController (mDragController);
        mDragController.setDragListener (mDragLayer);
        mDroppedRenderer = new EOneMatchedRenderer(this);
        
        
       mAudio = new Audio(this);
       mAudio.loadWordSounds(data);
       mAudio.loadLetterSoungs(data);
       mAudio.loadDefaultSounds();
        
      
        
        
       
       
  
     
        
    

        
       
        
        mGridViewLeft = (GridView) findViewById(R.id.gridViewLeft);
        mGridViewRight = (GridView) findViewById(R.id.gridViewRight);
        
        mGridViewRight.setAdapter(new CardViewAdapter(data, this, new JustImageRenderer(this),R.layout.game_one_card_view));
        mGridViewLeft.setAdapter(new CardViewAdapter(data, this, new JustLetterRenderer(this),R.layout.game_one_card_view));
        
        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController (mDragController);
        
        mDragLayer.setGridViewLeft(mGridViewLeft);
        mDragLayer.setGridViewRight(mGridViewRight);

        mDragController.setDragListener (mDragLayer);
        mDroppedRenderer = new EOneMatchedRenderer(this);
        
        
 
        
        
       
        
      }




	private void loadDataCard() {

		
		
		data = new ArrayList<Card>();
        Card card1 = new Card("A","Auto","/storage/emulated/0/Images/Auto.jpg",
        		"Auto.ogg", "/storage/emulated/0/Sounds/A_fede.ogg");
        Card card2 = new Card("B","Botella", "/storage/emulated/0/Images/Botella.jpg","Botella.ogg", "");
        Card card3 = new Card("C","Conejo","storage/emulated/0/Images/Conejo.jpg",
        		"Conejo.ogg", "/storage/emulated/0/Sounds/C_fede.ogg");
        Card card4 = new Card("D","Dado","storage/emulated/0/Images/Dado.jpg",
        		"Dado.ogg", "/storage/emulated/0/Sounds/C_fede.ogg");
        Card card5 = new Card("E","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg", "/storage/emulated/0/Sounds/E_fede.ogg");
        data.add(card1);
        data.add(card2);
        data.add(card3);
        data.add(card4);
        data.add(card5);
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
		                // acciones que se ejecutan tras los milisegundos
		            	Intent intent = new Intent(GameOneActivity.this, WinActivity.class);
		    			intent.putExtra(GameActivity.INTENT_LEVEL_KEY, mCurrrentLevel);
		    			intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, gadorcha);
		    			intent.putExtra(GameActivity.INTENT_CLASS_LAUNCHER_KEY, CLASS_NAME);
		    			startActivity(intent);
		            }
		        }, 500);
		    }
			
//			finish();
			   
			
		}
		}
		
	}

	
	
	
   
   


