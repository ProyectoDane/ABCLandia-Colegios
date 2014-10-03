package com.frba.abclandia;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.dtos.Alumno;
import com.frba.abclandia.dtos.Maestro;


public class StartActivity extends Activity {
	
	private static final int DISPLAY = 3000;
	SharedPreferences prefs = null;
	DataBaseHelper myDbHelper;
	
	
	protected void callNextActivity(){
		startActivity(new Intent(this, LoginActivity.class));
	}
	
	protected void onCreate(Bundle paramBundle){
		super.onCreate(paramBundle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_splash);
		
		
		//Iniciamos la base de datos
		iniciarDB();
		
		prefs = getSharedPreferences("com.frba.abclandia", MODE_PRIVATE);

		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				StartActivity.this.callNextActivity();
				StartActivity.this.finish();
				
			}
		}, DISPLAY);
	}
	
	private void iniciarDB() {
		// Inicializar servicios
		myDbHelper = new DataBaseHelper(this);
		try {
			myDbHelper.createDatabase();
		} catch (IOException ioe) {
			throw new Error("No se pudo crear la base de datos");
			
		}
		
		try {
			myDbHelper.openDatabase();
		}catch (SQLException sqle){
			Log.d("POOCHIE", "No se pudo abrir la BD");
			throw sqle;
		}
		
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
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    if (myDbHelper != null) {
	    	myDbHelper.close();
	    }
	}
}


