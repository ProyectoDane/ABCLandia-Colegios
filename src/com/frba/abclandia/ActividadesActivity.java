package com.frba.abclandia;

import com.example.abclandia.AbcPlayerActivity;
import com.example.abclandia.CardLetterPlayerActivity;

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
		
		Intent intent = new Intent(this,AbcPlayerActivity.class );
		startActivity(intent);
	}
	
	public void btnReproducirLetra(View view){
		
		Intent intent = new Intent(this,CardLetterPlayerActivity.class );
		startActivity(intent);
	}
	
	public void btnJugar(View view){
		//TODO: Llamar a JugarActivity
    	Intent intent = new Intent(this, JugarActivity.class);
		startActivity(intent);
	}
}
