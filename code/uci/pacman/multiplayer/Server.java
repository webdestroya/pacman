package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;
import code.uci.pacman.ai.*;
import code.uci.pacman.controllers.*;
import code.uci.pacman.controllers.*;
import code.uci.pacman.objects.controllable.*;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 * Server is responsible for handling incoming client requests.
 * @author Networking Team
 *	
 */
public class Server extends Thread
{
	private static final long serialVersionUID = 1L;
	protected DatagramSocket socket = null;

	// Tells us who is playing on the server right now
	protected static ArrayList<GhostType> clients;


    protected boolean moreQuotes = true;

	// this class just sends notifications that slots are open on the server
	private class OpenSlots extends Thread 
	{
		public void run()
		{
			// be lazy at the start
			try
			{
				// for some reason, if the client connects too fast, the game explodes
				Thread.currentThread().sleep(5000);
			}
			catch(Exception e){}

			// start the notifications
			while(true)
			{
				try
				{
					boolean spotsOpen = false;
					byte[] buf = new byte[4];
					GhostType gtype = GhostType.CLYDE;
					if( !Server.clients.contains(GhostType.PINKY) )
					{
						spotsOpen = true;
						gtype = GhostType.PINKY;
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
					else if( !Server.clients.contains(GhostType.BLINKY) )
					{
						spotsOpen = true;
						gtype = GhostType.BLINKY;
					}

					
					// be quiet if we dont have any slots open
					if(spotsOpen)
					{
						// build the packet
						buf[0] = new Integer(PType.SPOTFREE.ordinal()).byteValue();
						buf[1] = new Integer(gtype.ordinal()).byteValue();

						// send the packet out
						InetAddress group = InetAddress.getByName("230.0.0.1");
						DatagramSocket socketSend = new MulticastSocket();
						DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446 );
						socketSend.send(packet);
						socketSend.close();

						// sleep some
						Thread.currentThread().sleep(5000);
					}
					else
					{
						// take longer naps when we are full
						Thread.currentThread().sleep(10000);
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
		sendData(buf);
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
		sendData(buf);
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
		sendData(buf);
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
		sendData(buf);
	}


	private static void sendData(byte[] buf)
	{
		sendClientData(buf);
	}

	// sends raw data to a client
	private static void sendClientData(byte[] buf)
	{
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

		}
	}


	private String capitalize(String s)
	{
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}





	/**
	 * runs the server
	 */
    public void run()
	{
		// should be while game is not over
		new OpenSlots().start();

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
				//System.out.print("PACKET("+packetType+","+data1+","+data2+","+data3+"): ");

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
								break;
							case 1://c
								gtype = GhostType.CLYDE;
								break;
							case 2://i
								gtype = GhostType.INKY;
								break;
							case 3://p
								gtype = GhostType.PINKY;
								break;
						}

						GameState.getInstance().getGhosts().getObjectAt(capitalize(gtype.toString()) ).setDirection(Direction.UP);

						Server.clients.add(gtype);

					}
					else
					{
						// game is full
						//bufOut[0] = new Integer( PType.GAMEFULL.ordinal() ).byteValue();
						//sendClientData( bufOut );
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
					//System.out.println("Moving "+data2+" in dir "+data1);
					
					GameState.getInstance().getGhosts().getObjectAt( capitalize(gtype.name()) ).setDirection(dir);

					// notify all the clients
					send(gtype, dir);
					


				}
				else if( PType.LEAVE.ordinal() == packetType )
				{
					// a ghost is leaving

					GhostType gtype = GhostType.BLINKY;
					// find which ghost it is, and notify all the clients that they dropped
					switch( data1 )
					{
						case 0://b
							gtype = GhostType.BLINKY;
							break;
						case 1://c
							gtype = GhostType.CLYDE;
							break;
						case 2://i
							gtype = GhostType.INKY;
							break;
						case 3://p
							gtype = GhostType.PINKY;
							break;
					}

					send(PType.LEAVE, gtype);
					GameState.getInstance().getGhosts().getObjectAt( capitalize(gtype.name()) ).returnAI();
					Server.clients.remove( gtype );


				}
				else
				{
					// some other junk packet
					//sendError(address,ServErrors.UNKNOWN_COM);
				}
			}
			catch (Exception e)
			{
				//e.printStackTrace(System.out);
				//moreQuotes = false;
			}
		}

		// TODO: when the socket fails out, we should make everyone use the AI again

		//System.out.println("Server Shutdown");
		socket.close();
    }//run

	
	
	
}
