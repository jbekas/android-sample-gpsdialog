package com.example.gpsdialog;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends Activity {

    protected CheckBox checkbox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.e("MainActivity", ex.getMessage());
        }
        
        if (! gps_enabled) {
            Dialog dialog = createDialog();
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public Dialog createDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View neverShow = inflater.inflate(R.layout.never_show, null);
        checkbox = (CheckBox) neverShow.findViewById(R.id.checkbox);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(neverShow)
               .setTitle(R.string.location_settings_title)
               .setMessage(R.string.location_instructions)
               .setPositiveButton(R.string.location_settings, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       if (checkbox.isChecked()) {
                           doNotShowAgain();
                       }
                       Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                       startActivity(myIntent);
                   }
               })
               .setNegativeButton(R.string.location_skip, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                       if (checkbox.isChecked()) {
                           doNotShowAgain();
                       }
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    
    private void doNotShowAgain() {
        // persist shared preference to prevent dialog from showing again.
        Log.d("MainActivity", "TODO: Persist shared preferences.");
    }

}
