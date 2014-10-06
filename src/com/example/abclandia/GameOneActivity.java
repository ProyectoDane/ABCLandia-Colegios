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
import java.util.HashMap;
import java.util.List;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;


import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.CardView;
import com.example.abclandia.graphics.CompleteCardRenderer;
import com.example.abclandia.graphics.EOneMatchedRenderer;
import com.example.abclandia.graphics.JustImageRenderer;
import com.example.abclandia.graphics.JustLetterRenderer;
import com.example.abclandia.graphics.JustWordRenderer;
import com.example.abclandia.graphics.Renderer;

import com.frba.abclandia.R;
import com.frba.abclandia.adapters.CardViewAdapter;





public class GameOneActivity extends GameActivity 
	
{
	public static final int TOTAL_JOINS = 5;
	
	
	private DragLayer mDragLayer;
	private GridView mGridViewLeft;
	private GridView mGridViewRight;
	
	private List<Card> data;
	
	private static String CLASS_NAME = "com.example.abclandia.GameOne";
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
        
        
       
        setContentView(R.layout.game_one);
        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController (mDragController);
        mDragController.setDragListener (mDragLayer);
        mDroppedRenderer = new EOneMatchedRenderer(this);
        
        
       mAudio = new Audio(this);
       mAudio.loadWordSounds(data);
        
      
        
        
       
       
  
     
        
       setContentView(R.layout.game_one);
        mDragController = new DragController (this);
        
        loadDataCard();
        
       
        
        mGridViewLeft = (GridView) findViewById(R.id.gridViewLeft);
        mGridViewRight = (GridView) findViewById(R.id.gridViewRight);
        
        mGridViewRight.setAdapter(new CardViewAdapter(data, this, new CompleteCardRenderer(this)));
        mGridViewLeft.setAdapter(new CardViewAdapter(data, this, new JustImageRenderer(this)));
        
        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController (mDragController);
        
        mDragLayer.setGridViewLeft(mGridViewLeft);
        mDragLayer.setGridViewRight(mGridViewRight);

        mDragController.setDragListener (mDragLayer);
        mDroppedRenderer = new EOneMatchedRenderer(this);
        
        
       mAudio = new Audio(this);
       mAudio.loadWordSounds(data);
        
        
       
        
      }




	private void loadDataCard() {
//		GameDataStructure.getSecuence(1,1, 1);
//		GameDataStructure.getSecuence(1,1,2);
//		GameDataStructure.isFinalSecuence(1, 1, 10);
//		GameDataStructure.isFinalSecuence(1,1,9);
//		GameDataStructure.isFinalLevel(1,6);
		
		
		data = new ArrayList<Card>();
        Card card1 = new Card("A","Auto","/storage/emulated/0/Images/Auto.jpg", "Auto.ogg");
        Card card2 = new Card("B","Botella", "/storage/emulated/0/Images/Botella.jpg","Botella.ogg");
        Card card3 = new Card("C","Conejo","storage/emulated/0/Images/Conejo.jpg", "Conejo.ogg");
        Card card4 = new Card("D","Dado","storage/emulated/0/Images/Dado.jpg","Dado.ogg");
        Card card5 = new Card("E","Elefante","storage/emulated/0/Images/Elefante.jpg","Elefante.ogg");
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
	
	public Renderer getDefaultRenderer(){
		return mDroppedRenderer;
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

