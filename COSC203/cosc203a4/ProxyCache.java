/**
 * ProxyCache.java - Simple caching proxy
 *
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class ProxyCache {
    /** Port for the proxy */
    private static int port;
    /** Socket for client connections */
    private static ServerSocket socket;
	private static Map<String, HttpResponse> webcache;

    /** Create the ProxyCache object and the socket */
    public static void init(int p) {
	port = p;
		try {
			socket = new ServerSocket(port);/* create a ServerSocket for communication with a client */
			webcache = new HashMap<String, HttpResponse>(); /* create a HashMap for storing caches */
		} catch (IOException e) {
			System.out.println("Error creating socket: " + e);
			System.exit(-1);
		}
    }

    public static void handle(Socket client) {
		Socket server = null;
		HttpRequest request = null;
		HttpResponse response = null;

		/* Process request. If there are any exceptions, then simply
		* return and end this request. This unfortunately means the
		* client will hang for a while, until it timeouts. */

		/* Read request */
		try {
			BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()))/*  read request from socket. You need to use InputStreamReader to convert the data from a byte stream to a character stream  */;
			request = new HttpRequest(fromClient)/* create an HttpRequest object based on the request received from socket, check HttpRequest.java for details about the HttpRequest Class */;
			System.out.println("Request received from client: " +request.URI);
		} catch (IOException e) {
			System.out.println("Error reading request from client: " + e);
			return;
		}

		/* response: cached or not cached */
		if (webcache.containsKey(request.URI)) {
			/*send the response from cache */
			System.out.println("Find a cached version, respond to client with the Cached version");
			try {
				DataOutputStream toClient =  new DataOutputStream(client.getOutputStream());/* create a DataOutputStream for writing to the client socket */

				HttpResponse r = webcache.get(request.URI);/* read the cached web object from webcache */
				toClient.writeBytes(r.toString());/* send the cached web object to the client */
				// toClient.write(r.body);

				toClient.write(r.body, 0, r.length);


				client.close(); /* close client socket */
				
			}catch (IOException e) {
				System.out.println("Error writing response to client: " + e);
			}	
		} else {
			/* Send request to the origin server */
			System.out.println("Can't find a cached version in the proxy");
			try {
				/* Open socket and write re COMPLETE ME: quest to socket */
				server = new Socket(request.getHost(), request.getPort())/* create a new socket for connection to the origin server */;
				DataOutputStream toServer = new DataOutputStream(server.getOutputStream())/* create a DataOutputStream object for sending the request */;
				toServer.writeBytes(request.toString());/* write the request created in Line 39 to the socket using the writeBytes() function*/

				System.out.println("Proxy requests the origin server for:" + request.URI);
			} catch (UnknownHostException e) {
				System.out.println("Unknown host: " + request.getHost() );
				System.out.println(e);
				return;
			} catch (IOException e) {
				System.out.println("Error writing request to server: " + e);
				return;
			}

			/* Read response from the origin server and forward it to client */
			try {
				DataInputStream fromServer = new DataInputStream(server.getInputStream())/* use a DataInputStream object to read the response from the server socket*/;
				response = new HttpResponse(fromServer)/* create an HttpResponse object, check HttpResponse.java for details on the HttpResponse class */;
				System.out.println("Proxy receives the response from Origin server for: "+ request.URI);
				
				DataOutputStream toClient = new DataOutputStream(client.getOutputStream())/* create a DataOutputStream object for forwarding the response to the client */;
				
				/* Write response to the client. First headers, then body */
				/* write the response header to the socket */
				toClient.writeBytes(response.toString());

				/* write the response body to the socket */
				// String body = new String();
			
				toClient.write(response.body, 0, response.length);


				System.out.println("Proxy forwards the received response to client");

				client.close();
				server.close();
				
				/* Insert object into the cache */
				webcache.put(request.URI, response);

				System.out.println("Proxy caches for: "+request.URI);
			} catch (IOException e) {
				System.out.println("Error writing response to client: " + e);
			}
		}
    }


    /** Read command line arguments and start proxy */
    public static void main(String args[]) {
		int myPort = 0;
		
		try {
			myPort = Integer.parseInt(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Need port number as argument");
			System.exit(-1);
		} catch (NumberFormatException e) {
			System.out.println("Please give port number as integer.");
			System.exit(-1);
		}
		
		init(myPort);

		/** Main loop. Listen for incoming connections and spawn a new
		 * thread for handling them */
		Socket client = null;
		
		while (true) {
			try {
				client = socket.accept()/*  accept an incoming request to the socket */;
				System.out.println("");
				System.out.println("Got connection " + client);
				handle(client);
			} catch (IOException e) {
				System.out.println("Error reading request from client: " + e);
				/* Definitely cannot continue processing this request,
				* so skip to next iteration of while loop. */
				continue;
			}
		}

    }
}