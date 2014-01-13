package com.AMohamed.torontofreeparkinglite;

import com.google.android.gms.ads.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	static final LatLng TORONTO = new LatLng(43.6725, -79.4);
	private GoogleMap map;
	private ArrayList<ArrayList<String>> markers = new ArrayList<ArrayList<String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				"01df15e51acd8869").build();
		adView.loadAd(adRequest);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(TORONTO, 15));
		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

		try {
			// Get all marker information and load it
			InputStream is = getAssets().open("info.txt");
			load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Make all the markers
		for (ArrayList<String> array : markers) {
			double lat = Double.parseDouble(array.get(0));
			double lng = Double.parseDouble(array.get(1));
			Marker temp = map.addMarker(new MarkerOptions()
					.position(new LatLng(lat, lng)).title(array.get(3))
					.snippet(array.get(2)));

			if (array.get(4).equals("1")) {
				temp.setIcon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_icon_type_1_));
			}
			if (array.get(4).equals("2")) {
				temp.setIcon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_icon_type_2_));
			}
			if (array.get(4).equals("3")) {
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

	public void load(InputStream is) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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

	}
}