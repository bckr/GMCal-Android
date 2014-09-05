package com.nils.becker.fhplaner.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

public class FetchCoursesTask extends AsyncTask<String, Void, JSONArray> {

	private Fetcher callback;
	
	public FetchCoursesTask(Fetcher callback) {
		this.callback = callback;
	}
	
	@Override
	protected JSONArray doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(params[0]);
		
		// Accept JSON
		httpget.addHeader("accept", "application/json");
		// Execute the request
		HttpResponse response;
		JSONArray json = new JSONArray();
		
		try {
			response = httpclient.execute(httpget);
			// Get the response entity
			HttpEntity entity = response.getEntity();
			// If response entity is not null
			if (entity != null) {
				// get entity contents and convert it to string
				String result = EntityUtils.toString(entity);
				// construct a JSON object with result
				json = new JSONArray(result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return the json
		return json;
	}
	
	@Override
	protected void onPostExecute(JSONArray result) {
		this.callback.responseLoaded(result);
	}
	
}
