package com.amielskywalker.notebook;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {

    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create an instance of the NotebookDBAdapter
        NotebookDBAdapter dbAdapter = new NotebookDBAdapter(getActivity().getBaseContext());

        // Open the database before doing anything else
        dbAdapter.open();

        // Get the note from the database and store them to the local notes ArrayList variable
        notes = dbAdapter.getAllNotes();

        // Close database after working with database
        dbAdapter.close();


        noteAdapter = new NoteAdapter(getActivity(), notes);

        setListAdapter(noteAdapter);

        // Set the contextMenu
        registerForContextMenu(getListView());


    }// END OF ON ACTIVITY CREATED

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l,v,position,id);

        // Call the method for launching the ShowNoteActivity
        launchShowNoteActivity(MainActivity.FragmentToLaunch.VIEW, position);

    }

    // Overrde the Contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        // Create a MenuInflater when an item is long pressed
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //Get the position of whatevcer the note is longPressed
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        // Create Note instance for deletion
        Note note = (Note) getListAdapter().getItem(position);

        //Return the id of the menu that was selected
        switch (item.getItemId()) {
            case R.id.edit:
                // Launch the edit note activity
                launchShowNoteActivity(MainActivity.FragmentToLaunch.EDIT, position);

                // Log this to logcate
                Log.d("It Works", "Edit was clicked ");
                return true;
            case R.id.delete:
                // Delete items from database
                NotebookDBAdapter notebookDBAdapter = new NotebookDBAdapter(getActivity().getBaseContext());

                // Open database
                notebookDBAdapter.open();
                notebookDBAdapter.deleteNote(note.getNoteId());

                // Add the updated list after the deletion of a note
                notes.clear();
                notes.addAll(notebookDBAdapter.getAllNotes());
                noteAdapter.notifyDataSetChanged();

                // Close Database
                notebookDBAdapter.close();

                return true;
        }

        return onContextItemSelected(item);
    }

    // Creates an intent and passes the information about the note
    public void launchShowNoteActivity(MainActivity.FragmentToLaunch ftl, int position) {

        // Get the information on whatever the note item was clicked based on it's position
        Note note = (Note) getListAdapter().getItem(position);

        // Create and pass the information on the intent to launch showNoteActvity
        Intent intent = new Intent(getActivity(), ShowNoteActivity.class);
        intent.putExtra(MainActivity.EXTRA_NOTE_TITLE, note.getTitle());
        intent.putExtra(MainActivity.EXTRA_NOTE_ID, note.getNoteId());
        intent.putExtra(MainActivity.EXTRA_NOTE_MESSAGE, note.getMessage());
        intent.putExtra(MainActivity.EXTRA_NOTE_CATEGORY, note.getCategory());

        // Send which fragment to launch
        switch (ftl) {
            case VIEW:
                intent.putExtra(MainActivity.EXTRA_NOTE_FRAGMENT_TO_LOAD, MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.EXTRA_NOTE_FRAGMENT_TO_LOAD, MainActivity.FragmentToLaunch.EDIT);
                break;
        }

        // Start Activity
        startActivity(intent);

    }

}// END OF CLASS
