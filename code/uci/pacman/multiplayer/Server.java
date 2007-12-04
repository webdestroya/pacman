package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;

import java.io.*;
import java.net.*;
import java.util.*;

/// COMMAND ENUMS
enum PType 
{ 
	JOIN,		// 						(duh)
	GTYPE,		// GHOST_TYPE			(Tells the ghost what ghost he will be)
	GMOVE, 		// DIR, GHOST_TYPE		(tells the direction and what ghost to move)
	PMOVE, 		// DIR					( direction of pacman)
	GAMEOVER, 	// 						(duh)
	LEAVE, 		// GHOST_TYPE			(tells the server to drop the ghost)
	GAMESTART	//						(duh)
};

// The ghost type
enum GhostType { BLINKY,CLYDE,INKY,PINKY };

/**
 * Server is responsible for handling incoming client requests.
 * @author Networking Team
 *	
 */
public class Server extends Thread
{
	private static final long serialVersionUID = 1L;
	protected DatagramSocket socket = null;

	protected static ArrayList<InetAddress> clients;

    protected boolean moreQuotes = true;

   /**
    * Starts the Server.
    * @throws IOException
    */
    public Server() throws IOException 
    {
		this("Server");
    }
	
	/**
	 * Starts a server at socket 4445
	 * @param name unknown
	 * @throws IOException
	 */
    public Server(String name) throws IOException
    {
        super(name);
        socket = new DatagramSocket(4445);
		Server.clients = new ArrayList<InetAddress>();

		// We first fill the list up with fake stuff as a placeholder
		InetAddress local = InetAddress.getByName("127.0.0.1");
		Server.clients.add(local);
		Server.clients.add(local);
		Server.clients.add(local);
		Server.clients.add(local);

        System.out.println("START SERVER");
    }
	
	// a single command
    
    /**
     * Sends a command
     * @param type the command to send out
     */
	public static void send(PType type)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer(type.ordinal()).byteValue();
		Server.sendData(buf);
	}

	// moving a ghost
	public static void send(GhostType ghost, Direction dir)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer(PType.GMOVE.ordinal()).byteValue(); // TYPE
		buf[1] = new Integer(dir.ordinal()).byteValue(); // DIRECTION
		buf[2] = new Integer(ghost.ordinal()).byteValue(); // GHOST_TYPE
		Server.sendData(buf);
	}

	// only for moving pacman
	public static void send(Direction dir)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer(PType.PMOVE.ordinal()).byteValue();
		buf[1] = new Integer(dir.ordinal()).byteValue();
		Server.sendData(buf);
	}
	
	/**
	 * Sends a command
	 * @param type the type of command to send
	 * @param dir the direction command to send
	 */
	public static void send(PType type, Direction dir)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer(type.ordinal()).byteValue();
		buf[1] = new Integer(dir.ordinal()).byteValue();
		
		Server.sendData(buf);
	}


	private static void sendData(byte[] buf)
	{
		for(int i=0; i < Server.clients.size(); i++)
		{
			Server.sendClientData(i, buf);
		}
	}

	private static void sendClientData(int clientID, byte[] buf)
	{
		try
		{
			DatagramSocket socketSend = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, Server.clients.get(clientID), 4445);
			socketSend.send(packet);
		}
		catch(Exception e)
		{
			Server.clients.remove(clientID);
		}
	}
	

	/**
	 * runs the server
	 */
    public void run()
	{
		// should be while game is not over
		while (moreQuotes) 
		{
			try 
			{
				byte[] buf = new byte[4];
				byte[] bufOut = new byte[4];

				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				buf = packet.getData();
				
				// add them to the clients list to receive updates
				InetAddress address = packet.getAddress();
				if( !Server.clients.contains(address) )
				{
					Server.clients.add( address );
				}

				// get the packet type
				int packetType = buf[0] & 0x000000FF;
				int data1 = buf[1] & 0x000000FF;
				int data2 = buf[2] & 0x000000FF;
				int data3 = buf[3] & 0x000000FF;
				if( PType.JOIN.ordinal() == packetType )
				{
					System.out.println("JOIN");
					InetAddress local = InetAddress.getByName("127.0.0.1");
					int localIndex = Server.clients.indexOf(local);
					
					bufOut[0] = new Integer( PType.GTYPE.ordinal() ).byteValue();
					bufOut[1] = new Integer( localIndex  ).byteValue();
					Server.clients.set( localIndex, address );
					sendClientData( localIndex, bufOut );
				}
				else if( PType.GMOVE.ordinal() == packetType )
				{
					// a ghost move
				}
				else if( PType.LEAVE.ordinal() == packetType )
				{
					// a ghost is leaving
					
				}
				else
				{
					// some other junk packet
					System.out.println("UNKNOWN");
				}
				
				// figure out response
				String dString = null;
				dString = new Date().toString();
				buf = dString.getBytes();

				

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

	
	
	
}
