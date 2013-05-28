package com.pse.trainingappdroid;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;


public class TabsActivity extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        
        
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        
        intent = new Intent().setClass(this, TabsMeActivity.class);
        spec =  tabHost.newTabSpec("Form").setIndicator( "Me?").setContent(intent);
        tabHost.addTab(spec);
        
    //    intent = new Intent().setClass(this, TabsAchActivity.class);
    //    spec = tabHost.newTabSpec("Form").setIndicator("Achievements").setContent(intent);
    //    tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, TabsHistoryActivity.class);
        spec = tabHost.newTabSpec("Form").setIndicator("History").setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, TabsSettingsActivity.class);
        spec = tabHost.newTabSpec("Form").setIndicator("Settings").setContent(intent);
	 	tabHost.addTab(spec);
	 	
	 	tabHost.setCurrentTab(0);
 }	  
	

}
