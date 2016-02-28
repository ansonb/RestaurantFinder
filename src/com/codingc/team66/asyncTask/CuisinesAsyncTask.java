package com.codingc.team66.asyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import com.codingc.team66.restofinder.CuisinesActivity;
import com.codingc.team66.helpers.Constants;
import com.codingc.team66.models.CuisinesModel;
import com.codingc.team66.parsers.CuisinesParser;
import com.codingc.team66.restservices.RestClient;

import android.database.CursorJoiner.Result;
import android.os.AsyncTask;
import android.util.Log;

public class CuisinesAsyncTask extends AsyncTask<String, Void, ArrayList<CuisinesModel>>
{
	CuisinesActivity context;
	String cityId;

	public CuisinesAsyncTask(CuisinesActivity context,String cityId) {
		this.context=context;
		this.cityId=cityId;
	}
	@Override
	protected ArrayList<CuisinesModel>doInBackground(String... Paramater)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("city_id",cityId));
		params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));

		String strJsonReponse;
		ArrayList<CuisinesModel>cuisineArray = null;
		try {
			strJsonReponse = RestClient.getInstance(context).doApiCall(Constants.strCuisines, "GET", params);
			Log.d("Cuisines Data Response",String.valueOf(strJsonReponse));
			CuisinesParser parser=new CuisinesParser();
			cuisineArray=parser.parseCuisines(strJsonReponse);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cuisineArray;
	}

	@Override
	protected void onPostExecute(ArrayList<CuisinesModel> cuisineArray)
	{
		//context.getCuisines(cuisineArray);
	}


}
