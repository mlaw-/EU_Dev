package com.example.myaccount;

public class Response {
	
	public boolean success;
	public String message;
	
	public Response(boolean success, String message){
		this.success = success;
		this.message = message;
	}

}
