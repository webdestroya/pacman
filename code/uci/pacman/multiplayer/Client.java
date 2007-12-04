
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
				}
				else if( PType.GMOVE.ordinal() == packetType )
				{
					// a ghost move

				}
				else if( PType.PMOVE.ordinal() == packetType )
				{
					// pacman made a move
					
				}
				else if( PType.GAMESTART.ordinal() == packetType )
				{
					// game commencing

				}
				else if( PType.GAMEOVER.ordinal() == packetType )
				{
					// game ended
					moreQuotes = false;
					GameController.getInstance().getPacInstance().drawGameover();
				}
				else
				{
					// some other junk packet
					System.out.println("UNKNOWN");
				}
			}
			catch (IOException e)
			{
				//e.printStackTrace();
				//moreQuotes = false;
			}
		}
		socket.close();
    }




}//Client
