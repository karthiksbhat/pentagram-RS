package org.kkns.core.algo.collaborative;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.kkns.core.StreamWrapper;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class CollaborativeFilter extends Thread{

	String user_id;
	String recommendationResults;
	
	public CollaborativeFilter(String id) {
		
		if(id!=null)
			user_id=id;
		recommendationResults=new String();
		this.start();
	}
	
	public Map getResults()
	{
		JsonParserFactory jsonParserFactory=JsonParserFactory.getInstance();
		JSONParser jsonParser=jsonParserFactory.newJsonParser();
		Map resultsJson=jsonParser.parseJson(recommendationResults);
		if ((Boolean)resultsJson.get("success")==false)
			return null;
		else if((Boolean)resultsJson.get("success")==true)
			return resultsJson;
		else 
			return null;
	}
	private StreamWrapper getStreamWrapper(InputStream is, String type){
        return new StreamWrapper(is, type);
	}
	
	public void run()
	{
		
		
		Runtime rt = Runtime.getRuntime();
        StreamWrapper output;
        try {
			Process proc = rt.exec(new String[]{"bash","-c","curl -s -G --data-urlencode 'script=getRecommendation(identity)' --data-urlencode 'load=collaborative_filter' --data-urlencoded 'params={\"identity\":\""+user_id+"\"} http://localhost:7474/tp/gremlin/execute"});
        	output = getStreamWrapper(proc.getInputStream(), "OUTPUT");
		    proc.waitFor();
			recommendationResults=output.getMessage();
		} catch (IOException|InterruptedException e) {
			e.printStackTrace();
			recommendationResults=null;
		}
		
	}

}
