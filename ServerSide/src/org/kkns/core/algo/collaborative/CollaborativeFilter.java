package org.kkns.core.algo.collaborative;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kkns.core.StreamWrapper;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class CollaborativeFilter {

	String user_id;
	String recommendationResults;
	Long startTime=null;
	
	public CollaborativeFilter(String id,Long timestamp) {
		
		if(id!=null)
			user_id=id;
		recommendationResults=new String();
		startTime=timestamp;
		this.run();
	}
	
	@SuppressWarnings("all")
	public String getResults()
	{
		JsonParserFactory jsonParserFactory=JsonParserFactory.getInstance();
		JSONParser jsonParser=jsonParserFactory.newJsonParser();
		Map resultsJson=null;
		
		if(recommendationResults!=null && recommendationResults.length()!=0)
		{	resultsJson=jsonParser.parseJson(recommendationResults);
		}
		else
			return null;
		if (resultsJson.get("success").equals("false")){
			return null;}
		else if(resultsJson.get("success").equals("true"))
		{
			HashMap<Integer,Integer> ids_map = new HashMap<Integer,Integer>();
			ArrayList reco_apps_ids = (ArrayList) resultsJson.get("results");
			for(int i = 0;i<reco_apps_ids.size();i++)
			{
				reco_apps_ids.set(i,(Integer.decode((String)reco_apps_ids.get(i))));
			}
			for(int i =0 ;i<reco_apps_ids.size();i++)
			{
				if (!ids_map.containsKey(reco_apps_ids.get(i)))
				{
					ids_map.put((Integer)reco_apps_ids.get(i), 1);
				}
				else
				{
					ids_map.put((Integer)reco_apps_ids.get(i), ids_map.get((Integer)reco_apps_ids.get(i))+1);
				}
			}
			if(ids_map.isEmpty()){return null;}
			System.out.println(ids_map.toString());
			
			Runtime rt = Runtime.getRuntime();
	        StreamWrapper output;
			ArrayList<String> outputArray=new ArrayList<String>();
			for (Map.Entry<Integer, Integer> entry : ids_map.entrySet()) {
				Integer key = entry.getKey();
				Integer val = entry.getValue();
				
				if(val > 1)
				{
					try {
						Process proc = rt.exec(new String[]{"bash","-c","curl -s -G --data-urlencode 'script=g.v(identity).appname' --data-urlencode 'params={\"identity\":\""+key+"\"}' http://localhost:7474/tp/gremlin/execute"});
						output = getStreamWrapper(proc.getInputStream(), "OUTPUT");
			        	proc.waitFor();
						System.out.println("Finally"+output.getMessage());
						JSONObject tempObject=new JSONObject(output.getMessage());
						JSONArray tempArray=tempObject.getJSONArray("results");
						outputArray.add(tempArray.getString(0));
					} catch (IOException | InterruptedException | JSONException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(outputArray.size()>0)
				return outputArray.toString();
			else
				return null;
		}
		else {
			return null;
		}
	}
	private StreamWrapper getStreamWrapper(InputStream is, String type){
        return new StreamWrapper(is, type);
	}
	
	public void run()
	{
		
		
		Runtime rt = Runtime.getRuntime();
        StreamWrapper output;
        try {
			Process proc = rt.exec(new String[]{"bash","-c","curl -s -G --data-urlencode 'script=getRecommendation(identity)' --data-urlencode 'load=collaborative_filter_one_line' --data-urlencode 'params={\"identity\":\""+user_id+"\"}' http://localhost:7474/tp/gremlin/execute"});
			output = getStreamWrapper(proc.getInputStream(), "OUTPUT");
        	proc.waitFor();
			System.out.println("here in run \n"+output.getMessage());
		    recommendationResults=output.getMessage();
		    System.out.println("Time taken for recommendation::"+(System.currentTimeMillis()-startTime)); 
		} catch (IOException|InterruptedException e) {
			e.printStackTrace();
			recommendationResults=null;
		}
		
	}

}
