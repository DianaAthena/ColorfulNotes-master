package hr.athena.colorfulnotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.athena.colorfulnotes.database.NotesDB;
import hr.athena.colorfulnotes.database.NotesDao;
import hr.athena.colorfulnotes.model.Note;

public class EditNoteActivity extends AppCompatActivity {

    private EditText inputNote;
    private EditText inputNoteTitle;
    private NotesDao dao;
    private Note temp;
    public static final String NOTE_EXTRA_Key = "note_id";

    @BindView(R.id.edit_note_activity_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        inputNote = findViewById(R.id.input_note);
        inputNoteTitle = findViewById(R.id.etTitle);
        dao = NotesDB.getInstance(this).notesDao();

        if (getIntent().getExtras() != null) {
            int id = getIntent().getExtras().getInt(NOTE_EXTRA_Key, 0);
            temp = dao.getNoteById(id);
            inputNote.setText(temp.getNoteText());
            inputNoteTitle.setText(temp.getNoteTitle());
        } else inputNote.setFocusable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edite_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.save_note)
            onSaveNote();

        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {

        String text = inputNote.getText().toString();

        String title = inputNoteTitle.getText().toString();

        if (!text.isEmpty()&&!title.isEmpty()) {
            long date = new Date().getTime();

            if (temp == null) {
                temp = new Note(text,title, date);
                dao.insertNote(temp);
            } else {
                temp.setNoteText(text);
                temp.setNoteDate(date);
                dao.updateNote(temp);
            }

            finish();
        }
    }
}