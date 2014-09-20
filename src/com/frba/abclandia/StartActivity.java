package com.frba.abclandia;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class StartActivity extends Activity {
	private static final int DISPLAY = 3000;
	SharedPreferences prefs = null;
	
	protected void callNextActivity(){
		startActivity(new Intent(this, LoginActivity.class));
	}
	
	protected void onCreate(Bundle paramBundle){
		super.onCreate(paramBundle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_splash);
		// Inicializar servicios
		
		prefs = getSharedPreferences("com.frba.abclandia", MODE_PRIVATE);

		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				StartActivity.this.callNextActivity();
				StartActivity.this.finish();
				
			}
		}, DISPLAY);
	}
	
	@Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
        	
        	// Crear base de datos
        	// Inicializar maestros
        	// Algo mas?
        	Toast.makeText(this, "Primera ejecucion", Toast.LENGTH_LONG).show();
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}


