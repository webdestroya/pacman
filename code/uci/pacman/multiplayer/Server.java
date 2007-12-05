package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;
import code.uci.pacman.ai.*;
import code.uci.pacman.controllers.*;
import code.uci.pacman.objects.controllable.*;
import java.io.*;
import java.net.*;
import java.util.*;

/// COMMAND ENUMS
enum PType 
{ 
	JOIN,		// GHOST_TYPE			(duh - GHOST_TYPE tells the type joining)
	GTYPE,		// GHOST_TYPE			(Tells the ghost what ghost he will be)
	GMOVE, 		// DIR, GHOST_TYPE		(tells the direction and what ghost to move)
	PMOVE, 		// DIR					( direction of pacman)
	GAMEOVER, 	// 						(duh)
	LEAVE, 		// GHOST_TYPE			(tells the server to drop the ghost)
	GAMEFULL,	//						(tells a client that the game is full)
	SPOTFREE	// GHOST_TYPE			(tells the clients listening what spots are free on the server)
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

	protected static ArrayList<GhostType> clients;
	private static int currentPlayers = 0;
	private static InetAddress localAddr;
    protected boolean moreQuotes = true;

	// this class just sends notifications that slots are open on the server
	private class OpenSlots implements Runnable
	{
		public void run()
		{
			while(true)
			{
				try
				{
					boolean spotsOpen = false;
					byte[] buf = new byte[4];
					GhostType gtype = GhostType.BLINKY;
					if( !Server.clients.contains(GhostType.BLINKY) )
					{
						spotsOpen = true;
						gtype = GhostType.BLINKY;
					}
					else if( !Server.clients.contains(GhostType.CLYDE) )
					{
						spotsOpen = true;
						gtype = GhostType.CLYDE;
					}
					else if( !Server.clients.contains(GhostType.INKY) )
					{
						spotsOpen = true;
						gtype = GhostType.INKY;
					}
					else if( !Server.clients.contains(GhostType.PINKY) )
					{
						spotsOpen = true;
						gtype = GhostType.PINKY;
					}

					
					if(spotsOpen)
					{
						buf[0] = new Integer(PType.SPOTFREE.ordinal()).byteValue();
						buf[1] = new Integer(gtype.ordinal()).byteValue();


						InetAddress group = InetAddress.getByName("230.0.0.1");
						DatagramSocket socketSend = new MulticastSocket();
						DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446 );
						socketSend.send(packet);
						socketSend.close();

						Thread.currentThread().sleep(5000);
					}
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}

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
		Server.clients = new ArrayList<GhostType>();

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
		//for(int i=0; i < Server.clients.size(); i++)
	//	{
			Server.sendClientData(buf);
	//	}
	}

	// sends raw data to a client
	private static void sendClientData(byte[] buf)
	{
		//iif( !Server.localAddr.equals( addr ) )
		//{
			try
			{
				InetAddress group = InetAddress.getByName("230.0.0.1");
				MulticastSocket socketSend = new MulticastSocket();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446 );
				socketSend.send(packet);
				socketSend.close();
			}
			catch(Exception e)
			{
				// they failed out, so remove from player table
				//Server.clients.remove(addr);
			}
		//}
	}
	// just an alias
	private static void sendClientData(int clientID, byte[] buf)
	{
		sendClientData(buf);
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
				//System.out.print("PACKET ["+packet.getPort()+"] ("+packetType+","+data1+","+data2+","+data3+"): ");

				if( PType.JOIN.ordinal() == packetType )
				{
					if( Server.clients.size() < 4)
					{
						// get the ghost type
						GhostType gtype = GhostType.BLINKY;
						
						// Update the server
						switch(data1)
						{
							case 0://b
								gtype = GhostType.BLINKY;
								GameState.getInstance().getGhosts().getObjectAt("Blinky").setDirection(Direction.UP);
								break;
							case 1://c
								gtype = GhostType.CLYDE;
								GameState.getInstance().getGhosts().getObjectAt("Clyde").setDirection(Direction.UP);
								break;
							case 2://i
								gtype = GhostType.INKY;
								GameState.getInstance().getGhosts().getObjectAt("Inky").setDirection(Direction.UP);
								break;
							case 3://p
								gtype = GhostType.PINKY;
								GameState.getInstance().getGhosts().getObjectAt("Pinky").setDirection(Direction.UP);
								break;
						}
						
						Server.clients.add(gtype);

					}
					else
					{
						// game is full
						bufOut[0] = new Integer( PType.GAMEFULL.ordinal() ).byteValue();
						sendClientData( bufOut );
					}
				}
				else if( PType.GMOVE.ordinal() == packetType )
				{
					// a ghost move
					
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
							GameState.getInstance().getGhosts().getObjectAt("Blinky").setDirection(dir);
							break;
						
						case 1://clyde
							gtype = GhostType.CLYDE;
							GameState.getInstance().getGhosts().getObjectAt("Clyde").setDirection(dir);
							break;

						case 2://inky
							gtype = GhostType.INKY;
							GameState.getInstance().getGhosts().getObjectAt("Inky").setDirection(dir);
							break;

						case 3://pinky
							gtype = GhostType.PINKY;
							GameState.getInstance().getGhosts().getObjectAt("Pinky").setDirection(dir);
							break;
					}
					//System.out.println("Moving "+data2+" in dir "+data1);
					
					// notify all the clients
					send(gtype, dir);
					


				}
				else if( PType.LEAVE.ordinal() == packetType )
				{
					// a ghost is leaving

					// find which ghost it is, and notify all the clients that they dropped
					switch( data1 )
					{
						case 0://b
							GameState.getInstance().getGhosts().getObjectAt("Blinky").returnAI();
							Server.clients.remove(GhostType.BLINKY);
							send(PType.LEAVE, GhostType.BLINKY);
							break;
						case 1://c
							GameState.getInstance().getGhosts().getObjectAt("Clyde").returnAI();
							Server.clients.remove(GhostType.CLYDE);
							send(PType.LEAVE, GhostType.CLYDE);
							break;
						case 2://i
							GameState.getInstance().getGhosts().getObjectAt("Inky").returnAI();
							Server.clients.remove(GhostType.INKY);
							send(PType.LEAVE, GhostType.INKY);
							break;
						case 3://p
							GameState.getInstance().getGhosts().getObjectAt("Pinky").returnAI();
							Server.clients.remove(GhostType.PINKY);
							send(PType.LEAVE, GhostType.PINKY);
							break;
					}
				}
				else
				{
					// some other junk packet
					//sendError(address,ServErrors.UNKNOWN_COM);
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
