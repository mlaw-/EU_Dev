package com.example.myaccount;

import android.app.Application;

public class MyApplication extends Application {
	private String GUID;
	private String username;
	private String locationID;
	private String password;
	
	public Response authenticateUser(String username, String password){
		return new Response(true, String.format("%s %s failed", username, password));
	}
	
	public void setGUID(String guid){
		this.GUID = guid;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}
	
	public String getGUID(){
		return GUID;
	}
	
	public String getUsername(){
		return username;
	}
	public void setLocationID(String locationID){
		this.locationID = locationID;
	}
	public String getLocationID(){
		return locationID;
	}
}
