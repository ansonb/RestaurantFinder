package com.codingc.team66.restofinder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.codingc.team66.adapters.CuisinesAdapter;
import com.codingc.team66.adapters.RestaurantAdapter;
import com.codingc.team66.database.CuisineDbAdapter;
import com.codingc.team66.database.DbFunctions;
import com.codingc.team66.database.RestaurantDbAdapter;
import com.codingc.team66.helpers.AppStatus;
import com.codingc.team66.helpers.Constants;
import com.codingc.team66.models.CuisinesModel;
import com.codingc.team66.models.RestaurantsModel;
import com.codingc.team66.parsers.CuisinesParser;
import com.codingc.team66.parsers.RestaurantParser;
import com.codingc.team66.restservices.RestClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RestaurantActivity extends Activity {
	String cuisineId,cityId;
	ArrayList<RestaurantsModel> restaurantsArray;
	ListView list;
	DbFunctions dbFunctions = new DbFunctions(RestaurantActivity.this); // DbFunctions
	AppStatus appStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurants);

		appStatus = AppStatus.getInstance(this);
		Intent intent=getIntent();
		if (appStatus.isOnline(RestaurantActivity.this))
		{
			Log.v("Restaurant_SCREEN", "App is Online");
			cuisineId=(String)intent.getSerializableExtra("cuisineId");
			cityId=(String)intent.getSerializableExtra("cityId");
			getRestaurantsInfo();
		}
		else
		{
			Log.v("Restaurant_SCREEN", "App is Offline");
			RestaurantDbAdapter mdbAdapter = new RestaurantDbAdapter(this, Constants.strRestaurantTableName);
			restaurantsArray  = mdbAdapter.getRestaurantsModels();
			dispalyList();
		}
	}

	private void getRestaurantsInfo()
	{
		RestaurantDbAdapter restaurantDbAdapter = new RestaurantDbAdapter(this, Constants.strRestaurantTableName);
		try {

			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("city_id",cityId));
			params.add(new BasicNameValuePair("cuisine_id", cuisineId));
			params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));

			String strJsonReponse = RestClient.getInstance(this).doApiCall(Constants.strSearch, "GET", params);

			Log.d("Cuisines Data Response...",String.valueOf(strJsonReponse));
			RestaurantParser parser=new RestaurantParser();
			restaurantsArray = parser.parseRestaurants(strJsonReponse);

			for(RestaurantsModel r:restaurantsArray)
			{
				Log.d("Restaurants: ", r.toString());
			}

			/*list = (ListView)findViewById(R.id.list);
			RestaurantAdapter adapter = new RestaurantAdapter(RestaurantActivity.this, restaurantsArray);
			dbFunctions.storeRestaurantDataInDB(restaurantsArray);

			list.setAdapter(adapter);
			*/
			dispalyList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void dispalyList()
	{
		list = (ListView)findViewById(R.id.list);
		RestaurantAdapter adapter = new RestaurantAdapter(RestaurantActivity.this, restaurantsArray);
		dbFunctions.storeRestaurantDataInDB(restaurantsArray);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
			{
				RestaurantsModel restaurant=restaurantsArray.get(position);
				Intent intent=new Intent(RestaurantActivity.this,TabClass.class);
				intent.putExtra("RestaurantId", restaurant.getID());
				intent.putExtra("RestaurantLat", restaurant.getlat());
				intent.putExtra("RestaurantLng", restaurant.getlng());
				intent.putExtra("Restaurantaddr", restaurant.getAddress());
				intent.putExtra("Restaurantname", restaurant.getName());
				intent.putExtra("Restaurantrating", restaurant.getRating());
				startActivity(intent);
			}
		});
	}

}
