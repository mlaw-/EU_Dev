package com.example.myaccount.DataModels;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ArrayOfLocation {
	@ElementList(inline=true, type=Location.class, entry="Location")
	   private List<Location> locations;
	
	public List<Location> getLocations(){
		return locations;
	}
	
	public static Location[] arrayListToArray(ArrayList<Location> locations){
		Location[] retVal = new Location[locations.size()];
		
		for(int i = 0; i < locations.size(); i++){
			retVal[i] = locations.get(i);
		}
		return retVal;
	}
}
