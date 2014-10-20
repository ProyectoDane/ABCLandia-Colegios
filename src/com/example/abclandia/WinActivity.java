package com.example.abclandia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.frba.abclandia.R;

public class WinActivity extends Activity {
	int gameLevel;
	int gameSecuence;
	Class<?> classLauncher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win);

		Button button = (Button) findViewById(R.id.gadorcha);
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
		
			gameLevel = extras.getInt(GameActivity.INTENT_LEVEL_KEY);
			gameSecuence = extras.getInt(GameActivity.INTENT_SECUENCE_KEY);

			try {
				classLauncher = Class.forName(extras
						.getString(GameActivity.INTENT_CLASS_LAUNCHER_KEY));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nextSecuence();
				Intent intent = new Intent(WinActivity.this, classLauncher);

				intent.putExtra(GameActivity.INTENT_LEVEL_KEY, gameLevel);
				intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, gameSecuence);

				startActivity(intent);
				

			}
		});
	}
	
	private void nextSecuence(){
		
		
		if (GameDataStructure.isLevelComplete(1, gameLevel, gameSecuence)){
			gameLevel ++;
			gameSecuence =1;
		}else {
			gameSecuence++;
		}
		
			
			
	}

}
