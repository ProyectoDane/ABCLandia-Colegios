package com.frba.abclandia;

import com.example.abclandia.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class JugarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jugar);
	}




	
	public void btnEjercicio1 (View view){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	
	}
	
	public void btnEjercicio2 (View view){
		Toast.makeText(this, "Ejercicio 2 no implementado", Toast.LENGTH_LONG).show();
	}
	public void btnEjercicio3 (View view){
		Toast.makeText(this, "Ejercicio 3 no implementado", Toast.LENGTH_LONG).show();
	}
	public void btnEjercicio4 (View view){
		Toast.makeText(this, "Ejercicio 4 no implementado", Toast.LENGTH_LONG).show();
	}
	public void btnEjercicio5 (View view){
		Toast.makeText(this, "Ejercicio 5 no implementado", Toast.LENGTH_LONG).show();
	}
	public void btnEjercicio6 (View view){
		Toast.makeText(this, "Ejercicio 6 no implementado", Toast.LENGTH_LONG).show();
	}
	
}
