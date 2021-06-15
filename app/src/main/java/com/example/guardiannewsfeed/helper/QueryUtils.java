package com.example.guardiannewsfeed.helper;

import com.example.guardiannewsfeed.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QueryUtils {
    public QueryUtils(){
    }

    public ArrayList<News> getNewsList(String jsonResponse){
        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject jsonRootObject = new JSONObject(jsonResponse);

            //Get the instance of JSONArray that contains JSONObjects
            JSONObject jsonObjectResponse = jsonRootObject.optJSONObject("response");

            JSONArray jsonArrayResults = jsonObjectResponse.optJSONArray("results");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArrayResults.length(); i++){
                JSONObject jsonObjectResult = jsonArrayResults.getJSONObject(i);

                String webTitle = jsonObjectResult.optString("webTitle");
                String webPublicationDateRaw = jsonObjectResult.optString("webPublicationDate");
                String webUrl = jsonObjectResult.optString("webUrl");

                String[] webPublicationDateParts = webPublicationDateRaw.split("T");

                news.add(new News(webTitle,webPublicationDateParts[0],webUrl));
            }

        } catch (JSONException e ) {
            e.printStackTrace();
        }

        return news;
    }
}
