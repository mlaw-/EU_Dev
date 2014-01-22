package com.example.myaccount.DataModels;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;


public class Utility {
	@Element(required=false)
	@Namespace(prefix="a")
	public String string;
}
