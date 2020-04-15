/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server;

/**
 *
 * @author User
 */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

// Each Client Connection will be managed in a dedicated Thread
public class HTTPServer implements Runnable{ 
	
	static final File WEB_ROOT = new File(".");
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";
	// port to listen connection
	static final int PORT = 80;
	
	// verbose mode
	static final boolean verbose = true;
	
	// Client Connection via Socket Class
	private Socket connect;
	
	public HTTPServer(Socket c) {
		connect = c;
	}
        
        private String referer = " ";
	
	public static void main(String[] args) {
		try {
			ServerSocket serverConnect = new ServerSocket(PORT);
			System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
			
			// we listen until user halts server execution
			while (true) {
				HTTPServer myServer = new HTTPServer(serverConnect.accept());
				
				if (verbose) {
					System.out.println("Connecton opened. (" + new Date() + ")");
				}
				
				// create dedicated thread to manage the client connection
				Thread thread = new Thread(myServer);
				thread.start();
			}
			
		} catch (IOException e) {
			System.err.println("Server Connection error : " + e.getMessage());
		}
	}

	@Override
	public void run() {
		// we manage our particular client connection
		BufferedReader in = null; BufferedReader dataIn = null; PrintWriter out = null; BufferedOutputStream dataOut = null;
		String fileRequested = null;
                
		
		try {
			// we read characters from the client via input stream on the socket
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));			
                        dataIn = new BufferedReader(new InputStreamReader(connect.getInputStream()));

			// we get character output stream to client (for headers)
			out = new PrintWriter(connect.getOutputStream());
			// get binary output stream to client (for requested data)
			dataOut = new BufferedOutputStream(connect.getOutputStream());
			
			// get first line of the request from the client
			String input = in.readLine();
			// we parse the request with a string tokenizer
			StringTokenizer parse = new StringTokenizer(input);
			String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
			// we get file requested
			fileRequested = parse.nextToken().toLowerCase();
                       
                        
                        boolean refererFound = false;
                        String header = " ";
                        String value = " ";
                        
                        while (!refererFound && parse.hasMoreTokens())
                        {
                            header= parse.nextToken().toLowerCase();
                        
                            if ("referer:".equals(header))
                            {
                                value = parse.nextToken().toLowerCase();
                                refererFound = true;
                            }
                        }
                        
                        if (refererFound)
                        {
                            referer = value;
                        }else{
                           referer = "http://www.mfserver.com/index.html"; 
                        }

