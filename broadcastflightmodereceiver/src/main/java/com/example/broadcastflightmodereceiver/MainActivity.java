package com.example.broadcastflightmodereceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// EVT. arve fra Activity
public class MainActivity extends Activity {
	private MyReceiver myReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		super.onStart();
		myReceiver = new MyReceiver();

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		this.registerReceiver(myReceiver, filter);

	}

	@Override
	protected void onStop() {
		super.onStop();

		if (myReceiver!=null)
			this.unregisterReceiver(myReceiver);
	}
}
