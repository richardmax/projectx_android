package com.hogarth.googlemap;
import org.json.JSONObject;

public class dataBaseItem {
	
	//private String _dbaseid;
	private String _uid;
	private String _name;
	private String _contactPhone;
	private String _contactTwitter;
	private String _localAddress;
	private String _city;
	private String _state;
	private String _crossStreet;
	private String _postCode;
	private String _country;
	private String _latitude;
	private String _longitude;
	private String _distance;
	private String _SiteURL;
	private String _Hours;
	private String _Price;
	private String _smokingRatingTotal;
	private String _smokingRatingCount;
	private String _Description;
	private boolean _outsideradius;
	private String _PhotoPrefix;
	private String _PhotoSuffix;
	private String _Categories;
	private String _distanceUnit;
	private String _errorMessage;
	
	public dataBaseItem(JSONObject items){
		
		initialiseFromJSONObject(items);
	}
	
	
	
	protected void initialiseFromJSONObject(JSONObject items) {
		
		_errorMessage = null;
		try{
			
			//_dbaseid = items.getString("_id");
			_uid = items.getString("id");
			_name = items.getString("name");
			_contactPhone = items.getString("contactPhone");
			_contactTwitter = items.getString("contactTwitter");
			_localAddress = items.getString("localAddress");
			_city = items.getString("city");
			_state = items.getString("state");
			_crossStreet = items.getString("crossStreet");
			_postCode = items.getString("postCode");
			_country =	items.getString("country");
			_latitude = items.getString("latitude");
			_longitude = items.getString("longitude");
			
			_SiteURL = items.getString("SiteURL");
			_Hours = items.getString("Hours");
			_Price = items.getString("Price");
			_smokingRatingTotal = items.getString("smokingRatingTotal");
			_smokingRatingCount = items.getString("smokingRatingCount");
			_Description = items.getString("Description");
			
			String flag = items.getString("outsideradius");
			if (Integer.parseInt(flag) == 1 ){
			
				_outsideradius = true;
			}
			else
				
			{
				_outsideradius = false;
			}
			
			_PhotoPrefix = items.getString("PhotoPrefix");
			_PhotoSuffix = items.getString("PhotoSuffix");
			_Categories = items.getString("Categories");
			_distance = items.getString("distance");
			_distanceUnit =	items.getString("distanceUnit");
		}
		
		catch(Exception ex){
			_errorMessage = ex.getMessage();
			
		}
		
		
		
	}
	
	/*public String getDbaseid() {
		return _dbaseid;
	}
	public void setDbaseid(String dbaseid) {
		this._dbaseid = dbaseid;
	}*/
	public String getUid() {
		return _uid;
	}
	public void setUid(String uid) {
		this._uid = uid;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	public String getContactPhone() {
		return _contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this._contactPhone = contactPhone;
	}
	public String getContactTwitter() {
		return _contactTwitter;
	}
	public void setContactTwitter(String contactTwitter) {
		this._contactTwitter = contactTwitter;
	}
	public String getLocalAddress() {
		return _localAddress;
	}
	public void setLocalAddress(String localAddress) {
		this._localAddress = localAddress;
	}
	public String getCity() {
		return _city;
	}
	public void setCity(String city) {
		this._city = city;
	}
	public String getState() {
		return _state;
	}
	public void setState(String state) {
		this._state = state;
	}
	public String getCrossStreet() {
		return _crossStreet;
	}
	public void setCrossStreet(String crossStreet) {
		this._crossStreet = crossStreet;
	}
	public String getPostCode() {
		return _postCode;
	}
	public void setPostCode(String postCode) {
		this._postCode = postCode;
	}
	public String getCountry() {
		return _country;
	}
	public void setCountry(String country) {
		this._country = country;
	}
	public String getLatitude() {
		return _latitude;
	}
	public void setLatitude(String latitude) {
		this._latitude = latitude;
	}
	public String getLongitude() {
		return _longitude;
	}
	public void setLongitude(String longitude) {
		this._longitude = longitude;
	}
	public String getDistance() {
		return _distance;
	}
	public void setDistance(String distance) {
		this._distance = distance;
	}
	public String getSiteURL() {
		return _SiteURL;
	}
	public void setSiteURL(String siteURL) {
		_SiteURL = siteURL;
	}
	public String getHours() {
		return _Hours;
	}
	public void setHours(String hours) {
		_Hours = hours;
	}
	public String getPrice() {
		return _Price;
	}
	public void setPrice(String price) {
		_Price = price;
	}
	public String get_smokingRatingTotal() {
		return _smokingRatingTotal;
	}



	public void set_smokingRatingTotal(String _smokingRatingTotal) {
		this._smokingRatingTotal = _smokingRatingTotal;
	}



	public String get_smokingRatingCount() {
		return _smokingRatingCount;
	}



	public void set_smokingRatingCount(String _smokingRatingCount) {
		this._smokingRatingCount = _smokingRatingCount;
	}
	public String getDescription() {
		return _Description;
	}
	public void setDescription(String description) {
		_Description = description;
	}
	public boolean isOutsideradius() {
		return _outsideradius;
	}
	public void setOutsideradius(boolean outsideradius) {
		this._outsideradius = outsideradius;
	}
	public String getPhotoPrefix() {
		return _PhotoPrefix;
	}
	public void setPhotoPrefix(String photoPrefix) {
		_PhotoPrefix = photoPrefix;
	}
	public String getPhotoSuffix() {
		return _PhotoSuffix;
	}
	public void setPhotoSuffix(String photoSuffix) {
		_PhotoSuffix = photoSuffix;
	}
	public String getCategories() {
		return _Categories;
	}
	public void setCategories(String categories) {
		_Categories = categories;
	}
	
	public String get_errorMessage() {
		return _errorMessage;
		
		
	}
	public String get_distanceUnit() {
		return _distanceUnit;
	}



	public void set_distanceUnit(String _distanceUnit) {
		this._distanceUnit = _distanceUnit;
	}
	
	


}
