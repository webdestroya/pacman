package code.uci.pacman.multiplayer;


import java.io.*;
import java.net.*;

import javax.swing.Action;

class ClientWorker implements Runnable {
   
	private static final long serialVersionUID = 1L;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket client;
	private String nickname;
	private PacManGame game;
	
  ClientWorker(Socket cl, PacManGame game) {
	  this.game = game;
	  client = cl;
	  out = null;
	  in = null;
  }
  
  public void run(){
	GameState inputGameState;
    try{
     out = new ObjectOutputStream(client.getOutputStream());
     in = new ObjectInputStream(client.getInputStream());
    } catch (IOException e) {
      System.out.println("in or out failed");
      System.exit(-1);
    }
    while(!client.isClosed()){//while client is open
      try{ 
    	  inputGameState = (GameState)in.readObject();
       } catch (IOException e) {
    	   	finalize();
       } catch (ClassNotFoundException e) {
			System.out.println("Class could not be found");
			e.printStackTrace();
			}
    	} 
  }

  
  public void firePacket(Object packet){
	  System.out.println("Server out!");
		          try {
		        		out.writeObject(null);
				} catch (IOException e) {
					System.out.println("Object could not be written");
					e.printStackTrace();
				}
  }

  //close up connections
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