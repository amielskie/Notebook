package com.amielskywalker.notebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowNoteFragment extends Fragment {


    public ShowNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create a view reference of the layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_show_note, container, false);

        // Get the reference for the layout widgets
        TextView title = (TextView) fragmentLayout.findViewById(R.id.viewNoteTitle);
        TextView message = (TextView) fragmentLayout.findViewById(R.id.viewNoteMessage);
        ImageView icon = (ImageView) fragmentLayout.findViewById(R.id.viewNoteIcon);


        // Get the intent passed from the activity that opened this activity
        Intent intent = getActivity().getIntent();

        // Set the values for the widgets
        title.setText(intent.getExtras().getString(MainActivity.EXTRA_NOTE_TITLE));
        message.setText(intent.getExtras().getString(MainActivity.EXTRA_NOTE_MESSAGE));

        // Get the note icon basaed on the category ** getSerializableExtra is called because we are returning an enum data type**
        Note.Category noteCat = (Note.Category) intent.getSerializableExtra(MainActivity.EXTRA_NOTE_CATEGORY);
        // If there is no value passed to extra
        icon.setImageResource(R.drawable.p);

        // Inflate the layout for this fragment
        return fragmentLayout;
    }

}
