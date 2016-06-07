package com.nitkkr.sunnny.oasys;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by SUNNNY on 16-01-2016.
 */
public class JSONParser {
      static InputStream is=null;
      static JSONObject jObj=null;
      static String json="";

    //constructor of class
    public JSONParser(){

    }

    public static JSONObject makeHttpRequest(String urls,Uri.Builder builder)
    {
        String str="hello";
        try {
             URL url=new URL(urls);
             HttpURLConnection httpCon=(HttpURLConnection)url.openConnection();
             httpCon.setChunkedStreamingMode(0);
             httpCon.setDoOutput(true);
             httpCon.setDoInput(true);
            // httpCon.setConnectTimeout(15000);
            // httpCon.setReadTimeout(15000);
             httpCon.setRequestMethod("POST");
             httpCon.setRequestProperty("Accept-Encoding", "identity");
             System.setProperty("http.keepAlive", "false");
             OutputStream os=httpCon.getOutputStream();
             BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
             String query=builder.build().getEncodedQuery();
             writer.write(query);
             writer.flush();
             is=httpCon.getInputStream();

        }catch (Exception e)
        {
            System.out.println("###nnn1###"+e.getMessage());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            System.out.println("############"+json);
        }   catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e)
        {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON String
        return jObj;
    }
}
