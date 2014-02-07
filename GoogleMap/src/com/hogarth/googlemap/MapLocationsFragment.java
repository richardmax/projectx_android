package com.hogarth.googlemap;


import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MapLocationsFragment extends Fragment implements OnClickListener, ConnectionCallbacks,
OnConnectionFailedListener,
LocationListener,OnMarkerClickListener, OnInfoWindowClickListener{
	
	//private static final String Location_URL = "http://hwwmobileapp.cloudapp.net:3000/location/get/47.37/8.54/10/0";
	private static final String Location_URL = "http://api.v-marketing.co.uk/location/get/47.37/8.54/10/0";
	//private static final String Location_URL = "http://hwwmobileapp.cloudapp.net:3000/location/all ";
	private double TempLatitude = 47.37;
	private double TempLongitude = 8.54;
	private static AsyncHttpClient sClient = new AsyncHttpClient();
	
	private ArrayList<dataBaseItem> ObjectsToShow;
	
	
	protected MapView mMapView;
	
	private GoogleMap googlemap;
	private LocationClient mLocationClient;
	private boolean firstload = true;
	
	private Location mCurrentLocation;
	private LocationRequest mLocationRequest;
	private float currentZoom = 9.5f;
	
	/**
     * Define the number of items visible when the carousel is first shown.
     */
    private static final float INITIAL_ITEMS_COUNT = 3.5F;
    
    private LinearLayout mCarouselContainer;
	
	public static MapLocationsFragment newInstance() {
		return new MapLocationsFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View layout = inflater.inflate(R.layout.maplocationsfragment, container, false);
		//((android.widget.FrameLayout) layout.findViewById(R.id.mapFrame)).addView(mMapView);
		mCarouselContainer = (LinearLayout) layout.findViewById(R.id.carousel);
	
		this.Construct_carousel();
		
		mLocationRequest = LocationRequest.create();
		//mLocationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);
		//mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		//mLocationRequest.setInterval(UPDATE_INTERVAL);
		//mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		
	    setUpLocationClientIfNeeded();
        mLocationClient.connect();
        setUpMapIfNeeded();
		
		return layout;

	}
	
	 //Setup location here
	  private void setUpLocationClientIfNeeded() {
	        if (mLocationClient == null) {
	            mLocationClient = new LocationClient(
	                    this.getActivity().getApplicationContext(),
	                    this,  // ConnectionCallbacks
	                    this); // OnConnectionFailedListener
	        }
	       
	    } 
	  
	  private void setUpMapIfNeeded() {
	        // Do a null check to confirm that we have not already instantiated the map. Says google,
		   
	        if (googlemap == null) {
	            // Try to obtain the map from the SupportMapFragment.
	            googlemap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapfrag))
	                    .getMap();
	            
	            // Check if we were successful in obtaining the map.
	            if (googlemap != null) {
	            	
	                setUpMap();
	            }
	            else{
	            	// We are unable to get map. Users 
	            	GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
	            }
	        }
	        else{
	        	setUpMap();
	        }
	    }
	  
	  
	  private void setUpMap() {
			 
		  googlemap.setMyLocationEnabled(true); 
		  googlemap.animateCamera(CameraUpdateFactory.zoomBy(currentZoom));
			
			 googlemap.setOnMarkerClickListener(this);
			 googlemap.setOnInfoWindowClickListener(this);
			 
			 
			 googlemap.setOnCameraChangeListener(new OnCameraChangeListener() {
					
				    @Override
				    public void onCameraChange(CameraPosition pos) {
				        if (pos.zoom != currentZoom){
				            currentZoom = pos.zoom;
				            
				           
				        }
				        //Log.d("Joseph", Float.toString(currentZoom) + ":" +  Float.toString(pos.zoom) );
				        if(mLocationClient.isConnected()){
				        //mCurrentLocation = mLocationClient.getLastLocation();
				        // Change this when going live....................
				        mCurrentLocation.setLatitude(TempLatitude);
				        mCurrentLocation.setLongitude(TempLongitude);
				        }
				       
				    }

				});
			 
		    }
	  
	  @Override
		public void onConnected(Bundle arg0) {
			
			
			mLocationClient.requestLocationUpdates(mLocationRequest, this);
			
			if(firstload){
				
				mCurrentLocation = mLocationClient.getLastLocation();
				// Remove following 2 lines when going live....................
		        mCurrentLocation.setLatitude(TempLatitude);
		        mCurrentLocation.setLongitude(TempLongitude);
				refreshLocationWithData();
				firstload = false;
			}
			//Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
			 
		}
	  

	  private void refreshLocationWithData() {
			try {
				 // Load Data from Database async
				 GetData();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		}
	  
	  
	  public void GetData() throws JSONException, UnsupportedEncodingException {
			
		 	
			/*String Latitude = Double.toString(mCurrentLocation.getLatitude());
			String Longitude = Double.toString(mCurrentLocation.getLongitude());
			String NumberOfRecords = "10";
			String StartFrom = "0";*/
			
			//Log.d("Joseph", Latitude + "," + Longitude);
			
			/*StringEntity entity = new StringEntity(String.format(
					"{latitude:'%s',longitude:'%s'}", Latitude, Longitude));
			
			
			entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json; charset=UTF-8")); */
			sClient.setTimeout(5000);
			sClient.get(Location_URL, new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(String response) {
							
							ObjectsToShow = new ArrayList<dataBaseItem>();  
							try {
								//TODO Stop progress here;
								JSONObject myJsonObject = new JSONObject(response);
								
							if(myJsonObject.getJSONArray("data").length() == 0){
								
								Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
								
								
							}
							else{
								
								// Get all Suppliers in this area
								
								JSONArray r = myJsonObject.getJSONArray("data");
								
								
								for (int i = 0; i < r.length(); i++) {
									
								    JSONObject obj = r.getJSONObject(i); //r.getString(i); 
					
								    dataBaseItem s = new dataBaseItem(obj);
								    ObjectsToShow.add(s);
								    
								    
								}
								
								
								}
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							super.onSuccess(response);
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							
							Log.d("Joseph", arg1);
							//showAlertBox("UPP", "Cannot save to server, try later"); 						
							super.onFailure(arg0, arg1);
							
							
						}
						@Override
					     public void onFinish() {
							
							
							
							 if(ObjectsToShow.size() > 0){
								 
									for(int i = 0; i < ObjectsToShow.size(); i++){
										
										Double Lat = Double.valueOf(ObjectsToShow.get(i).getLatitude());
										Double Long = Double.valueOf(ObjectsToShow.get(i).getLongitude());
										String Name = ObjectsToShow.get(i).getName() ;
										String Snippet = ObjectsToShow.get(i).getLocalAddress();
										
										LatLng location = new LatLng(Lat,Long);
										
										// Set up the markers
										
										googlemap.addMarker(new MarkerOptions()
							            .position(location)
							            .title(Name)
							            .snippet(Snippet)	
								        .icon(BitmapDescriptorFactory.fromResource(R.drawable.light_map_marker)));
										
										LatLng l = new LatLng( mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
											 googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(l , currentZoom));
										 
									}
									
									
							 }
							 
							 else{
								   // Even if we don't have any venue in the area still move the camera
								 LatLng l = new LatLng( mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
								 googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(l , currentZoom));
							 }
							 
							 //for testing  __________________________________________________
							 /*ArrayList<LatLng> SupplierList = new ArrayList<LatLng>();
							 SupplierList.add(ENFIELD);
							 SupplierList.add(TOTTENHAM);
							 SupplierList.add(CHINGFORD);
							 SupplierList.add(EDMONTON_GREEN);
							 SupplierList.add(WALTHAM_CROSS);
							 
							 for(int i = 0; i < SupplierList.size(); i ++){
									
									//builder.include(SupplierList.get(i));
									googlemap.addMarker(new MarkerOptions()
						            .position(SupplierList.get(i))
						            .title("uup")
						            .snippet("Power Available here")
						            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
								}*/
							
							
					     }

						

					});
			
			
		}

	  
	  @Override
		public void onLocationChanged(Location location) {
			
		// Remove following 2 lines when going live....................
		  googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude()) , currentZoom));
		  //googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()) , currentZoom));
			
		}
	
	

	@Override
	public void onDisconnected() {
		 //Toast.makeText(getActivity(), "Disconnected. Please re-connect.",
	     //           Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());

      // set title
      alertDialogBuilder.setTitle("Connection failure");

      // set dialog message
      alertDialogBuilder.setMessage("Cannot connect to play store try later").setCancelable(false)
              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int id) {
                      
                  }
              });

      // create alert dialog
      AlertDialog alertDialog = alertDialogBuilder.create();

      // show it
      alertDialog.show();

		
	}

	
	
	
	
	
	 @Override
	    public void onPause() {
	        super.onPause();
	        if (mLocationClient != null) {
	            mLocationClient.disconnect();
	        } 
	    }
	 @Override
		public void onResume() {
		        
		        if (mLocationClient == null){
		        	//setUpLocationClientIfNeeded();
		        	 firstload = true;
				       mLocationClient.connect();
		        }
		        
		       /* try {
			          SupportMapFragment fragment = (SupportMapFragment) getActivity()
			                                            .getSupportFragmentManager().findFragmentById(
			                                            		R.id.mapfrag);
			          
			          if (fragment == null) getFragmentManager().beginTransaction().add(fragment,"added").commit();

			      } catch (IllegalStateException e) {
			          //handle this situation because you are necessary will get 
			          //an exception here :-(
			      } */
		        
		        super.onResume();
		        
	 }
		  
	 
		  @Override
		public void onStop() {
		        // Disconnecting the client invalidates it.
			  if (mLocationClient.isConnected()) {
				  mLocationClient.disconnect();  
			  }
		        
		        super.onStop();
		    }
		  @Override
		  public void onDestroyView() {
		      super.onDestroyView();
		      try {
		          SupportMapFragment fragment = (SupportMapFragment) getActivity()
		                                            .getSupportFragmentManager().findFragmentById(
		                                            		R.id.mapfrag);
		          if (fragment != null) getFragmentManager().beginTransaction().remove(fragment).commit();
		          // Men it took me hours to find out you need the 2 lines below. If you comment those out
		          // When this is teared down you cannot assign location to the google map, all you get is the map of the world
		          fragment = null;
		          googlemap = null;

		      } catch (IllegalStateException e) {
		          
		    	  
		      } 
		  }




	@Override
	public void onInfoWindowClick(Marker marker) {
		
		int pos = 0;
		
		for (int i = 0; i < ObjectsToShow.size(); i++){
			
			dataBaseItem s = ObjectsToShow.get(i);
			String Latitude = Double.toString(marker.getPosition().latitude);
			String Longitude = Double.toString(marker.getPosition().longitude);
			String LocationName = marker.getTitle();
			
			if (s.getLatitude().equalsIgnoreCase(Latitude)  &&  s.getLongitude().equalsIgnoreCase(Longitude) && s.getName().equalsIgnoreCase(LocationName)){
			
				pos = i;
				break;
			}
			
		}
		
		// Update: I have not found a way around this view this article
		//http://stackoverflow.com/questions/14931955/android-google-map-api-v2-applied-inside-a-fragment
		// Fragment won't work here as you cannot start an activity from and activity we are using 
		//Toast.makeText(getActivity(), "Info Clicked", Toast.LENGTH_LONG).show();
		//?? don't work
		/* if (mLocationClient != null) {
	            mLocationClient.disconnect();
		 } */
		//??? don't work
		//LinearLayout LLay = (LinearLayout) this.getActivity().findViewById(R.id.getfuellayout);
		//LLay.removeAllViewsInLayout();
		
		//?? don't work
		/*SupportMapFragment fragment = (SupportMapFragment) getActivity()
              .getSupportFragmentManager().findFragmentById(
              		R.id.mapfrag);
		if (fragment != null) getFragmentManager().beginTransaction().remove(fragment).commit(); */
		
		// --------------------- The map fragment cannot be removed as it was created in xml
		// --------------------- As I don't have the luxury of time this can be tried latter
		// -------------------- For now i will be using activity
		
		//String Name = ObjectsToShow.get(pos).getName();
		/*Fragment f = new VenueDetailsFragment();
		
		FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.mapfrag, f);
		ft.addToBackStack(null);
		ft.commit();  */
		
		
		
		String  uid = ObjectsToShow.get(pos).getUid();
		String  Name = ObjectsToShow.get(pos).getName();
		String  ContactPhone = ObjectsToShow.get(pos).getContactPhone();
		String Twitter = ObjectsToShow.get(pos).getContactTwitter();
		String Address = ObjectsToShow.get(pos).getLocalAddress();
		String City = ObjectsToShow.get(pos).getCity();
		String  State = ObjectsToShow.get(pos).getState();
		//String  WebsiteAddressText 
		
		// Also check if store is closed
		String  CrossStreet = ObjectsToShow.get(pos).getCrossStreet();
		String  PostCode = ObjectsToShow.get(pos).getPostCode();
		String  Country = ObjectsToShow.get(pos).getCountry();
		String  Latitude = ObjectsToShow.get(pos).getLatitude();
		String  Longitude = ObjectsToShow.get(pos).getLongitude();
		String  Distance = ObjectsToShow.get(pos).getDistance();
		String  SiteURL = ObjectsToShow.get(pos).getSiteURL();
		
		String  Hours = ObjectsToShow.get(pos).getHours();
		String  Price = ObjectsToShow.get(pos).getPrice();
		String  smokingRatingTotal = ObjectsToShow.get(pos).get_smokingRatingTotal();
		String  smokingRatingCount = ObjectsToShow.get(pos).get_smokingRatingCount();
		String  Description = ObjectsToShow.get(pos).getDescription();
		boolean  isOutsideradius = ObjectsToShow.get(pos).isOutsideradius();
		String  PhotoPrefix = ObjectsToShow.get(pos).getPhotoPrefix();
		String  Categories = ObjectsToShow.get(pos).getCategories();
		String  distanceUnit = ObjectsToShow.get(pos).get_distanceUnit();
		
		Intent intent = new Intent(getActivity(), VenueDetailsFragment.class);
		intent.putExtra("uid", uid);
		intent.putExtra("Name", Name);
		intent.putExtra("ContactPhone", ContactPhone);
		intent.putExtra("Twitter", Twitter);
		intent.putExtra("Address", Address);
		intent.putExtra("City", City);
		intent.putExtra("State", State);
		intent.putExtra("CrossStreet", CrossStreet);
		
		intent.putExtra("PostCode", PostCode);
		intent.putExtra("Country",Country);
		intent.putExtra("Latitude", Latitude);
		intent.putExtra("Longitude", Longitude);
		intent.putExtra("Distance", Distance);
		intent.putExtra("SiteURL", SiteURL);
		intent.putExtra("Hours", Hours);
		intent.putExtra("Price", Price);
		intent.putExtra("smokingRatingTotal", smokingRatingTotal);
		intent.putExtra("smokingRatingCount", smokingRatingCount);
		intent.putExtra("Description", Description);
		intent.putExtra("isOutsideradius", isOutsideradius);
		intent.putExtra("PhotoPrefix", PhotoPrefix);
		intent.putExtra("Categories", Categories);
		intent.putExtra("distanceUnit", distanceUnit);
		
		
		
		
	    startActivity(intent);
		
	}
	

	@Override
	public boolean onMarkerClick(Marker marker) {
		
		//Toast.makeText(getActivity(), "Marker Clicked", Toast.LENGTH_LONG).show();
		return false;
	}

	@Override
	public void onClick(View v) {
		
		
		
	}
	private void Construct_carousel(){
		
		 // Compute the width of a carousel item based on the screen width and number of initial items.
        final DisplayMetrics displayMetrics = new DisplayMetrics();
       getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int imageWidth = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);
        
        // Images
     		int gallery_grid_Images[]={R.drawable.puppy_01,R.drawable.puppy_02,R.drawable.puppy_03,R.drawable.puppy_04,R.drawable.puppy_05,R.drawable.puppy_06,R.drawable.puppy_07,R.drawable.puppy_08};
     	// Populate the carousel with items
     		for(int i=0;i<gallery_grid_Images.length;i++)
            {
            //  This will create dynamic image view and add them to carousel
    			ImageView image = new ImageView(getActivity().getApplicationContext());
    			image.setImageResource(gallery_grid_Images[i]);
    			image.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageWidth));
    			
    			mCarouselContainer.addView(image);
            }	
     		
	}
	

	

}
