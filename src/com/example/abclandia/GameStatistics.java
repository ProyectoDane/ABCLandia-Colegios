package com.example.abclandia;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.database.SQLException;
import android.util.Log;

import com.frba.abclandia.db.DataBaseHelper;
import com.frba.abclandia.dtos.Estadistica;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GameStatistics {
	private GameActivity mGame;
	private long mStartTime, mElapsedTime;
	private int failsCount, hitsCount;
	private DataBaseHelper myDbHelper;
	
	public GameStatistics(GameActivity gameActivity){
		mGame = gameActivity;
		mStartTime = System.currentTimeMillis();
		iniciarDB();
		
	}
	 public void countHit(){
		 hitsCount++;
	}
	 
	public void countFail(){
		 failsCount++;
	}
	//Aca tenes que hacer el insert en la BD. Fijate que hice un par de metodos get en GameActivity
	// para obtener categoria, alumno, etc. Fijate que esta la variable mGame para pedirle los datos
	public void saveStatistics(){
		
		
		mElapsedTime = System.currentTimeMillis() - mStartTime;
		JSONObject jsonEstadisticas = new JSONObject();
//        alumno_id,maestro_id,categoria_id,letra,ejercicio,nivel,secuencia,tiempo,cantidad_aciertos,cantidad_fallas
		try {
			jsonEstadisticas.put("alumno_id", mGame.getUnAlumno());
			jsonEstadisticas.put("maestro_id", mGame.getUnMaestro());
			jsonEstadisticas.put("categoria_id", mGame.getUnaCategoria());
			jsonEstadisticas.put("ejercicio", mGame.getmGameNumber());
			jsonEstadisticas.put("nivel", mGame.getNivel());
			jsonEstadisticas.put("secuencia", mGame.getSecuence());
			jsonEstadisticas.put("tiempo", mElapsedTime);
			jsonEstadisticas.put("cantidad_aciertos", hitsCount);
			jsonEstadisticas.put("cantidad_fallas", failsCount);
			jsonEstadisticas.put("timestamp", getCurrentTimeStamp());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postStats(jsonEstadisticas);
		Estadistica unaEstadistica =  new Estadistica(jsonEstadisticas);
		myDbHelper.insertEstadistica(unaEstadistica);
	}
	
	/**
	 * 
	 * @return yyyy-MM-dd HH:mm:ss formate date as string
	 */
	public static String getCurrentTimeStamp(){
	    try {

	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

	        return currentTimeStamp;
	    } catch (Exception e) {
	        e.printStackTrace();

	        return null;
	    }
	}
	
	
	private void postStats(JSONObject jsonEstadisticas) {
		// TODO Auto-generated method stub
		
		AsyncHttpClient client  = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("estadisticas", jsonEstadisticas.toString());
		Log.d("PARAM", params.toString());
		client.post("http://104.200.20.108/abclandia/public/index.php/api/estadisticas", params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				Log.d("Post", "stats enviadas");
				Log.d("Post",response);
				
			}

			@Override
			public void onFailure(int statusCode, Throwable error, String content) {
					Log.d("Error Occured", "No sincronizamos: " + statusCode);
			}
		});

	}
	private void iniciarDB() {
		// Inicializar servicios
		myDbHelper = new DataBaseHelper(mGame.getApplicationContext());
		try {
			myDbHelper.createDatabase();
		} catch (IOException ioe) {
			throw new Error("No se pudo crear la base de datos");
			
		}
		
		try {
			myDbHelper.openDatabase();
		}catch (SQLException sqle){
			Log.d("DB", "No se pudo abrir la BD");
			throw sqle;
		}
		
	}

}
