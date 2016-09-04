package com.amielskywalker.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

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
            return true;
        }

        // If action_add_note button was clicked
        else if (id == R.id.action_add_nnote) {

            // Create an intent for launching the Activity for adding notes
            Intent intent = new Intent(this, ShowNoteActivity.class);

            // Send an intent with the extra info for which activity will be launched by the ShowNoteActivity
            intent.putExtra(MainActivity.EXTRA_NOTE_FRAGMENT_TO_LOAD, FragmentToLaunch.CREATE);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
