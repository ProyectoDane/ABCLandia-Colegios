package com.example.abclandia;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import com.example.abclandia.audio.Audio;
import com.example.abclandia.graphics.CompleteCardRenderer;
import com.example.abclandia.graphics.EOneMatchedRenderer;
import com.example.abclandia.graphics.JustImageRenderer;
import com.example.abclandia.graphics.JustLetterRenderer;
import com.example.abclandia.graphics.Renderer;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.CardViewAdapter;

public class GameSixActivity extends GameActivity {
	
public static final int TOTAL_JOINS = 6;
	
	
	private DragLayer mDragLayer;
	private GridView mGridViewLeft,mGridViewCenter, mGridViewRight ;

	
	private List<Card> data;
	
	private static String CLASS_NAME = "com.example.abclandia.GameOne";
	private static int GAME_NUMBER = 6;

	

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
        
        
       
        setContentView(R.layout.game_six);
        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController (mDragController);
        mDragController.setDragListener (mDragLayer);
        mDroppedRenderer = new EOneMatchedRenderer(this);
        
        
       mAudio = new Audio(this);
       mAudio.loadWordSounds(data);
        
      
        
        
       
       
  
     
        

  
        
        loadDataCard();
        
       
        
        mGridViewLeft = (GridView) findViewById(R.id.gridViewLeft);
        mGridViewRight = (GridView) findViewById(R.id.gridViewRight);
        mGridViewCenter = (GridView) findViewById(R.id.gridViewCenter);
       
        
        mGridViewRight.setAdapter(new CardViewAdapter(data, this, new JustLetterRenderer(this),R.layout.grid_row));
        mGridViewLeft.setAdapter(new CardViewAdapter(data, this, new JustImageRenderer(this),R.layout.grid_row));
        mGridViewCenter.setAdapter(new CardViewAdapter(data, this, new JustImageRenderer(this),R.layout.grid_row));
        
        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController (mDragController);
        
        mDragLayer.setGridViewLeft(mGridViewLeft);
        mDragLayer.setGridViewCenter(mGridViewCenter);
        mDragLayer.setGridViewRight(mGridViewRight);

        mDragController.setDragListener (mDragLayer);
        mDroppedRenderer = new EOneMatchedRenderer(this);
        
        
       mAudio = new Audio(this);
       mAudio.loadWordSounds(data);
        
        
       
        
      }




	private void loadDataCard() {

		
		
		data = new ArrayList<Card>();
        Card card1 = new Card("A","Auto","/storage/emulated/0/Images/Auto.jpg", "Auto.ogg");
        Card card2 = new Card("B","Botella", "/storage/emulated/0/Images/Botella.jpg","Botella.ogg");
        Card card3 = new Card("C","Conejo","storage/emulated/0/Images/Conejo.jpg", "Conejo.ogg");
    
        data.add(card1);
        data.add(card2);
        data.add(card3);
      
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
