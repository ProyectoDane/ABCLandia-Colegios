package com.frba.abclandia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class ActividadesActivity extends Activity {
	protected void onCreate(Bundle paramBundle){
	super.onCreate(paramBundle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_actividades);
	}
	
	
	public void btnReproducirAbecedario(View view){
		
		//TODO: Llamar a ReproducirAbecedarioActivity;
		Toast.makeText(this, "Reproducir Abecedario Todavia no implementado", Toast.LENGTH_LONG).show();
		//Intent intent = new Intent(this, ActividadesActivity.class);
		//startActivity(intent);
	}
	
	public void btnReproducirLetra(View view){
		//TODO Llamar a ReproducirLetraActivity
		Toast.makeText(this, "Reproducir Letra Todavia no implementado", Toast.LENGTH_LONG).show();
		//Intent intent = new Intent(this, ActividadesActivity.class);
		//startActivity(intent);
	}
	
	public void btnJugar(View view){
		//TODO: Llamar a JugarActivity
    	Intent intent = new Intent(this, JugarActivity.class);
		startActivity(intent);
	}
}
