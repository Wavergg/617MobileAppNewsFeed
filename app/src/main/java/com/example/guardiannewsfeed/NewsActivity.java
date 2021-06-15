package com.example.guardiannewsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.guardiannewsfeed.adapter.AdapterNews;
import com.example.guardiannewsfeed.helper.QueryUtils;
import com.example.guardiannewsfeed.models.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    //https://content.guardianapis.com/search?section=football&page-size=3&page=2&api-key=4b8ccbdb-5959-4c3e-b777-e1a5550d850c
    final String BASE_URL="https://content.guardianapis.com/search?page-size=10&section=";
    final String API_KEY="&api-key=4b8ccbdb-5959-4c3e-b777-e1a5550d850c";

    static String sectionUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ProgressBar progressBar = findViewById(R.id.progressBar_news);
        progressBar.setVisibility(View.VISIBLE);
        ListView listView = findViewById(R.id.listView_News);
        listView.setVisibility(View.GONE);

        Intent intent = getIntent();
        String sectionName = intent.getStringExtra("sectionName");

        sectionUrl = BASE_URL + sectionName + API_KEY;

        NewsAsyncTask task = new NewsAsyncTask();
        task.execute();
    }

    private void updateUi(String jsonResponse) {
        // Display the earthquake title in the UI
        if(jsonResponse != "") {

            ArrayList<News> news = new QueryUtils().getNewsList(jsonResponse);
            ListView newsListView = (ListView)  findViewById(R.id.listView_News);
            AdapterNews adapter = new AdapterNews(this, news);

            newsListView.setAdapter(adapter);

            ProgressBar progressBar = findViewById(R.id.progressBar_news);
            progressBar.setVisibility(View.GONE);
            ListView listView = findViewById(R.id.listView_News);
            listView.setVisibility(View.VISIBLE);
        }
    }


    public class NewsAsyncTask extends AsyncTask<URL, Void, String> {


        public final String LOG_TAG = this.getClass().getSimpleName();


        @Override
        public String doInBackground(URL... urls) {
            URL url = createUrl(NewsActivity.sectionUrl);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
                Log.v(LOG_TAG,"IO Exception: "+ e.getMessage());
            }

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return jsonResponse;
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }


        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                if(urlConnection.getResponseCode() == 200 ) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.v(LOG_TAG,"Response Status Code: "+ urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                // TODO: Handle the exception
                Log.v(LOG_TAG,"IO Exception: "+ e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            if (jsonResponse == "") {
                return;
            }

            updateUi(jsonResponse);
        }




        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
    }
}