package com.example.myaccount;

import java.util.ArrayList;
import java.util.List;

import com.example.myaccount.DataModels.ArrayOfLocation;
import com.example.myaccount.DataModels.Location;
import com.example.myaccount.WebServiceAPI.LocationAsyncResults;

import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    Location[] mOptions;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		//btnOutageMap = (Button) findViewById(R.id.btnOutageMap);
		
		ArrayList<Location> result = (ArrayList<Location>) getIntent().getSerializableExtra("Locations");

		 mTitle = mDrawerTitle = getTitle();
		 
		 mOptions = ArrayOfLocation.arrayListToArray(result);//getResources().getStringArray(R.array.planets_array);
		 
		 List<Item> navDrawerItems = new ArrayList<Item>();
		 Location curr = null;
		 navDrawerItems.add(new Header(this.getLayoutInflater(),"Locations"));
		 for(int i = 0; i < mOptions.length; i++){
			 curr = mOptions[i];
			 navDrawerItems.add(new ListItem(this.getLayoutInflater(), curr));
		 }
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.left_drawer);

	        // set a custom shadow that overlays the main content when the drawer opens
	        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	        // set up the drawer's list view with items and click listener
	        /*
	        mDrawerList.setAdapter(new ArrayAdapter<Location>(this,
	                R.layout.drawer_list_item, mOptions));*/
	        TwoTextArrayAdapter adapter = new TwoTextArrayAdapter(this, navDrawerItems);
	        mDrawerList.setAdapter(adapter);
	        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this));
	        if(mDrawerList.getCount() > 1){
	        	mDrawerList.setChoiceMode(1);
	        	mDrawerList.setItemChecked(1, true);
	        	ListItem tmpItem = (ListItem) mDrawerList.getItemAtPosition(1);
	        	((MyApplication)this.getApplication()).setLocationID(tmpItem.loc.getLocationID());
	        	refreshContent();
	        }
        	
        	
	        // enable ActionBar app icon to behave as action to toggle nav drawer
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);

	        // ActionBarDrawerToggle ties together the the proper interactions
	        // between the sliding drawer and the action bar app icon
	        mDrawerToggle = new ActionBarDrawerToggle(
	        		this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description for accessibility */
	                R.string.drawer_close  /* "close drawer" description for accessibility */
	                ) {
	            public void onDrawerClosed(View view) {
	            	getActionBar().setTitle(mTitle);
	            	invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }

	            public void onDrawerOpened(View drawerView) {
	            	getActionBar().setTitle(mDrawerTitle);
	            	invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);
	        
	}

	public void refreshContent(){
		String locationID = ((MyApplication)this.getApplication()).getLocationID();
		
		if(locationID.compareTo("") == 0)
			return;
		
		
	}
	public void btnOutageMap_onClick(View view){
    	/*
    	Intent intent = new Intent(this, OutageMap.class);
    	startActivity(intent);*/

        WebServiceAPI api = new WebServiceAPI();
        api.getOutages();
    }
	
	
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        /*case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;*/
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    

    private void selectItem(int position) {
        // update the main content by replacing fragments
    	/*
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();*/

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    
    	MainMenu mainMenu;
    	public DrawerItemClickListener(MainMenu mainMenu){
    		super();
			this.mainMenu = mainMenu;
    	}
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			ImageView c = (ImageView) v.findViewById(R.id.listitem_imageview);
			c.setSelected(true);
			mainMenu.mDrawerLayout.closeDrawer(mDrawerList);
	        WebServiceAPI.refreshMainMenu(mainMenu);
		}
    }
    
    public interface Item {
        public int getViewType();
        public View getView(LayoutInflater inflater, View convertView);
    }
    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

    public class TwoTextArrayAdapter extends ArrayAdapter<Item> {
        private LayoutInflater mInflater;

        
        public TwoTextArrayAdapter(Context context, List<Item> items) {
            super(context, 0, items);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getViewTypeCount() {
            return RowType.values().length;

        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).getViewType();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(mInflater, convertView);
        }
    }
    public class Header implements Item {
        private final String         name;

        public Header(LayoutInflater inflater, String name) {
            this.name = name;
        }

        @Override
        public int getViewType() {
            return RowType.HEADER_ITEM.ordinal();
        }

        @Override
        public View getView(LayoutInflater inflater, View convertView) {
            View view;
            if (convertView == null) {
                view = (View) inflater.inflate(R.layout.nav_header, null);
                // Do some initialization
            } else {
                view = convertView;
            }

            TextView text = (TextView) view.findViewById(R.id.separator);
            text.setText(name);

            return view;
        }

    }
    public class ListItem implements Item {
        private final String         str1;
        private final String         str2;
        private final Location loc;
        private final LayoutInflater inflater;

        public ListItem(LayoutInflater inflater, Location loc) {
            this.str1 = loc.getLocationID();
            this.str2 = loc.getAddress();
            this.inflater = inflater;
            this.loc = loc;
        }

        @Override
        public int getViewType() {
            return RowType.LIST_ITEM.ordinal();
        }

        @Override
        public View getView(LayoutInflater inflater, View convertView) {
            View view;
            
            if (convertView == null) {
                view = (View) inflater.inflate(R.layout.nav_listitem, null);
                // Do some initialization
            } else {
                view = convertView;
                
            }
            
            TextView text1 = (TextView) view.findViewById(R.id.list_content1);
            TextView text2 = (TextView) view.findViewById(R.id.list_content2);
            text1.setText(str1);
            text2.setText(str2);

            return view;
        }

    }
}
