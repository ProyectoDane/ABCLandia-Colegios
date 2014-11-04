package com.frba.abclandia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.frba.abclandia.db.DataBaseHelper;


public class MainActivity extends Activity {
	private static final String PREFERENCE_NAME = "com.frba.abclandia";
	private static final int DISPLAY = 3000;
	private DataBaseHelper myDbHelper;
	SharedPreferences preferences = null;
	String TARGET_BASE_PATH;
	
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

		setPreferences();
		
		int density= getResources().getDisplayMetrics().densityDpi;
		   switch(density)
		  {
		  case DisplayMetrics.DENSITY_LOW:
		     Toast.makeText(this, "LDPI", Toast.LENGTH_SHORT).show();
		      break;
		  case DisplayMetrics.DENSITY_MEDIUM:
		       Toast.makeText(this, "MDPI", Toast.LENGTH_SHORT).show();
		      break;
		  case DisplayMetrics.DENSITY_HIGH:
		      Toast.makeText(this, "HDPI", Toast.LENGTH_SHORT).show();
		      break;
		  case DisplayMetrics.DENSITY_XHIGH:
		       Toast.makeText(this, "XHDPI", Toast.LENGTH_SHORT).show();
		      break;
		  }
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				MainActivity.this.callNextActivity();
				MainActivity.this.finish();
				
			}
		}, DISPLAY);
		

	}

	protected void setPreferences() {
		preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("letter_type", 1);
		editor.commit();
	}
	
	/**
	 * Iniciamos la base de datos
	 * Esto pasa porque no creamos un Singleton
	 */
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
			Log.d("ABCLandia", "No se pudo abrir la BD");
			throw sqle;
		}
	}
	

	@Override
    protected void onResume() {
        super.onResume();
        // TODO: Acomodar bien y cambiar todo a getExternalFilesDir(null).toString()
        // TARGET_BASE_PATH = getExternalFilesDir(null).toString() + "/";
        //TARGET_BASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
        TARGET_BASE_PATH =  getFilesDir() + "/";

        

        if (preferences.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs

        	preferences.edit().putBoolean("firstrun", false).commit();
        	copyFileOrDir("sonidos");
            copyFileOrDir("imagenes");

             	
        	Log.d("ABCLandia", "Primer Ejecucion");   
        }
    }
	

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    if (myDbHelper != null) {
	    	myDbHelper.close();
	    }
	}
	
	private void copyFileOrDir(String path) {
	    AssetManager assetManager = this.getAssets();
	    String assets[] = null;
	    try {
	        Log.i("tag", "copyFileOrDir() "+path);
	        assets = assetManager.list(path);
	        if (assets.length == 0) {
	            copyFile(path);
	        } else {
	            String fullPath =  TARGET_BASE_PATH + path;
	            Log.i("tag", "path="+fullPath);
	            File dir = new File(fullPath);
	            if (!dir.exists() )
	                if (!dir.mkdirs())
	                    Log.i("tag", "could not create dir "+fullPath);
	            for (int i = 0; i < assets.length; ++i) {
	                String p;
	                if (path.equals(""))
	                    p = "";
	                else 
	                    p = path + "/";

	               
	                    copyFileOrDir( p + assets[i]);
	            }
	        }
	    } catch (IOException ex) {
	        Log.e("tag", "I/O Exception", ex);
	    }
	}

	private void copyFile(String filename) {
	    AssetManager assetManager = this.getAssets();

	    InputStream in = null;
	    OutputStream out = null;
	    String newFileName = null;
	    try {
	        Log.i("tag", "copyFile() "+filename);
	        in = assetManager.open(filename);
	        
	            newFileName = TARGET_BASE_PATH + filename;
	        out = new FileOutputStream(newFileName);

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();
	        in = null;
	        out.flush();
	        out.close();
	        out = null;
	    } catch (Exception e) {
	        Log.e("tag", "Exception in copyFile() of "+newFileName);
	        Log.e("tag", "Exception in copyFilee() "+e.toString());
	    }
	}
	

}


