package com.example.gpsdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

public class LocationSettingsDialogFragment extends DialogFragment {
    private CheckBox checkbox;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View neverShow = inflater.inflate(R.layout.never_show, null);
        checkbox = (CheckBox) neverShow.findViewById(R.id.checkbox);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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