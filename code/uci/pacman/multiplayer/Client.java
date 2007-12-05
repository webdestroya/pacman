
package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;
import code.uci.pacman.controllers.*;
import java.io.*;
import java.net.*;


/**
 * Client handles the input and output of the server. 
 * @author Networking Team
 */
public class Client extends Thread
{
	private static final long serialVersionUID = 1L;
	private static InetAddress address;
	protected MulticastSocket socket = null;
	private static boolean moreQuotes = true;

	// This tells the client what type he is
	private static GhostType ghostType = GhostType.BLINKY;

	/**
	 * Starts a Client
	 * @param host IP address or host name
	 */
	public Client()
	{
		try
		{
			socket = new MulticastSocket(4446);
		}
		catch(Exception e)
		{
			// cant make a socket?
		}
	}
	/**
	 * Changes the host
	 * @param host IP Address or Host Name
	 */
	public static void setHost(String host)
	{
		try
		{
			Client.address = InetAddress.getByName(host);
		}
		catch(Exception e)
		{

		}
	}

	/**
	 * Sends a packet to the Server;
	 * @param type the packet type
	 */
	public static void send(PType type)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer( type.ordinal() ).byteValue();	
		buf[1] = new Integer( ghostType.ordinal() ).byteValue();
		Client.sendData(buf);
	}

	/**
	 * Sends a packet to the Server;
	 * @param dir the direction the ghost is moving
	 */
	public static void send(Direction dir)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer( PType.GMOVE.ordinal() ).byteValue();	
		buf[1] = new Integer( dir.ordinal() ).byteValue();
		buf[2] = new Integer( ghostType.ordinal() ).byteValue();

		Client.sendData(buf);
	}

	// send the raw packet to the server
	private static void sendData(byte[] buf)
	{
		try
		{
			DatagramSocket socksend = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket( buf, buf.length, Client.address, 4445 );
			socksend.send(packet);
			socksend.close();
		}
		catch(Exception e)
		{
		
		}
	}

	/**
	 * This is the client listener, that listens for updates
	 */
	public void run()
	{
		// should be while game is not over
		try
		{
			InetAddress group = InetAddress.getByName("230.0.0.1");
			socket.joinGroup(group);

			while (moreQuotes) 
			{

				byte[] buf = new byte[4];

				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				buf = packet.getData();
				
				// update the server address
				Client.address = packet.getAddress();

				// get the packet type and data
				int packetType = buf[0] & 0x000000FF;
				int data1 = buf[1] & 0x000000FF;
				int data2 = buf[2] & 0x000000FF;
				int data3 = buf[3] & 0x000000FF;

				if( PType.SPOTFREE.ordinal() == packetType )
				{
					// tells the player that a spot is open on the server
					switch(data1)
					{
						case 0://blinky
							Client.ghostType = GhostType.BLINKY;
							break;
						case 1://clyde
							Client.ghostType = GhostType.CLYDE;
							break;
						case 2://inky
							Client.ghostType = GhostType.INKY;
							break;
						case 3://p
							Client.ghostType = GhostType.PINKY;
							break;
					}

					System.out.println("SPOTFREE GhostType Set: " + data1);
						
					//TODO: setup the player or something, go hooray?
				
				}
				else if( PType.GAMEFULL.ordinal() == packetType )
				{
					// the game is full. tell the player to suck it.
					System.out.println("Game Server is FULL. You cannot join.");
					System.exit(0);
				}
				else if( PType.GMOVE.ordinal() == packetType )
				{
					// another ghost moved. (We may or may not use this to also update the current player as consistency)
					

				}
				else if( PType.PMOVE.ordinal() == packetType )
				{
					// pacman made a move

					// what direction did he move?
					switch(data1)
					{
						case 0://up
							GameState.getInstance().getPacMan().step(Direction.UP);
							break;
						case 1://down
							GameState.getInstance().getPacMan().step(Direction.DOWN);
							break;
						case 2://left
							GameState.getInstance().getPacMan().step(Direction.LEFT);
							break;
						case 3://right
							GameState.getInstance().getPacMan().step(Direction.RIGHT);
							break;
					}
				}
				else if( PType.JOIN.ordinal() == packetType )
				{
					// notify us that another ghost has joined
					// readd them to the board or something
					
					// what ghost just joined?
					switch(data1)
					{
						case 0://blinky joined
							break;
						case 1:// clyde joined
							break;
						case 2:// inky joined
							break;
						case 3:// pinky joined
							break;
					}
				}
				else if( PType.LEAVE.ordinal() == packetType )
				{
					// notify that a ghost has left the game
					// if the ghost type is the same as the client, then client drops out.

					if( data1==Client.ghostType.ordinal() )
					{
						// the ghost leaving is the current player
						moreQuotes = false;
						
						System.out.println("You were dropped from the server, or you just left.");

						// drop them and explode
						System.exit(0);
						//GameController.getInstance().getPacInstance().drawGameover();
						//explode(); crash(); burn();
					}
					else
					{
						// someone else is leaving
						// TODO: Process the drop
					}
				}
				else if( PType.GAMEOVER.ordinal() == packetType )
				{
					// game ended
					moreQuotes = false;
					GameController.getInstance().getPacInstance().drawGameover();
				}
				else
				{
					// some other junk packet (these are discarded as nobody cares about a client
					//System.out.println("UNKNOWN");
				}
			}
			socket.leaveGroup(group);
			socket.close();
		}
		catch(Exception e)
		{

		}
    }




}//Client
