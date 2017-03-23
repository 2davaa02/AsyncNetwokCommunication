package com.example.a2davaa02.asyncnetwokcommunication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button downloadButton=(Button)findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(this);
        Button addSongButton=(Button)findViewById(R.id.AddSongButton);
        addSongButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.downloadButton)
        {
            Intent intent = new Intent(this,DownloadActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.AddSongButton)
        {
            Intent intent = new Intent(this,AddSongActivity.class);
            startActivity(intent);
        }
    }
}
