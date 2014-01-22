package com.example.myaccount.DataModels;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Summary {
	@Element(required=false)
	public String LYDate;
	@Element(required=false)
	public String LYelectricusage;
	@Element(required=false)
	public String LYgasusage;
	@Element(required=false)
	public String LYwaterusage;
	@Element(required=false)
	public String electricusage;
	@Element(required=false)
	public String gasusage;
	@Element(required=false)
	public String waterusage;
	@Element(required=false)
	public String address;
	@Element(required=false)
	public String balance;
	@Element(required=false)
	public String billCity;
	@Element(required=false)
	public String billState;
	@Element(required=false)
	public String billStreet;
	@Element(required=false)
	public String cableoutage;
	@Element(required=false)
	public String electricoutage;
	@Element(required=false)
	public String gasoutage;
	@Element(required=false)
	public String internetoutage;
	@Element(required=false)
	public String lastbill;
	@Element(required=false)
	public String currbill;
	@Element(required=false)
	public String wateroutage;

	@ElementList(inline=true, type=Utility.class, entry="utilities")
	public List<Utility> utilities;
}
