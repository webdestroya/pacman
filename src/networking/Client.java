package networking;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;

class Client{

	private static final long serialVersionUID = 1L;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean connected = false;
	private boolean inError = false;
	
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
