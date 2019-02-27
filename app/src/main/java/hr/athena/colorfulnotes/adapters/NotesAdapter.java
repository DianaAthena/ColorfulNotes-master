package hr.athena.colorfulnotes.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hr.athena.colorfulnotes.R;
import hr.athena.colorfulnotes.callbacks.NoteEventListener;
import hr.athena.colorfulnotes.model.Note;
import hr.athena.colorfulnotes.utils.NoteUtils;

@SuppressWarnings("NullableProblems")
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {

    private Context context;
    private ArrayList<Note> notes;
    private NoteEventListener listener;
    private boolean multiCheckMode = false;

    public NotesAdapter(Context context, ArrayList<Note> notes) {

        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.note_layout, parent, false);
        return new NoteHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {

        final Note note = getNote(position);

        if (note != null) {
            holder.noteTitle.setText(note.getNoteTitle());
            holder.noteText.setText(note.getNoteText());
            holder.noteDate.setText(NoteUtils.dateFromLong(note.getNoteDate()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onNoteClick(note);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    listener.onNoteLongClick(note);
                    return false;
                }
            });

            if (multiCheckMode) {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.checkBox.setChecked(note.isChecked());
            } else holder.checkBox.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private Note getNote(int position) {
        return notes.get(position);
    }

    public List<Note> getCheckedNotes() {

        List<Note> checkedNotes = new ArrayList<>();

        for (Note n : this.notes) {
            if (n.isChecked())
                checkedNotes.add(n);
        }
        return checkedNotes;
    }

    @SuppressWarnings("WeakerAccess")
    class NoteHolder extends RecyclerView.ViewHolder {

        TextView noteText, noteDate, noteTitle;
        CheckBox checkBox;

        public NoteHolder(View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);
            noteDate = itemView.findViewById(R.id.note_date);
            noteText = itemView.findViewById(R.id.note_text);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }


    public void setListener(NoteEventListener listener) {
        this.listener = listener;
    }

    public void setMultiCheckMode(boolean multiCheckMode) {
        this.multiCheckMode = multiCheckMode;

        if (!multiCheckMode)
            for (Note note : this.notes) {
                note.setChecked(false);
            }
        notifyDataSetChanged();
    }
}
