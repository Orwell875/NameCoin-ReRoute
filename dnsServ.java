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

import java.io.*;
import java.net.*;

public class dnsServ extends Thread 
{
	private InetAddress IP;
	private dnsCli client; // the client used
	private DatagramSocket serverSocket;
	private int port=0;
	private dnsCli altDns = null;
	
    public dnsServ() {
    	try {
    		this.IP = InetAddress.getByAddress(new byte[]{(byte)127, (byte)0, (byte)0, (byte)1});
    	
    	} catch(Exception e) {
    		System.err.println("Something wrong with the ip addr");
    	}    	
		
    	start();
	}
	
	public void setClient(dnsCli client, dnsCli altDns) {
		this.client = client;
		this.altDns = altDns;
	}
	
	public synchronized void sendPacket(DatagramPacket p) {     
	    try {
	    	p.setAddress(IP); // the new IP for the packet	    	
	    	p.setPort(port);	  	    
	    	serverSocket.send(p);
	    } catch(Exception e) {
    		System.err.println("5876876");
    	} 
	}
	
	public void run() {
		try {
          serverSocket = new DatagramSocket(53);               
		 } catch(Exception e) {
	    		System.err.println("5");
	    	} 
		 
             byte[] receiveBuffer = new byte[1024];
         	byte[] t=null;
            while(true)
              { 
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                 
               	try {
                   serverSocket.receive(receivePacket);
                   t = receivePacket.getData();
               	} catch(Exception e) {
    	    		System.err.println(e.toString());
    	    	} 
              
               	port = receivePacket.getPort(); // get the data we need to reply
               	IP = receivePacket.getAddress();
           
	             boolean dropName = false;  // true if we want to deactivate namecoin   
	             boolean useDNSserv = false;  // 
            
              	if (!namecoin.Check(receivePacket.getData()) || dropName) {
                	client.sendPacket(receivePacket);  // Send the packet trough the client object
                	}
                	else 
                	{           
                		if (useDNSserv) {
                			altDns.sendPacket(receivePacket);  // Send the packet trough the client object
                		} else {
                			dnsAnsw svar = new dnsAnsw(t); // Make the DNS Answer
                			byte[] as = svar.getMin();                			
                		
							// Create the UDP packet and send it back
                			DatagramPacket sendPacket = new DatagramPacket(as, as.length, IP, port);
                		    sendPacket(sendPacket);   
                		}
                	}                  	                           	 				                        	
                } 
            
         //     	serverSocket.close();
         //    	System.out.println("server closed");
       }
}