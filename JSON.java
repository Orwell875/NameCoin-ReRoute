/* 
Copyright (c) 2012, Orwell
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the 
following conditions are met:
- Redistributions of source code must retain the above copyright notice, this list of conditions and the following 
  disclaimer.
- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the 
  following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE 
USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JSON {
	//Question
	private String qdomain;
	private String sub=""; // the subdomain of qdomain
	private String domain;
	private String dom_end; // the .bit part; */	
	
	//Answer
	private String answer="";
	
	public JSON(String qdomain) {
		this.qdomain = qdomain;
		SplitQ();
		Check();
	}
	
	public String getAnsw() {
		return answer;
	}
	
	// Slitt opp spm domenet
	private void SplitQ() {
		
		qdomain = qdomain.replace(".", ";"); // . apparently dosent work with split
		String[] temp = qdomain.split(";");
	
		if (temp.length == 3) {
			sub = temp[0];
			domain = temp[1];
			dom_end = temp[2]; // the .bit part; */			
		}
		else
		{
			domain = temp[0];
			dom_end = temp[1]; // the .bit part; */	
		}		
	}
	
	// Get what ever is inside ""
	private  String getCont(String inn) {
		String ut;
		ut = inn.substring(inn.indexOf("\"")+1);	
		ut = ut.substring(0,ut.indexOf("\"")-1);			
		return ut;
	}
	
   // dotbit -  "map\":{\"\":{\"ns\":[\"ns0.web-sweet-web.net\",\"ns1.web-sweet-web.net\"]
    //gee -     "map\": {\"\": \"176.9.26.234\"
  //wikileaks - "map\": {\"\": \"88.80.16.63\"
	//bbk -             {\"\":\"212.232.51.96\",\"www\":\"212.232.51.96\"                                                                                                                       

private  void decode(String str) {	
	if (str.substring(0, 22).compareTo("{\"result\":[{\"name\":\"d/") != 0) return; // Not good 
	if (str.substring(22, str.indexOf("\",\"v")).compareTo(domain) != 0) return; // The domain is right
	
	// Next few lines are just to delete junk
	String map = str.substring(str.indexOf("\"map\\\":")+7 , str.indexOf("}}"));
	
	String siste = map.substring(map.indexOf("{\\\"\\\":")+6);
	System.out.println(siste);
	
	// Split the answers
	String[] temp = siste.split(",");
	
// Go trough the answers and split questin:answer	
String nullsvar = ""; // The answer if there is no sub domain
for(int i =0; i < temp.length ; i++) {
	// Spm:svar
	String spm = "";
	String svar = "";
	if (temp[i].contains(":")) {
		spm = temp[i].substring(0,temp[i].indexOf(":"));	
		spm = getCont(spm);
		svar = temp[i].substring(temp[i].indexOf(":"));	
		svar = getCont(svar);
	}
	else
	{
		svar = getCont(temp[i]);
	}

	if (spm.compareTo("") == 0) {
		nullsvar = svar;
	}

	if (spm.compareTo(sub) == 0) {
		 answer = svar;		
	}
	}

	if (answer.compareTo("") == 0) answer = nullsvar;
	answer = answer.replace(" ", ""); // take away spaces
	System.out.println(qdomain + " = " + answer);
}
	 
	private void Check() {		
			  StringBuffer answer = new StringBuffer();
			  try {	            
		            // Send the request
		            URL url = new URL("http://127.0.0.1:8336");
		           
		            URLConnection conn = url.openConnection();
		        
		            conn.setRequestProperty("Authorization", "Basic dGVzdDp0ZXN0"); // user:pas base64 encoded
		            conn.setRequestProperty("Content-Type", "application/json");
		            conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
		    
		         
		           	conn.setDoOutput(true);
		            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		            
		            //write parameters	            
		            writer.write("{\"method\": \"name_scan\", \"params\": [\"d/"+domain+"\", \"1\"], \"id\":0}");
		            	 
		            writer.flush();
		            writer.close();  
		            		            	
		            // Get the response
		          	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		            String line;
		            while ((line = reader.readLine()) != null) {
		                answer.append(line);
		            }	           
		            reader.close();             
		                		           		            
		        } catch (MalformedURLException ex) {
		            ex.printStackTrace();		           
		        } catch (IOException ex) {		        
		            ex.printStackTrace();
		        }		       
		     
		       decode(answer.toString());
		}

		  
}
