package com.example.a2davaa02.asyncnetwokcommunication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadActivity extends Activity implements View.OnClickListener {

    class MyTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/hits.php?artist="+strings[0]+"&format=json");
                conn = (HttpURLConnection)url.openConnection();

                if(conn.getResponseCode()==200)
                {
                    InputStream in=conn.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(in));
                    String result="",line;
                    while((line=br.readLine())!=null)
                    {
                        result+=line;
                    }
                    JSONArray jsonArray=new JSONArray(result);
                    String ret="";
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject curObj=jsonArray.getJSONObject(i);
                        String name=curObj.getString("song");
                        int year=curObj.getInt("year");
                        ret+="Song name: "+name+" ("+year+")"+"\n";
                    }
                    return ret;
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
            catch (JSONException e)
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
            TextView tv=(TextView)findViewById(R.id.result);
            tv.setText(result);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Button go=(Button)findViewById(R.id.go);
        go.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        EditText artist=(EditText)findViewById(R.id.artist);
        MyTask t=new MyTask();
        t.execute(artist.getText().toString());
    }
}
