// Anders B�gild, September 2012

package com.pse.trainingappdroid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

// This is a use-once object because thread and socket are use-once.

@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class BtServerThreaded{

	private BtThread mBtThread = new BtThread();
	
	private BluetoothAdapter adapter = null;
	private BluetoothDevice device = null;
	private BluetoothSocket btsocket = null;
	private Context context = null;
	
	int stream_id;
	
	private static final UUID RFCOMM_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //128bit UUID for Serial Port Protocol (SPP)
	private static final String BT_NAME = "BluetoothAppServer";
	
	private static final Boolean D = true;
	private static final String TAG = "BtServerThreaded";
	
	public static final String BT_NEW_DATA_INTENT = "com.pse.trainingappdroid.BT_NEW_DATA_INTENT";
	public static final String BT_NEW_DATA_INTENT_EXTRA_BT_DATA = "bt_data";
	public static final String BT_NEW_DATA_INTENT_EXTRA_BT_DATA_STREAM_ID= "bt_id";
	
	public BtServerThreaded(Context context, String mac, int id){
		
		this.stream_id = id;
		this.adapter = BluetoothAdapter.getDefaultAdapter();
		this.device = adapter.getRemoteDevice(mac);
		this.context = context;

		try {
			btsocket = device.createInsecureRfcommSocketToServiceRecord(RFCOMM_UUID);
		} catch (IOException e) {
			if (D) Log.e(TAG,"could not create BT socket" );
			e.printStackTrace();
		}

		
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (D) Log.i(TAG,"Garbage collector reclaiming.. stream_id=" + stream_id);
		
	}

	public void startServer(){
		adapter.cancelDiscovery();
		mBtThread.start();
	}
	
	
	public void stopServer(){
		mBtThread.runThread = false;
	}
	
	
	private class BtThread extends Thread{
		
		private Boolean runThread = true;

		@Override
		public void run() {
			super.run();
			
			if (D) Log.i(TAG,"running server thread stream_id=" + stream_id);
			
			BluetoothServerSocket btserver = null;
			
			// Create BT server
			try {
				btserver = adapter.listenUsingInsecureRfcommWithServiceRecord(BT_NAME, RFCOMM_UUID);
			} catch (IOException e) {
				if (D) Log.e(TAG,"could not create BT server");
				if (D) e.printStackTrace();
				return;
			}

			
			if (D) Log.i(TAG,"server thread stream_id=" + stream_id + " listening...");
			// Start listening for incoming connections (blocking)
			try {
				btsocket = btserver.accept();
			} catch (IOException e) {
				if (D) Log.e(TAG,"could receive incoming BT connections");
				if (D) e.printStackTrace();
				return;
			}

			if (D) Log.i(TAG,"server thread stream_id=" + stream_id + " got connection!!");
			
			//InputStream inputstream = null;
			OutputStream outputstream = null;
			
			try {
				outputstream = btsocket.getOutputStream();
			} catch (IOException e) {
				if (D) Log.e(TAG,"could not get outputstream from socket");
				if (D) e.printStackTrace();
				return;
			}
			
			//BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
			BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream));
			String line;

			while ( this.runThread == true ) {
				try {
				
					if (D) Log.i(TAG,"sending...");
					bufferedwriter.write("Hello from server");
					bufferedwriter.newLine();
					bufferedwriter.flush();
					
					sleep(500);

				} catch (IOException e) {
					if (D) Log.e(TAG,"could not write line to buffered writer");
					if (D) e.printStackTrace();
					this.runThread = false;
				} catch (InterruptedException e){
					if (D) Log.e(TAG,"sleep interrupted");
					this.runThread = false;
				}
				
			}
			
			// close down nicely, 
			try {
				bufferedwriter.close();
		    	outputstream.close();
		    	btsocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (D) Log.i(TAG,"thread for server stream_id=" + stream_id + " done");
		}
	
	}
}
