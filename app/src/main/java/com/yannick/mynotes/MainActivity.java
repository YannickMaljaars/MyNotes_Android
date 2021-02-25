package com.yannick.mynotes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Note> notes = new ArrayList<>();;
    ListView notesListView;
    ArrayAdapter<Note> noteArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If there are no notes yet, create example note
        if (notes.size() == 0) {
            createExampleNote(notes);
        }

        notesListView = findViewById(R.id.notesListView);

        noteArrayAdapter = new ArrayAdapter<>(this, R.layout.list_layout, notes);

        notesListView.setAdapter(noteArrayAdapter);

        addSavedNote(savedInstanceState);

            //On item click in list, go to edit page for clicked item
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), WriteNote.class);
                Note note = (Note) parent.getItemAtPosition(position);

                intent.putExtra("noteID", position);
                intent.putExtra("NOTE", note);
                startActivity(intent);
            }
        });


                //On item long click in list, delete item
        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Remove Item")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                noteArrayAdapter.notifyDataSetChanged();
                            }
                        }
                        ).setNegativeButton("No", null)
                        .show();

                return true;

            }
        });

    }


        //add the saved note or update in list
    private void addSavedNote(Bundle savedInstanceState) {
        Note newNote;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newNote= null;
            } else {
                newNote = (Note) extras.getSerializable("NOTE");
                int noteID = extras.getInt("noteID");

                //If new note: add to list, if existing note: update in list
                if(noteID < notes.size()) {
                    notes.set(noteID, newNote);

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.yannick.mynotes", Context.MODE_PRIVATE);

                } else {
                    notes.add(newNote);
                }

            }
        } else {
            newNote = (Note) savedInstanceState.getSerializable("NOTE");
            notes.add(newNote);
        }
    }

    private void createExampleNote(ArrayList<Note> notes) {
        Note exampleNote = new Note();
        exampleNote.setTitle("Example note, delete by long pressing");
        exampleNote.setNote("This is an example note, on this screen you can edit your notes!");
        exampleNote.setID(0);
        notes.add(exampleNote);
    }

    public void addNote(View view){
        Intent intent = new Intent(this, WriteNote.class);
        Note emptyNote = new Note();
        intent.putExtra("noteID", notes.size());
        intent.putExtra("NOTE", emptyNote);
        startActivity(intent);
    }
}