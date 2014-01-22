package com.example.myaccount.DataModels;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Element;

@Root
public class TokenResponse {
	@Element
	private String custnbr;
	
	@Element
	private String dateExpire;
	
	@Element
	private String error;
	
	@Element
	private boolean success;
	
	@Element
	private String token;
	
	
	public String getCustNbr(){
		return custnbr;
	}
	public String getDateExpire(){
		return dateExpire;
	}
	public String getError(){
		return error;
	}
	public String getToken(){
		return token;
	}
	public boolean getSuccess(){
		return success;
	}
}
