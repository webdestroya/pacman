
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
	protected DatagramSocket socket = null;
	private static boolean moreQuotes = true;
	private static int ghostType = 0;

	/**
	 * Starts a Client
	 * @param host IP address or host name
	 */
	public Client(String host)
	{
		Client.setHost(host);
		socket = new DatagramSocket(4446);
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
		buf[1] = new Integer( ghostType ).byteValue();
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
		buf[2] = new Integer( ghostType ).byteValue();

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
		while (moreQuotes) 
		{
			try 
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

				if( PType.GTYPE.ordinal() == packetType )
				{
					// tells the player what ghost he will be
					switch(data1)
					{
						case 0://blinky
							
							break;
						case 1://clyde

							break;
						case 2://inky

							break;
						case 3://p

							break;
					}
				}
				else if( PType.ERROR.ordinal() == packetType )
				{
					// client was a dumbass, and sent a bad packet
				}
				else if( PType.ACK.ordinal() == packetType )
				{
					// just an acknowledge, i dont think we need to process it, but can be used for consistency
				}
				else if( PType.GAMEFULL.ordinal() == packetType )
				{
					// the game is full. tell the player to suck it.
				}
				else if( PType.GMOVE.ordinal() == packetType )
				{
					// another ghost moved. (We may or may not use this to also update the current player as consistency)
					
				}
				else if( PType.PMOVE.ordinal() == packetType )
				{
					// pacman made a move
					
				}
				else if( PType.GAMESTART.ordinal() == packetType )
				{
					// game commencing

				}
				else if( PType.JOIN.ordinal() == packetType )
				{
					// notify us that another ghost has joined
					// readd them to the board or something
				}
				else if( PType.LEAVE.ordinal() == packetType )
				{
					// notify that a ghost has left the game
					// if the ghost type is the same as the client, then client drops out.
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
			catch (IOException e)
			{
			}
		}
		socket.close();
    }




}//Client
