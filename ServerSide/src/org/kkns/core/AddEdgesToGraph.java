package org.kkns.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AddEdgesToGraph extends Thread{
	String identity=null;
	File appFile;
	
	public AddEdgesToGraph(String id)
	{
		identity=id;
		appFile=new File("/home/nitin/Desktop/MINOR_PROJECT/neo4j-community-2.1.7/files/"+identity+"_apps.txt");
		this.start();
	}

	private StreamWrapper getStreamWrapper(InputStream is, String type){
        return new StreamWrapper(is, type);
	}
	
	public void run()
	{
		Runtime rt = Runtime.getRuntime();
		StreamWrapper output;
		try {
			Process proc = rt.exec(new String[]{"bash","-c","curl -s -G --data-urlencode 'script=add_edges(identity)' --data-urlencode 'load=add_graph_edges' --data-urlencode 'params={\"identity\":\""+identity+"\"}' http://localhost:7474/tp/gremlin/execute"});
			//String s;
			proc.waitFor();
			output=getStreamWrapper(proc.getInputStream(), "OUTPUT");
			System.out.println("Added edge to user:"+output.getMessage()+"\n");
			
	
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		appFile.delete();
		
				
	}	
}
