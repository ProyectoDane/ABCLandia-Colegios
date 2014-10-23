package com.frba.abclandia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.abclandia.GameActivity;
import com.example.abclandia.GameFourActivity;
import com.example.abclandia.GameSixActivity;

public class JugarActivity extends Activity implements View.OnClickListener{
	private Button btnEjercicio1, btnEjercicio2, btnEjercicio3,
			btnEjercicio4, btnEjercicio5, btnEjercicio6;
	
	// Definimos las variables para saber que Maestro, Alumno y Categoria estan involucrados. 
	private int unMaestro = 0;
	private int unAlumno = 0;
	private int unaCategoria = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jugar);
		
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




	
	public void btnEjercicio1 (View view){
		
		Intent intent = new Intent(this,NivelesActivity.class );
		intent.putExtra("unMaestro", unMaestro);
		intent.putExtra("unAlumno", unAlumno);
		intent.putExtra("unaCategoria", unaCategoria);
		startActivity(intent);
//		Intent intent = new Intent(this, GameOneActivity.class);
//		intent.putExtra(GameActivity.INTENT_LEVEL_KEY, 1);
//		intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, 1);
//		startActivity(intent);
	
	}
	
	public void btnEjercicio2 (View view){
		Toast.makeText(this, "Ejercicio 2 no implementado", Toast.LENGTH_LONG).show();
	}
	public void btnEjercicio3 (View view){
		Toast.makeText(this, "Ejercicio 3 no implementado", Toast.LENGTH_LONG).show();
	}
	public void btnEjercicio4 (View view){
		Intent intent = new Intent(this, GameFourActivity.class);
		intent.putExtra(GameActivity.INTENT_LEVEL_KEY, 1);
		intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, 1);
		intent.putExtra("unMaestro", unMaestro);
		intent.putExtra("unAlumno", unAlumno);
		intent.putExtra("unaCategoria", unaCategoria);
		startActivity(intent);
	}
	public void btnEjercicio5 (View view){
		Toast.makeText(this, "Ejercicio 5 no implementado", Toast.LENGTH_LONG).show();
	}
	public void btnEjercicio6 (View view){
		Intent intent = new Intent(this, GameSixActivity.class);
		intent.putExtra(GameActivity.INTENT_LEVEL_KEY, 1);
		intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, 1);
		intent.putExtra("unMaestro", unMaestro);
		intent.putExtra("unAlumno", unAlumno);
		intent.putExtra("unaCategoria", unaCategoria);
		startActivity(intent);
	}
	
	private void startNivelesActivity(int exerciseNumber){
		Intent intent = new Intent(this, NivelesActivity.class);
		intent.putExtra(GameActivity.INTENT_EXERCISE_NUMBER, exerciseNumber);
		intent.putExtra("unMaestro", unMaestro);
		intent.putExtra("unAlumno", unAlumno);
		intent.putExtra("unaCategoria", unaCategoria);
		startActivity(intent);
		
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

			startActivity(intent);
			return;
		}
		
		Intent intent = new Intent(this,NivelesActivity.class );
		intent.putExtra(GameActivity.INTENT_EXERCISE_NUMBER, exerciseNumber);
		intent.putExtra("unMaestro", unMaestro);
		intent.putExtra("unAlumno", unAlumno);
		intent.putExtra("unaCategoria", unaCategoria);
		startActivity(intent);
		
		
		
	}
	
}
