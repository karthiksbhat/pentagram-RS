package org.kkns.core;

import java.io.IOException;
import java.util.Map;
import org.kkns.core.algo.collaborative.CollaborativeFilter;
import fi.iki.elonen.NanoHTTPD;

public class Server extends NanoHTTPD{

	public Server() {
		super(6666);
	}
    
	public static void main(String[] args) throws IOException
	{
		ServerRunner.run(Server.class);
	}
	
	@Override
	public Response serve(String uri, Method method, Map<String, String> headers,
			Map<String, String> params, Map<String, String> files) {
		
		//if GET request is received, client is asking for recommendations 
		if(Method.GET.equals(method))
		{
			String identity=null;
			if(params.containsKey("identity"))
				identity=params.get("identity");
			if(identity==null)
			{
				return new Response(Response.Status.BAD_REQUEST,MIME_PLAINTEXT,"Params:Identity missing");
			}
			else
			{
				CollaborativeFilter collaborativeFilter=new CollaborativeFilter(identity);
				Map results=collaborativeFilter.getResults();
				if (results==null)
				{
					return new Response(Response.Status.NO_CONTENT,MIME_PLAINTEXT,"TRY_LATER");
				}
				else
				{
					return new Response(Response.Status.OK,MIME_PLAINTEXT,results.toString());
				}
			}
		}
		else
			return new Response(Response.Status.OK,MIME_PLAINTEXT,"Chill it's working");
	}

	

}
