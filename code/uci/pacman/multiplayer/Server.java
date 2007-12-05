package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;
import code.uci.pacman.controllers.*;
import code.uci.pacman.objects.controllable.*;
import java.io.*;
import java.net.*;
import java.util.*;

/// COMMAND ENUMS
enum PType 
{ 
	JOIN,		// [GHOST_TYPE]			(duh - GHOST_TYPE is optional (not used on server, but used on clients))
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
	 * @param ghost the ghost to associate with
	 */
	public static void send(PType type, GhostType ghost)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer(type.ordinal()).byteValue();
		buf[1] = new Integer(ghost.ordinal()).byteValue();
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
				DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, 4446 );
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
				System.out.print("PACKET ["+packet.getPort()+"] ("+packetType+","+data1+","+data2+","+data3+"): ");

				if( PType.JOIN.ordinal() == packetType )
				{
					if( Server.clients.contains(Server.localAddr) )
					{
						// find the next slot available
						int localIndex = Server.clients.indexOf(Server.localAddr);
						
						// build the packet
						bufOut[0] = new Integer( PType.GTYPE.ordinal() ).byteValue();
						bufOut[1] = new Integer( localIndex  ).byteValue();
						Server.clients.set( localIndex, address );
						sendClientData( address, bufOut );

						// get the ghost type
						GhostType gtype = GhostType.BLINKY;
						
						// Update the server
						switch(localIndex)
						{
							case 0://b
								gtype = GhostType.BLINKY;
								Ghost gh = GameState.getInstance().getGhosts().getObjectAt("Blinky");
								int gx = gh.x();
								int gy = gh.y();
								GameState.getInstance().getGhosts().destroyAt("Blinky");
								GameState.getInstance().getGhosts().addObject( "Blinky", new RemoteGhost(gx, gy, "Blinky") );
								//remoteghost(x,y,name)
								break;
							case 1://c
								//GhostController gc = GameState.getInstance().getGhosts();
								gtype = GhostType.CLYDE;
								break;
							case 2://i
								//GhostController gc = GameState.getInstance().getGhosts();
								gtype = GhostType.INKY;
								break;
							case 3://p
								//GhostController gc = GameState.getInstance().getGhosts();
								gtype = GhostType.PINKY;
								break;
						}

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
					
					// ack the player
					sendAck(address);
					
					GhostType gtype = GhostType.BLINKY;
					Direction dir = Direction.UP;

					// get direction
					switch(data1)
					{
						case 0://up
							dir = Direction.UP;
							break;
						case 1://down
							dir = Direction.DOWN;
							break;
						case 2://left
							dir = Direction.LEFT;
							break;
						case 3://right
							dir = Direction.RIGHT;
							break;
					}

					// get the ghost
					switch(data2)
					{
						case 0://blinky
							gtype = GhostType.BLINKY;
							GameState.getInstance().getGhosts().getObjectAt("Blinky").step(dir);
							break;
						
						case 1://clyde
							gtype = GhostType.CLYDE;
							break;

						case 2://inky
							gtype = GhostType.INKY;
							break;

						case 3://pinky
							gtype = GhostType.PINKY;
							break;
					}
					System.out.println();
					
					// notify all the clients
					send(gtype, dir);
					


				}
				else if( PType.LEAVE.ordinal() == packetType )
				{
					// a ghost is leaving

					// acknowledge the player 
					sendAck(address);

					// find which ghost it is, and notify all the clients that they dropped
					switch( data1 )
					{
						case 0://b
							send(PType.LEAVE, GhostType.BLINKY);
							break;
						case 1://c
							send(PType.LEAVE, GhostType.CLYDE);
							break;
						case 2://i
							send(PType.LEAVE, GhostType.INKY);
							break;
						case 3://p
							send(PType.LEAVE, GhostType.PINKY);
							break;
					}
				}
				else
				{
					// some other junk packet
					sendError(address,ServErrors.UNKNOWN_COM);
				}
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