			if (!method.equals("GET")  &&  !method.equals("HEAD") && !method.equals("POST")) 
                        {
				if (verbose) {
					System.out.println("501 Not Implemented : " + method + " method.");
                                     }
				
				// we return the not supported file to the client
				File file = new File(WEB_ROOT, METHOD_NOT_SUPPORTED);
				int fileLength = (int) file.length();
				String contentMimeType = "text/html";
				//read content to return to client
				byte[] fileData = readFileData(file, fileLength);
					
				// we send HTTP Headers with data to client
				out.println("HTTP/1.1 501 Not Implemented");
				out.println("Server: Java HTTP Server from Mayquely & Felipe");
                                out.println("Host: www.mfserver.com");
                                out.println("Referer: " + referer);
				out.println("Date: " + new Date());
				out.println("Content-type: " + contentMimeType);
				out.println("Content-length: " + fileLength);
				out.println(); // blank line between headers and content, very important !
				out.flush(); // flush character output stream buffer
				// file
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();
				
			} else {
				// GET, HEAD or POST method
				if (fileRequested.endsWith("/")) {
					fileRequested += DEFAULT_FILE;
				}
				
				File file = new File(WEB_ROOT, fileRequested);
				int fileLength = (int) file.length();
				String content = getContentType(fileRequested);
                                
				
				if (method.equals("POST"))  
                                {
                                  //code to read and print headers
                                String headerLine = null;
                                    while((headerLine = in.readLine()).length() != 0){
                                        System.out.println(headerLine);
                                    }
                                 //code to read the post payload data
                                    StringBuilder payload = new StringBuilder();
                                            while(in.ready())
                                            {
                                                payload.append((char) in.read());
                                            }
                                    System.out.println(payload.toString()); 
                                     //code to encode HTML content 
                                    StringBuilder htmlBuilder = new StringBuilder();
                                    htmlBuilder.append("<html>")
                                    .append("\n")
                                    .append("<body>")
                                    .append("\n")
                                    .append("<h1>")
                                    .append("\n")
                                    .append(payload.toString())
                                    .append("\n")
                                    .append("</h1>")
                                    .append("\n")
                                    .append("</body>")
                                    .append("\n")
                                    .append("</html>");

                                    
                                    File filePost;
                                    filePost= new File("C:\\Users\\User\\Desktop\\HTTP server\\HTTP Server"+ fileRequested);
  
                                    //Create the file
                                    if (filePost.createNewFile())
                                    {
                                        System.out.println("File" + fileRequested+ " is created!");
                                    } else {
                                        System.out.println("File already exists.");
                                    }

                                    //Write Content
                                    FileWriter writer = new FileWriter(filePost);
                                    writer.write(htmlBuilder.toString());
                                    writer.close();
                                    
                                    
                                    
                                    
                                    //////**********************************/////////////////////
                                    
                                        out.println("HTTP/1.1 200 OK");
					out.println("Server: Java HTTP Server from Mayquely & Felipe POST");
                                        out.println("Host: www.mfserver.com");
                                        out.println("Referer: " + referer);
					out.println("Date: " + new Date());
					out.println("Content-type: " + content);
					out.println("Content-length: " + fileLength);
					out.println(); // blank line between headers and content, very important !
					out.flush(); // flush character output stream buffer

				}
                                
                                
                                else //GET or HEAD
                                {
					// send HTTP Headers
					out.println("HTTP/1.1 200 OK");
					out.println("Server: Java HTTP Server from Mayquely & Felipe");
                                        out.println("Host: www.mfserver.com");
                                        out.println("Referer: " + referer);
					out.println("Date: " + new Date());
					out.println("Content-type: " + content);
					out.println("Content-length: " + fileLength);
					out.println(); // blank line between headers and content, very important !
					out.flush(); // flush character output stream buffer
					
                                    if (method.equals("GET"))  
                                    {
                                       byte[] fileData = readFileData(file, fileLength);
                                       dataOut.write(fileData, 0, fileLength);
                                       dataOut.flush();
                                    }   
					
                                }
				
                                
				if (verbose) 
                                {
					System.out.println("File " + fileRequested + " of type " + content + " returned");
				}        
			}
			
		} catch (FileNotFoundException fnfe) {
			try {
				fileNotFound(out, dataOut, fileRequested);
			} catch (IOException ioe) {
				System.err.println("Error with file not found exception : " + ioe.getMessage());
			}
			
		} catch (IOException ioe) {
			System.err.println("Server error : " + ioe);
		} finally {
			try {
				in.close();
				out.close();
				dataOut.close();
				connect.close(); // close socket connection
			} catch (Exception e) {
				System.err.println("Error closing stream : " + e.getMessage());
			} 
			
			if (verbose) {
				System.out.println("Connection closed.\n");
			}
		}
		
		
	}
	
	private byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null) 
				fileIn.close();
		}
		
		return fileData;
	}
	
	// return supported MIME Types
	private String getContentType(String fileRequested) {
		if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
			return "text/html";
		else
			return "text/plain";
	}
	
	private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException {
		File file = new File(WEB_ROOT, FILE_NOT_FOUND);
		int fileLength = (int) file.length();
		String content = "text/html";
		byte[] fileData = readFileData(file, fileLength);
		
		out.println("HTTP 404 File Not Found");
		out.println("Server: Java HTTP Server from Mayquely & Felipe");
                out.println("Host: www.mfserver.com");
                out.println("Referer: " + referer);
		out.println("Date: " + new Date());
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println(); // blank line between headers and content, very important!
		out.flush(); // flush character output stream buffer
		
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();
		
		if (verbose) {
			System.out.println("File " + fileRequested + " not found");
		}
	}
	
}