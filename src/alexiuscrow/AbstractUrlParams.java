package alexiuscrow;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

public abstract class AbstractUrlParams {
	protected static String encodeParams(Map<String, String> params){
		final String urlParams = Joiner.on("&").join(
				Iterables.transform(params.entrySet(), new Function<Entry<String, String>, String>() {

					@Override
					public String apply(Entry<String, String> input) {
						StringBuffer sb = new StringBuffer();
						try {
							sb.append(input.getKey());
							sb.append("=");
							sb.append(URLEncoder.encode(input.getValue(), "UTF-8"));
							
							return sb.toString();
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException(e);
						}
					}
				})
				);
		return urlParams;
	}
}
