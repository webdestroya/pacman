package multiplayer;


import java.io.*;
import java.net.*;

import javax.swing.Action;
/**
 * 
 * @author Networking Team
 * This class allows for more than one connection to the server.
 * It is created when a new client connects to the server.  It 
 * handles all of the actions that are sent to the server.  
 *
 */
class ClientWorker implements Runnable {
   
	private static final long serialVersionUID = 1L;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket client;
	private String nickname;
	
	/**
	 * 
	 * @param cl Pass to it the client that it is going to handle
	 */
  ClientWorker(Socket cl) {
	  client = cl;
	  out = null;
	  in = null;
  }

  /**
   * starts thread
   */
  public void run(){
	Object A;
    try{
     out = new ObjectOutputStream(client.getOutputStream());
     in = new ObjectInputStream(client.getInputStream());
    } catch (IOException e) {
      System.out.println("in or out failed");
      System.exit(-1);
    }
    while(!client.isClosed()){//while client is open
      try{ 
    	  A = in.readObject();
       } catch (IOException e) {
    	   	finalize();
       } catch (ClassNotFoundException e) {
			System.out.println("Class could not be found");
			e.printStackTrace();
			}
    	} 
  }
  
  /**
   * When an event occurs on the server, the resulting action is sent to the client.
   * @param action The action that is going to be sent to client
   */
  public void fireVBListener(Action action){
		          try {
		        		out.writeObject(null);
				} catch (IOException e) {
					System.out.println("Object could not be written");
					e.printStackTrace();
				}
  }

  /**
   * closes the input and output streams. 
   */
  protected void finalize(){
	     try{
	         in.close();
	         out.close();
	    } catch (IOException e) {
	        System.out.println("Could not close.");
	        System.exit(-1);
	    }
	  }
  
  
}