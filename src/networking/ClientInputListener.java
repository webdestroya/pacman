package networking;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientInputListener implements Runnable{
	
	private Client client;
	private ObjectInputStream in;
	
	
	ClientInputListener(ObjectInputStream in, Client client){
		this.client = client;
		this.in = in;
	}
	

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

	public void finalize(){
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
