package com.amielskywalker.notebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

    private ImageButton noteCatButton;
    private Note.Category savedButtonCategory;
    private EditText title,message;
    private Button savedButton;
    private AlertDialog categoryDialogObject, confirmDialogObject;

    private static final String MODIFIED_CATEGORY = "Modiefied";

    private boolean newNote = false;
    private long noteId = 0;

    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Fetch the bundle passed by the CREATE switchStatement from the ShowNoteActivity
        Bundle bundle = this.getArguments();

        // If bundle is not null, it means the CREATE switch passed along extra information
        if(bundle != null){
            newNote = bundle.getBoolean(ShowNoteActivity.NEW_NOTE_EXTRA, false);
        }

        /* The values on onSaveInstance method is passed to the bundle of the onCreateView
        * If it's not null, use the value of the savedInstanceState to retrieve saved info*/
        if(savedInstanceState != null){
            savedButtonCategory = (Note.Category) savedInstanceState.get(MODIFIED_CATEGORY);
        }

        // Create a reference for the layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_note_edit, container, false);

        // Get reference for the layout widget
        title = (EditText) fragmentLayout.findViewById(R.id.editNoteTitle);
        message = (EditText) fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCatButton = (ImageButton) fragmentLayout.findViewById(R.id.editNoteButton);
        savedButton = (Button) fragmentLayout.findViewById(R.id.saveNoteButton);

        // Get the intent fromt the parent activity
        Intent intent = getActivity().getIntent();

        // Set the valeus for the widgets
        title.setText(intent.getExtras().getString(MainActivity.EXTRA_NOTE_TITLE, ""));
        message.setText(intent.getExtras().getString(MainActivity.EXTRA_NOTE_MESSAGE, ""));
        noteId = intent.getExtras().getLong(MainActivity.EXTRA_NOTE_ID, 0);
        //
        if(savedButtonCategory != null) {
            // Use the savedButtonCategory if it's not null
            noteCatButton.setImageResource(Note.categoryToDrawable(savedButtonCategory));
        }
        // If newNote is false , fetch the category icon from the editFragment
        else if(!newNote){
            // Get note icon based on category because if it's not null then it means the orientation has changed
            Note.Category noteCat = (Note.Category) intent.getSerializableExtra(MainActivity.EXTRA_NOTE_CATEGORY);
            savedButtonCategory = noteCat;
            noteCatButton.setImageResource(Note.categoryToDrawable(noteCat));
        }


        // Call the dialog builder methods
        buildCategoryDialog();
        buildConfirmDialog();

        // Set an onClickListener to the noteCatButton (For editing the category)
        noteCatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Show the dialog
                categoryDialogObject.show();

            } // END OF ONCLICK
        });


        // Set an onClickListener to the savedButton Button
        savedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // Show the confirm dialog
                confirmDialogObject.show();
            }
        });

        // Inflate the layout for this fragment
        return fragmentLayout;

    } // END OF onCreate

    // Saves all data or information before changing orientation
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        // saves data of modified category to be used if the orientation changes
        savedInstanceState.putSerializable(MODIFIED_CATEGORY, savedButtonCategory);
    }

    // Build a dialog box
    private void buildCategoryDialog() {

        // Create string of optioons for the dialog
        final String[] categories = new String[]{"Personal" , "Anime" , "Finance" , "Movies", "Tech"};

        // Create the category dialog builder using AlertDialog.Builder
        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose Note Type");

        // Adds the categories to the dialog and creates an onClick event *2ND PARAM IS INDEX*
        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                // Dissmiss dialog window
                categoryDialogObject.cancel();

                // Choose what to do on what option was chosen
                switch (item) {
                    case 0:
                        savedButtonCategory = Note.Category.PERSONAL;
                        noteCatButton.setImageResource(R.drawable.p);
                        break;
                    case 1:
                        savedButtonCategory = Note.Category.ANIME;
                        noteCatButton.setImageResource(R.drawable.a);
                        break;
                    case 2:
                        savedButtonCategory = Note.Category.FINANCE;
                        noteCatButton.setImageResource(R.drawable.f);
                        break;
                    case 3:
                        savedButtonCategory = Note.Category.MOVIES;
                        noteCatButton.setImageResource(R.drawable.m);
                        break;
                    case 4:
                        savedButtonCategory = Note.Category.TECH;
                        noteCatButton.setImageResource(R.drawable.t);
                        break;

                }// END OF SWITCH
            }// END OF onClickListener
        });

        // Create the category dialog object
        categoryDialogObject = categoryBuilder.create();

    } // END OF buildCategoryDialog

    // Build the confirm dialog for saving notes
    private void buildConfirmDialog() {

        // Create the confirm builder using AlertDialog.Builder
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
        confirmBuilder.setTitle("Are you sure?");
        confirmBuilder.setMessage("Are you sure you want to save?");

        // Set the button for CONFIRM
        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(" Save Note " , " Note Title: " + title.getText() + " Note Message: " + message.getText()
                        + " Note Category: " + savedButtonCategory );

                // Create a new NoteDBAdapter
                NotebookDBAdapter notebookDBAdapter = new NotebookDBAdapter(getActivity().getBaseContext());

                // Open Database
                notebookDBAdapter.open();

                // If this is a new note, create it and save it to the database
                if(newNote){
                    notebookDBAdapter.createNote(title.getText() + "", message.getText() + "",
                            (savedButtonCategory == null) ? Note.Category.PERSONAL : savedButtonCategory);
                }
                // Otherwise, it's an old note to be updated in the database
                else {
                    notebookDBAdapter.updateNote(noteId, title.getText() + "", message.getText() + "", savedButtonCategory);
                }

                // Close database
                notebookDBAdapter.close();

                // Send the user back to the list of notes (MainActivity)
                Intent intent = new Intent(getActivity(), MainActivity.class);

                // Prevents user from going back to previous editActivity or createNewActivity, user is sent back to the main activity
                getActivity().finish();

                // Send intent
                startActivity(intent);


            }
        });

        // Set the button for CANCEL
        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // DO NOTHING
            }
        });

        confirmDialogObject = confirmBuilder.create();

    }

} // END OF CLASS
