package com.example.digifestmethod.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.digifestmethod.Fragment.ScreenShare;
import com.example.digifestmethod.R;

public class QueryTaken extends AppCompatActivity {

    FrameLayout frameLayout;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_taken);

        frameLayout= (FrameLayout) findViewById(R.id.fram);

        btn= (Button)findViewById(R.id.button);
        final String qid = getIntent().getStringExtra("id");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(QueryTaken.this, FeedbackActivity.class);
                i.putExtra("queryId",qid);
                startActivity(i);


            }
        });
        ScreenShare screenShare= new ScreenShare();

        getSupportFragmentManager().beginTransaction().replace(R.id.fram, screenShare).commit();








    }
}
