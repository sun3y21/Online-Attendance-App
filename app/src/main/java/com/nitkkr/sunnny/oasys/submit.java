package com.nitkkr.sunnny.oasys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Date;


public class submit extends ActionBarActivity {

    ProgressDialog p;
    JSONObject json=null;
    String aStr;
    final String url="http://www.nitkkrs.comxa.com/submitA.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Intent i=getIntent();
        TextView todayDate=(TextView)findViewById(R.id.todaydate);
        Date d=new Date();
        String date=d.toString();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(date.substring(0,date.indexOf(" ")));
        date=date.substring(date.indexOf(" ")+1,date.length());

        stringBuilder.append(" "+date.substring(0,date.indexOf(" ")));
        date=date.substring(date.indexOf(" ")+1,date.length());

        stringBuilder.append(" "+date.substring(0,date.indexOf(" ")));
        date=date.substring(date.indexOf(" ")+1,date.length());

        todayDate.setText(stringBuilder.toString());
        TextView atd=(TextView)findViewById(R.id.score);
        aStr=i.getStringExtra("atd");
        int count=0;
        for(int k=0;k<aStr.length();k++)
        {
            if(aStr.charAt(k)=='P')
            {
                count++;
            }
        }
        atd.setText("Attendance : "+count+"/"+aStr.length());
    }

    public void doSubmit(View view)
    {
            try {
                new submitAttendance().execute(url);
            } catch (Exception e) {
                System.out.println("^^^^^^^^^^" + e.getMessage());
            }
    }

    public void doExit(View view)
    {
           finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit, menu);
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
           Intent i=new Intent(getApplicationContext(),result.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.action_changePassword)
        {
            Intent i=new Intent(getApplicationContext(),ChangePassword.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class submitAttendance extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p=new ProgressDialog(submit.this);
            p.setMessage("Submitting...");
            p.setIndeterminate(true);
            p.setCancelable(true);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

         try{
             ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo netInfo=connectivityManager.getActiveNetworkInfo();
             if(netInfo!=null&&netInfo.isConnected())
             {
                 Uri.Builder builder=new Uri.Builder();
                 EditText pass=(EditText)findViewById(R.id.password);
                 String passStr=pass.getText().toString();
                 builder.appendQueryParameter("attendance",aStr);
                 builder.appendQueryParameter("password",passStr);
                 json=JSONParser.makeHttpRequest(strings[0],builder);
                 if(json==null)
                    return "no";
                 return json.toString();
             }
             else
             {
                 return "no";
             }
             }catch (Exception e)
           {
               System.out.println("**********"+e.getMessage());
           }
             return "no";
        }

        @Override
        protected void onPostExecute(String s) {
            p.dismiss();
            if(!s.equalsIgnoreCase("no"))
            {
                try{
                    int code=json.getInt("success");
                    if(code==0)
                    {
                        Toast.makeText(getApplicationContext(),"Wrong password",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                       Toast.makeText(getApplicationContext(),"Submitted Successfully",Toast.LENGTH_LONG).show();
                       ImageView imageView=(ImageView)findViewById(R.id.imageView);
                       TextView t1=(TextView)findViewById(R.id.todaydate);
                       TextView t2=(TextView)findViewById(R.id.score);
                       TextView t3=(TextView)findViewById(R.id.result);
                       t1.setVisibility(View.INVISIBLE);
                       imageView.setVisibility(View.VISIBLE);
                       t2.setVisibility(View.INVISIBLE);
                       t3.setVisibility(View.VISIBLE);
                       t3.setText("Submitted");
                       Button b=(Button)findViewById(R.id.exitB);
                       Button b1=(Button)findViewById(R.id.submitB);
                        b1.setVisibility(View.INVISIBLE);
                       EditText e=(EditText)findViewById(R.id.password);
                       e.setVisibility(View.INVISIBLE);
                       b.setVisibility(View.VISIBLE);
                    }
                }catch(Exception e)
                {
                    System.out.println("$$$$$$"+e.getMessage());
                }
            }
        }
    }
}
