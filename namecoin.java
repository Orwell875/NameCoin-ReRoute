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

public class namecoin {
		
	// Check if it is a .bit query
	public static boolean Check(byte[] query) {
		boolean ret = false;
		int pos = findbit(query); // find bit in query
		if (pos != -1) { // We have a bit domain
			ret = true;
		}		
        return ret;
	}	

	public static int findbit(byte[] query) {
	int ret = -1;
		for (int i=0; i <= query.length-50; i++) {			
			String test = ((char) query[i]) + ""+((char) query[i+1]) + ""+ ((char) query[i+2])+ ""+((char) query[i+3]);
		
			if (test.compareTo((char)3 + "bit") == 0) {
				ret = i+4;
				break;
			} 
		} 		
	
        return ret;
	} 
}
