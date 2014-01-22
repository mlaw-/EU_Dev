package com.example.myaccount;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myaccount.DataModels.ArrayOfLocation;
import com.example.myaccount.DataModels.ArrayOfOutage;
import com.example.myaccount.DataModels.AuthGUIDResponse;
import com.example.myaccount.DataModels.Location;
import com.example.myaccount.DataModels.Outage;
import com.example.myaccount.DataModels.Summary;

public class WebServiceAPI {
	public static final String webServiceURLPrefix = 
			"http://69.2.160.132:8888/CustomerPortal/CustomerData/v1/CustomerPortalWebService/";
	public static final String securityURLPrefix = "http://69.2.160.132:8888/CustomerPortal/Security/v1/STS/";
	
	public static final String getOutagesURL = "outages";
	public static final String getLocationsURL = "secured/customer";
	public static final String getSummaryURL = "secured/summary";
	public static final String authenticateURL = "AuthenticateUser";
	public static final String authenticateGUID = "AuthenticateGUID";

	/**
	 * Starting point for the SAAJ - SOAP Client Testing
	 */
	public void getOutages() {
		new RetrieveOutageAsync().execute((OutageMap) null);
	}
	
	
	public static void getLocations(final Activity activity){
		final String GUID = ((MyApplication) activity.getApplication()).getGUID();
		final String username = ((MyApplication) activity.getApplication()).getUsername();
		
		//new RetrieveLocations().execute(new LocationAsyncParams(GUID, username, activity));
		checkGUID(activity, new Callable<Object>(){
			public Object call(){
				new RetrieveLocations().execute(new LocationAsyncParams(GUID, username, activity));
				return null;
			}
		});
	}
	
	public static void refreshMainMenu(final MainMenu mainMenu){
		MyApplication app = (MyApplication)mainMenu.getApplication();
		String locationID = app.getLocationID();
		final String GUID = app.getGUID();
		final String username = app.getUsername();
		
		
		checkGUID((Activity)mainMenu, new Callable<Object>(){
			public Object call(){
				new RetrieveSummaryAsync().execute(new SummaryAsyncParams(mainMenu));
				return null;
			}
		});
	}
	

	static class SummaryAsyncParams{
		Activity activity;
		
		public SummaryAsyncParams(Activity activity){
			this.activity = activity;
		}
		
	}
	static class SummaryAsyncResults{
		SummaryAsyncParams params;
		Summary resp;
		
		public SummaryAsyncResults(SummaryAsyncParams params, Summary resp){
			this.params = params;
			this.resp = resp;
		}
		
	}
	static class RetrieveSummaryAsync extends AsyncTask<SummaryAsyncParams, Void, SummaryAsyncResults>{

		@Override
		protected SummaryAsyncResults doInBackground(SummaryAsyncParams... arg0) {
			if(arg0.length == 0)
				return null;
			SummaryAsyncParams params = arg0[0];
			MyApplication app = (MyApplication)params.activity.getApplication();
			String username = app.getUsername();
			String GUID = app.getGUID();
			String locationID = app.getLocationID();
			Activity activity = params.activity;
			
			BufferedReader in = null;
	        String data = null;


	        try
	        {
	            HttpClient client = new DefaultHttpClient();
	            //URI website = new URI("https://server.com:8443/Timesheets/ping");
	            URI website = new URI(webServiceURLPrefix + getSummaryURL + 
	            		String.format("?Username=%s&GUID=%s&LocationID=%s", username, GUID, locationID));
	            HttpGet request = new HttpGet();
	            request.setURI(website);
	            HttpResponse response = client.execute(request);
	            response.getStatusLine().getStatusCode();

	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	            StringBuffer sb = new StringBuffer("");
	            String l = "";
	            String nl = System.getProperty("line.separator");
	            while ((l = in.readLine()) !=null){
	                sb.append(l + nl);
	            }
	            in.close();
	            data = sb.toString();
	            
	        	Serializer serializer = new Persister();
	        	Summary summary = serializer.read(Summary.class, new StringReader(data));
	        	
	            
	            return new SummaryAsyncResults(params, summary);  
	        } 
	        catch(Exception ex){
	        	System.out.println(ex.getMessage());
	        }
			
			return null;
		}
	}
	private static void checkGUID(Activity activity, Callable func){
		new checkGUIDAsyncTask().execute(new CheckGUIDParams(activity, func ));
	}
	static class CheckGUIDParams{
		Activity activity;
		Callable call;
		
		public CheckGUIDParams(Activity activity, Callable call){
			this.activity = activity;
			this.call = call;
		}
		
	}
	static class CheckGUIDResults{
		CheckGUIDParams params;
		AuthGUIDResponse resp;
		
		public CheckGUIDResults(CheckGUIDParams params, AuthGUIDResponse resp){
			this.params = params;
			this.resp = resp;
		}
		
	}
	static class checkGUIDAsyncTask extends AsyncTask<CheckGUIDParams, Void, CheckGUIDResults>{

