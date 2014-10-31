package com.frba.abclandia;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.abclandia.GameActivity;
import com.example.abclandia.GameDataStructure;
import com.example.abclandia.GameSixActivity;
import com.frba.abclandia.NivelesDialogFragment.DialogLevelListener;

public class JugarActivity extends Activity 
		implements View.OnClickListener, DialogLevelListener{
	private Button btnEjercicio1, btnEjercicio2, btnEjercicio3,
			btnEjercicio4, btnEjercicio5, btnEjercicio6;
	
	// Definimos las variables para saber que Maestro, Alumno y Categoria estan involucrados. 
	private int unMaestro = 0;
	private int unAlumno = 0;
	private int unaCategoria = 0;
	
	private Class<?> exerciseClass;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.ejercicios_activity);
		
		// Recuperamos los valores de Maestro, Alumno y Categoria
		Intent i = getIntent();
		this.unMaestro = i.getIntExtra("unMaestro", 0);
		this.unAlumno = i.getIntExtra("unAlumno", 0);
		this.unaCategoria = i.getIntExtra("unaCategoria", 0);
		
		btnEjercicio1 = (Button) findViewById(R.id.btnEjercicio1);
		btnEjercicio2 = (Button) findViewById(R.id.btnEjercicio2);
		btnEjercicio3 = (Button) findViewById(R.id.btnEjercicio3);
		btnEjercicio4 = (Button) findViewById(R.id.btnEjercicio4);
		btnEjercicio5 = (Button) findViewById(R.id.btnEjercicio5);
		btnEjercicio6 = (Button) findViewById(R.id.btnEjercicio6);
		
		btnEjercicio1.setOnClickListener(this);
		btnEjercicio2.setOnClickListener(this);
		btnEjercicio3.setOnClickListener(this);
		btnEjercicio4.setOnClickListener(this);
		btnEjercicio5.setOnClickListener(this);
		btnEjercicio6.setOnClickListener(this);
	}




	
	


	@Override
	public void onClick(View v) {
		int exerciseNumber = 0;
		if (v == btnEjercicio1){
			exerciseNumber = 1;
		}
		if (v == btnEjercicio2){
			exerciseNumber = 2;
		}
		if (v == btnEjercicio3){
			exerciseNumber = 3;
		}
		if (v == btnEjercicio4){
			exerciseNumber = 4;
		}
		if (v == btnEjercicio5){
			exerciseNumber = 5;
		}
		if (v == btnEjercicio6){
			Intent intent = new Intent(this, GameSixActivity.class);
			intent.putExtra(GameActivity.INTENT_LEVEL_KEY, 1);
			intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, 1);
			intent.putExtra("unMaestro", unMaestro);
			intent.putExtra("unAlumno", unAlumno);
			intent.putExtra("unaCategoria", unaCategoria);

			startActivity(intent);
			return;
			
		}
		exerciseClass = GameDataStructure.getExerciseClass(exerciseNumber);
		

		
		FragmentManager fm = getFragmentManager();
        NivelesDialogFragment editNameDialog = new NivelesDialogFragment();
        editNameDialog.show(fm, "fragment_edit_name");
		
		
		
	}





	@Override
	public void onChooseLevel(int levelNumber) {
		Intent intent = new Intent(this, exerciseClass);
		intent.putExtra(GameActivity.INTENT_LEVEL_KEY, levelNumber);
		intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, 1);
		intent.putExtra("unMaestro", this.unMaestro);
		intent.putExtra("unAlumno", this.unAlumno);
		intent.putExtra("unaCategoria", this.unaCategoria);
		startActivity(intent);
		
	}
	
}
