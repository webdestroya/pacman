package multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
/**
 * 
 * @author Networking Team
 * 	Allows the Client to accept actions.  It runs in it's own thread along
 *  side the client.  
 *
 */
public class ClientInputListener implements Runnable{
	
	private Client client;
	private ObjectInputStream in;
	
	/**
	 * 
	 * @param in This is the object that is sent to the client.
	 * @param client The current client
	 */
	ClientInputListener(ObjectInputStream in, Client client){
		this.client = client;
		this.in = in;
	}
	
	/**
	 * starts thread
	 */
	public void run(){
		Object A;
		    while(client.isConnected()){
			      try {
			    	  A = in.readObject();
					} catch (IOException e) {
						finalize();
			      	} catch (ClassNotFoundException e) {
					System.out.println("Class Not Found");
					e.printStackTrace();
					}
		    }
	}

	/**
	 * closes up input stream connections. 
	 */
	public void finalize(){
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
