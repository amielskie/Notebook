package com.amielskywalker.notebook;

/**
 * Created by Amiel Skywalker on 9/2/2016.
 */
public class Note {

    private String title, message;
    private long noteId, dateCreatedMilli;
    private Category category;

    public Note(String title, String message, Category category) {
        this.title = title;
        this.message = message;
        this.category = category;
        this.noteId = 0;
        this.dateCreatedMilli = 0;
    }// END OF CONSTRUCTOR

    public Note(String title, String message, Category category, long noteId, long dateCreatedMilli) {
        this.title = title;
        this.message = message;
        this.category = category;
        this.noteId = noteId;
        this.dateCreatedMilli = dateCreatedMilli;
    }// END OF CONSTRUCTOR

    public enum Category {
        ANIME, MOVIES, FINANCE, PERSONAL, TECH
    } // END OF Category

    // Getter methods
    public String getTitle(){
        return title;
    }

    public String getMessage(){
        return message;
    }

    public Category getCategory(){
        return category;
    }

    public long getNoteId() {
        return noteId;
    }

    public long getDateCreatedMilli() {
        return dateCreatedMilli;
    }

    public String toString() {
        return "ID: " + noteId + " Title: " + title + " Message: "
                + message + " IconID: " + category.name() + " Date: ";
    }

    public int getAssociatedDrawable(){
        return categoryToDrawable(category);
    }

    // Get drawable of a category
    public static int categoryToDrawable(Category noteCategory){
        switch(noteCategory){
            case ANIME:
                return R.drawable.a;
            case FINANCE:
                return R.drawable.f;
            case MOVIES:
                return R.drawable.m;
            case PERSONAL:
                return R.drawable.p;
            case TECH:
                return R.drawable.t;
        }// END OF SWITCH

        return R.drawable.p;
    }// END OF categoryToDrawable
}
