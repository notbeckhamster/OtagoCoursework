/**
 * HttpRequest - HTTP request container and parser
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpRequest {
    /** Help variables */
    final static String CRLF = "\r\n";
    final static int HTTP_PORT = 80;

    /** Store the request parameters */
    String method;
    String URI;
    String version;
    String headers = "";

    /** Server and port */
    private String host;
    private int port;

    /** Create HttpRequest by reading it from the client socket */
    public HttpRequest(BufferedReader from) {
		String firstLine = "";
		try {
			firstLine = from.readLine();
		} catch (IOException e) {
			System.out.println("Error reading request line: " + e);
		}

		String[] tmp = firstLine.split(" ");
		method = tmp[0];
		URI = tmp[1];
		version = tmp[2];

		if (!method.equals("GET")) {
			System.out.println("Error: Method not GET");
		}
		try {
			String line = from.readLine();
			while (line.length() != 0) {
			headers += line + CRLF;

			/* COMPLETE ME: from the headers of the request, find the name of the server and its port number *
			* use Wireshark to capture an HTTP request message and inspect its headers.
			* You will see the header that contains the server name and port number should start with "Host:"
			* If the web server uses the default port number 80, that header contains only the server name in the format of "Host: servername\r\n", otherwise
			* it should contain both the server name and port number in the format of "Host: hostname:port\r\n"
			* 
			* Assign the server name to the host variable (line 22) and the port number to the port variable (line 23)
			*/
            if (line.startsWith("Host:")){
                int lastIdxColon = line.lastIndexOf(":");
                if ( lastIdxColon== 4){
                    //Only Host: assing port 80
                    System.out.println(line);
                    host = line.substring(6);
                    port =80;
                } else {
                    host = line.substring(6, lastIdxColon);
                    port = Integer.parseInt(line.substring(lastIdxColon+1));

                }
            }
			line = from.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error reading from socket: " + e);
			return;
		}
    }

    /** Return host for which this request is intended */
    public String getHost() {
		return host;
    }

    /** Return port for server */
    public int getPort() {
		return port;
    }

    /**
     * Convert request into a string for easy re-sending.
     */
    public String toString() {
		String req = "";

		req = method + " " + URI + " " + version + CRLF;
		req += headers;
		/* This proxy does not support persistent connections */
		req += "Connection: close" + CRLF;
		req += CRLF;
		
		return req;
    }
}