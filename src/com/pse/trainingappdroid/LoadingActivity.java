package com.pse.trainingappdroid;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends Activity {
   
	//Déclarations 
	TextView loading;
	ProgressBar loadingProgressBar;
	int loadingProgress = 90;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        
        loadingProgressBar=(ProgressBar)findViewById(R.id.loadingProgressBar);
        new Thread(myThread).start();
    }
        
 
    
    private Runnable myThread = new Runnable(){

    	 public void run() {
    	  // TODO Auto-generated method stub
    	  while (loadingProgress<100){
    	   try{
    	    myHandle.sendMessage(myHandle.obtainMessage());
    	    Thread.sleep(1000);
    	    
    	   }
    	   catch(Throwable t){
    	   }
    	   }
    	  if (loadingProgress>=40){
    		  Intent myIntent = new Intent(LoadingActivity.this, LoginActivity.class);
    		  LoadingActivity.this.startActivity(myIntent);
    	  	  finish();
    	  }
    	  }
    	      Handler myHandle = new Handler(){

    	       @Override
    	       public void handleMessage(Message msg) {
    	        // TODO Auto-generated method stub
    	        loadingProgress= loadingProgress + 20;  
    	        loadingProgressBar.setProgress(loadingProgress); 
    	       }
    	                     	     
    	      };      
        
    };
};