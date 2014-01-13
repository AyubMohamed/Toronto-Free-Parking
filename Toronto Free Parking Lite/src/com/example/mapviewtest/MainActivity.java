package com.example.mapviewtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;


import android.view.Menu;


import com.example.MapViewTest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity  {
  static final LatLng TORONTO = new LatLng(43.7000, -79.4000);
  static final LatLng SAUGA = new LatLng(43.6000, -79.6500);
  private GoogleMap map;
  private ArrayList<ArrayList<String>> markers = new ArrayList<ArrayList<String>>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
        .getMap();
    /*Marker hamburg = map.addMarker(new MarkerOptions().position(TORONTO)
        .title("TDot"));
    Marker kiel = map.addMarker(new MarkerOptions()
        .position(SAUGA)
        .title("sauga")
        .sniPpet("sauga is cool"));
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.ic_launcher)));*/

    // Move the camera instantly to hamburg with a zoom of 15.
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(TORONTO, 15));

    // Zoom in, animating the camera.
    map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
    
    try {
    	String[] s = this.getAssets().list("");
    	InputStream iS = getAssets().open("info.txt");
    	BufferedReader reader = new BufferedReader(new InputStreamReader(iS));
		String line;
		while ((line = reader.readLine()) != null) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(line);
			temp.add(reader.readLine());
			temp.add(reader.readLine());
			temp.add(reader.readLine());
			temp.add(reader.readLine());
			markers.add(temp);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    for(ArrayList<String> array: markers){
    	double lat = Double.parseDouble(array.get(0));
    	double lng = Double.parseDouble(array.get(1));
    	Marker temp = map.addMarker(new MarkerOptions()
        .position(new LatLng(lat, lng))
        .title(array.get(3))
        .snippet(array.get(2)));
    	
    	if(array.get(4).equals("1")){
    		temp.setIcon(BitmapDescriptorFactory
    	            .fromResource(R.drawable.ic_icon_type_1_));
    	}
    	if(array.get(4).equals("2")){
    		temp.setIcon(BitmapDescriptorFactory
    	            .fromResource(R.drawable.ic_icon_type_2_));
    	}
    	if(array.get(4).equals("3")){
    		temp.setIcon(BitmapDescriptorFactory
    	            .fromResource(R.drawable.ic_icon_type_3_));
    	}
    	
    }
      
  }
  
   

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  public void loadFile(File file) throws FileNotFoundException {
		
		if (file.exists()) {
			String path = file.getPath();
			Scanner scanner = new Scanner(new FileInputStream(path));
			
			while (scanner.hasNext()) {
				String date = scanner.nextLine();
				String title = scanner.nextLine();
				String hadith = scanner.nextLine();
				ArrayList<String> array = new ArrayList<String>();
				array.add(date);
				array.add(title);
				array.add(hadith);
				//pastHadith.add(array); 
				}
			scanner.close();
			}
			
	}
} 