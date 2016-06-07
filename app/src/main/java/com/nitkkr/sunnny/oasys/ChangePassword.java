package com.nitkkr.sunnny.oasys;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class ChangePassword extends ActionBarActivity {

    JSONObject json=null;
    ProgressDialog p;
    EditText oldP,newP;
    final String url="http://www.nitkkrs.comxa.com/changePass.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldP=(EditText)findViewById(R.id.oldPass);
        newP=(EditText)findViewById(R.id.newPass);

    }

    public void doChangePassword(View view)
    {
        if(newP.getText().toString().length()>3)
        {
            new InternetManager().execute(url);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Password Must be greater than 3 digits.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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
            p=new ProgressDialog(ChangePassword.this);
            p.setIndeterminate(true);
            p.setCancelable(true);
            p.setMessage("Processing...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo=connectivityManager.getActiveNetworkInfo();
            if(netInfo!=null&&netInfo.isConnected())
            {
                Uri.Builder builder=new Uri.Builder();
                builder.appendQueryParameter("oldPass",oldP.getText().toString());
                builder.appendQueryParameter("newPass",newP.getText().toString());
                json=JSONParser.makeHttpRequest(strings[0],builder);
                if(json==null)
                    return "no";
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_LONG).show();
                return "no";
            }
            return json.toString();
        }

        protected void onPostExecute(String result)
        {
            p.dismiss();
            super.onPostExecute(result);
            if(!result.equalsIgnoreCase("no"))
            {
               try{
                   int n=json.getInt("success");
                   if(n==1)
                   {
                       Toast.makeText(getApplication(),"Password Changed",Toast.LENGTH_LONG).show();
                       oldP.setText("");
                       newP.setText("");
                   }
                   else
                   {
                       Toast.makeText(getApplication(),"Wrong old password",Toast.LENGTH_LONG).show();
                       oldP.setText("");
                       newP.setText("");
                   }
               }catch (Exception e)
               {
                   System.out.println(e.getMessage());
               }
            }
        }
    }
}
