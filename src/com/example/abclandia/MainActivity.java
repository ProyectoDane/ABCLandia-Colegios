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
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.CardView;
import com.example.abclandia.graphics.EOneMatchedRenderer;
import com.example.abclandia.graphics.JustImageRenderer;
import com.example.abclandia.graphics.JustLetterRenderer;
import com.example.abclandia.graphics.Renderer;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.CardViewAdapter;





public class MainActivity extends Activity 
	implements View.OnLongClickListener, View.OnClickListener,
				View.OnTouchListener, DragController.DragListener
{
	public static final int TOTAL_JOINS = 5;
	private int countHits = 0;
	private DragController mDragController;
	private DragLayer mDragLayer;
	private GridView mGridViewLeft;
	private GridView mGridViewRight;
	private Renderer mDroppedRenderer;
	private List<Card> data;
	
	private WindowManager.LayoutParams mWindowParams;
	private WindowManager mWindowManager;
	private AnimatorSet mAnimatorSet;
	private  SoundPool mSoundPool;
	private  AudioManager  mAudioManager;
	private  HashMap<Integer, Integer> mSoundPoolMap;
	private String auto = "/storage/emulated/0/Sounds/Auto.ogg";
	private Audio mAudio;
	 
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        int level = intent.getIntExtra("level", 1);
        int secuence = intent.getIntExtra("secuence", 2);
        


        // Para hacer la pantalla fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mWindowManager = (WindowManager)getSystemService("window");
        
        setContentView(R.layout.main3);
        mDragController = new DragController (this);
        
        loadDataCard();
        
        Renderer justLetterRenderer = new JustLetterRenderer();
        Renderer justImageRenderer = new JustImageRenderer();
        Renderer justWordRenderer = new JustLetterRenderer();
        mGridViewLeft = (GridView) findViewById(R.id.gridView1);
        mGridViewLeft.setAdapter(new CardViewAdapter(data, this, justImageRenderer));
        mGridViewRight = (GridView) findViewById(R.id.gridView2);
        mGridViewRight.setAdapter(new CardViewAdapter(data, this, justWordRenderer));
        
        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController (mDragController);
        
        mDragLayer.setGridViewLeft(mGridViewLeft);
        mDragLayer.setGridViewRight(mGridViewRight);

        mDragController.setDragListener (mDragLayer);
        mDroppedRenderer = new EOneMatchedRenderer();
        
        
       mAudio = new Audio(this);
       mAudio.loadWordSounds(data);
        
        
        //load fx
        
        
        

        
        
      
        }




	private void loadDataCard() {
		GameDataStructure.getSecuence(1,1, 1);
		GameDataStructure.getSecuence(1,1,2);
		
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
	    
		boolean handledHere = false;
		CardView cardView = (CardView) v;
		final int action = event.getAction();
		
	

		
			
			
	    // In the situation where a long click is not needed to initiate a drag, simply start on the down event.
	    if (action == MotionEvent.ACTION_DOWN) {
//	    	float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//			streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//			 int mStream1= mSoundPool.play(mSoundPoolMap.get(3), streamVolume, streamVolume, 1, 0, 1f);
	    	if (!cardView.isEmptyCard() && cardView.allowDrag()) {
	    		mAudio.playSoundWord(cardView.getCardId());
	    		
	    	}
	    	

			
				
		
	       handledHere = startDrag (v);
	    }
	    
	    return handledHere;
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onLongClick(View v) {
		int a = 1;
		return false;
	}
	
	public boolean startDrag (View v)
	{
	    DragSource dragSource = (DragSource) v;

	    // We are starting a drag. Let the DragController handle it.
	    mDragController.startDrag (v, dragSource, dragSource, DragController.DRAG_ACTION_MOVE);

	    return true;
	}
	
	public Renderer getDefaultRenderer(){
		return mDroppedRenderer;
	}




	@Override
	public void onDragStart(DragSource source, Object info, int dragAction) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onDragEnd(boolean success) {
		if (success) {
		countHits++;
		if (countHits == TOTAL_JOINS) {
			  Toast toast1 =
			            Toast.makeText(getApplicationContext(),
			                    "Secuencia Completa!!!", Toast.LENGTH_SHORT);
			 
			        toast1.show();
			   
			
		}
		}
		
	}

	
	
	
   
   
}

