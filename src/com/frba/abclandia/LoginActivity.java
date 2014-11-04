package com.frba.abclandia;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.frba.abclandia.webserver.ABCLandiaRestServer;

public class LoginActivity extends Activity {
	
	enum Answer { YES, NO, ERROR, NULL};
	Answer respuesta = Answer.NULL;
	ABCLandiaRestServer server;
	
	
	protected void onCreate(Bundle paramBundle){
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.login_activity);
		}
	
	public void loginMaestro(View view){
		showMsgDialogMaestro(this);
	}
	
	public void loginPadre (View view){
		// Mostrar mensaje para padre
		// Ok -- > Mostrar niveles
		showMsgDialogPadre(this);	
			
	}
	
	public void loginPadreOk(){
		if (respuesta == Answer.YES){
			//TODO: Llamamos los niveles.
			Toast.makeText(this, "El Padre acepto", Toast.LENGTH_LONG).show();
		}
	}
	
	public void showMsgDialogPadre(final Context self) {
		
		AlertDialog.Builder dlgMensaje = new AlertDialog.Builder(this);
		dlgMensaje.setMessage(R.string.msgPadre);
		dlgMensaje.setTitle(R.string.app_name);
		dlgMensaje.setPositiveButton(R.string.msgAceptar,  new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	    		//TODO: Usuario acepta el mensaje
	        	respuesta = Answer.YES;
	        	Intent intent = new Intent(self, ActividadesActivity.class);
	    		//TODO Pasamos datos si hay que pasarlos.
	    		startActivity(intent);
	          }
	      });
		dlgMensaje.setCancelable(true);
		dlgMensaje.create().show();
		
	}
	
	public void showMsgDialogMaestro(final Context self) {
		
		AlertDialog.Builder dlgMensaje = new AlertDialog.Builder(this);
		dlgMensaje.setMessage(R.string.msgMaestro);
		dlgMensaje.setTitle(R.string.app_name);
		dlgMensaje.setPositiveButton(R.string.msgAceptar,  new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	respuesta = Answer.YES;
	        	Intent intent = new Intent(self, MaestroListActivity.class);
	    		startActivity(intent);
	          }
	      });
		dlgMensaje.setCancelable(true);
		dlgMensaje.create().show();
		
	}
	

}
