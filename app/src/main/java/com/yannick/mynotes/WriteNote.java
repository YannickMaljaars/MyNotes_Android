package com.yannick.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class WriteNote extends AppCompatActivity {

    Note thisNote = new Note();
    EditText editTextTitle;
    EditText editTextNote;
    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextNote = findViewById(R.id.editTextNote);

        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", -1);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                thisNote = null;
            } else {
                thisNote = (Note) extras.getSerializable("NOTE");
                noteID = extras.getInt("noteID");
                editTextTitle.setText(thisNote.getTitle());
                editTextNote.setText(thisNote.getNote());
            }
        } else {
            thisNote = new Note();

        }
    }

    public void saveNote(View view) {
        thisNote.setTitle(editTextTitle.getText().toString());
        thisNote.setNote(editTextNote.getText().toString());
        thisNote.setID(noteID);

        Intent i = new Intent(WriteNote.this, MainActivity.class);
        i.putExtra("NOTE", thisNote);
        i.putExtra("noteID", noteID);
        startActivity(i);

    }
}