package com.hogarth.googlemap;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class VenueDetailsFragment extends Activity {
	
	private TextView uid;
	private TextView name;
	private TextView contactPhone;
	private TextView contactTwitter;
	private TextView localAddress;
	private TextView city;
	private TextView state;
	private TextView crossStreet;
	private TextView postCode;
	private TextView country;
	private TextView latitude;
	private TextView longitude;
	private TextView distance;
	private TextView SiteURL;
	private TextView Hours;
	private TextView Price;
	private TextView smokingRatingTotal;
	private TextView smokingRatingCount;
	private TextView Description;
	private TextView outsideradius;
	private ImageView PhotoPrefix;
	private TextView Categories;
	private TextView distanceUnit;
	private String PhotoImageURL;
	
	
	//public dataBaseItem dbItem;
	
	public static  VenueDetailsFragment newInstance() {
		return new  VenueDetailsFragment();
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_venue_details);
		
		 Bundle extras = getIntent().getExtras();
		 
		 uid = (TextView)findViewById(R.id.uid);
			name = (TextView)findViewById(R.id.Name);
			contactPhone = (TextView)findViewById(R.id.Phone);
			contactTwitter =(TextView)findViewById(R.id.twitter);
			localAddress =(TextView)findViewById(R.id.Address);
			city = (TextView)findViewById(R.id.city);
			state = (TextView)findViewById(R.id.state);
			crossStreet = (TextView)findViewById(R.id.crossstreet);
			postCode = (TextView)findViewById(R.id.postcode);
			country = (TextView)findViewById(R.id.country);
			latitude= (TextView)findViewById(R.id.latitude);
			longitude = (TextView)findViewById(R.id.longitude);
			distance = (TextView)findViewById(R.id.distance);
			SiteURL = (TextView)findViewById(R.id.siteurl);
			Hours = (TextView)findViewById(R.id.hours);
			Price = (TextView)findViewById(R.id.price);
			smokingRatingTotal = (TextView)findViewById(R.id.smokingratingtotal);
			smokingRatingCount = (TextView)findViewById(R.id.smokingratingcount);
			Description = (TextView)findViewById(R.id.description);
			outsideradius = (TextView)findViewById(R.id.outsideradius);
			PhotoPrefix = (ImageView)findViewById(R.id.imgPhotoPrefix);
			Categories = (TextView)findViewById(R.id.categories);
			distanceUnit = (TextView)findViewById(R.id.distanceunit);
			
		 showText(extras);
		 
	}
	
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View layout = inflater.inflate(R.layout.activity_venue_details, container, false);
		
		uid = (TextView)layout.findViewById(R.id.uid);
		name = (TextView)layout.findViewById(R.id.Name);
		contactPhone = (TextView)layout.findViewById(R.id.Phone);
		contactTwitter =(TextView)layout.findViewById(R.id.twitter);
		localAddress =(TextView)layout.findViewById(R.id.Address);
		city = (TextView)layout.findViewById(R.id.city);
		state = (TextView)layout.findViewById(R.id.state);
		crossStreet = (TextView)layout.findViewById(R.id.crossstreet);
		postCode = (TextView)layout.findViewById(R.id.postcode);
		country = (TextView)layout.findViewById(R.id.country);
		latitude= (TextView)layout.findViewById(R.id.latitude);
		longitude = (TextView)layout.findViewById(R.id.longitude);
		distance = (TextView)layout.findViewById(R.id.distance);
		SiteURL = (TextView)layout.findViewById(R.id.siteurl);
		Hours = (TextView)layout.findViewById(R.id.hours);
		Price = (TextView)layout.findViewById(R.id.price);
		smokingRatingTotal = (TextView)layout.findViewById(R.id.smokingratingtotal);
		smokingRatingCount = (TextView)layout.findViewById(R.id.smokingratingcount);
		Description = (TextView)layout.findViewById(R.id.description);
		outsideradius = (TextView)layout.findViewById(R.id.outsideradius);
		PhotoPrefix = (ImageView)layout.findViewById(R.id.imgPhotoPrefix);
		Categories = (TextView)layout.findViewById(R.id.categories);
		distanceUnit = (TextView)layout.findViewById(R.id.distanceunit);
		
		 Bundle extras = getActivity().getIntent().getExtras();
		
		 showText(extras);
		 
		return layout;
	} */
	
	private void showText(Bundle extras) {
		
		uid.setText(extras.getString("uid"));
		name.setText(extras.getString("Name"));
		contactPhone.setText(extras.getString("ContactPhone"));
		contactTwitter.setText(extras.getString("Twitter"));
		localAddress.setText(extras.getString("Address"));
		city.setText(extras.getString("City"));
		state.setText(extras.getString("State"));
		crossStreet.setText(extras.getString("CrossStreet"));

		PhotoImageURL = (extras.getString("PhotoPrefix"));
		
		//PhotoPrefix.setImageBitmap(new DownloadImage().execute(PhotoImageURL));
		
		//new DownloadImage.execute("");
		DownloadImage dn = new DownloadImage();
		dn.execute("");
		
	}
		

	 
	 public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

	        @Override
	        protected Bitmap doInBackground(String... url) {
	            
	        	URL imageURL = null;
	        	Bitmap mImage_va = null;
	        	
			    if (PhotoImageURL != null) {
			        try {
			            imageURL = new URL(PhotoImageURL);         
			            HttpURLConnection connection = (HttpURLConnection) imageURL
			                    .openConnection();
			            connection.setDoInput(true);
			            connection.connect();
			            InputStream inputStream = connection.getInputStream();
			            mImage_va = BitmapFactory.decodeStream(inputStream);// Convert to bitmap
			           
			           
			        } catch (IOException e) {
			            e.printStackTrace();
			            
			        }
			    } else {
			        //set any default
			    }
			    return mImage_va;
	        	
	            
	        }

	        @Override
	        protected void onPostExecute(Bitmap result) {
	        	
	        	PhotoPrefix.setImageBitmap(result);
	        }

	        @Override
	        protected void onPreExecute() {}

	        @Override
	        protected void onProgressUpdate(Void... values) {}
	    }

}
