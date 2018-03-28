package com.quickhire.quickhire;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by matth_000 on 2018-03-27.
 */

public class videoStreamer extends AsyncTask<videoAnswer, Void, Void> {

    int index = 0;

    @Override
    protected Void doInBackground(videoAnswer... f) {
        // TODO Auto-generated method stub
        int q=5;
        connection c = connection.getConnection();
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        String urlString = "UploadVideo";
        this.index=0;
        try{

            Uri video = f[0].vid;
            File file = new File(video.getPath());
            FileInputStream fileInputStream=new FileInputStream(file);

            //InputStream fileInputStream = connection.getConnection().appcontext.getContentResolver().openfile(new File(video.getPath()));

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0){
                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                    int i = index;
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Transmitting",("Packet number "+String.valueOf(i)));
                    }
                };
                Response.ErrorListener errorListener =new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                };
                String JSON = "{\"applicationID\":"+f[0].getApplicationID()+","
                            +"\"questionID\":"+f[0].getQuestionID()+","
                            +"\"packetNum\":"+this.index+","
                            +"\"data\":\""+ Base64.encodeToString(buffer,Base64.URL_SAFE)+"\"}";
                c.publicgeneric(JSON,urlString,listener,errorListener);
                //Logic for next request generation
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                this.index++;
            }
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                int i = index;
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Transmitting",("Final Packet: "+String.valueOf(i)));
                }
            };
            Response.ErrorListener errorListener =new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            };
            String JSON = "{\"applicationID\":"+f[0].getApplicationID()+","
                    +"\"questionID\":"+f[0].getQuestionID()+","
                    +"\"packetNum\":"+this.index+","
                    +"\"data\":\"done\"}";
            c.publicgeneric(JSON,urlString,listener,errorListener);

            fileInputStream.close();
        }
        catch (MalformedURLException ex){
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
        catch (IOException ioe){
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }
        catch (Exception fnoe){
            Log.e("Broke", fnoe.getMessage());
        }
        c.disableExternal();
        return null;
    }

}