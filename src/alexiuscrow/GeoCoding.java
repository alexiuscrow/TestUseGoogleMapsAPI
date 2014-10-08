package alexiuscrow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import com.google.common.collect.Maps;

public class GeoCoding extends AbstractUrlParams{
	
	public static URL getLatLngUrl(String address) throws MalformedURLException{
		StringBuilder url = new StringBuilder();
		url.append("http://maps.googleapis.com/maps/api/geocode/json?");
		
		Map<String, String> param = Maps.newHashMap();
		param.put("sensor", "false");
		param.put("address", address);
		param.put("language", "ru");
		
		url.append(encodeParams(param));
		
		return new URL(url.toString());
	}
	
	public static Map<String, Double> getLatLng(String address) throws MalformedURLException, IOException{
		JSONObject response = JsonReader.read(getLatLngUrl(address).toString());
		JSONObject location = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
		Map<String, Double> mapLocation = Maps.newHashMap();
		mapLocation.put("lat", location.getDouble("lat"));
		mapLocation.put("lng", location.getDouble("lng"));
		return mapLocation;
	}
	
	public static URL getAddressUrl(Double lat, Double lng) throws MalformedURLException{
		StringBuilder url = new StringBuilder();
		url.append("http://maps.googleapis.com/maps/api/geocode/json?");
		
		StringBuilder latlng = new StringBuilder();
		latlng.append(lat);
		latlng.append(",");
		latlng.append(lng);
		
		Map<String, String> param = Maps.newHashMap();
		param.put("sensor", "false");
		param.put("latlng", latlng.toString());
		param.put("language", "ru");
		
		url.append(encodeParams(param));
		
		return new URL(url.toString());
	}
	
	public static String getAddress(Double lat, Double lng) throws MalformedURLException, IOException{
		JSONObject response = JsonReader.read(getAddressUrl(lat, lng).toString());
		JSONObject location = response.getJSONArray("results").getJSONObject(0);
		return location.getString("formatted_address");
	}
	
}
