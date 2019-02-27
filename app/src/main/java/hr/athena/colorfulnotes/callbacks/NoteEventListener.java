package hr.athena.colorfulnotes.callbacks;

import hr.athena.colorfulnotes.model.Note;

public interface NoteEventListener {

    void onNoteClick(Note note);

    void onNoteLongClick(Note note);
}
