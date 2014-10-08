package alexiuscrow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.base.Joiner;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

public class DistanceMatrix extends AbstractUrlParams {

	public static URL findNearestPointURL(String[] currentPlace, String[] destionations) throws MalformedURLException{
		
		StringBuilder url = new StringBuilder();
		url.append("http://maps.googleapis.com/maps/api/distancematrix/json?");
		Map<String, String> param = Maps.newHashMap();
		param.put("sensor", "false");
		param.put("language", "ru");
		param.put("mode", "walking");
		
		param.put("origins", Joiner.on("|").join(currentPlace));
		param.put("destinations", Joiner.on("|").join(destionations));
		
		url.append(encodeParams(param));
		
		return new URL(url.toString());
	}
	
	public static RemotePoint findNearestPoint(String[] currentPlace, final String[] destionations) throws MalformedURLException, IOException{
		JSONObject response = JsonReader.read(findNearestPointURL(currentPlace, destionations).toString());
		JSONObject location = response.getJSONArray("rows").getJSONObject(0);
		final JSONArray array = location.getJSONArray("elements");
		JSONObject result = Ordering.from(new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				Integer duration1 = getDurationValue(o1); 
				Integer duration2 = getDurationValue(o2); 
				return duration1.compareTo(duration2);
			}
			
			private int getDurationValue(JSONObject obj){
				return obj.getJSONObject("duration").getInt("value");
			}
		}).min(new AbstractIterator<JSONObject>() {
			private int index = 0;
			
			@Override
			protected JSONObject computeNext() {
				JSONObject result;
				if(index < array.length()){
					String destionation = destionations[index];
					result = array.getJSONObject(index++);
					result.put("address", destionation);
				}
				else{
					result = endOfData();
				}
				return result;
			}
		});
		
		return new RemotePoint(result.getString("address"), result.getJSONObject("distance").getString("text"), 
				result.getJSONObject("duration").getString("text"));
	}
	
}
