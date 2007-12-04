package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Server is responsible for handling incoming client requests.  It hands out responsibilites to ClientWorkers which
 * handle the incoming data from the Clients. 
 * @author Networking Team
 *	
 */
public class Server extends Thread
{
	private static final long serialVersionUID = 1L;
	protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

   
    public Server() throws IOException 
    {
		this("Server");
    }
/**
 * 
 * @param name
 * @throws IOException
 */
    public Server(String name) throws IOException
    {
        super(name);
        socket = new DatagramSocket(4445);
        System.out.println("START SERVER");
    }

    public void run() {

		while (moreQuotes) 
		{
			try 
			{
				byte[] buf = new byte[256];

				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				//System.out.println("RECV: " + packet.getData() );

				// figure out response
				String dString = null;
				//if (in == null)
				dString = new Date().toString();
				//else
				//    dString = getNextQuote();

				buf = dString.getBytes();
				//buf = GameState.getInstance().getBytes();//dString.getBytes();

				// send the response to the client at "address" and "port"
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
				socket.send(packet);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				moreQuotes = false;
			}
		}
		System.out.println("CLOSE");
		socket.close();
    }
    /**
     * @return
     */
    protected GameState getNextQuote()
    {
		String returnValue = null;
		/*
		try
		{
			if ((returnValue = in.readLine()) == null) 
			{
				in.close();
				moreQuotes = false;
				returnValue = "No more quotes. Goodbye.";
			}
		}
		catch (IOException e)
		{
			returnValue = "IOException occurred in server.";
		}
		*/

		//return returnValue;
		return GameState.getInstance();
    }

	
	
	
}
