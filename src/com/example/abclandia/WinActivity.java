package com.example.abclandia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.frba.abclandia.R;

public class WinActivity extends Activity {
	int gameLevel;
	int gameSecuence;
	Class<?> classLauncher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		WindowManager mWindowManager = (WindowManager) getSystemService("window");
		
		setContentView(R.layout.win_activity);

		ImageButton button = (ImageButton) findViewById(R.id.gadorcha);
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
