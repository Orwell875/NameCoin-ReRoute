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

public class dnsCli extends Thread 
{
	private InetAddress IP;
	private DatagramSocket clientSocket;
	private dnsServ server; // the server used
	
    public dnsCli() {
    	try {    	
    		// 178.32.31.41 namecoin dns for testing
    		// 8.8.8.8 google dns
    		IP = InetAddress.getByAddress(new byte[]{(byte)8, (byte)8, (byte)8, (byte)8});
    		System.out.println(IP.toString());
    	} catch(Exception e) {
    		System.err.println("Something wrong with the ip addr");
    	} 
    	connect();
    
	}
    
    public dnsCli(InetAddress IP) {
    	// 178.32.31.41 namecoin dns for testing
    		// 8.8.8.8 google dns
    		this.IP = IP;    		
    		connect();
	}
	
	public void setServer(dnsServ server) {
		this.server = server;		
	}
	
	public synchronized void sendPacket(DatagramPacket p) {
		p.setAddress(IP); // the new IP for the pakcet
		p.setPort(53);	
		 
	 	try {
	 		clientSocket.send(p);	
	 	} catch(Exception e) {
    		System.err.println("2");
    	}  	
	}
	
	public void connect() {
		try {			
			clientSocket = new DatagramSocket();
		} catch(Exception e) {
    		System.err.println("3");
    	} 

		 start(); // Start the Client thread
	}
	
	public void run() {           
    System.out.println("Client running");
    byte[] receiveData = new byte[1024];
	byte[] t=null;
    while(true) {
    	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    	try {
    		clientSocket.receive(receivePacket);
    		server.sendPacket(receivePacket);  // Send the packet trough the server object
    		t = receivePacket.getData();
    	} catch(Exception e) {
    		System.err.println("4");
    	}      
    /*    String str = new String(t);
          System.out.println("clinet:>"+str);
    	
          dnsAnsw.ByteToText(t); 
	      server.sendPacket(receivePacket);  */ 
	   }
	   
 
	}
}