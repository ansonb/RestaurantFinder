package com.codingc.team66.adapters;

import java.util.ArrayList;

import com.codingc.team66.restofinder.R;
import com.codingc.team66.adapters.*;
//import com.codingc.team66.adapters.CuisinesAdapter.ViewHolder;
import com.codingc.team66.models.CuisinesModel;
import com.codingc.team66.models.RestaurantsModel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RestaurantAdapter extends ArrayAdapter<RestaurantsModel>{
	Activity context;
	ArrayList<RestaurantsModel> restaurantArray;

	public RestaurantAdapter(Activity context,ArrayList<RestaurantsModel> restaurantArray)
	{
		super(context,R.layout.list_item,R.id.textName,restaurantArray);

		this.context=context;
		this.restaurantArray=restaurantArray;
		//System.out.println("Restaurants Info:" + restaurantArray);

	}

	@Override
	public RestaurantsModel getItem(int position)
	{
		return restaurantArray.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		RestaurantsModel restaurantsModel =restaurantArray.get(position);
		if(convertView == null)
		{
			LayoutInflater inflater=context.getLayoutInflater();
			convertView=inflater.inflate(R.layout.customrestaurants,null);

			holder = new ViewHolder();

			holder.name = (TextView) convertView.findViewById(R.id.textName);
			holder.address = (TextView) convertView.findViewById(R.id.textAddress);
			holder.cuisines = (TextView) convertView.findViewById(R.id.textCuisine);
			holder.ratings = (TextView)convertView.findViewById(R.id.textRating);

			convertView.setTag(holder);
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}

		holder.name.setText("Restaurant: " + restaurantsModel.getName());
		holder.address.setText("Address: " + restaurantsModel.getAddress());
		holder.cuisines.setText("Cuisines: " + restaurantsModel.getCuisines());
		holder.ratings.setText("Ratings: " + restaurantsModel.getRating());

		return convertView;
	}

	static class ViewHolder
	{
		TextView name;
		TextView address;
		TextView cuisines;
		TextView ratings;
	}
}
