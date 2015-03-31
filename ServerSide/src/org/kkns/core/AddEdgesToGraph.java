package org.kkns.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AddEdgesToGraph extends Thread{
	String identity=null;
	File appFile=new File("/home/nitin/Desktop/MINOR_PROJECT/neo4j-community-2.1.7/files/"+identity+"_apps.txt");
	public AddEdgesToGraph(String id)
	{
		identity=id;
		this.start();
	}

	private StreamWrapper getStreamWrapper(InputStream is, String type){
        return new StreamWrapper(is, type);
	}
	
	public void run()
	{
		Runtime rt = Runtime.getRuntime();
		StreamWrapper output;
		StreamWrapper error;
		try {
			Process proc = rt.exec(new String[]{"bash","-c","curl -s -G --data-urlencode 'script=add_edges(identity)' --data-urlencode 'load=add_graph_edges' --data-urlencode 'params={\"identity\":\""+identity+"\"}' http://localhost:7474/tp/gremlin/execute"});
			//String s;
			proc.waitFor();
			output=getStreamWrapper(proc.getInputStream(), "OUTPUT");
			error=getStreamWrapper(proc.getErrorStream(),"ERROR");
			
			/*
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
	 
	        // read the output from the command
	        System.out.println("Here is the standard output of the command:\n");
	        while ((s = stdInput.readLine()) != null) {
	            System.out.println(s);
	        }
	             
	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	             System.out.println(s);
	        }
*/			
			System.out.println("Here is the standard output of the command:"+output.getMessage()+"\n");
			System.out.println("Here is the standard error of the command (if any):"+error.getMessage()+"\n");
			
	
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		appFile.delete();
		
				
	}	
}
