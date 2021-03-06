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
	int gameNumber;
	int gameLevel;
	int gameSecuence;
	Class<?> classLauncher;
	// Definimos las variables para saber que Maestro, Alumno y Categoria estan involucrados. 
	protected int unMaestro = 0;
	protected int unAlumno = 0;
	protected int unaCategoria = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		WindowManager mWindowManager = (WindowManager) getSystemService("window");
		
		setContentView(R.layout.win_activity);
		
		getExtraData();

		ImageButton btnJugar = (ImageButton) findViewById(R.id.btnJugar);
		btnJugar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nextSecuence();
				Intent intent = new Intent(WinActivity.this, classLauncher);

				intent.putExtra(GameActivity.INTENT_LEVEL_KEY, gameLevel);
				intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, gameSecuence);
				intent.putExtra("unMaestro", unMaestro);
				intent.putExtra("unAlumno", unAlumno);
				intent.putExtra("unaCategoria", unaCategoria);

				startActivity(intent);
				finish();
				

			}
		});
	}
	
	protected void getExtraData() {
		Bundle extras = getIntent().getExtras();
	
		if (extras != null) {
			unMaestro = extras.getInt("unMaestro", 0);
			unAlumno = extras.getInt("unAlumno", 0);
			unaCategoria = extras.getInt("unaCategoria", 0);
			gameNumber = extras.getInt(GameActivity.INTENT_EXERCISE_NUMBER, 1);
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

	}
	
	private void nextSecuence(){
		if (GameDataStructure.isLevelComplete(gameNumber, gameLevel, gameSecuence)){
			gameLevel ++;
			gameSecuence =1;
		}else {
			gameSecuence++;
		}
	}

}
