package com.example.myaccount.DataModels;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Outage {
	@Element(required=false)
	private float latitude;
	@Element(required=false)
	private float longitude;
	
	@Element(required=false)
	private String closeDT;
	
	@Element (required=false)
	private String openDT;
	
	@Element(required=false)
	private String person;
	
	@Element(required=false)
	private String service;
	
	@Element(required=false)
	private String type;
}
