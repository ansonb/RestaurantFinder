package com.codingc.team66.adapters;

import java.util.ArrayList;

import com.codingc.team66.restofinder.R;
import com.codingc.team66.adapters.*;
//import com.codingc.team66.adapters.CuisinesAdapter.ViewHolder;
import com.codingc.team66.models.CuisinesModel;
import com.codingc.team66.models.ParticularRestaurantModel;
import com.codingc.team66.models.RestaurantsModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParticularRestaurantAdapter extends ArrayAdapter<ParticularRestaurantModel>{
	Activity context;
	ArrayList<ParticularRestaurantModel> particularrestaurantArray;

	public ParticularRestaurantAdapter(Activity context,ArrayList<ParticularRestaurantModel> particularrestaurantArray)
	{
		super(context,R.layout.list_item,R.id.textUName,particularrestaurantArray);

		this.context=context;
		this.particularrestaurantArray=particularrestaurantArray;
		//System.out.println("Restaurants Info:" + restaurantArray);

	}

	@Override
	public ParticularRestaurantModel getItem(int position)
	{
		return particularrestaurantArray.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}


	@SuppressLint("InflateParams") @Override //edited
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		ParticularRestaurantModel particularrestaurantsModel =particularrestaurantArray.get(position);
		if(convertView == null)
		{
			LayoutInflater inflater=context.getLayoutInflater();
			convertView=inflater.inflate(R.layout.customrestaurants,null);

			holder = new ViewHolder();

			holder.Username = (TextView) convertView.findViewById(R.id.textUName);
			holder.Reviews = (TextView) convertView.findViewById(R.id.textReview);
			holder.Time = (TextView) convertView.findViewById(R.id.textTime);


			convertView.setTag(holder);
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}

		holder.Username.setText("user: " + particularrestaurantsModel.getUsername());
		holder.Reviews.setText("review: " + particularrestaurantsModel.getReviews());
		holder.Time.setText("- " + particularrestaurantsModel.getTime());


		return convertView;
	}

	static class ViewHolder
	{
		TextView Reviews;
		TextView Username;
		TextView Time;

	}
}

