package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;

import java.io.*;
import java.net.*;
import java.util.*;


/// COMMAND ENUMS
enum PType { JOIN, GMOVE, PMOVE, GAMEOVER, GAMESTART };


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

	protected static ArrayList<InetAddress> clients;

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
		clients = new ArrayList<InetAddress>();
        System.out.println("START SERVER");
    }
	
	// a single command
	public static void send(PType type)
	{
		byte[] buf = new byte[256];

		Server.sendData(buf);
	}
	
	// for a move type
	public static void send(PType type, Direction dir)
	{
		byte[] buf = new byte[256];
		
		
		Server.sendData(buf);
	}



	public static void sendData(byte[] buf)
	{
		try
		{
			for(int i=0; i < Server.clients.size(); i++)
			{
				//byte[] buf = new byte[256];
				DatagramSocket socketSend = new DatagramSocket();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, Server.clients.get(i), 4445);
				socketSend.send(packet);
			}
		}
		catch(Exception e)
		{

		}
	}

    public void run()
	{
		// should be while game is not over
		while (moreQuotes) 
		{
			try 
			{
				byte[] buf = new byte[256];

				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				// figure out response
				String dString = null;
				dString = new Date().toString();
				buf = dString.getBytes();

				// send the response to the client at "address" and "port"
				InetAddress address = packet.getAddress();
				
				// add them to the clients list to receive updates
				if( !Server.clients.contains(address) )
				{
					Server.clients.add( address );
				}


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
		System.out.println("Server Shutdown");
		socket.close();
    }
    /**
     * @return resulting GameState
     */

	
	
	
}
