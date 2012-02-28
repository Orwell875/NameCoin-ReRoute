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

import java.net.InetAddress;

class main
{
	private static dnsCli client;
	private static dnsServ server;
	
  public static void main(String args[]) throws Exception
       {
			System.out.println("NameCoin ReRoute v1.00a1"); // Still dont have a good name for it
			                    	     
			/*byte[] tull = new byte[]{(byte)1,(byte)2};
			dnsAnsw answ = new dnsAnsw(tull);

			byte[] tq = answ.getMin();
		    String sentence = new String(tq); 
	        System.out.println("RECEIVED: " + sentence);  */
	        
	        /*System.out.println("");  
			tq = answ.getSPM();
		    sentence = new String(tq); 
	        System.out.println("RECEIVED: " + sentence);   */   
	      
	        
		//	JSON.Update("getinfo();");
		   server = new dnsServ();
		   client = new dnsCli();
		   
			// Used for sending .bit request to a namecoin DNS server     	
			dnsCli altDns=null;
	    	try {   	   		    		
	    		InetAddress altIP = InetAddress.getByAddress(new byte[]{(byte)216, (byte)231, (byte)140, (byte)69});    		
	    		altDns = new dnsCli(altIP);
	    	} catch(Exception e) {
	    		System.err.println("Something wrong with the ip addr");
	    	} 	   
			
	    	server.setClient(client, altDns); 
			client.setServer(server);       
			altDns.setServer(server); 			 
	   }
	   
}

