import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;


public class Checking {

	public static void main(String[] args) throws JSONException {
		String a="{\"results\":[\"VLC\",\"app3\"],\"success\":true}";
		String b="{\"results\":[],\"success\":true}";
		JsonParserFactory jsonParserFactory=JsonParserFactory.getInstance();
		JSONParser jsonParser=jsonParserFactory.newJsonParser();
		Map results=jsonParser.parseJson(a);
		JSONObject j=new JSONObject(a);
		JSONArray array=j.getJSONArray("results");
		System.out.println(array.get(1));
		JSONObject j1=new JSONObject(b);
		JSONArray a1=j1.getJSONArray("results");
		if(a1.length()==0){System.out.println("here");}

		
	}

}
