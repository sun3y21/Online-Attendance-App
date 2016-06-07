package com.nitkkr.sunnny.oasys;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void doSubmit(View view) {
        Toast.makeText(getApplicationContext(),"Hello ",Toast.LENGTH_LONG).show();
           // new ConnectionManager().execute("http://10.0.2.2/android/attendance.php");
             new ConnectionManager().execute("http://www.nitkkrs.comxa.com/attendance.php");
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

    class ConnectionManager extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
           super.onPreExecute();
            p=new ProgressDialog(MainActivity.this);
            p.setMessage("Connecting to Server...");
            p.setProgress(0);
            p.setMax(100);
            p.setIndeterminate(true);
            p.setCancelable(true);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
               String str="hello";
           try {
               Uri.Builder builder=new Uri.Builder();
               EditText editName=(EditText)findViewById(R.id.editName);
               EditText editAge=(EditText)findViewById(R.id.editAge);
               String name=editName.getText().toString();
               String age=editAge.getText().toString();
               builder.appendQueryParameter("name",name);
               builder.appendQueryParameter("age",age);
               JSONObject jObj=JSONParser.makeHttpRequest(strings[0],builder);
               str=jObj.toString();
               }catch (Exception e)
               {
                  System.out.println("###nnn###"+e.getMessage());
               }
            return str;
        }

        protected void onPostExecute(String s)
        {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            p.dismiss();
        }
    }
}