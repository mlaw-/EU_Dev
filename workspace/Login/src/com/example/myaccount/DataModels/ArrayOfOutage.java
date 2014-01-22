package com.example.myaccount.DataModels;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;



@Root
public class ArrayOfOutage {

   @ElementList(inline=true, type=Outage.class, entry="Outage")
   private List<Outage> outages;

  
   public ArrayOfOutage() {
      super();
   }  

   public List<Outage> getOutage(){
	   return outages;
   }
   
}

