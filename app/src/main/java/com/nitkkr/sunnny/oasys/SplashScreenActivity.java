package com.nitkkr.sunnny.oasys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SplashScreenActivity extends ActionBarActivity {



    ProgressBar p;
    JSONObject json=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        p=(ProgressBar)findViewById(R.id.progressBar);
        p.setProgress(1);
        new InternetManager().execute("http://www.nitkkrs.comxa.com/getAllRollNo.php");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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
  class InternetManager extends AsyncTask<String,String,String>
  {
       protected void onPreExecute()
       {
           super.onPreExecute();

       }

      @Override
      protected String doInBackground(String... strings) {

          ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
          NetworkInfo netInfo=connectivityManager.getActiveNetworkInfo();
          if(netInfo!=null&&netInfo.isConnected())
          {
              TextView e=(TextView)findViewById(R.id.textView2);
              e.setText("Loading...");
              e.setVisibility(View.VISIBLE);
              json=JSONParser.makeHttpRequest(strings[0],null);
              if(json==null)
                  return "no";
          }
          else
          {
              TextView e=(TextView)findViewById(R.id.textView2);
              e.setVisibility(View.VISIBLE);
              return "no";
          }
          return json.toString();
      }

      protected void onPostExecute(String result)
      {
          super.onPostExecute(result);

          if(!result.equalsIgnoreCase("no"))
          {
              Intent i = new Intent(getApplicationContext(), MainActivity.class);
              ArrayList<String> rollNumbers=new ArrayList<>();
              ArrayList<String> names=new ArrayList<>();
             try
             {

                 JSONArray jsonArray=json.getJSONArray("rollNumbers");
                 for(int j=0;j<jsonArray.length();j++)
                 {
                     JSONObject student=jsonArray.getJSONObject(j);
                     String name=student.getString("name");
                     String roll=student.getString("roll");
                     rollNumbers.add(roll);
                     names.add(name);
                 }
                 int sec1=json.getInt("sec1");
                 int sec2=json.getInt("sec2");
                 int sec3=json.getInt("sec3");



                 Bundle bundle=new Bundle();
                 bundle.putStringArrayList("rollNo",rollNumbers);
                 bundle.putStringArrayList("names",names);

                 bundle.putInt("sec1",sec1);
                 bundle.putInt("sec2",sec2);
                 bundle.putInt("sec3",sec3);


                 i.putExtra("students",bundle);
                 i.putExtra("status",1);
                 startActivity(i);
                 finish();
             }catch(Exception e)
             {
                 System.out.println("^^^^^^^^^^^^^"+e.getMessage());
             }
          }
      }
  }
}
