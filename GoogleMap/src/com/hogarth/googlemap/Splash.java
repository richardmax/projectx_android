package com.hogarth.googlemap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

	private static final int SPLASH_DISPLAY_LENGTH = 1000;

	private static boolean SPLASH_DISPLAY_SHOWN = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.splash);

		if (!SPLASH_DISPLAY_SHOWN) {
			/*
			 * New Handler to start the Menu-Activity and close this Splash-Screen after some seconds.
			 */
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					SPLASH_DISPLAY_SHOWN = true;
					launchActivity();
				}
			}, SPLASH_DISPLAY_LENGTH);
		} else {
			launchActivity();
		}
	}

	protected void launchActivity() {
		SharedPreferences pref = getSharedPreferences("default", MODE_PRIVATE);
		Intent mainIntent;
		if (pref.contains("loginUser")) {
			mainIntent = new Intent(Splash.this, MainActivity.class);
			mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		} else {
			//we can show login page here.
			//mainIntent = new Intent(Splash.this, Login.class);
			// But for now lets show main Activity
			mainIntent = new Intent(Splash.this, MainActivity.class);
			mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}
		
		Splash.this.startActivity(mainIntent);
		Splash.this.finish();
	}
}