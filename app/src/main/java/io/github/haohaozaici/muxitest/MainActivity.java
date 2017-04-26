package io.github.haohaozaici.muxitest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.query.Query;

public class MainActivity extends AppCompatActivity {

    private NoteDao mNoteDao;
    private Query<Note> mNoteQuery;
    private Adapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //database
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        mNoteDao = daoSession.getNoteDao();

        //setup view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter(this);
        mRecyclerView.setAdapter(mAdapter);

        //noteList
        Setting setting = new Setting(this);
        setting.loadSetting();
        if (setting.isFirstRun()) {
            for (int i = 0; i < 6; i++) {
                Note note = new Note();
                note.setIsChecked(false);
                note.setContent("List " + i);
                note.setMDate(new Date());
                mNoteDao.insert(note);
            }
            setting.setFirstRun(false);
            setting.saveSetting();
            updateNotes();
        }

        // mNoteQuery = mNoteDao.queryBuilder().build();
        updateNotes();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note();
                note.setIsChecked(false);
                note.setContent("");
                note.setMDate(new Date());
                mNoteDao.insert(note);

                Log.d("DaoExample", "Inserted new note, ID: " + note.getId());

                updateNotes();

                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //     .setAction("Action", null).show();
            }
        });
    }


    public void updateNote(Note note) {
        mNoteDao.update(note);
    }


    public void deleteNote(Note note) {
        mNoteDao.delete(note);
    }


    public void updateNotes() {
        List<Note> notes = mNoteDao.loadAll();
        mAdapter.setItem(notes);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
