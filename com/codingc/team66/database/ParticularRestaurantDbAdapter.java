package com.codingc.team66.database;

import java.util.ArrayList;

import com.codingc.team66.helpers.Constants;
import com.codingc.team66.models.CuisinesModel;
import com.codingc.team66.models.ParticularRestaurantModel;
import com.codingc.team66.models.RestaurantsModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


public class ParticularRestaurantDbAdapter extends DbAdapter {
	public String strTableName = Constants.strParticularRestaurantTableName;

	public ParticularRestaurantDbAdapter(Context context, String strTableName){
		super(context, strTableName);
		Log.i("ParticularRestaurant dBAdapter.......", "!!!!!!");
		this.strTableName=strTableName;
		setDbName();
		setDbColumns();
	}

	public static final String Reviews = "reviews";
	public static final String Username = "username";
	public static final String Time = "time";

	@Override
	protected void setDbName() {
		// TODO Auto-generated method stub
		this.dbName = strTableName;
		Log.i("DB Name Set", dbName);
	}

	@Override
	protected void setDbColumns() {
		// TODO Auto-generated method stub
		this.dbColumns = new String[] { "_id",Username, Reviews ,Time};
		Log.i("Db Comolmn Set", dbColumns.toString());
	}

	public long create(ContentValues Values) {

		return super.create(Values);
	}

	public boolean update(long rowId, ContentValues Values) {

		return super.update(rowId, Values);
	}

	public ArrayList<ParticularRestaurantModel>getParticularRestaurantModels() {
        Cursor cursor = this.fetchAll(null, null);

		ArrayList<ParticularRestaurantModel>List = new ArrayList<ParticularRestaurantModel>();

		while (cursor.moveToNext()) {
			 String Username = cursor.getString(cursor.getColumnIndex("Username"));
			 String Reviews = cursor.getString(cursor.getColumnIndex("Reviews"));
			 String Time = cursor.getString(cursor.getColumnIndex("Time"));

			 ParticularRestaurantModel model = new ParticularRestaurantModel();
			 model.setReviews(Reviews);
			 model.setUsername(Username);
			 model.setTime(Time);


			 Log.i("Cursor values", String.valueOf(Reviews));
			 List.add(model);
		}
		cursor.close();
		return List;
	}

	public void deleteAll() {
		try {
			db.beginTransaction();
			this.delete();
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

	}

}
