package org.kkns.core.algo.collaborative;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.kkns.core.StreamWrapper;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class CollaborativeFilter {

	String user_id;
	String recommendationResults;
	
	public CollaborativeFilter(String id) {
		
		if(id!=null)
			user_id=id;
		recommendationResults=new String();
		this.run();
	}
	
	@SuppressWarnings("all")
	public Map getResults()
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
			System.out.println(ids_map.toString());
			
			Runtime rt = Runtime.getRuntime();
	        StreamWrapper output;
			
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
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			return resultsJson;
		}
		else {
			return null;}
	}
	private StreamWrapper getStreamWrapper(InputStream is, String type){
        return new StreamWrapper(is, type);
	}
	
	public void run()
	{
		
		
		Runtime rt = Runtime.getRuntime();
        StreamWrapper output;
        try {
        	System.out.println(user_id+"\n");
			Process proc = rt.exec(new String[]{"bash","-c","curl -s -G --data-urlencode 'script=getRecommendation(identity)' --data-urlencode 'load=collaborative_filter_one_line' --data-urlencode 'params={\"identity\":\""+user_id+"\"}' http://localhost:7474/tp/gremlin/execute"});
			//Process proc = rt.exec(new String[]{"bash","-c","curl -s -G --data-urlencode 'script=g.E'  http://localhost:7474/tp/gremlin/execute"});
			output = getStreamWrapper(proc.getInputStream(), "OUTPUT");
        	proc.waitFor();
			System.out.println("here in run \n"+output.getMessage());
		    recommendationResults=output.getMessage();
		} catch (IOException|InterruptedException e) {
			e.printStackTrace();
			recommendationResults=null;
		}
		
	}

}
