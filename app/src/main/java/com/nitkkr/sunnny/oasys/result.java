package com.nitkkr.sunnny.oasys;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;



public class result extends ActionBarActivity {

    JSONObject json=null;
    final String url="http://www.nitkkrs.comxa.com/result.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        try {
              new InternetManager().execute(url);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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

                json=JSONParser.makeHttpRequest(strings[0],null);
                if(json==null)
                    return "no";
            }
            else
            {
                Toast.makeText(getApplicationContext(),"No Connection",Toast.LENGTH_LONG).show();
                return "no";
            }
            return json.toString();
        }

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            if(!result.equalsIgnoreCase("no"))
            {
                try
                {
                    JSONArray jsonArray=json.getJSONArray("rollNumbers");
                    int total_lec=json.getInt("total_lec");
                    String rollNumbers[]=new String[jsonArray.length()];
                    String names[]=new String[jsonArray.length()];
                    String totalA[]=new String[jsonArray.length()];
                    for(int j=0;j<jsonArray.length();j++)
                    {
                        JSONObject student=jsonArray.getJSONObject(j);
                        String name=student.getString("name");
                        String roll=student.getString("roll");
                        String totalAtt=student.getString("totalA");
                        rollNumbers[j]=roll;
                        names[j]=name;
                        totalA[j]=totalAtt;
                    }

                    ProgressBar p=(ProgressBar)findViewById(R.id.progressBar2);
                    p.setVisibility(View.INVISIBLE);
                    TextView t=(TextView)findViewById(R.id.loadingR);
                    t.setVisibility(View.INVISIBLE);
                    ListView listView=(ListView)findViewById(R.id.resultList);
                    CustomResultAdapter c=new CustomResultAdapter(getApplicationContext(),rollNumbers,names,totalA,total_lec);
                    listView.setAdapter(c);
                    listView.setVisibility(View.VISIBLE);

                }catch(Exception e)
                {
                    System.out.println("^^^^^^^^^^^^^"+e.getMessage());
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
        }
    }
}
