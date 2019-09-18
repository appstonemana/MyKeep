package com.mb.mykeep;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mb.mykeep.adapter.NoteAdapter;
import com.mb.mykeep.helper.DbHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener{

    FloatingActionButton mBtn;
    TextView mTvNoItems;
    RecyclerView mRv;

    DbHelper helper;

    private static int REQ_CODE_ADD = 100;
    private static int REQ_CODE_UPDATE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DbHelper(this);

        mTvNoItems = findViewById(R.id.no_of_items);
        mBtn = findViewById(R.id.fab);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("EXTRA_TYPE", "ADD");
                startActivityForResult(intent, REQ_CODE_ADD);
            }
        });

        mRv = findViewById(R.id.rview);
        mRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        setupAdapter();
    }

    @Override
    public void onUpdate(String  id, String title, String text) {
        Intent updateIntent = new Intent(MainActivity.this, NoteActivity.class);
        updateIntent.putExtra("EXTRA_TYPE", "UPDATE");
        updateIntent.putExtra("id",id);
        updateIntent.putExtra("title",title);
        updateIntent.putExtra("text",text);
        startActivityForResult(updateIntent, REQ_CODE_UPDATE);
        setupAdapter();
    }

    @Override
    public void onDelete(String  id) {
        helper.deleteData(id);
        setupAdapter();
    }


    private void setupAdapter() {
        List<Note> list;
        list = helper.getAllData();

        if (list.size() > 0) {
            mTvNoItems.setVisibility(View.GONE);
            mRv.setVisibility(View.VISIBLE);
            NoteAdapter adapter = new NoteAdapter(MainActivity.this, list);
            adapter.setListener(this);
            mRv.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        } else {
            mTvNoItems.setVisibility(View.VISIBLE);
            mRv.setVisibility(View.GONE);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_ADD && resultCode == RESULT_OK) {
            assert data != null;
            setupAdapter();

        } else if (requestCode == REQ_CODE_UPDATE && resultCode == RESULT_OK) {
            setupAdapter();

        }
    }


}
