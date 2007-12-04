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
	GAMEFULL,	//						(tells a client that the game is full)
	GAMESTART,	//						(duh)
	ACK,		//						(Response that server acknowledges them - not really used, but good for debugging)
	ERROR		// SERV_ERROR			( Error, you broke something)
};

// The ghost type
enum GhostType { BLINKY,CLYDE,INKY,PINKY };

// Server Errors
enum ServErrors {
	UNKNOWN_COM,	// unknown command
};

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
	private static InetAddress localAddr;
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
		Server.localAddr = InetAddress.getByName("127.0.0.1");
		Server.clients.add(localAddr);
		Server.clients.add(localAddr);
		Server.clients.add(localAddr);
		Server.clients.add(localAddr);

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

	/**
     * Sends a command
     * @param ghost the type of ghost
	 * @param dir the direction its moving
     */
	public static void send(GhostType ghost, Direction dir)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer(PType.GMOVE.ordinal()).byteValue(); // TYPE
		buf[1] = new Integer(dir.ordinal()).byteValue(); // DIRECTION
		buf[2] = new Integer(ghost.ordinal()).byteValue(); // GHOST_TYPE
		Server.sendData(buf);
	}

	/**
	 * Used for moving pacman
	 * @param dir the direction pacman is moving
	 */
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

	// sends raw data to a client
	private static void sendClientData(InetAddress addr, byte[] buf)
	{
		if( !Server.localAddr.equals( addr ) )
		{
			try
			{
				DatagramSocket socketSend = new DatagramSocket();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, 4445);
				socketSend.send(packet);
				socketSend.close();
			}
			catch(Exception e)
			{
				// they failed out, so remove from player table
				Server.clients.remove(addr);
			}
		}
	}
	// just an alias
	private static void sendClientData(int clientID, byte[] buf)
	{
		sendClientData( Server.clients.get(clientID), buf);
	}

	// Send an acknowledgement packet
	private static void sendAck(int clientID)
	{
		sendAck( Server.clients.get(clientID) );
	}
	private static void sendAck(InetAddress addr)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer( PType.ACK.ordinal() ).byteValue();
		sendClientData(addr,buf);
	}

	// Send an error
	private static void sendError(int clientID, ServErrors err)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer( PType.ERROR.ordinal() ).byteValue();
		buf[1] = new Integer( err.ordinal() ).byteValue();
		sendClientData(clientID, buf);
	}
	private static void sendError(InetAddress addr, ServErrors err)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer( PType.ERROR.ordinal() ).byteValue();
		buf[1] = new Integer( err.ordinal() ).byteValue();
		sendClientData(addr, buf);
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
				
				// get client address
				InetAddress address = packet.getAddress();
				
				// get the packet type
				int packetType = buf[0] & 0x000000FF;
				int data1 = buf[1] & 0x000000FF;
				int data2 = buf[2] & 0x000000FF;
				int data3 = buf[3] & 0x000000FF;
				System.out.print("PACKET ("+packetType+","+data1+","+data2+","+data3+"): ");

				if( PType.JOIN.ordinal() == packetType )
				{
					System.out.println("JOIN");
					
					if( Server.clients.contains(Server.localAddr) )
					{
						// find the next slot available
						int localIndex = Server.clients.indexOf(Server.localAddr);
						
						// build the packet
						bufOut[0] = new Integer( PType.GTYPE.ordinal() ).byteValue();
						bufOut[1] = new Integer( localIndex  ).byteValue();
						Server.clients.set( localIndex, address );
						sendClientData( address, bufOut );
					}
					else
					{
						// game is full
						bufOut[0] = new Integer( PType.GAMEFULL.ordinal() ).byteValue();
						sendClientData( address, bufOut );
					}
				}
				else if( PType.GMOVE.ordinal() == packetType )
				{
					// a ghost move
					System.out.print("GMOVE");

					// get direction
					switch(data1)
					{
						case 0://up
							System.out.print(" UP");
							break;
						case 1://down
							System.out.print(" DOWN");
							break;
						case 2://left
							System.out.print(" LEFT");
							break;
						case 3://right
							System.out.print(" RIGHT");
							break;
					}

					// get the ghost
					switch(data2)
					{
						case 0://blinky
							System.out.print(" BLINKY");
							
							break;
						
						case 1://clyde
							System.out.print(" CLYDE");
							break;

						case 2://inky
							System.out.print(" INKY");
							break;

						case 3://pinky
							System.out.print(" PINKY");
							break;
					}
					System.out.println();
					
					sendAck(address);

				}
				else if( PType.LEAVE.ordinal() == packetType )
				{
					// a ghost is leaving
					
					sendAck(address);
				}
				else
				{
					// some other junk packet
					System.out.println("UNKNOWN("+packetType+")");
					sendError(address,ServErrors.UNKNOWN_COM);
				}
				
				// figure out response
				/*String dString = null;
				dString = new Date().toString();
				buf = dString.getBytes();

				

				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
				socket.send(packet);*/
			}
			catch (IOException e)
			{
				e.printStackTrace();
				moreQuotes = false;
			}
		}
		System.out.println("Server Shutdown");
		socket.close();
    }//run

	
	
	
}
