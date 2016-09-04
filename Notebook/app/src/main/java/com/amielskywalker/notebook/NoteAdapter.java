package com.amielskywalker.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Amiel Skywalker on 9/3/2016.
 */
public class NoteAdapter extends ArrayAdapter<Note>{

    // Create inner class for a viewholder
    public static class ViewHolder{
        TextView title;
        TextView message;
        ImageView noteIcon;
    }


    public NoteAdapter(Context context, ArrayList<Note> notes) {
        super(context, 0 , notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
        Note note = getItem(position);

       // Initialize ViewHolder
        ViewHolder viewHolder;
       // Check if an existing view is being reused, otherwise inflate anew view from custom row layout
        if(convertView == null){
            /* If there is no view that is being used, create one. Create a viewHolder with the view
             * to save the references to. */
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_list_fragment, parent, false);

            /* Set View to the view holder to avoid going back and using findViewById (which is resource intensive)
             * every time a new row is created. */
            viewHolder.title = (TextView) convertView.findViewById(R.id.listItemNoteTitle);
            viewHolder.message = (TextView) convertView.findViewById(R.id.listItemNoteMessage);
            viewHolder.noteIcon = (ImageView) convertView.findViewById(R.id.listItemNoteImg);

            // User set tag to remember the view holder that is holding the references to the widgets.
            convertView.setTag(viewHolder);

        }// END OF if
        else {
            // If a view already exists , go to viewHolder and grab the widgets from it.
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set values for the views
        viewHolder.title.setText(note.getTitle());
        viewHolder.message.setText(note.getMessage());
        viewHolder.noteIcon.setImageResource(note.getAssociatedDrawable());

        return convertView;

    }// END OF CONSTRUCTOR

}
