package networking;

import javax.swing.*;

import java.io.*;
import java.net.*;

class Client{

	private static final long serialVersionUID = 1L;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean connected = false;
	private boolean inError = false;
	
	/**
	Methods to ADD
	fire
	
	
	**/
	
	
   Client(){ 
       socket = null;
       out = null;
       in = null;
       }
      
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
  
  //this is what happens when an input is received 
  public void performAction(Action action){
	
  }
  
  
  public boolean isConnected(){
	  return connected;
  }
  
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
