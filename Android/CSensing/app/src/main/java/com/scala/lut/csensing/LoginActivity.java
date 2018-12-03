package com.scala.lut.csensing;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.location.LocationManager;

public class LoginActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkAccess();
    }

    protected void checkAccess(){
        Log.i("access","chk");
        TextView internetCheck = (TextView)findViewById(R.id.internetCheck);
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED &&  manager.isProviderEnabled( LocationManager.GPS_PROVIDER)||  (conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED && manager.isProviderEnabled( LocationManager.GPS_PROVIDER)))) {
            Log.i("Access", "Available");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(LoginActivity.this, WebviewActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();
                }
            }, 1000);
        }else{
            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                Log.i("GPS", "Disabled");
                internetCheck.setText("Please turn on GPS and try again!");
            }else {
                internetCheck.setText("Please Connect to internet and try again!");
                Log.i("Internet ", "Disabled");
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkAccess();
                }
            }, 5000);
        }
    }
}