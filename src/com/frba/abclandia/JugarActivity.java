package com.frba.abclandia;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class JugarActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jugar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// TODO: Decidir si vamos a  usar menu o no.
		getMenuInflater().inflate(R.menu.jugar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO: Seguramente tendremos que sacarlo
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void btnEjercicio1 (View view){
		Toast.makeText(this, "Ejercicio 1 no implementado", Toast.LENGTH_LONG).show();
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
