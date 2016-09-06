package com.amielskywalker.notebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_TITLE = "com.amielskywalker.notebook.NOTE_TITLE";
    public static final String EXTRA_NOTE_ID = "com.amielskywalker.notebook.NOTE_ID";
    public static final String EXTRA_NOTE_MESSAGE = "com.amielskywalker.notebook.NOTE_MESSAGE";
    public static final String EXTRA_NOTE_CATEGORY = "com.amielskywalker.notebook.NOTE_CATEGORY";
    public static final String EXTRA_NOTE_FRAGMENT_TO_LOAD = "com.amielskywalker.notebook.FRAGMENT_TO_LOAD";

    // Create enum for selecting a fragment
    public enum FragmentToLaunch {VIEW, EDIT, CREATE}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load preference settings
        loadPreferences();
    }


    /* Make sures that when a user finished editing or creating a new note and gets sent back to
    * the MainActivity, and pressed back button the application will close.*/
    @Override
    public void onBackPressed() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AppPreferences.class);
            startActivity(intent);
            return false;
        }

        // If action_add_note button was clicked
        else if (id == R.id.action_add_nnote) {

            // Create an intent for launching the Activity for adding notes
            Intent intent = new Intent(this, ShowNoteActivity.class);

            // Send an intent with the extra info for which activity will be launched by the ShowNoteActivity
            intent.putExtra(MainActivity.EXTRA_NOTE_FRAGMENT_TO_LOAD, FragmentToLaunch.CREATE);

            // Start the ShowNoteActivity and pass the intent
            startActivity(intent);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void loadPreferences() {
        // Get a preference from any preference screen from any activity in the application
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Get key from AppPreference layout for theme color * False is default value*
        boolean isBackgroundLight = sharedPreferences.getBoolean("theme_color", false);

        // Change background color according to user
        if(isBackgroundLight) {
            // Main Activity Background
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainActivityLayout);
            mainLayout.setBackgroundResource(R.color.background_color_light);

        }
        else {
            Log.d("DARK_BACKGROUND" , "Background is DARK!");
        }

        // Set the title that the user typed in
        String notebookTitle = sharedPreferences.getString("title" , "Notebook");
        setTitle(notebookTitle);

    }
}// END OF CLASS
















