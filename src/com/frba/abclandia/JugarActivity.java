package com.frba.abclandia;

import com.example.abclandia.GameActivity;
import com.example.abclandia.GameFourActivity;
import com.example.abclandia.GameOneActivity;
import com.example.abclandia.GameSixActivity;

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
		Intent intent = new Intent(this, GameOneActivity.class);
		intent.putExtra("level", 1);
		intent.putExtra("secuence", 0);
		startActivity(intent);
	
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
		intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, 0);
		startActivity(intent);
	}
	public void btnEjercicio5 (View view){
		Toast.makeText(this, "Ejercicio 5 no implementado", Toast.LENGTH_LONG).show();
	}
	public void btnEjercicio6 (View view){
		Intent intent = new Intent(this, GameSixActivity.class);
		intent.putExtra(GameActivity.INTENT_LEVEL_KEY, 1);
		intent.putExtra(GameActivity.INTENT_SECUENCE_KEY, 0);
		startActivity(intent);
	}
	
}
