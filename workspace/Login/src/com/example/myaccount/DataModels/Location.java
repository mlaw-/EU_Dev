package com.example.myaccount.DataModels;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("serial")
@Root(strict=false)
public class Location implements Serializable {
	@Element
	private String address;
	
	@Element
	private String locationID;
	
	@Element
	private float balance;
	
	public String getAddress(){
		return address;
	}
	
	public String getLocationID(){
		return locationID;
	}
	
	public float getBalance(){
		return balance;
	}
	
	@Override
	public String toString(){
		return address;
	}
}
