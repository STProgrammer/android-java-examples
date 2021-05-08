package com.example.broadcastflightmodereceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		boolean isOn = extras.getBoolean("state");
		if (isOn) {
			Toast.makeText(context, "Flightmode PÅ!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "Flightmode AV!", Toast.LENGTH_SHORT).show();
		}
	}
}
