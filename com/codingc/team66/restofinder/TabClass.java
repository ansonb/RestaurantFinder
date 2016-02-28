package com.codingc.team66.restofinder;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TabClass extends TabActivity{
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       setContentView(R.layout.tabclass);
	        Intent intent=getIntent(); 
	        TabHost tabHost = getTabHost();
	        
	        Display display = getWindowManager().getDefaultDisplay();
	        int width = display.getWidth();
	        int height = display.getHeight();
	        
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	         
	        // Tab for restinfo
	        TabSpec infospec = tabHost.newTabSpec("info");
	        // setting Title and Text for the Tab
	        infospec.setIndicator("Restaurant info offers and reviews");
	        Intent info = new Intent(this, RestInfoActivity.class);
	        info.putExtra("RestaurantId", (String)intent.getSerializableExtra("RestaurantId"));
			info.putExtra("Restaurantaddr", (String)intent.getSerializableExtra("Restaurantaddr"));
			info.putExtra("Restaurantname", (String)intent.getSerializableExtra("Restaurantname"));
			info.putExtra("Restaurantrating", (String)intent.getSerializableExtra("Restaurantrating"));
	        infospec.setContent(info);
	        
	         
	        // Tab for rating ,reviewing and recommending
	        TabSpec ratespec = tabHost.newTabSpec("rate");        
	        ratespec.setIndicator("rate review recommend");
	        Intent rate = new Intent(this, ParticularRestaurantActivity.class);
	        rate.putExtra("RestaurantId", (String)intent.getSerializableExtra("RestaurantId"));
			rate.putExtra("Restaurantaddr", (String)intent.getSerializableExtra("Restaurantaddr"));
			rate.putExtra("Restaurantname", (String)intent.getSerializableExtra("Restaurantname"));
			rate.putExtra("Restaurantrating", (String)intent.getSerializableExtra("Restaurantrating"));
	        ratespec.setContent(rate);
	         
	        // Adding all TabSpec to TabHost
	        tabHost.addTab(ratespec); 
	        tabHost.addTab(infospec); 
	        
	        // Setting tab size
	        tabHost.setCurrentTab(0);
	        tabHost.getTabWidget().getChildAt(0).setLayoutParams(new
                  LinearLayout.LayoutParams((width/2)-2,height/13));
	        tabHost.getTabWidget().getChildAt(1).setLayoutParams(new
                  LinearLayout.LayoutParams((width/2)-2,height/13));
	    }
	 
	 
}
