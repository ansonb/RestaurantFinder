package com.codingc.team66.parsers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.codingc.team66.models.CuisinesModel;
import com.codingc.team66.models.RestaurantsModel;
import com.codingc.team66.models.ParticularRestaurantModel;


public class ParticularRestaurantParser {
	ArrayList<ParticularRestaurantModel> ParticularrestaurantArray;

	public ArrayList<ParticularRestaurantModel> parseParticularRestaurant(String strJsonReponse)
	{

		ParticularrestaurantArray=new ArrayList<ParticularRestaurantModel>();
		try
		{
			JSONObject responseParticularRestaurant = new JSONObject(strJsonReponse);
			JSONArray particularrestaurantObj = responseParticularRestaurant.getJSONArray("results");

			for(int i=0;i < particularrestaurantObj.length();i++)
			{
				ParticularRestaurantModel particularrestaurantModel =new ParticularRestaurantModel();
				JSONObject jObj=particularrestaurantObj.getJSONObject(i);
				JSONObject particularrestaurant=jObj.getJSONObject("result");
				particularrestaurantModel.setReviews(particularrestaurant.getString("reviewText").toString());
				particularrestaurantModel.setUsername(particularrestaurant.getString("userName").toString());
				particularrestaurantModel.setTime(particularrestaurant.getString("reviewTimeFriendly").toString());
				ParticularrestaurantArray.add(particularrestaurantModel);

			}

		}
		catch (Exception e) {
			// TODO: handle exception
		}

		return ParticularrestaurantArray;
	}

}
