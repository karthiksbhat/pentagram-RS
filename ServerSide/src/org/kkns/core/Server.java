package org.kkns.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.kkns.core.algo.collaborative.CollaborativeFilter;

import au.com.bytecode.opencsv.CSVReader;
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
		
		//if GET request is received, client is either 1.asking for recommendations  2.Registering 
		if(Method.GET.equals(method))
		{
			String identity=null;
			if(params.containsKey("identity"))
				identity=params.get("identity");
			if(identity==null)
			{
				return new Response(Response.Status.BAD_REQUEST,MIME_PLAINTEXT,"Params:identity missing");
			}
			else
			{
				//request for get recommendation
				if(params.get("action")==null || params.get("action").length()==0)
				{
					return new Response(Response.Status.BAD_REQUEST,MIME_PLAINTEXT,"Params:action missing");
				}
				else if(params.get("action").equals("get_reco"))
				{	
					System.out.println(identity+identity.length());
					CollaborativeFilter collaborativeFilter=new CollaborativeFilter(identity);
					@SuppressWarnings("all")
					Map results=collaborativeFilter.getResults();
					if (results==null)
					{
						System.out.println("hi i am sleepy!");
						return new Response(Response.Status.NO_CONTENT,MIME_PLAINTEXT,"TRY_LATER");
					}
					else
					{
						return new Response(Response.Status.OK,MIME_PLAINTEXT,results.toString());
					}
					
				}
				//request for registration
				else if(params.get("action").equals("register"))
				{
					@SuppressWarnings("unused")
					RegisterUser registerUser=new RegisterUser(identity);
					return new Response(Response.Status.OK,MIME_PLAINTEXT,"Added succesfully");
					
					
				}
				else
				{
					return new Response(Response.Status.BAD_REQUEST,MIME_PLAINTEXT,"Params:action missing");
				}
			}
		}
		
		
		//if POST request is received then the client is pushing the logs
		else if(Method.POST.equals(method))
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
				if(params.get("action")==null || params.get("action").length()==0)
				{
					return new Response(Response.Status.BAD_REQUEST,MIME_PLAINTEXT,"Params:action missing");
				}
				else if(params.get("action").equals("pushing_logs"))
				{
						File file=new File(files.get("fileupload"));
						try {
							File appFile=new File("/home/nitin/Desktop/MINOR_PROJECT/neo4j-community-2.1.7/files/"+identity+"_apps.txt");
							FileWriter appFileWriter=new FileWriter(appFile);
							File tempFile=new File("/home/nitin/Desktop/MINOR_PROJECT/neo4j-community-2.1.7/files/"+identity+".csv");
							FileWriter tempFileWriter=new FileWriter(tempFile);
							
							tempFileWriter.write("USER,PID,CPU,MEM,VSZ,RSS,TTY,STAT,START,TIME,PROGRAM"+"\n");
								
							InputStream is=new GZIPInputStream(new FileInputStream(file));
							BufferedReader br=new BufferedReader(new InputStreamReader(is));
							String currentline;
							br.readLine();
							while((currentline=br.readLine())!=null)
							{
								tempFileWriter.write(currentline+"\n");
							}
							
							tempFileWriter.close();
							is.close();
							
							CSVReader csvReader=new CSVReader(new FileReader(tempFile),',','"',1);
							String[] nextLine;
							while ((nextLine = csvReader.readNext()) != null) {
						         if (nextLine != null) {
						             /*System.out.println(Arrays.toString(nextLine));*/
						        	 if(nextLine[nextLine.length-1].contains("\0\0"))
						        	 {
						        		 String truncated=nextLine[nextLine.length-1].substring(0, nextLine[nextLine.length-1].indexOf('\0') );
						        		 appFileWriter.write(truncated+"\n");
						        	 }
						        	 else
						        	 {
						        		 appFileWriter.write(nextLine[nextLine.length-1]+"\n");
						        	 }
						         }
						    }
							
							appFileWriter.close();
							@SuppressWarnings("unused")
							AddEdgesToGraph addEdgesToGraph=new AddEdgesToGraph(identity);
							tempFile.delete();
							csvReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return new Response(Response.Status.OK,MIME_PLAINTEXT,"Added");
					
				}
				else
				{
					return new Response(Response.Status.BAD_REQUEST,MIME_PLAINTEXT,"Params:action missing");
				}
			}
		}
		else
			return new Response(Response.Status.OK,MIME_PLAINTEXT,"Default response");
	}

	

}
