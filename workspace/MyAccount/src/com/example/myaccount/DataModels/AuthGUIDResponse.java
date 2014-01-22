package com.example.myaccount.DataModels;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class AuthGUIDResponse {
	@Element(required=false)
	private String CustNbr;
	
	@Element
	private boolean isAuthenticated;
	
	public String getCustNbr(){
		return CustNbr;
	}
	
	public boolean isAuthenticated(){
		return isAuthenticated;
	}
}
