package com.pucpr.heverton.allaboutfishing;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataStoreClima {

    private static DataStoreClima instance = null;
    private Context context;


    private Clima clima;



    protected DataStoreClima() {}

    public static DataStoreClima sharedInstance() {
        if (instance == null)
            instance = new DataStoreClima();
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;


        new ReadJSON().execute(new String[] {
                "https://allaboutfishing.azurewebsites.net/testando.json"
        });
    }

    private class ReadJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String text = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setDoInput(true);
                InputStream inputStream = new BufferedInputStream(
                        connection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String read = reader.readLine();
                while (read != null) {
                    text += read;
                    read = reader.readLine();
                }
                reader.close();
                inputStreamReader.close();
                inputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {

                JSONArray array = new JSONArray(jsonStr);
               /*JSONObject json = new JSONObject(jsonStr);
                JSONArray array = json.getJSONArray("RincÃƒÂ£o Alegre");*/

                for (int i=0; i<array.length(); i++) {
                    JSONObject news = array.getJSONObject(i);
                    String nomeCity = news.getString("name");
                    String estado = news.getString("state");
                    String pais = news.getString("country");
                    clima = new Clima(nomeCity,estado,pais,19,100,"Friozinho");
                }
                //Log.d("Tag", "signInWithCredential:DATASTORE" + dicas);




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public Clima clima(){




            return clima;
        }
    }

}
