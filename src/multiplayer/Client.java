package multiplayer;

import javax.swing.*;

import java.io.*;
import java.net.*;
/**
 * 
 * @author Networking Team
 * Acts as the multiplayer server.  Clients connect to it. 
 *
 */
class Client{

	private static final long serialVersionUID = 1L;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean connected = false;
	private boolean inError = false;
	
	
	/**
	 * creates a new client
	 */
   Client(){ 
       socket = null;
       out = null;
       in = null;
       }
   
      /**
       * Attempts to connect to a server. If fails, message appears. 
       * @param serverAddress insert IP address
       */
  public void connect(String serverAddress){
	  try {
		  	//currently the port is set to 6112
			socket = new Socket(serverAddress, 6112);
			connected = true;
			} catch (UnknownHostException e) {
				message("Could not find server");
				connected = false;
				inError = true;
			} catch (IOException e) {
				message("Could not find server");
				connected = false;
				inError = true;
			}
			
	//if not in error	
			if(!inError){
    	 	try {
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
    	 	} catch (IOException e) {
				connected = false;
				e.printStackTrace();
			}
    	 
			}	
 }
  
  /**
   * Sends an action to the server it is connected to.
   * @param action Generic action to be sent.
   */
  //when action occurs write action to output stream
  public void fireAction(Action action){
	   if(isConnected()){
		          try {
		        	  out.writeObject(null);
				} catch (IOException e) {
					message("Warning: Server not active");
					connected = false;
					finalize();
				}
	   	}
  }
  
  /**
   * Performs an action to the client.
   * @param action generic action to perform on the client
   */
  public void performAction(Action action){
	
  }
  
  /**
   * 
   * @return returns true if connected
   */
  public boolean isConnected(){
	  return connected;
  }
  
  /**
   * closes up in and out streams of the client
   */
  protected void finalize(){
	     try{
	         out.close();
	         in.close();
	    } catch (IOException e) {
	        System.out.println("Could not close.");
	    }
	  }
  
 
   private void message(String message){
	   JFrame errorFrame = new JFrame();
	   JOptionPane.showMessageDialog(errorFrame,
			    message,
			    "Client Message",
			    JOptionPane.ERROR_MESSAGE);
   }
   
}
