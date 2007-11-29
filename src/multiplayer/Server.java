package multiplayer;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
/**
 * 
 * @author Networking Team
 * Acts as the multiplayer server.  Clients connect to it. 
 *
 */
class Server{
	
	private static final long serialVersionUID = 1L;
	private ServerSocket server;
	
   Server(){
   } 
    
   /**
    *  Essentially starts the server. It "listens" for incoming clients. 
    *  It's a continuous loop that waits for clients.  Only stops once the
    *  program is exited. 
    */
   public void listenSocket(){

	    try{
	    	server = new ServerSocket(6112);//server is a socket made through port 6112
	    } catch (IOException e) {//if port cannot be found/port-failed exit
	      errorMessage("Server Cannot Connect\nport 6112 is in use");
	      System.exit(-1);
	    }
		    //READ IN DATA
		    while(true){
		    	ClientWorker w;
		      try {
			    	w = new ClientWorker(server.accept());
			    	Thread t = new Thread(w);
			    	t.start();
				} catch (IOException e) {
				System.out.println("IO connection could not be made");
				e.printStackTrace();
		      	}
		    }
	  }
   

/**
 * Closes up server in streams and out streams. 
 */
  protected void finalize(){
//	Clean up 
	     try{
	         server.close();
	    } catch (IOException e) {
	        System.out.println("Could not close.");
	        System.exit(-1);
	    }
	  }
 
   
   private void errorMessage(String message){
	   JFrame errorFrame = new JFrame();
	   JOptionPane.showMessageDialog(errorFrame,
			    message,
			    "Server Message",
			    JOptionPane.ERROR_MESSAGE);
	   
   }
}
