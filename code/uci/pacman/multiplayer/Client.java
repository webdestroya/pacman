package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;

import javax.swing.*;

import java.io.*;
import java.net.*;
/**
 * Client handles the input and output of the server. It is responsible for starting the ClientInputListener thread.
 * @author Networking Team
 */
public class Client{

	private static final long serialVersionUID = 1L;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean connected = false;
	private boolean inError = false;
	private PacManGame game;
	
	/**
	 * Start a new client
	 * @param game when created Client must recieve the PacManGame, so that it may make necessary changes. 
	 */
   Client(PacManGame game){ 
	   this.game = game;
       socket = null;
       out = null;
       in = null;
       }
      /**
       * Connects to the server if the IP address is valid.  If not valid, then an error message is thrown.  
       * will be able to try again indefinetly. 
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
   * Sends an Object to the Server.
   * @param action
   */
  //when action occurs write action to output stream
  public void fireObjectToServer(Object packet){
	   if(isConnected()){
		          try {
		        	  out.writeObject(packet);
				} catch (IOException e) {
					message("Warning: Server not active");
					connected = false;
					finalize();
				}
	   	}
  }
  
  //this is what happens when an input is received 
  public void performAction(Action action){
	
  }
  
  /**
   * checks to see if the client is connect to the server.
   * @return true if connected
   */
  public boolean isConnected(){
	  return connected;
  }
  /**
   * closes output and input streams.
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
