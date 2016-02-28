package com.codingc.team66.models;

import android.util.Log;

public class ParticularRestaurantModel {
    String Reviews;
    String Username;
    String Time;
    public ParticularRestaurantModel(){
    	super();
    }

    public ParticularRestaurantModel(String Reviews,String Username,String TimeS){
    	super();
    	this.Reviews=Reviews;
    }

    public String getReviews(){
    	return Reviews;
    }
    public void setReviews(String Reviews){
    	this.Reviews=Reviews;
    	Log.i("In PRmodel (setReviews):",this.Reviews);
    }

    public String getUsername(){
    	return Username;
    }
    public void setUsername(String Username){
    	this.Username=Username;
    }

    public String getTime(){
    	return Time;
    }
    public void setTime(String Time){
    	this.Time=Time;
    }

    @Override
	public String toString() {
          Log.i("In prmodel:(tostring)",Reviews );
		return "ParticularRestaurantModel [Reviews=" + Reviews + "Username=" + Username +"Time="+ Time+  "]";
	}
}
