package com.frba.abclandia.webserver;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.frba.abclandia.dtos.Alumno;
import com.frba.abclandia.dtos.Maestro;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ABCLandiaRestClientUsage {
	
	/**
	 * Devuelve todos los Maestros que estan en la base del server.
	 * @return ArrayList<Maestro>
	 * @throws JSONException
	 */
    public ArrayList<Maestro> getMaestros() throws JSONException{
    	final ArrayList<Maestro> maestros = new ArrayList<Maestro>();
	ABCLandiaRestClient.get("index.php/api/maestros", null, new JsonHttpResponseHandler() {
        
    	@Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray
    		try {
				Maestro unMaestro = new Maestro(response.getInt("id"), response.getString("apellido"), response.getString("nombre"));
				maestros.add(unMaestro);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray serverMaestros) {
            // maestros es un array con todos los maestros que devuelve el server.
			try {
				if (serverMaestros != null){
					for (int i=0; i< serverMaestros.length(); i++){
						JSONObject unMaestro =  (JSONObject) serverMaestros.get(i);
						maestros.add(new Maestro(unMaestro.getInt("id"), unMaestro.getString("apellido"), unMaestro.getString("nombre")));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}
    	});
	return maestros;
    }
    /**
     *  Dado el ID de un Maestro recupera del Server un listado de todos los Alumnos de ese maestro.
     * @param unMaestroId
     * @throws JSONException
     */
	public ArrayList<Alumno> getAlumnosFromMaestro(final Integer unMaestroId) throws JSONException {
		final ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		ABCLandiaRestClient.get("index.php/api/maestros/"+ unMaestroId.toString() + "/alumnos", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess (int statusCode, Header[] headers, JSONObject response){
				// Si en lugar de un array nos responde con un unico JSONObject
				try {
					Alumno unAlumno = new Alumno(response.getInt("id"), response.getString("apellido"), response.getString("nombre"), (Integer) unMaestroId);
					alumnos.add(unAlumno);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
			@Override
			public void onSuccess (int statusCode, Header[] headers, JSONArray serverAlumnos) {
				// [{"id":"1","nombre":"Cesar","apellido":"Mendez","pivot":{"maestro_id":"2","alumno_id":"1"}}]
				String alumnos_cant =  "Hay " + serverAlumnos.length() + " ALUMNOS";
				Log.d("Alumnos", alumnos_cant );
				
		
				try {
					if (serverAlumnos != null){
						for (int i = 0; i < serverAlumnos.length(); i++){
							JSONObject unAlumno = (JSONObject) serverAlumnos.get(i);
							alumnos.add(new Alumno(unAlumno.getInt("id"), unAlumno.getString("apellido"), unAlumno.getString("nombre"), unMaestroId));							
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		});
		return alumnos;
	}

	
	
}