		@Override
		protected CheckGUIDResults doInBackground(CheckGUIDParams... params) {
			if(params.length == 0)
				return null;
			CheckGUIDParams vars = params[0];
			MyApplication app = (MyApplication)vars.activity.getApplication();
			String GUID = app.getGUID();
			String username = app.getUsername();
			
			try
	        {
	            HttpClient client = new DefaultHttpClient();
	            //URI website = new URI("https://server.com:8443/Timesheets/ping");
	            URI website = new URI(securityURLPrefix + authenticateGUID + 
	            		String.format("?Username=%s&GUID=%s", username, GUID));
	            HttpGet request = new HttpGet();
	            request.setURI(website);
	            HttpResponse response = client.execute(request);
	            response.getStatusLine().getStatusCode();

	            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	            StringBuffer sb = new StringBuffer("");
	            String l = "";
	            String nl = System.getProperty("line.separator");
	            while ((l = in.readLine()) !=null){
	                sb.append(l + nl);
	            }
	            in.close();
	            String data = sb.toString();
	            
	        	Serializer serializer = new Persister();
	        	AuthGUIDResponse resp = serializer.read(AuthGUIDResponse.class, new StringReader(data));
	        	
	            
	            return new CheckGUIDResults(vars, resp);  
	        } 
	        catch(Exception ex){
	        	System.out.println(ex.getMessage());
	        }
			
			return null;
		}
		@Override
	    protected void onPostExecute(CheckGUIDResults result) {
		    if(result == null)
		    	return;
		    CheckGUIDParams params = result.params;
		    AuthGUIDResponse resp = result.resp;
		    
        	if(resp.isAuthenticated()){
        		try {
					params.call.call();
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
        	}
        	else{
        		Intent intent = new Intent(params.activity, Login.class);
    		    
        		params.activity.startActivity(intent);
        	}
        	
        		
	    }
	}
	static class RetrieveLocations extends AsyncTask<LocationAsyncParams, Void, LocationAsyncResults>{

		@Override
		protected LocationAsyncResults doInBackground(LocationAsyncParams... arg0) {
			if(arg0.length == 0)
				return null;
			LocationAsyncParams params = arg0[0];
			String username = params.username;
			String GUID = params.GUID;
			Activity activity = params.activity;
			
			BufferedReader in = null;
	        String data = null;


	        try
	        {
	            HttpClient client = new DefaultHttpClient();
	            //URI website = new URI("https://server.com:8443/Timesheets/ping");
	            URI website = new URI(webServiceURLPrefix + getLocationsURL + 
	            		String.format("?Username=%s&GUID=%s", username, GUID));
	            HttpGet request = new HttpGet();
	            request.setURI(website);
	            HttpResponse response = client.execute(request);
	            response.getStatusLine().getStatusCode();

	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	            StringBuffer sb = new StringBuffer("");
	            String l = "";
	            String nl = System.getProperty("line.separator");
	            while ((l = in.readLine()) !=null){
	                sb.append(l + nl);
	            }
	            in.close();
	            data = sb.toString();
	            
	        	Serializer serializer = new Persister();
	        	ArrayOfLocation locations = serializer.read(ArrayOfLocation.class, new StringReader(data));
	        	
	            
	            return new LocationAsyncResults(activity, new ArrayList<Location>(locations.getLocations()));  
	        } 
	        catch(Exception ex){
	        	System.out.println(ex.getMessage());
	        }
			
			return null;
		}
		@Override
	    protected void onPostExecute(LocationAsyncResults result) {
			
		    if(result == null)
		    	return;
		    Intent intent = new Intent(result.activity, MainMenu.class);
		    intent.putExtra("Locations", result.locations);
		    
		    result.activity.startActivity(intent);
        	
	    }
		
	}
	static class LocationAsyncParams{
		public String GUID;
		public String username;
		public Activity activity;
		
		public LocationAsyncParams(String GUID, String username, Activity activity){
			this.GUID = GUID;
			this.username = username;
			this.activity = activity;
		}
	}
	static class LocationAsyncResults{
		public Activity activity;
		public ArrayList<Location> locations;
		
		public LocationAsyncResults(Activity activity, ArrayList<Location> locations){
			this.activity = activity;
			this.locations = locations;
		}
		
		
	}
	
	
	class RetrieveOutageAsync extends AsyncTask<OutageMap, Void, List<Outage>> {

		private OutageMap outageMapActivity;
		@Override
		protected List<Outage> doInBackground(OutageMap...params ) {
			outageMapActivity = params[0];
			BufferedReader in = null;
	        String data = null;


	        try
	        {
	            HttpClient client = new DefaultHttpClient();
	            //URI website = new URI("https://server.com:8443/Timesheets/ping");
	            URI website = new URI(webServiceURLPrefix + getOutagesURL);
	            HttpGet request = new HttpGet();
	            request.setURI(website);
	            HttpResponse response = client.execute(request);
	            response.getStatusLine().getStatusCode();

	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	            StringBuffer sb = new StringBuffer("");
	            String l = "";
	            String nl = System.getProperty("line.separator");
	            while ((l = in.readLine()) !=null){
	                sb.append(l + nl);
	            }
	            in.close();
	            data = sb.toString();
	            
	        	Serializer serializer = new Persister();
	        	ArrayOfOutage outages = serializer.read(ArrayOfOutage.class, new StringReader(data));
	        	
	            
	            return outages.getOutage();   
	        } 
	        catch(Exception ex){
	        	System.out.println(ex.getMessage());
	        }
	        
	        return null;
		}
		@Override
	    protected void onPostExecute(List<Outage> result) {
			
	    }
	}
	
	
}
