package com.mb.mykeep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mb.mykeep.helper.DbHelper;

public class NoteActivity extends AppCompatActivity {

    DbHelper helper;
    private Button mIbtn;
    private EditText mEttitle, mEttext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        String intentType = getIntent().getStringExtra("EXTRA_TYPE");


        mIbtn = findViewById(R.id.badd);
        mEttitle = findViewById(R.id.et_title);
        mEttext = findViewById(R.id.et_text);

        if (intentType.equals("ADD")) {

            mIbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = mEttitle.getText().toString().trim();
                    String text = mEttext.getText().toString().trim();
                    savetoDb(title, text);


                }
            });
        } else if (intentType.equals("UPDATE")) {

            mIbtn.setText("UPDATE");
            final String id = getIntent().getStringExtra("id");
            String title = getIntent().getStringExtra("title");
            String text = getIntent().getStringExtra("text");

            mEttitle.setText(title);
            mEttext.setText(text);
            mIbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = mEttitle.getText().toString().trim();
                    String text = mEttext.getText().toString().trim();
                    updateDb(id, title, text);

                }
            });


        }

    }

    private void updateDb(String id, String title, String text) {
        helper = new DbHelper(NoteActivity.this);
        helper.updateData(id, title, text);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }


    private void savetoDb(String title, String text) {
        helper = new DbHelper(NoteActivity.this);
        helper.insertData(title, text);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }
}
