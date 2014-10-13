package com.frba.abclandia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity {
	private static final int DISPLAY = 3000;
	
	protected void callNextActivity(){
		// Splash Screen
		startActivity(new Intent(this, LoginActivity.class));
	}
	
	protected void onCreate(Bundle paramBundle){
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				SplashActivity.this.callNextActivity();
				SplashActivity.this.finish();
				
			}
		}, DISPLAY);
	}
}
