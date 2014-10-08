package alexiuscrow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONObject;

public class JsonReader {
	public static JSONObject read(String url) throws MalformedURLException, IOException{
		InputStream stream = new URL(url).openStream();
		
		try{
			BufferedReader rd = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
			String jsonStr = readAll(rd);
			rd.close();
			return new JSONObject(jsonStr);
		}
		finally{
			stream.close();
		}
		
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp = 0;
		
		while((cp = rd.read()) != -1){
			sb.append((char) cp);
		}
		
		return sb.toString();
	}
}
