package com.hhu.cshelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hhu.cshelper.R;
import com.hhu.cshelper.adapter.MainPageAdapter;
import com.hhu.cshelper.adapter.OnItemClickListener;
import com.hhu.cshelper.database.NoteDatabaseHelper;
import com.hhu.cshelper.entity.Note;

import java.util.List;

/**  搜索页
 * @name  SearchActivity
 * @description
 * @author  dyh
 * @date  2020/5/31
 */
public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchResult;
    private View emptyResult;
    private SearchView searchView;
    private MainPageAdapter mainPageAdapter;
    private androidx.appcompat.widget.Toolbar searchToolbar;
    protected List<Note> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        searchToolbar = findViewById(R.id.search_toolbar);
//        setSupportActionBar(searchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.search_title);
        init();
    }

    private void init() {
        searchResult = findViewById(R.id.search_result);
        emptyResult = findViewById(R.id.empty_result);
        searchResult.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        initSearchView(menu);
        return true;
    }

    private void initSearchView(Menu menu) {
        searchView = (SearchView) menu.findItem(R.id.menu_search_view).getActionView();
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchAndShowResult(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void searchAndShowResult(String s) {
        List<Note> notes = NoteDatabaseHelper.getInstance().getNotesByPattern(s);
        if (notes == null || notes.size() == 0) {
            searchResult.setVisibility(View.INVISIBLE);
            emptyResult.setVisibility(View.VISIBLE);
        } else {
            emptyResult.setVisibility(View.INVISIBLE);
            searchResult.setVisibility(View.VISIBLE);
            mainPageAdapter = new MainPageAdapter(this, notes);
            mainPageAdapter.setIsGridMode(false);
            searchResult.setAdapter(mainPageAdapter);
            mainPageAdapter.notifyDataSetChanged();

            mainPageAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    mNotes = NoteDatabaseHelper.getInstance().getAllNotes();
                    Note note = mNotes.get(position);
                    Intent intent = new Intent(SearchActivity.this, NoteDetailActivity.class);
                    intent.putExtra("note_item", note  );
                    intent.putExtra("pos", position);
                    intent.putExtra("id" , note.getId());
                    startActivity(intent);
                    //Toast.makeText(SearchActivity.this , " aaa" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: finish(); break;
        }
        return super.onOptionsItemSelected(item);
    }
}
