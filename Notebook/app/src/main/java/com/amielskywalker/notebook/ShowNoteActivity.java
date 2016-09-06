package com.amielskywalker.notebook;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShowNoteActivity extends AppCompatActivity {

    public static final String NEW_NOTE_EXTRA = "NEW NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        createAndAddFragment();
    }

    @Override
    public void onResume() {
        // After a pause or at startup
        super.onResume();

        // Change transition animation
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
    }

    // Creates and adds fragment to the activity_show_note layout
    private void createAndAddFragment() {

        // Create an intent to get the fragment to load extra
        Intent intent = getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch =
                (MainActivity.FragmentToLaunch) intent.getSerializableExtra(MainActivity.EXTRA_NOTE_FRAGMENT_TO_LOAD);


        // Create FragmentManager to be able to start adding/removing/modifying a particular fragment (showNoteFragment)
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Select which fragment to load
        switch(fragmentToLaunch) {
            case EDIT:
                    // Create instance of ShowNoteFragment
                    NoteEditFragment noteNoteFragment = new NoteEditFragment();

                    // Sets the title of the Activity on the actionBar
                    setTitle(R.string.long_press_menu_edit);

                    /* Adds the created showNoteFragment
                    * First param is the container where the fragment will be displayed (located at : activity_show_note.xml) named note_container
                    * Second param is the fragment that wil be added
                    * Third param Name of the fragment being displayed in case it gets referenced in the future*/
                    fragmentTransaction.add(R.id.note_container, noteNoteFragment, "EDIT_NOTE_FRAGMENT");
                    break;

            case VIEW:
                    // Create instance of ShowNoteFragment
                    ShowNoteFragment showNoteFragment = new ShowNoteFragment();

                    // Sets the title of the Activity on the actionBar
                    setTitle(R.string.show_Note_Title);

                    /* Adds the created showNoteFragment
                    * First param is the container where the fragment will be displayed (located at : activity_show_note.xml) named note_container
                    * Second param is the fragment that wil be added
                    * Third param Name of the fragment being displayed in case it gets referenced in the future*/
                    fragmentTransaction.add(R.id.note_container, showNoteFragment, "SHOW_NOTE_FRAGMENT");
                    break;
            case CREATE:
                    // Use the NoteEditFragment as a layout for adding a new note
                    NoteEditFragment noteCreateFragment = new NoteEditFragment();

                    setTitle(R.string.create_note);

                    // Create a bundle to send information to a fragment
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(NEW_NOTE_EXTRA, true);

                    //Associate the bundle created to the noteCreateFragment
                    noteCreateFragment.setArguments(bundle);

                    fragmentTransaction.add(R.id.note_container, noteCreateFragment, "CREATE_NOTE_FRAGMENT");
                    break;
        }

        // Applies all the thing did to the fragmentTransaction
        fragmentTransaction.commit();

    }
} // END OF CLASS

