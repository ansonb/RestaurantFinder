package com.codingc.team66.models;

public class RestaurantsModel {
	String name;
	String address;
	String cuisines;
	String rating;
	String id;
	String lat;
	String lng;
	public RestaurantsModel() {
		super();
	}
	public RestaurantsModel(String name, String address, String cuisines,String rating,String id,String lat,String lng)
	{
		super();
		this.name = name;
		this.address = address;
		this.cuisines = cuisines;
		this.rating = rating;
		this.id = id;
		this.lat=lat;
		this.lng=lng;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCuisines() {
		return cuisines;
	}
	public void setCuisines(String cuisines) {
		this.cuisines = cuisines;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}

	public String getlat() {
		// TODO Auto-generated method stub
		return lat;
	}
	public void setlat(String lat) {
		this.lat = lat;
	}

	public String getlng() {
		// TODO Auto-generated method stub
		return lng;
	}
	public void setlng(String lng) {
		this.lng = lng;
	}
	@Override
	public String toString() {
		return "RestaurantModel [name=" + name + ", address=" + address + ", cuisines=" + cuisines + ", rating=" + rating + ", id=" + id + ", lat=" + lat + ", lng=" + lng + "]";
	}






}
