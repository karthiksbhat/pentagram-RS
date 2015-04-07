package org.kkns.core;

import java.io.IOException;
import java.io.InputStream;

public class RegisterUser extends Thread{
	String identity=null;
	
	public RegisterUser(String id)
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
		Process proc;
		try {
			proc = rt.exec(new String[]{"bash","-c","curl -s -G --data-urlencode 'script=add_vertex(identity)' --data-urlencode 'load=add_new_user' --data-urlencode 'params={\"identity\":\""+identity+"\"}' http://localhost:7474/tp/gremlin/execute"});
			proc.waitFor();
			output=getStreamWrapper(proc.getInputStream(),"OUTPUT");
			System.out.println("Registered user:"+output.getMessage()+"\n");
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}
