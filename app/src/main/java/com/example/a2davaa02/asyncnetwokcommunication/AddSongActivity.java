package com.example.a2davaa02.asyncnetwokcommunication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddSongActivity extends AppCompatActivity implements View.OnClickListener {

    class MyTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/addhit.php");
                conn = (HttpURLConnection)url.openConnection();

                String postData= "song="+strings[0]+"&artist="+strings[1]+"&year="+strings[2];

                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(postData.length());

                OutputStream out = null;
                out = conn.getOutputStream();
                out.write(postData.getBytes());

                if(conn.getResponseCode()==200)
                {
                    InputStream in=conn.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(in));
                    String result="",line;
                    while((line=br.readLine())!=null)
                    {
                        result+="\n"+line;
                    }
                    return result;
                }
                else
                {
                    return "HTTP ERROR "+conn.getResponseCode();
                }
            }
            catch (IOException e)
            {
                return e.toString();
            }
            finally {
                if(conn!=null)
                    conn.disconnect();
            }
        }

        public void onPostExecute(String result)
        {
            TextView tv=(TextView)findViewById(R.id.addsongresult);
            tv.setText(result);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        Button btn= (Button)findViewById(R.id.addsong);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText song=(EditText)findViewById(R.id.songTitle);
        EditText artist=(EditText)findViewById(R.id.songArtist);
        EditText year=(EditText)findViewById(R.id.songYear);


        MyTask t=new MyTask();
        t.execute(song.getText().toString(),artist.getText().toString(),year.getText().toString());
    }
}
